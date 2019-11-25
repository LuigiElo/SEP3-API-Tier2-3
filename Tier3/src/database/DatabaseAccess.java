package database;


import domain.Item;
import domain.Party;
import domain.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseAccess implements DatabaseCon {

    Connection connection;

    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "postgres";


    public DatabaseAccess() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection connect() throws SQLException {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return connection;
    }

    /**
     * Method for closing the connection.
     * Recommended to use after getting the tables or updating them.
     */
    @Override
    public void close() {
        try {
            connection.close();
            connection = null;

        } catch (SQLException e) {
            System.out.println("No closing this one bitch");
            e.printStackTrace();
        }
    }


    @Override
    public Person login(Person person) throws SQLException {
        //Roxy is usually right... (Except when it comes to Anne Hathaway vs Scarlett Johansson)

        ResultSet rs;
        connect();
        Person person1 = null;

        PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM sep3.person_table WHERE username = ? AND password = ?;");
        statement.setString(1, person.getUsername());
        statement.setString(2, person.getPassword());
        rs = statement.executeQuery();
        close();
        Person person2 = null;
        
        if (rs.next()) {
            if (rs.getString("username").equals(person.getUsername())
                && rs.getString("password").equals(person.getPassword())) {
                
                int personID = rs.getInt("personid");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String username = rs.getString("username");
                
                person2 = new Person(personID,name,email,password,false);
            }
            
            return person2;
        }

        else {
            System.out.println("User doesn't exist");
            System.out.println("No parties for you my friend... Yet?");
            return null;
        }
    }




    @Override
    public List<Party> getPartiesBySomething(String something) throws SQLException {

        List<Party> partyList = new ArrayList<>(100);

        connect();
        ResultSet rs;

        PreparedStatement statement1 = connection.prepareStatement
                ("SELECT * FROM sep3.party_table WHERE description = "
                        + something + " OR address = " + something + " OR date = "
                        + something + " OR partytitle = " + something + " OR time = "
                        + something + ";");
        rs = statement1.executeQuery();

        close();

        while (rs.next()) {
            int partyID = rs.getInt(1);
            String description = rs.getString(2);
            String address = rs.getString(3);
            String date = rs.getString(4);
            String partyTitle = rs.getString(5);
            String time = rs.getString(6);

            Party party1 = new Party(partyTitle, description, address, partyID, date, time);
            partyList.add(party1);
        }
        return partyList;
    }


    @Override
    public Party getParty(String partyID) throws SQLException {

        connect();
        ResultSet rs;
        PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM sep3.party_table WHERE partyID = " + partyID + ";");
        rs = statement.executeQuery();

        int partyid = rs.getInt(1);
        String description = rs.getString(2);
        String address = rs.getString(3);
        String date = rs.getString(4);
        String partyTitle = rs.getString(5);
        String time = rs.getString(6);

        Party party = new Party(partyTitle, description, address, partyid, date, time);

        return party;
    }

    @Override
    public List<Person> getParticipants(String partyID) throws SQLException {

        connect();
        ResultSet rs;
        PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM sep3.participates_in_party WHERE partyID = " + partyID + " AND isHost = false" + ";");
        rs = statement.executeQuery();

        List<String> participants = new ArrayList<>(100);

        while (rs.next()) {

            String participantID = rs.getString(2);

            participants.add(participantID);
        }

        List<Person> people = new ArrayList<>(100);

        for (int i = 0; i < participants.size(); i++) {

            rs = null;
            connect();
            statement = connection.prepareStatement
                    ("SELECT * FROM sep3.person_table WHERE personID = " + participants.get(i) + ";");
            rs = statement.executeQuery();

            while (rs.next()) {

                int personID = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                String password = rs.getString(4);

                Person person = new Person(personID, name, email, password, false);
                people.add(person);
            }
        }
        return people;
    }

    @Override
    public List<Item> getItems(Party party) throws SQLException {

        connect();
        ResultSet rs;
        PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM sep3.party_has_items WHERE partyID = " + party.getPartyID() + ";");
        rs = statement.executeQuery();

        List<String> itemsInParty = new ArrayList<>(100);

        while (rs.next()) {

            String itemID = rs.getString(2);

            itemsInParty.add(itemID);
        }

        List<Item> items = new ArrayList<>(100);

        for (int i = 0; i < itemsInParty.size(); i++) {

            rs = null;
            connect();
            statement = connection.prepareStatement
                    ("SELECT * FROM sep3.item_table WHERE itemID = " + itemsInParty.get(i) + ";");
            rs = statement.executeQuery();

            while (rs.next()) {

                String itemID = rs.getString(1);
                Double price = rs.getDouble(2);
                String name = rs.getString(3);

                Item item = new Item(itemID, price, name);
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public List<Person> getPeopleByName(String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM sep3.person_table");
        return null;
    }

    @Override
    public void addParticipant(Person person, Party party) throws SQLException {
        PreparedStatement statement = connection.prepareStatement
                ("INSERT INTO sep3.participates_in_party VALUES (?,?,?);");
        statement.setInt(1, party.getPartyID());
        statement.setInt(2, person.getPersonID());
        statement.setBoolean(3, false);
        statement.executeQuery();
    }

    @Override
    public void addItem(Item item, Party party) throws SQLException {
        PreparedStatement statement = connection.prepareStatement
                ("INSERT INTO sep3.party_has_items VALUES (?,?);");
        statement.setInt(1, party.getPartyID());
        statement.setString(2, item.getItemID());
        statement.executeQuery();
    }

    @Override
    public Person createPerson(Person person) throws SQLException {
        /*
        The block below might need an if() check to make sure
        there isn't one just like it already in the database.
        However all the values are set to 'unique'
        so it shouldn't be able to happen anyway.
        */
        PreparedStatement statement2 = connection.prepareStatement
                ("INSERT INTO sep3.person_table VALUES(?,?,?,?);");
        statement2.setInt(1, person.getPersonID());
        statement2.setString(2, person.getName());
        statement2.setString(3, person.getEmail());
        statement2.setString(4, person.getPassword());
        statement2.executeQuery();

        ///the registration is more then this.
        ///first there is a check to see if the person already exist
        //then an add
        //and a get to get the Person with his unique id after the introduction in the Database
        ///need to add getPerson
        return person;
    }

    @Override
    public void createItem(Item item) throws SQLException {
        /*
        The block below might need an if() check to make sure
        there isn't one just like it already in the database.
        However all the values are set to 'unique'
        so it shouldn't be able to happen anyway.
        */
        PreparedStatement statement2 = connection.prepareStatement
                ("INSERT INTO sep3.item_table VALUES (?,?,?);");
        statement2.setString(1, item.getItemID());
        statement2.setDouble(1, item.getPrice());
        statement2.setString(1, item.getName());
        statement2.executeQuery();
    }

    @Override
    public void updateParty(Party party) throws SQLException {
        PreparedStatement statement = connection.prepareStatement
                ("UPDATE sep3.party_table SET description = ?, address = ?, date = ?, partytitle = ?, time = ? WHERE partyid = ?;");
        //set
        statement.setString(1, party.getDescription());
        statement.setString(2, party.getLocation());
        statement.setString(3, party.getDate());
        statement.setString(4, party.getPartyTitle());
        statement.setString(5, party.getTime());
        //where
        statement.setInt(6, party.getPartyID());
    }

    @Override
    public void updatePerson(Person person) throws SQLException {

    }

    @Override
    public void removeParticipant(Party party, Person person) throws SQLException {

        PreparedStatement statement = connection.prepareStatement
                ("DELETE FROM sep3.participates_in_party WHERE personID = ? AND partyID = ?;");
        statement.setInt(1, person.getPersonID());
        statement.setInt(2, party.getPartyID());
        statement.executeQuery();
    }

    @Override
    public void removeItem(Party party, Item item) throws SQLException {

        PreparedStatement statement = connection.prepareStatement
                ("DELETE FROM sep3.party_has_items WHERE partyID = ? AND itemID = ?;");
        statement.setInt(1, party.getPartyID());
        statement.setString(2, item.getItemID());
        statement.executeQuery();
    }

    @Override
    public Party createParty(Party party) throws SQLException {
        connect();
        PreparedStatement statement = connection.prepareStatement
                ("INSERT INTO sep3.party_table(description, address, date, partytitle, time) VALUES (?,?,?,?,?)");
//not working because of the first parameter
        statement.setString(1, party.getDescription());
        statement.setString(2, party.getLocation()); //address
        statement.setString(3, party.getDate());
        statement.setString(4, party.getPartyTitle());
        statement.setString(5, party.getTime());
        statement.execute();
        close();

        connect();
        ResultSet rs;

        PreparedStatement statement1 = connection.prepareStatement
                ("SELECT * FROM sep3.party_table WHERE description = ? AND address = ? AND date = ? AND partytitle = ? AND time = ?;");
        statement1.setString(1, party.getDescription());
        statement1.setString(2, party.getLocation());
        statement1.setString(3, party.getDate());
        statement1.setString(4, party.getPartyTitle());
        statement1.setString(5, party.getTime());
        rs = statement1.executeQuery();
        close();

        Party party1 = null;

        while (rs.next()){

            int partyID = rs.getInt(1);
            String description = rs.getString(2);
            String address = rs.getString(3);
            String date = rs.getString(4);
            String partyTitle = rs.getString(5);
            String time = rs.getString(6);
            party1 = new Party(partyTitle, description, address, partyID, date, time);
        }


        return party1;

    }


}