package database;

import domain.Party;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseAccessTest {

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
    }

    @org.junit.Test
    public void createItem() {
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
    }
}