package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * java class defining and incapacitating the characteristics of a Party created by the user in the
 * system;
 */
public class Party implements Serializable {
    /**
     *Properties of a Party object; Include:
     * -a unique id;
     * -a title;
     * -a location (String representation)
     * -a description
     * -a date (String representation)
     * -a time (String representation)
     * - a boolean property that defines if a user can join the party freely
     * or it requires the Host permission
     * -a string containing the path to an online Spotify playlist
     * - a Person object that represents the host of the party (has full authority over the object)
     */
    private int partyID;
    private String partyTitle;
    private String location;
    private String description;
    private String date;
    private String time;
    private boolean isPrivate;
    private String playlistURL; //null by default
    private Person host;
    /**
     *Properties of the Party object; Describe the list of items necessary
     * for the party along with it's participants
     */
    private List<Item> items;
    private List<Person> people;

    /**
     *Empty constructor for the Party object
     */
    public Party() {
    }

    /**
     *Constructor of a Party object
     * @param partyTitle
     * @param description
     * @param location
     * @param partyID
     * @param date
     * @param time
     * @param isPrivate
     * @param host
     */
    public Party(String partyTitle, String description, String location, int partyID, String date, String time, boolean isPrivate, Person host) {
        this.partyTitle = partyTitle;
        this.description = description;
        this.location = location;
        this.partyID = partyID;
        this.date = date;
        this.time = time;
        this.isPrivate = isPrivate;
        items = new ArrayList<>(100);
        people = new ArrayList<>(100);
        playlistURL = null;
        this.host = host;
    }

    /**
     *Getter for the partyTitle variable
     * @return partyTitle
     */
    public String getPartyTitle() {
        return partyTitle;
    }

    /**
     *Setter for the partyTile variable
     * @param partyTitle
     */
    public void setPartyTitle(String partyTitle) {
        this.partyTitle = partyTitle;
    }

    /**
     *Getter for the location variable
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     *Setter for the location variable
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *Getter for a specific Item object variable from the List<Item></Item>
     * @param index
     * @return Item
     */
    public Item getItem(int index) {
        return items.get(index);
    }

    /**
     *Sets an Item object in the List<Item></Item> on a specific index
     * @param items
     * @param index
     */
    public void setItems(Item items, int index) {
        this.items.add(index, items);
    }

    /**
     *Getter for a Person object from the List<Person></Person> variable on a specific index
     * @param index
     * @return Person
     */
    public Person getPerson(int index) {
        return people.get(index);
    }

    /**
     *Getter for the people variable
     * @return people
     */
    public List<Person> getPeople(){return people;}

    /**
     * Sets a Person object in the List<Person></> on a specific index
     * @param person
     * @param index
     */
    public void setPeople(Person person, int index) {
        this.people.add(index, person);
    }

    /**
     * Setter for the people variable
     * @param people
     */
    public void setPeople(List<Person> people) {this.people = people;}

    /**
     *Getter for the date variable
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     *Setter for the date variable
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *Getter for the time variable
     * @return time
     */
    public String getTime() {
        return time;
    }

    /**
     * Setter for the time variable
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /***
     *Getter for the partyID variable
     * @return partyID
     */
    public int getPartyID() {
        return partyID;
    }

    /**
     *Setter for the partyID variable
     * @param partyID
     */
    public void setPartyID(int partyID) {
        this.partyID = partyID;
    }

    /**
     *Getter for the description variable
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     *Setter for the description variable
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *Getter for the isPrivate variable
     * @return isPrivate
     */
    public boolean isPrivate() {
        return isPrivate;
    }

    /**
     *Setter for the isPrivate variable
     * @param aPrivate
     */
    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    /**
     * Getter for the location variable
     * @param address
     */
    public void setAddress(String address) {
        this.location= location;
    }

    /**
     *Getter for the items variable
     * @return items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     *Setter for the items variable
     * @param items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * Getter for the playlistURL variable
     * @return playlistURL
     */
    public String getPlaylistURL() {
        return playlistURL;
    }

    /**
     *Setter for the playlistURL variable
     * @param playlistURL
     */
    public void setPlaylistURL(String playlistURL) {
        this.playlistURL = playlistURL;
    }

    /**
     * Getter for the host variable
     * @return host
     */
    public Person getHost() {
        return host;
    }

    /**
     * Setter for the host variable
     * @param host
     */
    public void setHost(Person host) {
        this.host = host;
    }

    /**
     *Returns a String representation of Party object
     * @return
     */
    @Override
    public String toString() {
        return "Party{" +
                "partyID='" + partyID + '\'' +
                ", partyTitle='" + partyTitle + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", isPrivate='" + isPrivate +'\''+
                ", items=" + items +
                ", people=" + people +
                '}';
    }
}

