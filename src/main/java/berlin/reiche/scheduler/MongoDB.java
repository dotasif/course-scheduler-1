package berlin.reiche.scheduler;

import java.net.UnknownHostException;
import java.util.List;

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
            datastore.ensureIndexes();
            datastore.ensureCaps();
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

    /**
     * Gets an entity of the database identified by its unique identifier.
     * 
     * @param cls
     *            the class type of the entity to retrieve.
     * @param id
     *            the unique identifier of the entity
     * @return the model object representing the entity.
     */
    public static <T, V> T get(Class<T> cls, V id) {
        return datastore.get(cls, id);
    }

    /**
     * Gets a list of all entities of a certain type.
     * 
     * @param cls
     *            the class type of the entity to retrieve.
     * @return the list of model objects representing the entities.
     */
    public static <T, V> List<T> getAll(Class<T> cls) {
        return datastore.find(cls).asList();
    }

    /**
     * Stores a model object as entity in the database.
     * 
     * @param entity
     *            the model object representing the entity.
     * @return the {@link String} representation of the entities key.
     */
    public static <T> Object store(T entity) {
        return datastore.save(entity).toString();
    }

    /**
     * Deletes a certain entity identified by its unique identifier.
     * 
     * @param cls
     *            the class type of the entity to retrieve.
     * @param id
     *            the unique identifier of the entity
     */
    public static <T, V> void delete(Class<T> cls, V id) {
        datastore.delete(cls, id);
    }
}
