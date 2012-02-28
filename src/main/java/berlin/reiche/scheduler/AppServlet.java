package berlin.reiche.scheduler;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

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

	/**
	 * Singleton instance.
	 */
	private static AppServlet instance = new AppServlet();

	/**
	 * Configuration used for the Freemarker template processing.
	 */
	Configuration configuration;

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

	private void processTemplate(String filename, Map<String, ?> data,
			Writer writer) throws IOException {

		try {
			Template template = configuration.getTemplate(filename);
			template.process(data, writer);
			writer.flush();
		} catch (TemplateException e) {
			System.err.println("The template" + filename
					+ "could not be processed properly.");
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath() + request.getPathInfo();
		Map<String, Object> data = new HashMap<>();
		Writer writer = response.getWriter();

		switch (path) {
		default:
		}

		processTemplate("index.html", data, writer);
	}

	public static AppServlet getInstance() {
		return instance;
	}

}
