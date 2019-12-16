package server;



import domain.*;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("/partyservice")
public class PartyPlannerService{


    private ModelManager manager;

    public PartyPlannerService() {

        this.manager = new ModelManager();
    }

   @POST
   @Path("/createparty")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
    public Party createParty(Party party) throws IOException {

//       System.out.println(party.toString());
//        return null;

       System.out.println("This is the party i got from the user");
       System.out.println(party.toString());
       System.out.println("And this is the actual party entity");
       Party party2 = manager.createParty(party);
       return party2;

   }

   @POST
   @Path("/register")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
    public Person registerPerson(Person person)
   {
       System.out.println("This is the person i got from the client");
       System.out.println(person.toString());
       Person person1 = manager.registerPerson(person);
       return person1;
   }

   @POST
   @Path("/login")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
    public Person loginPerson(Person person)
   {
       System.out.println("I am trying to log in");
       Person person1 = manager.login(person);
       System.out.println("The person is +"+ person1.getUsername());
       return person1;

   }

   @GET
   @Path("/getPartiesForPerson/{personId}")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
    public List<Party> getParties(@PathParam("personId") int personId)
   {
       Person person = new Person(personId, null, null, null, null,  false);
       List<Party> parties = manager.getParties(person);
       for (Party party: parties)
       {
           System.out.println("one party");
       }
       return parties;
   }

   ///make a new one for all the list of people
   @POST
    @Path("/addPerson")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addPerson(Party party) throws IOException
       {

           String result = manager.addPerson(party);
           return result;
       }

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

    @POST
    @Path("/updateParty")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public Party updateParty(BoxTier2 box)
    {
        System.out.println("I've reached the server");
        System.out.println(box.getParty().toString());
        for (Item item:box.getItemsAdded())
        {
            System.out.println("Item");
            System.out.println(item.toString());
        }

        for (Person person:box.getPeopleAdded())
        {
            System.out.println("Person");
            System.out.println(person.toString());
        }
        Party party = manager.updateParty(box);
        return party;
    }

    @GET
    @Path("getItemsForParty/{partyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Item> getItemsForParty(@PathParam("partyId") int partyId)
    {
        List<Item> items = manager.getItems(partyId);
        return items;
    }

    @POST
    @Path("/updatePartyD")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Party updatePartyD(Party party)
    {
        System.out.println("I've reached here!!!!!!!!!!!!!!!!!!!!!!!!!1111");
        System.out.println(party.toString());
        Party result = manager.updatePartyD(party);
        return result;
    }

   @GET
   @Path("/getNotifications/{personID}")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public List<Invitation> getInvitations(@PathParam("personID") int personID)
   {
       System.out.println("I am attemting to get my notifications");
       List<Invitation> invitations = manager.getInvitations(personID);
       return invitations;
   }

   @POST
   @Path("/answerInvite")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public String answerInvite(Invitation invitation)
   {
       String result = manager.answerInvitation(invitation);
       System.out.println("Result of the answer:" + result);
       return result;
   }

}
