package domain;

import java.io.Serializable;
import java.util.List;

public class BoxTier2 implements Serializable {

    private Party party;

    private List<Item> itemsAdded;
    private List<Item> itemsRemoved;

    private List<Person> peopleAdded;
    private List<Person> peopleRemoved;

    public BoxTier2() {
    }

    public BoxTier2(Party party, List<Item> itemsAdded, List<Item> itemsRemoved, List<Person> peopleAdded, List<Person> peopleRemoved) {
        this.party = party;
        this.itemsAdded = itemsAdded;
        this.itemsRemoved = itemsRemoved;
        this.peopleAdded = peopleAdded;
        this.peopleRemoved = peopleRemoved;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public List<Item> getItemsAdded() {
        return itemsAdded;
    }

    public void setItemsAdded(List<Item> itemsAdded) {
        this.itemsAdded = itemsAdded;
    }

    public List<Item> getItemsRemoved() {
        return itemsRemoved;
    }

    public void setItemsRemoved(List<Item> itemsRemoved) {
        this.itemsRemoved = itemsRemoved;
    }

    public List<Person> getPeopleAdded() {
        return peopleAdded;
    }

    public void setPeopleAdded(List<Person> peopleAdded) {
        this.peopleAdded = peopleAdded;
    }
    
    public List<Person> getPeopleRemoved() {
        return peopleRemoved;
    }

    public void setPeopleRemoved(List<Person> peopleRemoved) {
        this.peopleRemoved = peopleRemoved;
    }
}
