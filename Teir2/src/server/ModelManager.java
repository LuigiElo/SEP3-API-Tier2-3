package server;

import domain.Package;
import domain.Party;
import domain.Person;

import java.io.IOException;

public class ModelManager {

    private DatabaseConnection db;

    public ModelManager() {
        this.db = new DatabaseConnection();
    }

    public String addPerson(String personID, Party party)
    {
        String result ="";


        return result;
    }

    public String addPerson(Person person, String partyID)
    {
        String result ="";

        return result;
    }

    public Person searchPersonBySomething(String smth)
    {
        Person person = null;

        Package packageT = new Package();
        packageT.setCommand("searchPBySmth");
        packageT.addString(smth);
        try{
            Person person1 = db.searchPersonBySmth(packageT);
        }
        catch (Exception e)
        {
            System.out.println("The person could not be found");
            return null;

        }


        return person;
    }

    public String addPerson(Person person, Party party)
    {
        Package packageT = new Package();
        packageT.setCommand("addPerson");
        packageT.addPerson(person);
        packageT.addParty(party);
        String result = db.addPerson(packageT);
        return result;
    }

    public Party createParty(Party party) throws IOException {

        Package packageT = new Package();
        packageT.setCommand("createParty");
        packageT.addParty(party);
        System.out.println("bug2");
        Party party1 = db.createParty(packageT);
        System.out.println("bug3");

        if (party1 == null)
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return party1;

    }
}
