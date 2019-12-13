package model;


import database.*;
import database.DatabaseCon;
import domain.Item;
import domain.Package;
import domain.Party;
import domain.Person;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class DatabaseConnection implements Runnable, DatabaseCon {


    private DatabaseCon database;

    private ObjectOutputStream out2;
    private ObjectInputStream in2;

    public DatabaseConnection()
    {
        this.database = new DatabaseAccess();
    }

    public DatabaseConnection(Socket socket) throws IOException {

        in2 = new ObjectInputStream(socket.getInputStream());
        out2 = new ObjectOutputStream(socket.getOutputStream());

    }

    public void setSocket(Socket socket) throws IOException {
        in2 = new ObjectInputStream(socket.getInputStream());
        out2 = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public String addItems(Party party) throws SQLException
    {
        return database.addItems(party);
    }

    @Override
    public String removeItems(Party party) throws SQLException {
        return database.removeItems(party);
    }

    @Override
    public Connection connect() throws SQLException {
        return database.connect();
    }

    @Override
    public void close() throws SQLException {
        database.close();
    }

    @Override
    public List<Party> getPartiesBySomething(String something) throws SQLException {
        return database.getPartiesBySomething(something);
    }

    @Override
    public String setPartyPrivacy(boolean privacy, Party party) throws SQLException {
       return database.setPartyPrivacy(privacy,party);
    }

    @Override
    public Party getParty(int partyID) throws SQLException {
        return database.getParty(partyID);
    }

    @Override
    public List<Person> getParticipants(int partyID) throws SQLException {
        return database.getParticipants(partyID);
    }

    @Override
    public List<Item> getItems(Party party) throws SQLException {
        return database.getItems(party);
    }

    @Override
    public List<Person> getPeopleByName(String name) throws SQLException {
        return database.getPeopleByName(name);  ///it's just by smth
    }

    @Override
    public Party getHost(Party party) throws SQLException {
        return database.getHost(party);
    }

    @Override
    public Person getPersonByID(int personID) throws SQLException {
        return getPersonByID(personID);
    }

    @Override
    public String addParticipant(Person person, Party party) throws SQLException {
       String string = database.addParticipant(person,party);
       return string;
    }

    @Override
    public String addItem(Item item, Party party) throws SQLException {
        return database.addItem(item,party);
    }

    @Override
    public Person createPerson(Person person) throws SQLException {
        database.createPerson(person);
        return person;
    }

    @Override
    public Item createItem(Item item) throws SQLException {
        return database.createItem(item);
    }

    @Override
    public Party updateParty(Party party) throws SQLException {
        return database.updateParty(party);
    }

    @Override
    public void updatePerson(Person person) throws SQLException {
        database.updatePerson(person);
    }

    @Override
    public void removeParticipant(Party party, Person person) throws SQLException {
        database.removeParticipant(party,person);
    }

    @Override
    public String removeItem(Party party, Item item) throws SQLException {
        return database.removeItem(party, item);
    }

    public Party createParty(Party party) throws SQLException {

        Party party1 = database.createParty(party);

        return party1;
    }

    @Override
    public Person login(Person person) throws SQLException {
        Person person1 = database.login(person);
        return person1;
    }

    public List<Party> getPartiesForPerson(Person person) {
        List<Party> parties = database.getPartiesForPerson(person);
        return parties;
    }


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
                case "getItems":
                {

                }
                case "getParticipants":
                {

                }
                case "getParty":
                {

                }
                case "getPartiesBySomething":
                {

                }
                case "createItem":
                {

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
                case "addItem":
                {
                    Party party = packageR.getParties().get(0);
                    Item item = party.getItems().get(party.getItems().size()-1);
                    String result = addItem(item, party);
                    out2.writeObject(result);
                    break;

                }
                case "addPerson":
                {
                    Person person = packageR.getPeople().get(0);
                    Party party = packageR.getParties().get(0);

                     String result = addParticipant(person, party);
                     out2.writeObject(result);
                     break;

                }
                case "updateParty":
                {

                }
                case "removeParticipant":
                {

                }
                case "removeItem":
                {
                    Party party = packageR.getParties().get(0);
                    Item item = party.getItems().get(party.getItems().size()-1);
                    String result = removeItem(party, item);
                    out2.writeObject(result);
                    break;
                }
                case "login":
                {
                    Person person = packageR.getPeople().get(0);
                    Person person1 = login(person);
                    System.out.println(person1.getPersonID()+"!!!!!!!!!! is the id");
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
                case "addLastPersonToParty":
                {
                    Party party = packageR.getParties().get(0);
                    Person person = party.getPerson(party.getPeople().size()-1);
                    String result = addParticipant(person, party);
                    out2.writeObject(result);
                    break;
                }
                case "addItems":
                {
                    Party party = packageR.getParties().get(0);
                    String result = addItems(party);
                    out2.writeObject(result);
                    break;
                }
                case "updatePartyD":
                {
                    Party party = packageR.getParties().get(0);
                    Party party1 = database.updateParty(party);
                    out2.writeObject(party1);
                    break;
                }
                default:{
                    System.out.println("glueeeee");
                    return;}
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Hellllllllllllllo");
        }
    }
}