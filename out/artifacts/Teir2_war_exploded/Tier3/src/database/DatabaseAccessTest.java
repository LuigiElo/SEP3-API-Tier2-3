package database;

import domain.Item;
import domain.Party;
import domain.Person;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseAccessTest {

    DatabaseCon database = new DatabaseAccess();
    Person person = new Person(0,"Name", "Username", "user@FU.com", "password", false);
    Party party = new Party();

    @org.junit.Test
    public void connect() throws SQLException {
        DatabaseCon database = new DatabaseAccess();
        Connection connection1 = database.connect();
        Connection connection2 = DriverManager.getConnection
                ("jdbc:postgresql://localhost:5432/postgres", "postgres","postgres");
        assertEquals(connection1,connection2);
    }

    @org.junit.Test
    public void close() throws SQLException {
        DatabaseCon database = new DatabaseAccess();
        Connection connection1 = database.connect();
        connection1.close();
        assertNull(connection1);
    }

    @org.junit.Test
    public List<Party> getPartiesBySomething() {
        return null;
    }

    @org.junit.Test
    public void getParty() {

        try {
            database.connect();
            database.createParty(party);
            assertEquals(database.getParty(1).getPartyID(), 1);
        }
        catch (SQLException e) {
            e.printStackTrace();
            fail("dunno, doesn't work");
        }
    }

    @org.junit.Test
    public void getParticipants() {
    }

    @org.junit.Test
    public void getItems() {
    }

    @org.junit.Test
    public void getPeopleByName() {
        try {
            String name = "Jim";
            Person person = new Person(0,name,"Username","mail@mail.mail", "password", false);
            database.createPerson(person);

            assertEquals(database.getPeopleByName(name),name);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("doesn't work");
        }
    }

//    @org.junit.Test
//    public void addParticipant() {
//        try {
//            List<Person> people;
//            database.createParty(party);
//            database.addParticipant(person,party);
//            people = database.getParticipants(database.getParty(party.getPartyID()).getPartyID());
//            assertEquals(person,people.get(0));
//        } catch (SQLException e) {
//            e.printStackTrace();
//            fail("doesn't work");
//        }
//
//    }

    @org.junit.Test
    public void addItem() {
    }

    @org.junit.Test
    public void createPerson() {
        try {
            database.createPerson(person);
            assertEquals(database.getPeopleByName("Name").get(0), person);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @org.junit.Test
//    public void createItem() {
//        try {
//            database.createParty(party);
//
//            Item testItem = new Item(1,1.0,"name");
//            Item item = new Item(0,1.0, "name");
//
//            database.createItem(item);
//            database.addItem(item,party);
//            assertEquals(database.getItems(party).get(0),testItem);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            fail("doesn't work");
//        }
//    }

    @org.junit.Test
    public void updateParty() {
        fail("Not implemented yet. Sod off");
    }

//    @org.junit.Test
//    public void removeParticipant() {
//
//        List<Person> people;
//        try {
//            database.createParty(party);
//            database.addParticipant(person,party);
//            people = database.getParticipants(database.getParty(party.getPartyID()).getPartyID());
//
//            if (people !=null) {
//                database.removeParticipant(party,person);
//                assertTrue("no people in this b*tch", people == null);
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            fail("doesn't work");
//        }
//
//    }

    @org.junit.Test
    public void removeItem() {
    }

    @org.junit.Test
    public void createParty() {

        try {
            database.connect();
            database.createParty(party);
            assertEquals(database.getParty(1).getPartyID(), 1);
        }
        catch (SQLException e) {
            e.printStackTrace();
            fail("dunno, doesn't work");
        }
    }

    @Test
    public void getPartiesForPerson() {
    }

    @Test
    public void login() {
    }

    @Test
    public void addItems() {
    }

    @Test
    public void setPartyPrivacy() {
    }

    @Test
    public void updatePerson() {
    }
}