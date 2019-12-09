package server;



import domain.BoxTier2;
import domain.Party;
import domain.Person;

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
       Person person1 = manager.login(person);
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
    @Path("/searchPerson")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Person> searchPerson(String smth)
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
        return false; //todo
    }

    @POST
    @Path("/updateParty")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public void updateParty(BoxTier2 box) {
        manager.updateParty(box);
    }

}
