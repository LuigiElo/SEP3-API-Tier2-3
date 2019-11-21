package server;

import domain.Party;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {

        System.out.println("ss");
        PartyPlannerService service = new PartyPlannerService();

        System.out.println("ssss");
        Party newParty = new Party("Title", "Test", "VIA", null, "23-11-2019", "10:20");
        Party s = service.createParty(newParty);
        System.out.println(s.toString());
        System.out.println("sssssssss");
    }
}
