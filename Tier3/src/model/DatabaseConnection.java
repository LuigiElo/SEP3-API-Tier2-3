
package model;





import database.DatabaseAccess;
import database.DatabaseCon;
import domain.*;
import domain.Package;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DatabaseConnection implements Runnable,DatabaseCon {

    //shouldn't be needed
    /*private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    */
    private DatabaseCon database;

    private BufferedReader in;
    private PrintWriter out;

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
    public Party getParty(String partyID) throws SQLException {
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
        return database.getPeopleByName(name);
    }

    @Override
    public void addParticipant(Person person, Party party) throws SQLException {
        database.addParticipant(person,party);
    }

    @Override
    public void addItem(Item item, Party party) throws SQLException {
        database.addItem(item,party);
    }

    @Override
    public void createPerson(Person person) throws SQLException {
        database.createPerson(person);
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

        database.createParty(party);
        return party;
    }

    @Override
    public void run() {

        try {
            Package packageR = (Package) in2.readObject();


            switch (packageR.getCommand())
            {
                case "createParty":
                    {

                        Object[]  objects = packageR.getObjects();
                        Party party = (Party) objects[0];
                        Party returnParty = createParty(party);
                        out2.writeObject(returnParty);
                        break;
                    }

                case "getPeopleByName":
                {

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
                case "createPerson":
                {

                }
                case "addItem":
                {

                }
                case "addParticipant":
                {

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
                default: return;
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


