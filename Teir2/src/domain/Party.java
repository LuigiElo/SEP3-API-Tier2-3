package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Party implements Serializable {

    private int partyID;
    private String partyTitle;
    private String location;
    private String description;
    private String date;
    private String time;
    private boolean isPrivate;
    private List<Item> items;
    private List<Person> people;

    public Party() {

    }

    public Party(String partyTitle, String description, String location, int partyID, String date, String time, boolean isPrivate) {
        this.partyTitle = partyTitle;
        this.description = description;
        this.location = location;
        this.partyID = partyID;
        this.date = date;
        this.time = time;
        this.isPrivate = isPrivate;
        items = new ArrayList<>(100);
        people = new ArrayList<>(100);
    }

    public String getPartyTitle() {
        return partyTitle;
    }

    public void setPartyTitle(String partyTitle) {
        this.partyTitle = partyTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Item getItem(int index) {
        return items.get(index);
    }

    public void setItems(Item items, int index) {
        this.items.add(index, items);
    }

    public Person getPerson(int index) {
        return people.get(index);
    }

    public List<Person> getPeople(){return people;}

    public void setPeople(Person person, int index) {
        this.people.add(index, person);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPartyID() {
        return partyID;
    }

    public void setPartyID(int partyID) {
        this.partyID = partyID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setAddress(String address) {
        this.location= location;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


    @Override
    public String toString() {
        return "Party{" +
                "partyID='" + partyID + '\'' +
                ", partyTitle='" + partyTitle + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", items=" + items +
                ", people=" + people +
                '}';
    }
}

