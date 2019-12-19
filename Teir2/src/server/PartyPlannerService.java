package server;



import domain.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

/**
 * Java class used as export for the webservice;
 * The purpose of this class is to redirect the request of an Http Client
 * to a command executable by the server component of the application
 *
 * */

@Path("/partyservice")
public class PartyPlannerService{

/**
 * An variable of a ModelManager object;
 * Used to redirect the command that needs to be executed and retrive the result of the action;
 * */
    private ModelManager manager;

    /**
     * Constructor of the partyPlannerService object;
     * Initializes an variable of the ModelManager class
     * */
    public PartyPlannerService() {

        this.manager = new ModelManager();
    }

    /***
     * Method used to create a party at the request of an HttpClient;
     * The Party parameter is provided by the web service and serialized using JSON;
     *
     * It references the ModelManager object to execute the command and retrive the
     * result which is sent back to the HttpClient using the web service provided;
     *
     * The result is either a Party object, meaning the object has been created successfully or
     * null, meaning the procedure failed and was aborted
     * @param party
     * @return Party
     * @throws IOException
     */
   @POST
   @Path("/createparty")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
    public Party createParty(Party party) throws IOException {


       Party party2 = manager.createParty(party);
       return party2;

   }

    /***
     * Method used for the registration of a new user of the system;
     * The Person parameter is provided by the web service and serialized using JSON;
     *
     * It references the ModelManager object to execute the command and retrive the
     * result which is sent back to the HttpClient using the web service provided;
     *
     *The result is either a Person object, meaning the object has been created successfully or
     *null, meaning the procedure failed and was aborted
     *
     * @param person
     * @return Person
     */
   @POST
   @Path("/register")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
    public Person registerPerson(Person person)
   {

       Person person1 = manager.registerPerson(person);
       return person1;
   }


    /***
     * Method used for login of an already registered user of the system;
     * The Person parameter is provided by the web service and serialized using JSON;
     *
     * It references the ModelManager object to execute the command and retrive the
     * result which is sent back to the HttpClient using the web service provided;
     *
     *The result is either a Person object, meaning the object has been created successfully or
     *null, meaning the procedure failed and was aborted
     *
     * @param person
     * @return Person
     */
   @POST
   @Path("/login")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
    public Person loginPerson(Person person)
   {

       Person person1 = manager.login(person);

       return person1;

   }

    /***
     * Method used to retrive the parties in which a client participates upon login;
     * As parameter the unique id of the user is used and provided in the path describing the service @PathParam("personId");
     * For convenience the id is used to create a facade Person object used in the execution of the command;
     *
     * It references the ModelManager object to execute the command and retrive the
     * result which is sent back to the HttpClient using the web service provided;
     *
     * The result is either a List<Party></> in case favorable or null in case the procedure failed and was aborted
     * @param personId
     * @return List<Party>
     */

   @GET
   @Path("/getPartiesForPerson/{personId}")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
    public List<Party> getParties(@PathParam("personId") int personId)
   {
       Person person = new Person(personId, null, null, null, null,  false);
       List<Party> parties = manager.getParties(person);

       return parties;
   }

    /***
     * Method used to retrieve certain Users (Person object) that meet the criteria defined by the @PathParam("smth");
     *
     * It references the ModelManager object to execute the command and retrieve the
     * result which is sent back to the HttpClient using the web service provided;
     *
     * The result is either a List<Person></> in case favorable or null in case the procedure failed and was aborted
     * @param smth
     * @return List<Person></Person>
     */
    @GET
    @Path("/searchPerson/{smth}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Person> searchPerson(@PathParam("smth") String smth)
    {
        List<Person> people = manager.searchPersonBySomething(smth);
        return people;
    }

    @POST
    @Path("/addPeople")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addPeople(Party party)
    {
        String s ="fail";
        return s;
    }

    @POST
    @Path("/addItems")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public String addItems(Party party)
    {
        String s = "fail";
        s = manager.addItems(party);
        return s;
    }

    @POST
    @Path("/changePrivacy")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public boolean setPartyPrivacy(boolean privacy, Party party) {
        return manager.setPartyPrivacy(privacy,party);
    }

    /**
     *Method used to make modifications to a certain Party; The changes include:
     * -adding items;
     * -adding people
     * -removing items
     * -removing people
     * It references the ModelManager object to execute the updates and retrieve the
     * result which is sent back to the HttpClient using the web service provided;
     *
     * The result is either the Party object with the wanted changes if in case favorable or null if the procedure failed and was aborted;
     * @param box
     * @return Party
     */
    @POST
    @Path("/updateParty")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public Party updateParty(BoxTier2 box)
    {

        Party party = manager.updateParty(box);
        return party;
    }

    /***
     * Method used for retrieving the items of a Party stored in the system;
     *
     * It references the ModelManager object to execute the updates and retrieve the
     * result which is sent back to the HttpClient using the web service provided;
     *
     * The result is either a List<Item></Item> in case favorable or null if the procedure failed and was aborted
     * @param partyId
     * @return
     */
    @GET
    @Path("getItemsForParty/{partyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Item> getItemsForParty(@PathParam("partyId") int partyId)
    {
        List<Item> items = manager.getItems(partyId);
        return items;
    }

    /****
     *Method used to make modifications to a Party stored in the system; Modifications include:
     * -change title;
     * -change date
     * -change time;
     * -change location
     * -change description
     * -change privacy
     *
     * It references the ModelManager object to execute the updates and retrieve the
     * result which is sent back to the HttpClient using the web service provided;
     *
     * The result is either the Party object with te wanted modifications or null if the procedure failed and was aborted;
     *
     * @param party
     * @return Party
     */
    @POST
    @Path("/updatePartyD")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Party updatePartyD(Party party)
    {


        Party result = manager.updatePartyD(party);
        return result;
    }

    /***
     * Method used to retrieve teh user notifications/invites upon login. The user for which the procedure is needed
     * is mentioned using the @PathParam("personID") ;
     *
     * It references the ModelManager object to execute the updates and retrieve the
     * result which is sent back to the HttpClient using the web service provided;
     *
     * The result is either a List<Invitations></Invitations> or null in case the procedure failed (ex. the personID is invalid)
     * and was aborted
     * @param personID
     * @return List<Invitation>
     */
   @GET
   @Path("/getNotifications/{personID}")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public List<Invitation> getInvitations(@PathParam("personID") int personID)
   {

       List<Invitation> invitations = manager.getInvitations(personID);
       return invitations;
   }

    /***
     * Method to change the status of an invitation for joining a Party.
     *
     * It references the ModelManager object to execute the updates and retrieve the
     * result which is sent back to the HttpClient using the web service provided;
     *
     * The result of the procedure is either a String "success" in case favorable or "fail" if the procedure
     * failed and was aborted; If the result is "success" the method return the invitation received from the HttpClient or
     * null if the result is "fail".
     *
     * @param invitation
     * @return
     */
   @POST
   @Path("/answerInvite")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Invitation answerInvite(Invitation invitation)
   {
       String result = manager.answerInvitation(invitation);
       if (result.equals("success"))
       {
           return invitation;
       }
       else return null;
   }

}
