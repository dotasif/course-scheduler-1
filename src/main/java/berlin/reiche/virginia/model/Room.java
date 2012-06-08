package berlin.reiche.virginia.model;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;

/**
 * Represents a room to which courses can be scheduled.
 * 
 * @author Konrad Reiche
 * 
 */
@Entity("room")
public class Room implements Comparable<Room> {

    @Id
    ObjectId id;
    String number;
    String name;

    /**
     * The equipment and its quantity offered by the room.
     */
    @Reference
    Map<String, Integer> equipment = new HashMap<>();

    /**
     * Null object for HTML form purposes.
     */
    public static final Room NULL_ROOM = new Room(null, null);

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
     * @param name
     *            the name of the room, for instance used to identify the type
     *            of the room.
     */
    public Room(String number, String name) {
        super();
        this.number = number;
        this.name = name;
    }

    public Map<String, Integer> getEquipment() {
        return equipment;
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

    /**
     * Makes the rooms comparable in order to return the same ordering every
     * time when accessed.
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Room o) {
        return id.compareTo(o.id);
    }
    
    /**
     * String representation of the room object. If number of name contain a
     * <code>.</code> it is removed, since it is an illegal character to store
     * into the database.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String representation = number + " (" + name + ")";
        return representation.replace(".", "");
    }

}
