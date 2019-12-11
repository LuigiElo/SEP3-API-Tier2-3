package server;

import domain.BoxTier2;
import domain.Package;
import domain.Party;
import domain.Person;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ModelManager {

    private DatabaseConnection db;

    public ModelManager() {
        this.db = new DatabaseConnection();
    }


    public void updateParty(BoxTier2 box) {

        if (box.getItemsAdded().size()>=1){
            Package packageT = new Package();
            packageT.setCommand("addItem");
            for (int i = 0; i < box.getItemsAdded().size(); i++) {
                packageT.addItem(box.getItemsAdded().get(i));
            }
                db.addItems(packageT);
        }
        else {
            System.out.println("adding item doesn't work");
        }

        if (box.getItemsRemoved().size()>=1) {
            Package packageT = new Package();
            packageT.setCommand("removeItem");

            for (int i = 0; i < box.getItemsRemoved().size(); i++) {
                packageT.addItem(box.getItemsRemoved().get(i));
            }
            db.removeItems(packageT);
        }
        else {
            System.out.println("removing item doesn't work");
        }

        if (box.getPeopleAdded().size()>=1) {
            Package packageT = new Package();
            packageT.setCommand("addPeople");

            for (int i = 0; i < packageT.getPeople().size(); i++) {
                packageT.addPerson(box.getPeopleAdded().get(i));
            }
            db.addPeople(packageT);
        }
        else {
            System.out.println("adding people doesn't work");
        }

        if (box.getPeopleRemoved().size()>=1) {
            Package packageT = new Package();
            packageT.setCommand("removePeople");

            for (int i = 0; i < box.getPeopleRemoved().size(); i++) {
                packageT.addPerson(box.getPeopleRemoved().get(i));
            }
            db.removePeople(packageT);
        }
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

    public String addPerson(Party party)
    {
        Package packageT = new Package();
        packageT.setCommand("addLastPersonToParty");
        packageT.addParty(party);
        String result = db.addPerson(packageT);
        return result;
    }

    public String addPeople(Party party) {
        Package packageT = new Package();
        packageT.setCommand("addPeople");
        packageT.addParty(party);
        String result = db.addPeople(packageT);
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

    public String addItems(Party party) {

        Package packageT = new Package();
        packageT.setCommand("addItems");
        packageT.addParty(party);

        try {

            String result = db.addItems(packageT);
            return result;
        }
        catch (Exception e)
        {
            System.out.println("We couldn't add the items");
            e.printStackTrace();
            return "fail";
        }
    }

    public String removeItems(Party party) {

        Package packageT = new Package();
        packageT.setCommand("removeItems");

        for (int i = 0; i < party.getItems().size(); i++) {
            packageT.addItem(party.getItem(i));
        }

        try {

            String result = db.addItems(packageT);
            return result;
        }
        catch (Exception e)
        {
            System.out.println("We couldn't add the items");
            e.printStackTrace();
            return "fail";
        }
    }

    public boolean setPartyPrivacy(boolean privacy, Party party){

        Package packageT = new Package();
        packageT.setCommand("setPartyPrivacy");
        party.setPrivate(privacy);
        packageT.addParty(party);

        db.setPartyPrivacy(packageT);
        return privacy;
    }
}
