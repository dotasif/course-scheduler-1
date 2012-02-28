package berlin.reiche.scheduler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * The main servlet of the application which handles all incoming HTTP requests.
 * 
 * @author Konrad Reiche
 * 
 */
@SuppressWarnings("serial")
public class AppServlet extends HttpServlet {

	/**
	 * File path to the web resources.
	 */
	private static final String WEB_PATH = "site/";

	private static final String DEFAULT_VALUES_PATH = "site/resources/defaultValues.properties";

	/**
	 * Singleton instance.
	 */
	private static AppServlet instance = new AppServlet();

	/**
	 * Configuration used for the Freemarker template processing.
	 */
	Configuration configuration;

	/**
	 * The constructor is private in order to enforce the singleton pattern. The
	 * configuration for the template engine Freemarker is set up with default
	 * settings.
	 */
	private AppServlet() {

		try {
			configuration = new Configuration();
			configuration.setDirectoryForTemplateLoading(new File(WEB_PATH));
			configuration.setObjectWrapper(new DefaultObjectWrapper());
		} catch (IOException e) {
			System.err.println("The path " + WEB_PATH
					+ " could not be retrieved.");
			e.printStackTrace();
		}
	}

	/**
	 * Processed the given template file with the provided data. The result is
	 * written immediately to the given writer.
	 * 
	 * @param relativePath
	 *            the relative path from the base directory pointing to the
	 *            template file.
	 * @param data
	 *            the root node of the data model.
	 * @param writer
	 *            the writer to which the processed template is written.
	 * @throws IOException
	 *             if an I/O exception occurs due to writing or flushing.
	 */
	private void processTemplate(String relativePath, Map<String, ?> data,
			Writer writer) throws IOException {

		try {
			Template template = configuration.getTemplate(relativePath);
			template.process(data, writer);
			writer.flush();
		} catch (TemplateException e) {
			System.err.println("The template" + relativePath
					+ "could not be processed properly.");
			e.printStackTrace();
		}
	}

	/**
	 * Parses the HTTP request and writes the response by using the template
	 * engine.
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath() + request.getPathInfo();
		Map<String, ?> data = AppServlet.getDefaultData();
		Writer writer = response.getWriter();

		switch (path) {
		case "/":
			response.sendRedirect("/login");
			break;
		case "/login":
			processTemplate("ftl/login.ftl", data, writer);
			break;
		default:
		}
	}

	/**
	 * Generates a data model out of the default value property file.
	 * 
	 * @return the data model.
	 * @throws IOException
	 *             if the file with the default value properties could not been
	 *             found or an error occurred during reading from it.
	 * 
	 */
	private static Map<String, ?> getDefaultData() throws IOException {

		Map<String, Object> defaultData = new TreeMap<>();
		Properties defaultValues = new Properties();
		FileInputStream input = new FileInputStream(DEFAULT_VALUES_PATH);
		defaultValues.load(input);
		for (Entry<Object, Object> entry : defaultValues.entrySet()) {
			defaultData.put((String) entry.getKey(), (String) entry.getValue());
		}

		return defaultData;
	}

	/**
	 * @return an instance of {@link AppServlet}.
	 */
	public static AppServlet getInstance() {
		return instance;
	}

}
