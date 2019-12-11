package database;


import domain.Item;
import domain.Party;
import domain.Person;

import java.rmi.Remote;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface DatabaseCon extends Remote {

    public Connection connect() throws SQLException;

    public void close() throws SQLException;

    public List<Party> getPartiesBySomething(String something) throws SQLException;

    String setPartyPrivacy(boolean privacy, Party party) throws SQLException;

    List<Party> getPartiesForPerson(Person person);

    Party getParty(int partyID) throws SQLException;

    public List<Person> getParticipants(int partyID) throws SQLException;

    public List<Item> getItems(Party party) throws SQLException;

    public List<Person> getPeopleByName(String name) throws SQLException;


    String addParticipant(Person person, Party party) throws  SQLException;
    String addItem(Item item, Party party) throws  SQLException;
    Person createPerson(Person person) throws  SQLException; //register
    Item createItem(Item item) throws  SQLException;

    Party updateParty(Party party) throws  SQLException;
    void updatePerson(Person person) throws SQLException;

    void removeParticipant(Party party, Person person) throws SQLException;

    String removeItem(Party party, Item item) throws SQLException;

    public Party createParty(Party party) throws SQLException;


    Person login(Person person) throws SQLException;

    String addItems(Party party) throws SQLException;

    String removeItems(Party party) throws SQLException;

    List<Item> getItems(int partyId) throws Exception;
}
