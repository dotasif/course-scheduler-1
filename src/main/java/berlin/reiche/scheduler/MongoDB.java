package berlin.reiche.scheduler;

import java.net.UnknownHostException;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * A static provider of the data store interface to MongoDB.
 * 
 * @author Konrad Reiche
 * 
 */
public class MongoDB {

	private static final String DATABASE_NAME = "course-scheduler";

	private static Mongo mongo;
	private static Morphia morphia;
	private static Datastore datastore;

	/**
	 * Creates a Mongo instance on localhost with port 27017 with a database.
	 */
	static {
		try {
			mongo = new Mongo();
			morphia = new Morphia();
			datastore = morphia.createDatastore(mongo, DATABASE_NAME);
		} catch (UnknownHostException e) {
			System.err.println("The host could not be determined.");
			e.printStackTrace();
		} catch (MongoException e) {
			System.err.println("Something went wrong in Mongo during"
					+ " construction.");
			e.printStackTrace();
		}
	}

	public static Datastore getDatastore() {
		return datastore;
	}

	/**
	 * By performing a dummy request the current connection is checked. A raised
	 * exceptions means there is no connection, otherwise there is a connection.
	 * 
	 * @return whether there is a working connection to the MongoDB server and
	 *         the database as defined in the {@link Datastore}.
	 */
	public static boolean isConnected() {

		try {
			datastore.getDB().getCollectionNames();
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
