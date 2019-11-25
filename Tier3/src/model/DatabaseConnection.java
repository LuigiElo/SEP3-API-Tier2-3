package model;


import database.DatabaseAccess;
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

    public DatabaseConnection(Socket socket) throws IOException {


        in2 = new ObjectInputStream(socket.getInputStream());
        out2 = new ObjectOutputStream(socket.getOutputStream());

        this.database = new DatabaseAccess();

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
    public Party getParty(int partyID) throws SQLException {
        return database.getParty(partyID);
    }

    @Override
    public List<Person> getParticipants(String partyID) throws SQLException {
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
    public String addParticipant(Person person, Party party) throws SQLException {
       String string = database.addParticipant(person,party);
       return string;
    }

    @Override
    public void addItem(Item item, Party party) throws SQLException {
        database.addItem(item,party);
    }

    @Override
    public Person createPerson(Person person) throws SQLException {
        database.createPerson(person);
        return person;
    }

    @Override
    public void createItem(Item item) throws SQLException {
        database.createItem(item);
    }

    @Override
    public void updateParty(Party party) throws SQLException {
        database.updateParty(party);
    }

    @Override
    public void removeParticipant(Party party, Person person) throws SQLException {
        database.removeParticipant(party,person);
    }

    @Override
    public void removeItem(Party party, Item item) throws SQLException {
        database.removeItem(party, item);
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
                    Person person1 = createPerson(person);
                    out2.writeObject(person1);

                }
                case "addItem":
                {

                }
                case "addPerson":
                {
                    Person person = packageR.getPeople().get(0);
                    Party party = packageR.getParties().get(0);

                     String result = addParticipant(person, party);
                     out2.writeObject(result);

                }
                case "updateParty":
                {

                }
                case "removeParticipant":
                {

                }
                case "removeItem":
                {

                }
                case "login":
                {
                    Person person = packageR.getPeople().get(0);
                    Person person1 = login(person);
                    out2.writeObject(person1);
                }
                case "getPartiesForPerson":
                {
                    Person person = packageR.getPeople().get(0);
                    List<Party> parties = getPartiesForPerson(person);
                    out2.writeObject(parties);
                }
                case "addLastPersonToParty":
                {
                    Party party = packageR.getParties().get(0);
                    Person person = party.getPerson(party.getPeople().size()-1);
                    String result = addParticipant(person, party);
                    out2.writeObject(result);
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