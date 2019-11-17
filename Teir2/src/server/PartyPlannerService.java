package server;

import models.Party;

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

       return manager.createParty(party);

   }
}
