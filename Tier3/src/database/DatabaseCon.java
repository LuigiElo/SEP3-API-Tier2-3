package database;





import domain.Item;
import domain.Party;
import domain.Person;

import java.rmi.Remote;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface DatabaseCon extends Remote {

    public Connection connect() throws  SQLException;
    public void close() throws SQLException;

    public List<Party> getPartiesBySomething(String something) throws  SQLException;
    public Party getParty(String partyID) throws  SQLException;
    public List<Person> getParticipants(String partyID) throws  SQLException;
    public List<Item> getItems(Party party) throws  SQLException;
    public List<Person> getPeopleByName(String name) throws  SQLException;


    void addParticipant(Person person, Party party) throws  SQLException;
    void addItem(Item item, Party party) throws  SQLException;
    Person createPerson(Person person) throws  SQLException;
    void createItem(Item item) throws  SQLException;

    void updateParty(Party party) throws  SQLException;

    void removeParticipant(Party party, Person person) throws  SQLException;
    void removeItem(Party party, Item item) throws  SQLException;

    public Party createParty(Party party)throws  SQLException;


    List<String> login(Person person) throws SQLException;
}
