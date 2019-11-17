import domain.Party;
import model.ModelManager;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.GregorianCalendar;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/helloworld")
public class HelloWorld {


    /*@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(){
        return "Hello,I am text!";
    }


    @POST
    @Produces(MediaType.TEXT_XML)
    public String sayXMLHello() {
        return "<?xml version=\"1.0\"?>" + "<hello> Hello,I am xml!" + "</hello>";
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html> " + "<title>" + "Hello Jersey" + "</title>"
                + "<body><h1>" + "Hello,I am html!" + "</body></h1>" + "</html> ";
    } */

    /*@GET
    @Path("/fuckyou")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Party sayJSONHello() {
        GregorianCalendar calendar = new GregorianCalendar();
        Party party = new Party("dddddddddd","dd", "time","ID", date, time);

        return party;
    }*/

    @POST
    @Path("/party")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getJSONHello(Party party) {
        Test test = new Test();
        System.out.println(party);
        ModelManager mm = new ModelManager();
        mm.createParty(party);
         return party.toString();
    }


}

