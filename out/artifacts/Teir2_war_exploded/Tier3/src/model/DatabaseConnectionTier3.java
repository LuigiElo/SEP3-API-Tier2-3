package model;


import database.*;
import database.DatabaseCon;
import domain.*;
import domain.Package;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Java class used in reading/receiving and writing/data through the Socket connection established
 * between the different components of the system;
 *
 * It implements the Runnable and DatabaseCon interface;
 */
public class DatabaseConnectionTier3 implements Runnable, DatabaseCon {

    /**
     * Instance of a DatabaseCon object under which the part of the system that will execute the procedure
     * will be wrapped around
     */
    private DatabaseCon database;

    /**
     * Instances used in reading/ receiving the procedure command and writing/sending the procedure result
     * after execution
     */
    private ObjectOutputStream out2;
    private ObjectInputStream in2;

    /**
     * Constructor of the DatabaseConnectionTier3 object;
     * Initiates the DatabaseCon as a DatabaseAccess object;
     */
    public DatabaseConnectionTier3()
    {
        this.database = new DatabaseAccess();
    }

    public DatabaseConnectionTier3(Socket socket) throws IOException {

        in2 = new ObjectInputStream(socket.getInputStream());
        out2 = new ObjectOutputStream(socket.getOutputStream());

    }

    /**
     * Sets the variables used in receiving/sending data through the Socket connection
     * with the data provided by the socket object created upon the establishment of a
     * Socket connection;
     * @param socket
     * @throws IOException
     */
    public void setSocket(Socket socket) throws IOException {
        in2 = new ObjectInputStream(socket.getInputStream());
        out2 = new ObjectOutputStream(socket.getOutputStream());
    }


    /**
     * Method that establishes a connection to the database component of the system using
     * the JDBS technology
     * @return Connection
     * @throws SQLException
     */
    @Override
    public Connection connect() throws SQLException {
        return database.connect();
    }

    /**
     * Method that closes a connection to a database component of the system using
     * the JDBS technology
     * @throws SQLException
     */
    @Override
    public void close() throws SQLException {
        database.close();
    }

    /**
     * References the DatabaseCon for retrieving a List<Party></> that met the criteria specified by the
     * parameter
     * @param something
     * @return List<Party></>
     * @throws SQLException
     */
    @Override
    public List<Party> getPartiesBySomething(String something) throws SQLException {
        return database.getPartiesBySomething(something);
    }

    /**
     * References the DatabaseCon for modifying the privacy property of a specific Party
     * @param privacy
     * @param party
     * @return String
     * @throws SQLException
     */
    @Override
    public String setPartyPrivacy(boolean privacy, Party party) throws SQLException {
       return database.setPartyPrivacy(privacy,party);
    }

    /**
     * References the DatabaseCon variable for retrieving a Party object from the system having
     * the specified id equal to the parameter
     * @param partyID
     * @return Party
     * @throws SQLException
     */
    @Override
    public Party getParty(int partyID) throws SQLException {
        return database.getParty(partyID);
    }

    /**
     * References the DatabaseCon variable for retrieving the participants (List<Person></Person>) for a
     * certain Party specified by the parameter
     * @param partyID
     * @return List<Person></Person>
     * @throws SQLException
     */
    @Override
    public List<Person> getParticipants(int partyID) throws SQLException {
        return database.getParticipants(partyID);
    }

    /**
     * References the DatabaseCon variable for retrieving the list of items (List<Item></Item>) for a
     * certain Party equal to the parameter
     * @param party
     * @return List<Item></Item>
     * @throws SQLException
     */
    @Override
    public List<Item> getItems(Party party) throws SQLException {
        return database.getItems(party);
    }

    /**
     * References the DatabaseCon variable for retrieving a list of people that meet the criteria
     * defined by the parameter
     * @param name
     * @return List<Person></Person>
     * @throws SQLException
     */
    @Override
    public List<Person> getPeopleByName(String name) throws SQLException {
        return database.getPeopleByName(name);  ///it's just by smth
    }


    /***
     * References the DatabaseCon variable for retrieving the Person object from the system
     * with a matching id to the parameter
     * @param personID
     * @return Person
     * @throws SQLException
     */
    @Override
    public Person getPersonByID(int personID) throws SQLException {
        return getPersonByID(personID);
    }

    /***
     * References the DatabaseCon variable to add a new user/Person to the system.
     * The method returns the Person object resulted after insertion (with only persistence generated data)
     * @param person
     * @return Person
     * @throws SQLException
     */
    @Override
    public Person createPerson(Person person) throws SQLException {
        database.createPerson(person);
        return person;
    }

    /**
     * References the DatabaseCon variable to add a new item to the system;
     * The method returns the Item object obtain after insertion (with only persistence generated data)
     * @param item
     * @return Item
     * @throws SQLException
     */
    @Override
    public Item createItem(Item item) throws SQLException {
        return database.createItem(item);
    }

    /**
     * References the DatabaseCon variable to modify a Party object from the system;
     * The method return the Party object obtained after the execution of the command;
     * @param party
     * @return Party
     * @throws SQLException
     */
    @Override
    public Party updateParty(Party party) throws SQLException {
        return database.updateParty(party);
    }

    /**
     * References the DatabaseCon variable to modify the characteristics of a Person object
     * from the system;
     * The method return the Person object obtained after the execution of the command
     * @param person
     * @throws SQLException
     */
    @Override
    public void updatePerson(Person person) throws SQLException {
        database.updatePerson(person);
    }

    /**
     * References the DatabaseCon variable to create a new Party and insert it in the system data;
     * The method returns the Party object obtained after the insertion (contains persistence only generated
     * data)
     * @param party
     * @return Party
     * @throws SQLException
     */
    public Party createParty(Party party) throws SQLException {

        Party party1 = database.createParty(party);

        return party1;
    }

    /**
     * References the DatabaseCon variable to retrieve a Person object matching the credentials of
     * the Person parameter
     * @param person
     * @return Person
     * @throws SQLException
     */
    @Override
    public Person login(Person person) throws SQLException {
        Person person1 = database.login(person);
        return person1;
    }

    /**
     * References the DatabaseCon variable to retrieve the List<Party></Party> in which
     * the Person given as parameter participates in
     * @param person
     * @return List<Party></Party>
     */
    public List<Party> getPartiesForPerson(Person person) {
        List<Party> parties = database.getPartiesForPerson(person);
        return parties;
    }

    /**
     * References the DatabaseCon variable to retrieve the List<Item></Item> for a specific
     * Party specified through the parameter
     * @param partyId
     * @return List<Item></Item>
     * @throws Exception
     */
    public List<Item> getItems(int partyId) throws Exception {
        return database.getItems(partyId);
    }

    /**
     * References the DatabaseCon variable to add the List<Item></Item> parameter to the Party parameter
     * in the system;
     * The method returns a string reflecting the success of the procedure;
     * @param items
     * @param party
     * @return String
     */
    @Override
    public String addItems(List<Item> items, Party party) {
         return database.addItems(items, party);
    }

    /**
     * References the DatabaseCon variable to remove the List<Item> from the Party specified as
     * parameter from the system;
     * The method returns a string reflecting the success of the procedure;
     * @param items
     * @param party
     * @return String
     */
    @Override
    public String removeItems(List<Item> items, Party party) {
        return database.removeItems(items, party);
    }

    /**
     * References the DatabaseCon variable to add the List<Person></Person> parameter to the Party parameter
     * in the system;
     * The method returns a string reflecting the success of the procedure;
     * @param people
     * @param party
     * @return String
     */
    @Override
    public String addPeople(List<Person> people, Party party) {
        return database.addPeople(people, party);
    }

    /**
     * References the DatabaseCon variable to remove the List<Person></Person> parameter to the Party parameter
     * in the system;
     * The method returns a string reflecting the success of the procedure;
     * @param people
     * @param party
     * @return String
     */
    @Override
    public String removePeople(List<Person> people, Party party) {
        return database.removePeople(people, party);
    }

    /**
     * References the DatabaseCon variable to create an invitation using each Person object
     * in the List<Person></Person> parameter for the Party object specified as parameter
     *
     * The method returns a string reflecting the success of the procedure;
     * @param people
     * @param party
     * @return String
     */
    @Override
    public String makeInvitations(List<Person> people, Party party) {
        return database.makeInvitations(people, party);
    }

    /**
     * References the DatabaseCon variable to retrieve the invitations of a user/Person
     * specified through the parameter
     * @param personID
     * @return List<Invitation></Invitation>
     */
    @Override
    public List<Invitation> getInvitations(int personID) {
        return database.getInvitations(personID);
    }

    /**
     * References the DatabaseCon variable to change the status of the Invitation parameter
     * in the system to "accepted";
     * The method returns a string reflecting the success of the procedure;
     * @param invitation
     * @return String
     */
    @Override
    public String acceptInvite(Invitation invitation) {
        return database.acceptInvite(invitation);
    }

    /**
     * References the DatabaseCon variable to change the status of the Invitation parameter
     * in the system to "declined";
     * The method returns a string reflecting the success of the procedure;
     * @param invitation
     * @return String
     */
    @Override
    public String declineInvite(Invitation invitation) {
        return database.declineInvite(invitation);
    }

    /**
     * Method inherited from the Runnable interface;
     * Called when a Socket connection is being established;
     *
     * A Package is being read/received using the ObjectInputStream variable;
     * In a switch the command property of the Package is being identified.
     * Depending on the command the corresponding and needed objects are retrieved from the Package and
     * the action intended is being executed referencing teh DatabaseCon object;
     *
     * In case the command of teh Package cannot be identified thw method will throw a new Exception
     *
     */
    @Override
    public void run() {

        try {
            Package packageR = (Package) in2.readObject();


            switch (packageR.getCommand())
            {
                case "createParty":
                {

                    Party party = packageR.getParties().get(0);
                    Party returnParty = createParty(party);

                    System.out.println("This is the party i got through sockets");
                    System.out.println(party.toString());

                    out2.writeObject(returnParty);
                    break;
                }

                case "searchPBySmth":
                {

                    String smth = packageR.getStrings().get(0);
                    List<Person> list = getPeopleByName(smth);
                    out2.writeObject(list);
                    break;
                }
                case "getItemsForParty":
                {
                    int partyId = packageR.getParties().get(0).getPartyID();
                    List<Item> list = getItems(partyId);
                    out2.writeObject(list);
                    break;

                }

                case "getParty":
                {
                    Party party = packageR.getParties().get(0);
                    Party afterChanges = database.getParty(party.getPartyID());
                    out2.writeObject(afterChanges);
                    break;

                }

                case "registerPerson":
                {
                    Person person = packageR.getPeople().get(0);

                    System.out.println("This is the person i got through sockets");
                    System.out.println(person.toString());

                    Person person1 = createPerson(person);
                    out2.writeObject(person1);
                    break;

                }

                case "login":
                {
                    Person person = packageR.getPeople().get(0);
                    Person person1 = login(person);
                    out2.writeObject(person1);
                    break;
                }
                case "getPartiesForPerson":
                {
                    Person person = packageR.getPeople().get(0);
                    List<Party> parties = getPartiesForPerson(person);
                    out2.writeObject(parties);
                    break;
                }

                case "updatePartyD":
                {
                    Party party = packageR.getParties().get(0);
                    Party party1 = database.updateParty(party);
                    out2.writeObject(party1);
                    break;
                }
                case "addItems":
                {
                    Party party = packageR.getParties().get(0);
                    List<Item> items = packageR.getItems();

                    String result = database.addItems(items, party);
                    out2.writeObject(result);
                    break;
                }
                case "removeItems":
                {
                    Party party = packageR.getParties().get(0);
                    List<Item> items = packageR.getItems();

                    String result = database.removeItems(items, party);
                    out2.writeObject(result);
                    break;
                }
                case "addPeople":
                {
                    System.out.println("!!!!!11111");
                    Party party = packageR.getParties().get(0);
                    List<Person> people = packageR.getPeople();
                    System.out.println(people.size()+ "is the size of people to be added");

                    String result = database.addPeople(people, party);
                    out2.writeObject(result);
                    break;
                }
                case "removePeople":
                {
                    Party party = packageR.getParties().get(0);
                    List<Person> people = packageR.getPeople();

                    String result = database.removePeople(people, party);
                    out2.writeObject(result);
                    break;
                }
                case "makeInvitation":
                {
                    Party party = packageR.getParties().get(0);
                    List<Person> people = packageR.getPeople();

                    String result = database.makeInvitations(people, party);
                    out2.writeObject(result);
                    break;
                }
                case "getInvitations":
                {
                    Person person = packageR.getPeople().get(0);
                    int personID = person.getPersonID();

                    List<Invitation> invitations = database.getInvitations(personID);
                    out2.writeObject(invitations);
                    break;
                }
                case "acceptInvite":
                {
                    Invitation invitation = packageR.getInvitation();

                    String result = database.acceptInvite(invitation);
                    out2.writeObject(result);
                    break;
                }
                case "declineInvite":
                {
                    Invitation invitation = packageR.getInvitation();

                    String result = database.declineInvite(invitation);
                    out2.writeObject(result);
                    break;
                }
                default:{
                    System.out.println("glueeeee");
                    throw new Exception("I don't know what you want");
                    }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}