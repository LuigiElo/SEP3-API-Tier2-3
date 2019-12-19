package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Java class used in receiving complex commands/requests from a Client (user)
 * to modify a Party in which he/she participates; The modifications are dependent on the 
 * variables
 *
 */
public class BoxTier2 implements Serializable {
    /**
     *Property; Represents the Party to which the modifications need to be made
     */
    private Party party;
    /**
     *Properties; Define and name what items need to be added or removed from the Party
     */
    private List<Item> itemsAdded;
    private List<Item> itemsRemoved;
    /**
     *Properties; Define and name which users/Person need to be added or removed from the Party
     */
    private List<Person> peopleAdded;
    private List<Person> peopleRemoved;

    /**
     *Empty constructor for the BoxTier2 object
     */
    public BoxTier2() {

        this.itemsRemoved = new ArrayList<>();
        this.peopleAdded = new ArrayList<>();
        this.peopleRemoved =  new ArrayList<>();

    }

    /**
     *Constructor for a BoxTier2 Object
     * @param party
     * @param itemsAdded
     * @param itemsRemoved
     * @param peopleAdded
     * @param peopleRemoved
     */
    public BoxTier2(Party party, List<Item> itemsAdded, List<Item> itemsRemoved, List<Person> peopleAdded, List<Person> peopleRemoved) {
        this.party = party;
        this.itemsAdded = itemsAdded;
        this.itemsRemoved = itemsRemoved;
        this.peopleAdded = peopleAdded;
        this.peopleRemoved = peopleRemoved;
    }

    /**
     *Getter for the party variable;
     * @return party
     */
    public Party getParty() {
        return party;
    }

    /**
     *Setter for the party variable
     * @param party
     */
    public void setParty(Party party) {
        this.party = party;
    }

    /**
     *Getter for the itemsAdded variable;
     * @return itemsAdded
     */
    public List<Item> getItemsAdded() {
        return itemsAdded;
    }

    /**
     *Setter for the itemsAdded variable
     * @param itemsAdded
     */
    public void setItemsAdded(List<Item> itemsAdded) {
        this.itemsAdded = itemsAdded;
    }

    /**
     *Getter for the itemsRemoved variable;
     * @return itemsRemoved
     */
    public List<Item> getItemsRemoved() {
        return itemsRemoved;
    }

    /**
     *Setter for the itemsRemoved variable
     * @param itemsRemoved
     */
    public void setItemsRemoved(List<Item> itemsRemoved) {
        this.itemsRemoved = itemsRemoved;
    }

    /**
     *Getter for the peopleAdded variable;
     * @return peopleAdded
     */
    public List<Person> getPeopleAdded() {
        return peopleAdded;
    }

    /**
     *Setter for the peopleAdded variable
     * @param peopleAdded
     */
    public void setPeopleAdded(List<Person> peopleAdded) {
        this.peopleAdded = peopleAdded;
    }

    /**
     *Getter for the peopleRemoved variable;
     * @return peopleRemoved
     */
    public List<Person> getPeopleRemoved() {
        return peopleRemoved;
    }

    /**
     *Setter for the peopleRemoved variable
     * @param peopleRemoved
     */
    public void setPeopleRemoved(List<Person> peopleRemoved) {
        this.peopleRemoved = peopleRemoved;
    }
}
