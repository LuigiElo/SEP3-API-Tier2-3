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
    private final String password = "08191";
    private Person person2;


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
    public Person createPerson(Person person) throws SQLException {
        ResultSet rs;
        Person person1 = null;
        try {
            connect();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO sep3.person_table(name, email, password, username) VALUES(?,?,?,?)");
            statement.setString(1, person.getName());
            statement.setString(2, person.getEmail());
            statement.setString(3, person.getPassword());
            statement.setString(4, person.getUsername());

            statement.executeUpdate();
            close();
        } catch (SQLException e) {
            System.out.println("The person did not get created");
            e.printStackTrace();
            return null;
        }

        try {
            connect();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM sep3.person_table WHERE name =? AND email = ? AND password = ? AND username =?");
            statement.setString(1, person.getName());
            statement.setString(2, person.getEmail());
            statement.setString(3, person.getPassword());
            statement.setString(4, person.getUsername());

            rs = statement.executeQuery();
            close();

            while (rs.next()){
                int personID = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                String password = rs.getString(4);
                String username = rs.getString(5);
                person1 = new Person(personID, name, username, email, password, false);
                System.out.println(personID);

            }

            return person1;
        } catch (SQLException e) {
            System.out.println("The person could not be retrieved");
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public String addParticipant(Person person, Party party) throws SQLException {

        ResultSet rs;
        String result = "";

        int personID = person.getPersonID();
        int partyID = party.getPartyID();
        boolean isHost = person.isHost();

        try {
            connect();

            PreparedStatement statement = connection.prepareStatement("INSERT INTO sep3.participates_in_party VALUES(?,?,?);");
            statement.setInt(1, partyID);
            statement.setInt(2, personID);
            statement.setBoolean(3, isHost);

            statement.executeUpdate();
            return "success";
        } catch (SQLException e) {
            System.out.println("The person could not be added");
            e.printStackTrace();
            return "fail";
        }
    }


    @Override
    public List<Party> getPartiesForPerson(Person person) {
        ResultSet rs;
        List<Party> parties = new ArrayList<Party>();

        int personID = person.getPersonID();

        try {
            connect();
            PreparedStatement statement = connection.prepareStatement("SELECT partyid FROM sep3.participates_in_party WHERE personid =?");
            statement.setInt(1, personID);
            rs = statement.executeQuery();
            close();

            ArrayList<Integer> ids = new ArrayList();
            while (rs.next()) {
                int partyID = rs.getInt("partyid");
                ids.add(partyID);

            }


            for (int id : ids) {
                Party party = getParty(id);
                parties.add(party);
                System.out.println(party.getPartyTitle());
            }
            return parties;
        } catch (Exception e) {
            System.out.println("The list of parties could not be retrieved");
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Party getParty(int partyID) throws SQLException {

        connect();
        ResultSet rs;
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM sep3.party_table WHERE partyID = ?;");
            statement.setInt(1, partyID);
            rs = statement.executeQuery();

            Party party = null;
            if(rs.next()) {


                int partyid = rs.getInt(1);
                String description = rs.getString(2);
                String address = rs.getString(3);
                String date = rs.getString(4);
                String partyTitle = rs.getString(5);
                String time = rs.getString(6);

                party = new Party(partyTitle, description, address, partyid, date, time, false);
            }
            List<Item> items = getItems(party);
            party.setItems(items);
            List<Person> people = getParticipants(party.getPartyID());
            party.setPeople(people);
             close();
            return party;
        } catch (SQLException e) {
            System.out.println("The party could not be retrieved");
            e.printStackTrace();
            close();
            return null;
        }

    }


    @Override
    public List<Person> getPeopleByName(String smth) throws SQLException {

        ResultSet rs;
        List<Person> people = new ArrayList<Person>();

        try {
            connect();
            PreparedStatement statement = connection.prepareStatement("SELECT * from sep3.person_table WHERE name =? OR email = ? OR username = ? ;");
            statement.setString(1, smth);
            statement.setString(2, smth);
            statement.setString(3, smth);

            rs = statement.executeQuery();
            close();

            while (rs.next()) {
                int personId = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                String username = rs.getString(5);

                Person person = new Person(personId, name, username, email, null, false);
                System.out.println(person.getUsername() +" " + person.getEmail());
                people.add(person);
            }

            return people;

        } catch (Exception e) {
            System.out.println("The search could not be executed");
            e.printStackTrace();
            return null;
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
        Person person2 = null;

        if (rs.next()) {
            if (rs.getString("username").equals(person.getUsername())
                    && rs.getString("password").equals(person.getPassword())) {

                int personID = rs.getInt("personid");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String username = rs.getString("username");

                person2 = new Person(personID, name, username, email, password, false);
            }

            return person2;
        } else {
            System.out.println("User doesn't exist");
            System.out.println("No parties for you my friend... Yet?");
            return null;
        }
    }

    @Override
    public String addItems(Party party) throws SQLException {
        try {
            ResultSet rs;
            connect();
            PreparedStatement statement = connection.prepareStatement("SELECT count(partyid) from sep3.party_has_items where partyid = ?");
            statement.setInt(1,party.getPartyID());
            rs = statement.executeQuery();
            close();

            int count = 0;
            while (rs.next())
            {
                count = rs.getInt(1);
            }

            System.out.println(count +" !!!!!!!!!!!!!!!!!!!!!!");
            try {

                for (int i = count; i < party.getItems().size(); i++)
                {
                    String s = addItem(party.getItem(i), party);
                    if (s.equals("fail"))
                    {
                        return "fail";
                    }
                    System.out.println(s);
                }

            }
            catch (Exception e)
            {
                System.out.println("The index doesn't match");
                e.printStackTrace();
                return "fail";
            }

            return "success";
        }catch (Exception e)
        {
            System.out.println("Could not add the items");
            e.printStackTrace();
            return "fail";
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

            Party party1 = new Party(partyTitle, description, address, partyID, date, time, false);
            partyList.add(party1);
        }
        return partyList;
    }

    @Override
    public void setPartyPrivacy(boolean privacy, Party party) throws SQLException {

        connect();
        PreparedStatement statement = connection.prepareStatement("UPDATE sep3.party_table SET isprivate = ? WHERE partyid = ?;");
        statement.setBoolean(1, privacy);
        statement.setInt(2, party.getPartyID());
        statement.executeUpdate();
        close();
    }


    @Override
    public List<Person> getParticipants(int partyID) throws SQLException {

        connect();
        ResultSet rs;
        PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM sep3.participates_in_party WHERE partyID = " + partyID);
        //+ " AND isHost = false" + ";"
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
                String username = rs.getString(5);

                Person person = new Person(personID, name, email, password, username, false);
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

                int itemID = rs.getInt(1);
                Double price = rs.getDouble(2);
                String name = rs.getString(3);

                Item item = new Item(itemID, price, name);
                items.add(item);
            }
        }
        return items;
    }


    @Override
    public String addItem(Item item, Party party) throws SQLException {
        try {

            Item item1 = createItem(item);

            connect();
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO sep3.party_has_items(partyid, itemid) VALUES (?,?);");
            statement.setInt(1, party.getPartyID());
            statement.setInt(2, item1.getItemID());
            statement.execute();
            close();
            return "success";
        }catch (Exception e)
        {
            System.out.println("Could not add item");
            e.printStackTrace();
            return "fail";
        }

    }

    private Item getItem(Item item) throws SQLException {
        try {
            System.out.println("2");
            ResultSet resultSet;
            connect();
            PreparedStatement statement = connection.prepareStatement("SELECT * from sep3.item_table WHERE price = ? AND name = ?");
            statement.setDouble(1, item.getPrice());
            statement.setString(2, item.getName());
            resultSet = statement.executeQuery();
            close();

            System.out.println("3");
            Item item1 = null;
            while (resultSet.next())
            {
                System.out.println("4");
                int id = resultSet.getInt(1);
                System.out.println("5");
                double price=  resultSet.getDouble(2);
                System.out.println("6");
                String name = resultSet.getString(3);
                System.out.println("7");

                item1 = new Item(id, price, name);
                System.out.println("8");

            }
            System.out.println("9");
            if (item1==null)
            {
                System.out.println("10");
                throw  new SQLException("The item could not be retrieved");
            }
            else return item1;

        }
        catch (Exception e)
        {
            System.out.println("11");
            System.out.println("The item could not be retrieved");
            e.printStackTrace();
            throw new SQLException("The item could not be retrieved");

        }
    }

    @Override
    public Item createItem(Item item) throws SQLException {

        Item item1 = null;
        try {
            System.out.println("1");
             item1 = getItem(item);
            System.out.println("1'a");
             return item1;
        }catch (SQLException e)
        {
            try {
                connect();
                System.out.println("12");
                PreparedStatement statement2 = connection.prepareStatement
                        ("INSERT INTO sep3.item_table(price, name) VALUES (?,?);");
                statement2.setDouble(1, item.getPrice());
                statement2.setString(2, item.getName());
                System.out.println("13");
                statement2.execute();
                System.out.println("hello");
                close();
                System.out.println("14");
                item1 = getItem(item);
                System.out.println("15");
                return item1;
            }
            catch (Exception ex)
            {
                System.out.println("16");
                System.out.println("The important exception");
                e.printStackTrace();
                System.out.println("The item could not be created either");
                return null;
            }
        }

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
        statement.setInt(2, item.getItemID());
        statement.executeQuery();
    }

    @Override
    public Party createParty(Party party) throws SQLException {
        connect();
        PreparedStatement statement = connection.prepareStatement
                ("INSERT INTO sep3.party_table(description, address, date, partytitle, time, isprivate ) VALUES (?,?,?,?,?,?)");
        statement.setString(1, party.getDescription());
        statement.setString(2, party.getLocation()); //address
        statement.setString(3, party.getDate());
        statement.setString(4, party.getPartyTitle());
        statement.setString(5, party.getTime());
        statement.setBoolean(6, party.isPrivate());
        statement.execute();
        close();

        connect();
        ResultSet rs;

        PreparedStatement statement1 = connection.prepareStatement
                ("SELECT * FROM sep3.party_table WHERE description = ? AND address = ? AND date = ? AND partytitle = ? AND time = ? AND isprivate = ?;");
        statement1.setString(1, party.getDescription());
        statement1.setString(2, party.getLocation());
        statement1.setString(3, party.getDate());
        statement1.setString(4, party.getPartyTitle());
        statement1.setString(5, party.getTime());
        statement1.setBoolean(6, party.isPrivate());
        rs = statement1.executeQuery();

        Party party1 = null;

        while (rs.next()) {

            int partyID = rs.getInt(1);
            String description = rs.getString(2);
            String address = rs.getString(3);
            String date = rs.getString(4);
            String partyTitle = rs.getString(5);
            String time = rs.getString(6);
            Boolean isPrivate = rs.getBoolean(7);
            party1 = new Party(partyTitle, description, address, partyID, date, time, isPrivate);
        }
        close();

        //only for testing
        System.out.println(party1.toString());

        return party1;

    }


}