package berlin.reiche.virginia.model;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * The equipment of rooms.
 * 
 * @author Konrad Reiche
 * 
 */
@Entity("equipment")
public class Equipment {

    @Id
    ObjectId id;

    /**
     * The items of the equipment.
     */
    List<String> items;

    /**
     * This constructor is used by Morphia via Java reflections.
     */
    @SuppressWarnings("unused")
    private Equipment() {

    }

    /**
     * Default constructor.
     * 
     * @param items
     *            the items of the equipment.
     */
    public Equipment(List<String> items) {
        super();
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

}
