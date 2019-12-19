package server;

import domain.*;
import domain.Invitation;
import domain.Package;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Java class used to receive the information delivered by the client and wrap them in a format
 * readable for the rest of the system (Package) so that it can perform the correct command, or the action intended,
 * according to the respectful client wishes;
 */
public class ModelManager {

    /**
     * Instance of a DatabaseConnectionTier2 object used in transmitting the command Package to Tier 3 for execution;
     */
    private DatabaseConnectionTier2 db;

    /**
     * Constructor for the ModelManager object;
     * Initializes the DatabaseConnectionTier2 object variable;
     */
    public ModelManager() {
        this.db = new DatabaseConnectionTier2();
    }

    /**
     * Method used for updating/ modifying a Party object stored by the system;
     * Changes include:
     * -add items;
     * -remove Items;
     * -add people
     * -remove people
     * For each of the procedures a Package object is created with a respectable command and the needed objects
     * needed for the execution of the procedure are set;
     * In case of add people an invitation will be created instead;
     *
     * If the case one of the procedures fails (result is an Exception thrown) the method will return null;
     *
     * Lastly the method attempt to retrieve the modified Party using the same set of actions;
     *
     * @param box
     * @return Party
     */
    public Party updateParty(BoxTier2 box) {

        Party party = box.getParty();

        try {
            if (box.getItemsAdded().size() >= 1) {
                Package packageT = new Package();
                packageT.addParty(party);
                packageT.setCommand("addItems");
                for (int i = 0; i < box.getItemsAdded().size(); i++) {
                    packageT.addItem(box.getItemsAdded().get(i));
                }
                db.addItems(packageT);
            } else {
                System.out.println("adding item doesn't work");
            }

            if (box.getItemsRemoved().size() >= 1) {
                Package packageT = new Package();
                packageT.addParty(party);
                packageT.setCommand("removeItems");

                for (int i = 0; i < box.getItemsRemoved().size(); i++) {
                    packageT.addItem(box.getItemsRemoved().get(i));
                }
                db.removeItems(packageT);
            } else {
                System.out.println("removing item doesn't work");
            }

            if (box.getPeopleAdded().size() >= 1) {
                Package packageT = new Package();
                packageT.addParty(party);
                packageT.setCommand("makeInvitation");

                for (int i = 0; i < box.getPeopleAdded().size(); i++) {
                    packageT.addPerson(box.getPeopleAdded().get(i));
                }
                db.addPeople(packageT);
            } else {
                System.out.println("adding people doesn't work");
            }

            if (box.getPeopleRemoved().size() >= 1) {
                Package packageT = new Package();
                packageT.addParty(party);
                packageT.setCommand("removePeople");

                for (int i = 0; i < box.getPeopleRemoved().size(); i++) {
                    packageT.addPerson(box.getPeopleRemoved().get(i));
                }
                db.removePeople(packageT);
            } else {
                System.out.println("removing people doesn't work");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something happened in the changes");
            return null;
        }


        Package packageForesult = new Package();
        packageForesult.setCommand("getParty");
        packageForesult.addParty(party);

        try {

            Party finalResult = db.getParty(packageForesult);
            return finalResult;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("I could not get the party after the changes");
            return null;
        }
    }

    /***
     *  Method used in retrieving users that met a specific criteria specified by the parameter;
     *
     *  A Package object is being created with the command "searchPBySmth" and the parameter is
     *  being added/attached to the Package;
     *
     *  It then refers to the DatabaseConnectionTier2 variable that transmits the Package command for execution.
     *  The result received by the ModelManager through the  DatabaseConnectionTier2 variable is either a List<Person></>
     *  or null if the procedure a) failed and was aborted or b) no match was found
     *
     * @param smth
     * @return List<Person>
     */
    public List<Person> searchPersonBySomething(String smth) {
        List<Person> list = new ArrayList<Person>();

        Package packageT = new Package();
        packageT.setCommand("searchPBySmth");
        packageT.addString(smth);
        try {
            list = db.searchPersonBySmth(packageT);
        } catch (Exception e) {
            System.out.println("The person could not be found");
            return null;

        }
        return list;
    }

    public String addPerson(Party party) {
        Package packageT = new Package();
        packageT.setCommand("addLastPersonToParty");
        packageT.addParty(party);
        String result = db.addPerson(packageT);
        return result;
    }

    /**
     *  Method used to add new participants to a Party object stored in the system;
     *
     *  A Package object is being created with the command "addPeople" and the parameter is
     *  being added/attached to the Package;
     *
     *  It then refers to the DatabaseConnectionTier2 variable that transmits the Package command for execution.
     *  The result received by the ModelManager through the  DatabaseConnectionTier2 variable is either a String "success"
     *  or a String "fail" if the procedure failed and was aborted at some point
     * @param party
     * @return String
     * @throws Exception
     */

    public String addPeople(Party party) throws Exception {
        Package packageT = new Package();
        packageT.setCommand("addPeople");
        packageT.addParty(party);
        String result = db.addPeople(packageT);
        return result;
    }

    /***
     * Method used to create a new Party in the system.
     *
     *  A Package object is being created with the command "createParty" and the parameter is
     *  being added/attached to the Package;
     *
     *  It then refers to the DatabaseConnectionTier2 variable that transmits the Package command for execution.
     *  The result received by the ModelManager through the  DatabaseConnectionTier2 variable is either a Party
     *  object (with persistence only generated data) or null if the procedure failed and was aborted at some point
     * @param party
     * @return Party
     * @throws IOException
     */

    public Party createParty(Party party) throws IOException {

        Package packageT = new Package();
        packageT.setCommand("createParty");
        packageT.addParty(party);
        Party party1 = db.createParty(packageT);

        return party1;

    }

    /**
     * Method used to register a new User of the system;
     *
     *  A Package object is being created with the command "registerPerson" and the parameter is
     *  being added/attached to the Package;
     *
     *  It then refers to the DatabaseConnectionTier2 variable that transmits the Package command for execution.
     *  The result received by the ModelManager through the  DatabaseConnectionTier2 variable is either a Person
     *  object (with persistence only generated data) or null if the procedure failed and was aborted at some point
     * @param person
     * @return Person
     */
    public Person registerPerson(Person person) {

        Package packageT = new Package();
        packageT.setCommand("registerPerson");
        packageT.addPerson(person);

        try {
            Person person1 = db.registerPerson(packageT);
            return person1;
        } catch (Exception e) {
            System.out.println("The registration failed");
            e.printStackTrace();
            return null;
        }


    }

    /***
     * Method used for verification the credentials of a user that attempts to login;
     *
     *  A Package object is being created with the command "login" and the parameter is
     *  being added/attached to the Package;
     *
     *  It then refers to the DatabaseConnectionTier2 variable that transmits the Package command for execution.
     *  The result received by the ModelManager through the  DatabaseConnectionTier2 variable is either a Person
     *  object (with persistence only generated data) or null if a)the procedure failed and was aborted at some point
     *  b) the credentials provided by the parameter don't match with those from the system
     * @param person
     * @return Person
     */

    public Person login(Person person) {

        Package packageT = new Package();
        packageT.setCommand("login");
        packageT.addPerson(person);

        try {
            Person person1 = db.login(packageT);
            return person1;

        } catch (Exception e) {
            System.out.println("The login failed");
            e.printStackTrace();
            return null;
        }

    }

    /***
     * Method used to retrieve the Parties of a specific person/user of the system (either he/she participates
     * or hostess the party);
     *
     *  A Package object is being created with the command "getPartiesForPerson" and the parameter is
     *  being added/attached to the Package;
     *
     *  It then refers to the DatabaseConnectionTier2 variable that transmits the Package command for execution.
     *  The result received by the ModelManager through the  DatabaseConnectionTier2 variable is either a
     *  List<Person></Person> object (with persistence only generated data) or null if the procedure failed and was aborted at some point
     * @param person
     * @return
     */
    public List<Party> getParties(Person person) {

        Package packageT = new Package();
        packageT.setCommand("getPartiesForPerson");
        packageT.addPerson(person);

        try {
            List<Party> list = db.getPartiesForPerson(packageT);
            return list;
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            System.out.println("We couldn't add the items");
            e.printStackTrace();
            return "fail";
        }
    }


    public boolean setPartyPrivacy(boolean privacy, Party party) {

        Package packageT = new Package();
        packageT.setCommand("setPartyPrivacy");
        party.setPrivate(privacy);
        packageT.addParty(party);

        //this is not very correct. In case where smth fucks up, the server will throw an exception, break, but the
        //client will not see that because you are not returning him that,
        //only a half way response
        db.setPartyPrivacy(packageT);
        return privacy;
    }

    public List<Item> getItems(int partyId) {
        Package packageT = new Package();
        packageT.setCommand("getItemsForParty");
        Party party = new Party();
        party.setPartyID(partyId);
        packageT.addParty(party);

        List<Item> items = db.getItems(packageT);
        return items;
    }


    /***
     *  Method used to make modifications to the details of a certain Party of the system;
     *
     *  A Package object is being created with the command "updatePartyD" and the parameter is
     *  being added/attached to the Package;
     *
     *  It then refers to the DatabaseConnectionTier2 variable that transmits the Package command for execution.
     *  The result received by the ModelManager through the  DatabaseConnectionTier2 variable is either a Party
     *  object or null if the procedure failed and was aborted at some point
     * @param party
     * @return
     */
    public Party updatePartyD(Party party) {
        Package packageT = new Package();
        packageT.setCommand("updatePartyD");
        packageT.addParty(party);

        Party result = db.updatePartyP(packageT);
        return result;
    }

    /***
     * Method used to retrieve the Notifications/Invitations of a user/Person in the system specified
     * by the parameter;
     *
     *  A Package object is being created with the command "getInvitations" and the parameter is
     *  being added/attached to the Package;
     *
     *  It then refers to the DatabaseConnectionTier2 variable that transmits the Package command for execution.
     *  The result received by the ModelManager through the  DatabaseConnectionTier2 variable is either a
     *  List<Inviatation></> object or null if the procedure failed and was aborted at some point
     * @param personID
     * @return List<Inviatation></Inviatation>
     */
    public List<Invitation> getInvitations(int personID) {
        Package packageT = new Package();
        Person person = new Person();
        person.setPersonID(personID);
        packageT.addPerson(person);
        packageT.setCommand("getInvitations");

        List<Invitation> invitations = db.getInvitations(packageT);
        return invitations;
    }

    /**
     * Method used to change the status of an invitation stored in the system;
     *
     *  A Package object is being created and the parameter is
     *  being added/attached to the Package;
     *  If the Invitation parameter status is "accepted", the Package command is set to "acceptInvite",
     *  and "declineInvite" otherwise;
     *
     *  It then refers to the DatabaseConnectionTier2 variable that transmits the Package command for execution.
     *  The result received by the ModelManager through the  DatabaseConnectionTier2 variable is either a
     *  String "success"  or a String "fail" if the procedure failed and was aborted at some point
     * @param invitation
     * @return
     */
    public String answerInvitation(Invitation invitation) {
        Package packageT = new Package();
        packageT.setInvitation(invitation);

        String result;
        if (invitation.getStatus().equals("accepted")) {
            packageT.setCommand("acceptInvite");
            result = db.answerInvite(packageT);
        } else {
            packageT.setCommand("declineInvite");
            result = db.answerInvite(packageT);
        }
        return result;
    }
}
