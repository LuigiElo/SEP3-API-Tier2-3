package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * java class used in the communication between tier 2 and tier 3 of the application
 * The purpose of the Package object is to encapsulate the objects/ information required
 * for addressing one request of the client that will have to be resolved and registered in
 * the database
 */
public class Package implements Serializable {

    /**
     * The command of the package.
     * Provides information regarding what should be executated with the objects passed through the package
     */
    private String command;

    /**
     * The information contained by the package.
     * They can be null depending on the case/request
     */

    private List<Party> parties;
    private List<Person> people;
    private List<Item> items;
    private List<String> strings;
    private Invitation invitation;



    /**
     * empthy constructor for the Package object.
     * Used in case of complex requests like assigning one item to a person. The Package should have a
     * command , a list of Person, containing one person, and an item list, containing one or more items.
     */
    public Package()
    {
        this.command ="";
        this.parties = new ArrayList<Party>();
        this.people = new ArrayList<Person>();
        this.items = new ArrayList<Item>();
        this.strings = new ArrayList<String>();
        this.invitation = null;
    }


    /**
     * @param command
     * @param parties
     * @param people
     * @param items
*
*  Constructor of the Package class. It takes a string parameter for the command.
*  The rest of the parameters are lists of possible objects that would summaries a request
*  of the client.
*  In the case of creating a party the Package will have the following form: a command "createParty" and a List<Party> containing
     */

    public Package(String command, List<Party> parties, List<Person> people, List<Item> items, List<String> strings)
    {
        this.command = command;
        this.parties = parties;
        this.people = people;
        this.items = items;
        this.strings = strings;

    }


    /**
     * Getter for the command of the package
     * @return command (String)
     */
    public String getCommand() {
        return command;
    }

    /**
     * Setter for the command of the package
     * @param command
     */
    public void setCommand(String command) {
        this.command = command;
    }


    /**Getter for the List<Party> variable
     * @return parties;
     */
    public List<Party> getParties() {
        return parties;
    }

    /**
     * Setter for the List<Party> variable
     * @param parties
     */
    public void setParties(List<Party> parties) {
        this.parties = parties;
    }

    /**
     * Getter for the List<Person> variable
     * @return people
     */
    public List<Person> getPeople() {
        return people;
    }

    /**
     * Setter for the List<Person> variable
     * @param people
     */
    public void setPeople(List<Person> people) {
        this.people = people;
    }

    /**
     * Setter for the List<Item> variable
     * @return items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Setter for the List<Item> variable
     * @param items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * Method that adds a party to the List<Party>
     * @param party
     */
    public void addParty(Party party)
    {
        parties.add(party);
    }

    /**
     * Method that removes a party from the List<Party>
     * @param party
     */
    public void removeParty(Party party)
    {
        parties.remove(party);
    }

    /**
     * Method that adds a person object to the List<Person>
     * @param person
     */
    public void addPerson(Person person)
    {
        people.add(person);
    }

    /**
     * Method that removes a person object from the List<Person>
     * @param person
     */
    public void removePerson(Person person) { people.remove(person);
    }

    /**
     * Method that adds an item to the List<Item>
     * @param item
     */
    public void addItem(Item item)
    {
        items.add(item);
    }

    /**
     * Method that removes an item from the List<Item>     * @param item
     */
    public void removeItem(Item item)
    {
        items.remove(item);
    }

    /**
     *Getter for the List<String></> variable
     * @return
     */
    public List<String> getStrings() {
        return strings;
    }

    /**
     *Setter for the List<String></String> variable
     * @param strings
     */
    public void setStrings(List<String> strings)
    {
        this.strings = strings;
    }

    /**
     *Adds a string object to the List<String></String>
     * @param string
     */
    public void addString(String string)
    {
        strings.add(string);
    }

    /**
     *Removes a string object from the List<String></String>
     * @param string
     */
    public void removeString(String string)
    {
        strings.remove(string);
    }

    /**
     *Getter for the invitation variable
     * @return invitation
     */
    public Invitation getInvitation() {
        return invitation;
    }

    /**
     *Setter for the invitation variable
     * @param invitation
     */
    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
    }

}