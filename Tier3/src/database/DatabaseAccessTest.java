package database;

import domain.Item;
import domain.Party;
import domain.Person;
import org.junit.internal.runners.statements.Fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseAccessTest {

    DatabaseCon database = new DatabaseAccess();

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

        Party party = new Party();

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
        
    }

    @org.junit.Test
    public void addParticipant() {
    }

    @org.junit.Test
    public void addItem() {
    }

    @org.junit.Test
    public void createPerson() {
        try {
            Person person = new Person(0,"Name", "Username", "user@FU.com", "password", false);
            database.createPerson(person);
            assertEquals(database.getPeopleByName("Name").get(0), person);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void createItem() {
        try {
            Party party = new Party();
            database.createParty(party);

            Item testItem = new Item("1",1.0,"name");
            Item item = new Item(null,1.0, "name");

            database.createItem(item);
            database.addItem(item,party);
            assertEquals(database.getItems(party).get(0),testItem);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("doesn't work");
        }
    }

    @org.junit.Test
    public void updateParty() {
        fail("Not implemented yet. Sod off");
    }

    @org.junit.Test
    public void removeParticipant() {
    }

    @org.junit.Test
    public void removeItem() {
    }

    @org.junit.Test
    public void createParty() {

        Party party = new Party();

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
}