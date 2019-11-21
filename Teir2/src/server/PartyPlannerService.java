package server;

import domain.Party;
import domain.Person;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

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

//   @POST
//    @Path("/addPerson")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String addPerson(Person person, Party party) throws IOException
//       {}
}
