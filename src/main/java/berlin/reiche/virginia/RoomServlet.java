package berlin.reiche.virginia;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;

import berlin.reiche.virginia.model.Room;

/**
 * The room servlet which is dedicated for all requests regarding rooms.
 * 
 * @author Konrad Reiche
 * 
 */
@SuppressWarnings("serial")
public class RoomServlet extends HttpServlet {

    /**
     * File path to the web resources.
     */
    private static final String ROOMS_SITE = "ftl/rooms/list.ftl";
    private static final String ROOM_FORM_SITE = "ftl/rooms/form.ftl";

    /**
     * Singleton instance.
     */
    private static RoomServlet instance = new RoomServlet();

    public final static String root = "/rooms";

    /**
     * The constructor is private in order to enforce the singleton pattern.
     */
    private RoomServlet() {

    }

    /**
     * Parses the HTTP request and writes the response by using the template
     * engine.
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String path = request.getPathInfo();
        Map<String, Object> data = AppServlet.getDefaultData();
        Writer writer = response.getWriter();

        if (path == null) {
            data.put("rooms", MongoDB.getAll(Room.class));
            AppServlet.processTemplate(ROOMS_SITE, data, response.getWriter());
        } else if (path.equals("/")) {
            response.sendRedirect("/rooms");
        } else if (path.equals("/new")) {
            data.put(AppServlet.REQUEST_HEADLINE_VAR, "New Room");
            data.put("room", Room.NULL_ROOM);
            AppServlet.processTemplate(ROOM_FORM_SITE, data, writer);
        } else if (path.matches("/edit/" + AppServlet.ID_REGEX)) {
            ObjectId id = new ObjectId(path.substring("/edit/".length()));
            data.put(AppServlet.REQUEST_HEADLINE_VAR, "Edit Room");
            data.put("room", MongoDB.get(Room.class, id));
            AppServlet.processTemplate(ROOM_FORM_SITE, data,
                    response.getWriter());
        } else if (path.matches("/delete/" + AppServlet.ID_REGEX)) {
            ObjectId id = new ObjectId(path.substring("/delete/".length()));
            MongoDB.delete(Room.class, id);
            response.sendRedirect("/rooms");
        } else {
            AppServlet.processTemplate(AppServlet.NOT_FOUND_SITE, data, writer);
        }
    }

    /**
     * Parses all user HTML form requests and handles them.
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String path = request.getPathInfo();
        if ("/new".equals(path)) {
            handleRoomForm(request, response, null);
        } else if (path.matches("/edit/" + AppServlet.ID_REGEX)) {
            ObjectId id = new ObjectId(path.substring("/edit/".length()));
            Room room = MongoDB.get(Room.class, id);
            handleRoomForm(request, response, room);
        }
    }

    /**
     * Handles a room creation and modification requests.
     * 
     * @param request
     *            provides request information for HTTP servlets.
     * @param response
     *            provides HTTP-specific functionality in sending a response.
     * @param room
     *            The room object if it is present, if it is present this is an
     *            entity modification request, else it is an entity creation
     *            request.
     * @throws IOException
     *             if an input or output exception occurs.
     */
    private void handleRoomForm(HttpServletRequest request,
            HttpServletResponse response, Room room) throws IOException {

        String number = request.getParameter("number");
        String name = request.getParameter("name");
        String[] items = request.getParameterValues("item");
        String[] quantities = request.getParameterValues("quantity");

        if (room == null) {
            room = new Room(number, name);
        } else {
            room.setNumber(number);
            room.setName(name);
        }

        for (int i = 0; i < items.length; i++) {
            String constraint = items[i];
            int quantity = Integer.valueOf(quantities[i]);
            room.getEquipment().put(constraint, quantity);
        }
        MongoDB.store(room);
        response.sendRedirect("/rooms");

    }

    /**
     * @return a singleton instance of {@link RoomServlet}.
     */
    public static RoomServlet getInstance() {
        return instance;
    }

}
