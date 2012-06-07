package berlin.reiche.virginia;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import berlin.reiche.virginia.model.Equipment;

/**
 * The equipment servlet is dedicated to access the settings for configuring
 * the available equipment for rooms and courses to request upon.
 * 
 * @author Konrad Reiche
 * 
 */
@SuppressWarnings("serial")
public class EquipmentServlet extends HttpServlet {

    /**
     * File path to the web resources.
     */
    private static final String EQUIPMENT_SITE = "ftl/equipment/form.ftl";

    /**
     * Singleton instance.
     */
    private static final EquipmentServlet INSTANCE = new EquipmentServlet();

    public final static String root = "/equipment";
    
    /**
     * The constructor is private in order to enforce the singleton pattern.
     */
    private EquipmentServlet() {

    }

    /**
     * Parses the HTTP request and writes the response by using the template
     * engine.
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> data = AppServlet.getDefaultData();
        Writer writer = response.getWriter();
        AppServlet.checkAccessRights(request, response, root);

        Equipment equipment = MongoDB.get(Equipment.class);
        data.put("equipment", equipment);
        AppServlet.processTemplate(EQUIPMENT_SITE, data, writer);
    }

    /**
     * Parses all user HTML form requests and handles them.
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String[] items = request.getParameter("equipment").split("\n");
        Equipment equipment = MongoDB.get(Equipment.class);
        equipment.getItems().clear();
        equipment.getItems().addAll(Arrays.asList(items));
        MongoDB.store(equipment);
        response.sendRedirect("/");
    }

    /**
     * @return a singleton instance of {@link RoomServlet}.
     */
    public static EquipmentServlet getInstance() {
        return INSTANCE;
    }

}
