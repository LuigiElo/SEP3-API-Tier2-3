package database;


import domain.Invitation;
import domain.Item;
import domain.Party;
import domain.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *Java class responsible to connecting to the database component of the system and executing
 *the commands.
 * It makes use of a JDBC connection via a PreparedStatement to execute the procedures
 */
public class DatabaseAccess implements DatabaseCon {
    /**
     * Property; Defines the connection to the database through which the command are being
     * executed
     */
    Connection connection;
    /**
     * Properties; Define the characteristics of the connection;
     * It is advised that before the use of the system a database is created using the
     * available SQL.txt file and the followings are completed with valid postgres user
     * information
     */
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "08191";

    /**
     *Empty constructor for the DatabaseAccess object
     */
    public DatabaseAccess() { }

    /**
     * Method that enables the connection to the database;
     * @return connection
     * @throws SQLException
     */
    @Override
    public Connection connect() throws SQLException {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    /**
     * Method used to create a new instance of a person in the Database;
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     * After insertion the method retrieves in similar steps the Person object obtained
     * after the database insertion (with only persistence data generated)
     * @param person
     * @return Person
     * @throws SQLException
     */
    @Override
    public synchronized Person createPerson(Person person) throws SQLException {
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

            }

            return person1;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Method used to add a Person (user) as participant in a Party in the database with mathing
     * characteristics to the Party parameter
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     * IF the command has been executed with no issues the method will return "success" and "fail"
     * otherwise
     * @param person
     * @param party
     * @return
     * @throws SQLException
     */
    public synchronized String addParticipant(Person person, Party party) throws SQLException {

        ResultSet rs;
        String result = "";


        int personID = person.getPersonID();
        int partyID = party.getPartyID();
        boolean isHost = false;

        try {
            connect();

            PreparedStatement statement = connection.prepareStatement("INSERT INTO sep3.participates_in_party(partyid, personid, ishost) VALUES(?,?,?);");
            statement.setInt(1, partyID);
            statement.setInt(2, personID);
            statement.setBoolean(3, isHost);

            statement.executeUpdate();
            close();
            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     *Method used to retrieve the list of parties in which th user described by the parameter
     * participates in
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     * The result is either a List<Party></> or null if the procedure resulted in an Exception due to failure
     * @param person
     * @return List<Party></Party>
     */
    @Override
    public synchronized List<Party> getPartiesForPerson(Person person) {

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
            }
            return parties;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Method used to retrieve a Party by it's unique id from the database. Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     * The result is either a Party, mapped from the ResultSet of the PreparedStatement
     * or null if the procedure resulted in an Exception due to failure
     * @param partyID
     * @return
     * @throws SQLException
     */
    @Override
    public synchronized Party getParty(int partyID) throws SQLException {


        ResultSet rs;
        try {
            connect();
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM sep3.party_table WHERE partyID = ?;");
            statement.setInt(1, partyID);
            rs = statement.executeQuery();
            close();

            Party party = null;
            while (rs.next()) {


                int partyid = rs.getInt("partyid");
                String description = rs.getString("description");
                String address = rs.getString("address");
                String date = rs.getString("date");
                String partyTitle = rs.getString("partytitle");
                String time = rs.getString("time");
                boolean isPrivate = rs.getBoolean("isprivate");
                String playlistURL = rs.getString("playlisturl");
                party = new Party(partyTitle, description, address, partyid, date, time, isPrivate, null);

                if (playlistURL != null) {
                    party.setPlaylistURL(playlistURL);
                }
            }

            List<Item> items = getItems(party);
            party.setItems(items);
            List<Person> people = getParticipants(party.getPartyID());
            party.setPeople(people);
            party.setHost(getHostForParty(party));
            return party;
        } catch (SQLException e) {
            e.printStackTrace();
            close();
            return null;
        }

    }

    /**
     *Method used to retrieve a List<Person> (users) matching the criteria defined by the parameter.
     *
     *Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     * The result is either a List<Person>, mapped from the ResultSet of the PreparedStatement
     * or null if the procedure resulted in an Exception due to failure
     * @param smth
     * @return List<Person>
     * @throws SQLException
     */
    @Override
    public synchronized List<Person> getPeopleByName(String smth) throws SQLException {

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
                people.add(person);
            }

            return people;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * Method used to retrieve a Person object that is hosting the Party given as parameter;
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     * The result is either a Person, mapped from the ResultSet of the PreparedStatement
     * or null if the procedure resulted in an Exception due to failure
     *
     * @param party
     * @return
     */
    private synchronized Person getHostForParty(Party party)
    {
        ResultSet rs;
        Person person = new Person();
        try
        {
            connect();
            PreparedStatement statement = connection.prepareStatement("SELECT personid FROM sep3.participates_in_party WHERE partyid = ? AND ishost = true;");
            statement.setInt(1, party.getPartyID());
            rs = statement.executeQuery();
            close();

            while (rs.next())
            {
                int personID = rs.getInt(1);
                person = getPersonByID(personID);
            }
            return person;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

       return null;

    }
//
//    /**
//     *
//     * @param party
//     * @return
//     * @throws SQLException
//     */
//    @Override
//    public synchronized Party getHost(Party party) throws SQLException {
//        ResultSet rs;
//        connect();
//        PreparedStatement statement = connection.prepareStatement("SELECT * FROM sep3.participates_in_party WHERE partyid = ? AND ishost = true;");
//        statement.setInt(1, party.getPartyID());
//        rs = statement.executeQuery();
//        close();
//        List<Party> partyWithHost = new ArrayList<>();
//
//        do {
//            int partyID = rs.getInt("partyid");
//            int personID = rs.getInt("personid");
//
//            Party party1 = getParty(partyID);
//            party1.setHost(getPersonByID(personID));
//            partyWithHost.add(party1);
//        }
//        while (rs.next());
//
//        return party;
//    }

    /**
     * Method used to retrieve the user (Person object) matching with the id given as parameter
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     * The result is either a Person, mapped from the ResultSet of the PreparedStatement
     * or null if the procedure resulted in an Exception due to failure
     * @param personID
     * @return
     * @throws SQLException
     */
    @Override
    public synchronized Person getPersonByID(int personID) throws SQLException {
        ResultSet rs;
        connect();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM sep3.person_table WHERE personid = ?;");
        statement.setInt(1, personID);
        rs = statement.executeQuery();
        close();
        Person person = new Person();
        while (rs.next())
        {
            int ID = rs.getInt(1);
            String name = rs.getString(2);
            String email = rs.getString(3);
            String password = rs.getString(4);
            String username = rs.getString(5);


            person = new Person(ID,name,username,email,null,false);
        }


        return person;
    }

    /**
     * Method used to retrieve a user(Person object) if the credentials (name and username) for
     * the Person object given as parameter match an instance of a person from the database
     *
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     * The result is either a Person, mapped from the ResultSet of the PreparedStatement
     * or null if the procedure resulted in an Exception due to failure
     * @param person
     * @return
     * @throws SQLException
     */
    @Override
    public synchronized Person login(Person person) throws SQLException {
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

                person2 = new Person(personID, name, username, email, password, false);
            }

            return person2;
        } else {
            return null;
        }
    }

    /**
     * Method used to retrieve the List<Item> variable of a party given its unique id as parameter;
     *
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     * The result is either a List<Item>, mapped from the ResultSet of the PreparedStatement
     * or null if the procedure resulted in an Exception due to failure
     * @param partyId
     * @return List<Item></Item>
     * @throws Exception
     */
    @Override
    public synchronized List<Item> getItems(int partyId) throws Exception {

        ResultSet rs;
        try{
            connect();
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM sep3.party_has_items WHERE partyID = " + partyId + ";");
            rs = statement.executeQuery();
            List<Integer> itemsInParty = new ArrayList<>(100);
            close();

            while (rs.next()) {

                int itemID = rs.getInt(2);

                itemsInParty.add(itemID);
            }

            List<Item> items = new ArrayList<>(100);

            for (int i = 0; i < itemsInParty.size(); i++) {

                rs = null;
                connect();
                statement = connection.prepareStatement
                        ("SELECT * FROM sep3.item_table WHERE itemID = " + itemsInParty.get(i) + ";");
                rs = statement.executeQuery();
                close();

                while (rs.next()) {

                    int itemID = rs.getInt(1);
                    Double price = rs.getDouble(2);
                    String name = rs.getString(3);

                    Item item = new Item(itemID, price, name);
                    items.add(item);
                }
            }
            return items;
        }catch (Exception e)
        {
            e.printStackTrace();
            close();
            throw new Exception("Couln't get the items");

        }



    }


    /**
     * Method used to retrieve a List<Party> matching the criteria defined by the parameter;
     *
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     *
     * The result is either a List<Party>, mapped from the ResultSet of the PreparedStatement
     * or null if the procedure resulted in an Exception due to failure
     * @param something
     * @return
     * @throws SQLException
     */
    @Override
    public synchronized List<Party> getPartiesBySomething(String something) throws SQLException {

        List<Party> partyList = new ArrayList<>(100);

        connect();
        ResultSet rs;

        PreparedStatement statement1 = connection.prepareStatement
                ("SELECT * FROM sep3.party_table WHERE description = ? OR address = ? OR date = ? OR partytitle = ? OR time = ? ;");
        statement1.setString(1,something);
        statement1.setString(2,something);
        statement1.setString(3,something);
        statement1.setString(4,something);
        statement1.setString(5,something);
        rs = statement1.executeQuery();

        close();


        connect();
        ResultSet rs2;
        PreparedStatement statement2 = connection.prepareStatement
                ("SELECT * FROM sep3.participates_in_party WHERE partyid = ? AND ishost = true;");
        rs2 = statement2.executeQuery();
        close();

        while (rs.next()) {
            int partyID = rs.getInt(1);
            String description = rs.getString(2);
            String address = rs.getString(3);
            String date = rs.getString(4);
            String partyTitle = rs.getString(5);
            String time = rs.getString(6);
            boolean isPrivate = rs.getBoolean("isprivate");
            String playlistURL = rs.getString("playlisturl");

            Party party1 = new Party(partyTitle, description, address, partyID, date, time, isPrivate, getHostForParty(getParty(partyID)));
            partyList.add(party1);
        }
        return partyList;
    }

    /**
     *Method used to change the privacy variable of the Party given as parameter in the system;
     *Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     * IF the command has been executed with no issues the method will return "success" and "fail"
     * otherwise
     * @param privacy
     * @param party
     * @return String
     * @throws SQLException
     */
    @Override
    public synchronized String setPartyPrivacy(boolean privacy, Party party) throws SQLException {

        connect();
        PreparedStatement statement = connection.prepareStatement("UPDATE sep3.party_table SET isprivate = ? WHERE partyid = ?;");
        statement.setBoolean(1, privacy);
        statement.setInt(2, party.getPartyID());
        statement.executeUpdate();
        close();

        connect();
        PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM sep3.party_table WHERE partyid = ?;");
        statement1.setInt(1, party.getPartyID());
        ResultSet rs = statement1.executeQuery();
        close();
        return "Party: " + party.toString() + " is: " + rs.getString("isPrivate");
    }


    /**
     *Method used to retrieve a List<Person> that are participating the party specified through
     * it's unique id as parameter;
     *
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     *
     * The result is either a List<Person>, mapped from the ResultSet of the PreparedStatement
     * or null if the procedure resulted in an Exception due to failure
     * @param partyID
     * @return List<Person>
     * @throws SQLException
     */
    @Override
    public synchronized List<Person> getParticipants(int partyID) throws SQLException {

        connect();
        ResultSet rs;
        PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM sep3.participates_in_party WHERE partyID = " + partyID);
        //+ " AND isHost = false" + ";"
        rs = statement.executeQuery();
        close();

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
            close();

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

    /**
     * Method used to retrieve the items for a Party from the system
     *
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     *
     * The result is either a List<Item>, mapped from the ResultSet of the PreparedStatement
     * or null if the procedure resulted in an Exception due to failure
     * @param party
     * @return List<Item>
     * @throws SQLException
     */
    @Override
    public synchronized List<Item> getItems(Party party) throws SQLException {

        connect();
        ResultSet rs;
        PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM sep3.party_has_items WHERE partyID = " + party.getPartyID() + ";");
        rs = statement.executeQuery();
        close();

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
            close();

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


    /**
     * Method used to add an item to a Party;
     * The method verifies first if another instance of the same item, matching in name and price does exists
     * in the system;
     *
     * If yes, the item is then added to the party, if not it is created in tha system and then added;
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     *
     * IF the command has been executed with no issues the method will return "success" and "fail"
     * otherwise
     * @param item
     * @param party
     * @return String
     * @throws SQLException
     */
    public synchronized String addItem(Item item, Party party) throws SQLException {
        try {

            Item item1 = createItem(item);

            connect();
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO sep3.party_has_items(partyid, itemid) VALUES (?,?);");
            statement.setInt(1, party.getPartyID());
            statement.setInt(2, item1.getitemId());
            statement.execute();
            close();
            return "success";
        }catch (Exception e)
        {
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * Method that retrieves an item from the system if there is in the system an instance of an
     * Item matching in name and price.
     *
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     * If the match exists the result is the Item obtained by mapping through the Result Set, and an
     * Exception in case negative
     * @param item
     * @return Item
     * @throws SQLException
     */
    private Item getItem(Item item) throws SQLException {


            ResultSet resultSet;
            connect();
            PreparedStatement statement = connection.prepareStatement("SELECT * from sep3.item_table WHERE price = ? AND name = ?");
            statement.setDouble(1, item.getPrice());
            statement.setString(2, item.getName());
            resultSet = statement.executeQuery();
            close();


            Item item1 = null;
            while (resultSet.next())
            {

                int id = resultSet.getInt(1);

                double price=  resultSet.getDouble(2);

                String name = resultSet.getString(3);


                item1 = new Item(id, price, name);


            }

            if (item1==null)
            {

                throw  new SQLException("The item could not be retrieved");
            }
            return item1;

    }

    /**
     * Method used to create an Item or retrieve it from the database if it already exists;
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     *
     * The result is either a Item, mapped from the ResultSet of the PreparedStatement
     * or null if the procedure resulted in an Exception due to failure
     * @param item
     * @return Item
     * @throws SQLException
     */
    @Override
    public synchronized Item createItem(Item item) throws SQLException {

        Item item1 = null;
        try {
             item1 = getItem(item);
             return item1;
        }catch (SQLException e)
        {
            try {
                connect();
                PreparedStatement statement2 = connection.prepareStatement
                        ("INSERT INTO sep3.item_table(price, name) VALUES (?,?);");
                statement2.setDouble(1, item.getPrice());
                statement2.setString(2, item.getName());
                statement2.executeUpdate();
                close();
                item1 = getItem(item);
                return item1;
            }
            catch (Exception ex)
            {
                e.printStackTrace();
                return null;
            }
        }

    }

    /**
     * Method used to modify the static characteristics of the Party given as parameter in the system;
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     *
     * The result is either the updated Party, mapped from the ResultSet of the PreparedStatement
     * or null if the procedure resulted in an Exception due to failure
     * @param party
     * @return Party
     * @throws SQLException
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public synchronized Party updateParty(Party party) throws SQLException {

        try {
            connect();
            PreparedStatement statement = connection.prepareStatement
                    ("UPDATE sep3.party_table SET description = ?, address = ?, date = ?, partytitle = ?, time = ?, isprivate = ?, playlisturl = ? WHERE partyid = ?;");
            //set
            statement.setString(1, party.getDescription());
            statement.setString(2, party.getLocation());
            statement.setString(3, party.getDate());
            statement.setString(4, party.getPartyTitle());
            statement.setString(5, party.getTime());
            statement.setBoolean(6, party.isPrivate());
            statement.setString(7, party.getPlaylistURL());
            //where
            statement.setInt(8, party.getPartyID());
            statement.executeUpdate();
            close();

            try {
                connect();
                PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM sep3.party_table WHERE partyid = ?;");
                statement1.setInt(1, party.getPartyID());
                ResultSet rs = statement1.executeQuery();
                close();

                while (rs.next()) {
                    int partyID = rs.getInt("partyid");
                    String description = rs.getString("description");
                    String address = rs.getString("address");
                    String date = rs.getString("date");
                    String partytitle = rs.getString("partytitle");
                    String time = rs.getString("time");
                    boolean isPrivate = rs.getBoolean("isprivate");

                    Party party1 = new Party(partytitle,description,address,partyID,date,time,isPrivate,party.getHost());
                    List<Item> items = getItems(party1);
                    List<Person> people = getParticipants(partyID);
                    party1.setItems(items);
                    party1.setPeople(people);

                    return party1;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public synchronized void updatePerson(Person person) throws SQLException {

    }

    /**
     * Method used to remove a user (Person) as a participant from the party parameter in the system;
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     *
     * IF the command has been executed with no issues the method will return "success" and "fail"
     * otherwise
     * @param party
     * @param person
     * @return String
     * @throws SQLException
     */
    public synchronized String removeParticipant(Party party, Person person) throws SQLException {

        try {

            connect();
            PreparedStatement statement = connection.prepareStatement
                    ("DELETE FROM sep3.participates_in_party WHERE personID = ? AND partyID = ?;");
            statement.setInt(1, person.getPersonID());
            statement.setInt(2, party.getPartyID());
            statement.executeQuery();
            close();
            return "success";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * Method used to remove an Item from a Party in the system identified through the parameter
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     *
     * IF the command has been executed with no issues the method will return "success" and "fail"
     * otherwise
     * @param party
     * @param item
     * @return Item
     * @throws SQLException
     */
    public synchronized String removeItem(Party party, Item item) throws SQLException {

        try {
            connect();
            PreparedStatement statement = connection.prepareStatement
                    ("DELETE FROM sep3.party_has_items WHERE partyID = ? AND itemID = ?;");
            statement.setInt(1, party.getPartyID());
            statement.setInt(2, item.getitemId());
            statement.executeQuery();
            close();
            return "success";
        } catch (SQLException e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * Method used to create a new instance of a Party in the database system. It included the static
     * information of the party as well as it's host
     *
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     *
     * The result is either the new created Party, mapped from the ResultSet of the PreparedStatement
     * or null if the procedure resulted in an Exception due to failure
     * @param party
     * @return Party
     * @throws SQLException
     */
    @Override
    public synchronized Party createParty(Party party) throws SQLException {
        connect();
        PreparedStatement statement = connection.prepareStatement
                ("INSERT INTO sep3.party_table(description, address, date, partytitle, time, isprivate, playlisturl) VALUES (?,?,?,?,?,?,?)");
        statement.setString(1, party.getDescription());
        statement.setString(2, party.getLocation()); //address
        statement.setString(3, party.getDate());
        statement.setString(4, party.getPartyTitle());
        statement.setString(5, party.getTime());
        statement.setBoolean(6, party.isPrivate());
        statement.setString(7, party.getPlaylistURL());
        statement.executeUpdate();
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
        close();

        Party party1 = null;

        while (rs.next()) {

            int partyID = rs.getInt(1);
            String description = rs.getString(2);
            String address = rs.getString(3);
            String date = rs.getString(4);
            String partyTitle = rs.getString(5);
            String time = rs.getString(6);
            Boolean isPrivate = rs.getBoolean(7);
            party1 = new Party(partyTitle, description, address, partyID, date, time, isPrivate, null);
        }


        Person host = party.getHost();

        connect();
        PreparedStatement statement2 = connection.prepareStatement
                ("INSERT INTO sep3.participates_in_party(partyid, personid, ishost) VALUES (?,?,?);");
        statement2.setInt(1, party1.getPartyID());
        statement2.setInt(2, host.getPersonID());
        statement2.setBoolean(3, true);
        statement2.executeUpdate();
        close();

        party1.setHost(party.getHost());

        return party1;

    }

    /**
     * Method used to add a List<Item> to the Party given as parameter in the database;
     * It calls successively the method addItem for all objects in the list
     * If one add fails the method returns "fail" and aborts the procedure and "success" in the opposite
     * case;
     * @param items
     * @param party
     * @return String
     */
    @Override
    public synchronized String addItems(List<Item> items, Party party)
    {
        try
        {
            for (Item item: items)
            {
                String result = addItem(item, party);
                if(result.equals("fail"))
                {
                    return "fail";
                }
            }
            return "success";

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * Method used to remove a List<Item> to the Party given as parameter in the database;
     * It calls successively the method removeItem for all objects in the list
     * If one add fails the method returns "fail" and aborts the procedure and "success" in the opposite
     * case;
     * @param items
     * @param party
     * @return String
     */
    @Override
    public synchronized String removeItems(List<Item> items, Party party)
    {
        try {

            for (Item item:items)
            {
                String result = removeItem(party, item);
                if(result.equals("fail"))
                {
                    return "fail";
                }
            }
            return "success";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * Method used to add a List<Person> to the Party given as parameter in the database;
     * It calls successively the method addParticipant for all objects in the list
     * If one add fails the method returns "fail" and aborts the procedure and "success" in the opposite
     * case;
     * @param people
     * @param party
     * @return
     */
    @Override
    public synchronized String addPeople(List<Person> people, Party party)
    {

        try {

           for (int i = 0; i< people.size(); i++)
           {

                String result = addParticipant(people.get(i), party);
                if(result.equals("fail"))
                {
                    return "fail";
                }
           }
            return "success";
        }
        catch (Exception e)
        {

            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * Method used to remove a List<Person> to the Party given as parameter in the database;
     * It calls successively the method removeParticipant for all objects in the list
     * If one add fails the method returns "fail" and aborts the procedure and "success" in the opposite
     * case;
     * @param people
     * @param party
     * @return
     */
    @Override
    public synchronized String removePeople(List<Person> people, Party party)
    {
        try {

            for (Person person:people)
            {
                String result = removeParticipant(party, person);
                if(result.equals("fail"))
                {
                    return "fail";
                }
            }
            return "success";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "fail";
        }
    }

    /***
     * Method used to create an invitation to each instance of a Person (user) in the List<Person> given as
     * parameter for the party object given as parameter from the system;
     * It calls successively the method makeInvitation for all objects in the list
     * If one add fails the method returns "fail" and aborts the procedure and "success" in the opposite
     * @param people
     * @param party
     * @return String
     */
    @Override
    public synchronized String makeInvitations(List<Person> people, Party party) {

        for (Person person:people)
        {
            try
            {
                makeInvitation(person, party);
            }
            catch (Exception e)
            {
                return "fail";
            }
        }
        return "success";
    }

    /***
     * Method used to retrieve the invitations meant for a specific user specified through
     * the personID parameter
     *
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     * The result is either a List<Invitation>, mapped from the ResultSet of the PreparedStatement
     * or null if the procedure resulted in an Exception due to failure
     * @param personID
     * @return List<Invitation>
     */
    @Override
    public synchronized List<Invitation> getInvitations(int personID) {
        ResultSet rs;
        List<Invitation> invitations = new ArrayList<>();

        try {
            connect();
            PreparedStatement statement = connection.prepareStatement("SELECT partyid, status FROM sep3.invitations WHERE personid =? AND status = 'pending';");
            statement.setInt(1,personID);
            rs = statement.executeQuery();
            close();

            while (rs.next())
            {
                int partyId = rs.getInt(1);
                String status = rs.getString(2);

                Party party = getParty(partyId);

                Invitation invitation = new Invitation(personID, party, status);
                invitations.add(invitation);
            }
            return invitations;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * Method used to change the status of an invitation to "accepted"
     *
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     * IF the command has been executed with no issues the method will return "success" and "fail"
     * otherwise
     * @param invitation
     * @return String
     */
    @Override
    public  synchronized String acceptInvite(Invitation invitation) {

        try{
            connect();
            PreparedStatement statement = connection.prepareStatement("UPDATE sep3.invitations SET status = 'accepted' WHERE partyid = ? AND personid = ?;");
            statement.setInt(1,invitation.getParty().getPartyID());
            statement.setInt(2,invitation.getPersonId());
            statement.executeUpdate();
            close();

            Person person = new Person();
            person.setPersonID(invitation.getPersonId());
            addParticipant(person, invitation.getParty());
            return "success";

        } catch (SQLException e) {
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * Method used to change the status of an invitation to "declined"
     *
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     * IF the command has been executed with no issues the method will return "success" and "fail"
     * otherwise
     * @param invitation
     * @return
     */
    @Override
    public synchronized String declineInvite(Invitation invitation) {
        try{
            connect();
            PreparedStatement statement = connection.prepareStatement("UPDATE sep3.invitations SET status = 'declined' WHERE partyid = ? AND personid = ?;");
            statement.setInt(1,invitation.getParty().getPartyID());
            statement.setInt(2,invitation.getPersonId());
            statement.executeUpdate();
            close();
            return "success";

        } catch (SQLException e) {
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * Method used to create an invitation for a specific Person given as parameter in the system
     * to a specific Party in the database;
     * Procedure:
     *
     * Enables the connection to the database;
     * Sends the command in SQL format;
     * Closes the connection;
     *
     * If the procedure fails the method throws an Exception
     * @param person
     * @param party
     * @throws Exception
     */
    private synchronized void makeInvitation(Person person, Party party) throws Exception {
        try
        {
         connect();
         PreparedStatement statement = connection.prepareStatement
                 ("INSERT INTO sep3.invitations(partyid, personid, status) VALUES (?,?,?);");
         statement.setInt(1,party.getPartyID());
         statement.setInt(2, person.getPersonID());
         statement.setString(3, "pending");

         statement.executeUpdate();
         close();
        }
        catch (Exception e)
        {
            throw new Exception("I couldn't make this invitation");

        }
    }


}