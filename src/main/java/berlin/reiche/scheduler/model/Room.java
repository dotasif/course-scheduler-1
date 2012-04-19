package berlin.reiche.scheduler.model;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * Represents a room to which courses can be scheduled.
 * 
 * @author Konrad Reiche
 * 
 */
@Entity("room")
public class Room {

    @Id
    ObjectId id;
    String number;
    String name;

    /**
     * This constructor is used by Morphia via Java reflections.
     */
    @SuppressWarnings("unused")
    private Room() {

    }

    /**
     * Creates a new room by assigning the parameters directly, except the id
     * which is generated before saving the object to the database.
     * 
     * @param number
     *            the room number which uniquely identifies the room.
     * @param the
     *            name of the room, for instance used to identify the type of
     *            the room.
     */
    public Room(String number, String name) {
        super();
        this.number = number;
        this.name = name;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
