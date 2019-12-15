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

    public List<Item> getItems(int partyId) throws Exception {
        return database.getItems(partyId);
    }

    @Override
    public String addItems(List<Item> items, Party party) {
         return database.addItems(items, party);
    }

    @Override
    public String removeItems(List<Item> items, Party party) {
        return database.removeItems(items, party);
    }

    @Override
    public String addPeople(List<Person> people, Party party) {
        return database.addPeople(people, party);
    }

    @Override
    public String removePeople(List<Person> people, Party party) {
        return database.removePeople(people, party);
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
                case "getItemsForParty":
                {
                    int partyId = packageR.getParties().get(0).getPartyID();
                    List<Item> list = getItems(partyId);
                    out2.writeObject(list);
                    break;

                }
                case "getParticipants":
                {

                }
                case "getParty":
                {
                    Party party = packageR.getParties().get(0);
                    Party afterChanges = database.getParty(party.getPartyID());
                    out2.writeObject(afterChanges);
                    break;

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

                case "updateParty":
                {

                }
                case "removeParticipant":
                {

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
                    Party party = packageR.getParties().get(0);
                    List<Person> people = packageR.getPeople();

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}