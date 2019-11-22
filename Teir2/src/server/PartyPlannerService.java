package server;

import domain.Party;
import domain.Person;

import javax.annotation.PostConstruct;
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
        Party party1 = manager.createParty(party);
        System.out.println("bug1");
       return party1;

   }

   @POST
   @Path("/register")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
    public Person registerPerson(Person person)
   {
       Person person1 = manager.registerPerson(person);
       return person1;
   }

   @GET
   @Path("/login")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
    public Person loginPerson(Person person)
   {
       Person person1 = manager.login(person);
       return person1;

   }

   @GET
   @Path("/getPartiesForPerson")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
    public List<Party> getParties(Person person)
   {
       List<Party> parties = manager.getParties(person);
       return parties;
   }

//   @POST
//    @Path("/addPerson")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String addPerson(Person person, Party party) throws IOException
//       {}
}
