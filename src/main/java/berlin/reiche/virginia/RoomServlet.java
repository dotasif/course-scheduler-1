package berlin.reiche.virginia;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;

import berlin.reiche.virginia.model.Room;
import berlin.reiche.virginia.model.User;

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

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(AppServlet.LOGIN_ATTRIBUTE);
		if (user == null) {
			response.sendRedirect("/login");
			return;
		}

		if (path == null) {
			showRooms(request, response);
		} else if (path.equals("/")) {
			response.sendRedirect("/rooms");
		} else if (path.equals("/new")) {
			data.put(AppServlet.REQUEST_HEADLINE_VAR, "New Room");
			data.put("blankCourse", true);
			AppServlet.processTemplate(ROOM_FORM_SITE, data, writer);
		} else if (path.matches("/edit/" + AppServlet.ID_REGEX)) {
			ObjectId id = new ObjectId(path.substring("/edit/".length()));
			Room room = MongoDB.get(Room.class, id);
			handleRoomModification(request, response, room);
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
	 * Retrieves all rooms and displays them.
	 * 
	 * @param request provides request information for HTTP servlets.
	 * 
	 * @param response
	 *            provides HTTP-specific functionality in sending a response.
	 * @throws IOException
	 *             if an input or output exception occurs.
	 */
	private void showRooms(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		Map<String, Object> data = AppServlet.getDefaultData();
		List<Map<String, String>> roomDataList = new ArrayList<>();

		for (Room room : MongoDB.getAll(Room.class)) {
			Map<String, String> roomData = new TreeMap<>();
			roomData.put("id", room.getId().toString());
			roomData.put("number", room.getNumber());
			roomData.put("name", room.getName());
			roomDataList.add(roomData);
		}

		data.put("rooms", roomDataList);
		AppServlet.processTemplate(ROOMS_SITE, data, response.getWriter());
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
		if (room == null) {
			room = new Room(number, name);
		} else {
			room.setNumber(number);
			room.setName(name);
		}
		MongoDB.store(room);
		response.sendRedirect("/rooms");

	}

	/**
	 * Handles a room modification request.
	 * 
	 * @param request
	 *            provides request information for HTTP servlets.
	 * @param response
	 *            provides HTTP-specific functionality in sending a response.
	 * @param room
	 *            the room which is requested for modification.
	 * @throws IOException
	 *             if an input or output exception occurs.
	 */
	private void handleRoomModification(HttpServletRequest request,
			HttpServletResponse response, Room room) throws IOException {

		Map<String, Object> data = AppServlet.getDefaultData();

		data.put(AppServlet.REQUEST_HEADLINE_VAR, "Edit Room");
		data.put("number", room.getNumber());
		data.put("name", room.getName());

		AppServlet.processTemplate(ROOM_FORM_SITE, data, response.getWriter());
	}

	/**
	 * @return a singleton instance of {@link RoomServlet}.
	 */
	public static RoomServlet getInstance() {
		return instance;
	}

}
