package server;

import domain.Package;
import domain.Party;
import domain.Person;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

    public List<Person> searchPersonBySomething(String smth)
    {
        List<Person> list = new ArrayList<Person>();

        Package packageT = new Package();
        packageT.setCommand("searchPBySmth");
        packageT.addString(smth);
        try{
            list = db.searchPersonBySmth(packageT);
        }
        catch (Exception e)
        {
            System.out.println("The person could not be found");
            return null;

        }


        return list;
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
        Party party1 = db.createParty(packageT);


        if (party1 == null)
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return party1;

    }

    public Person registerPerson(Person person) {

        Package packageT = new Package();
        packageT.setCommand("registerPerson");
        packageT.addPerson(person);

        try {
            Person person1 = db.registerPerson(packageT);
            return person1;
        }
        catch (Exception e)
        {
            System.out.println("The registration failed");
            e.printStackTrace();
            return null;
        }



    }

    public Person login(Person person) {

        Package packageT = new Package();
        packageT.setCommand("login");
        packageT.addPerson(person);

        try
        {
            Person person1 = db.login(packageT);
            return person1;

        }
        catch (Exception e)
        {
            System.out.println("The login failed");
            e.printStackTrace();
            return null;
        }

    }

    public List<Party> getParties(Person person) {

        Package packageT = new Package();
        packageT.setCommand("getPartiesForPerson");
        packageT.addPerson(person);

        try {
            List<Party> list = db.getPartiesForPerson(packageT);
            return list;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("The parties could not be found");
            return null;

        }


    }
}
