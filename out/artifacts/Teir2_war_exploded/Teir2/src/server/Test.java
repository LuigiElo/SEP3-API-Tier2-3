package server;

import domain.Party;
import domain.Person;

import java.io.IOException;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {

        System.out.println("ss");
        PartyPlannerService service = new PartyPlannerService();

        System.out.println("ssss");
        Party newParty = new Party("PartyTitle", "TestForDatabase", "VIAUniversity", 1, "24-11-2019", "9:34");
//        Party s = service.createParty(newParty);
//        System.out.println(s.toString());

        Person person1 = new Person(7, "Roxana Spiridon", "RizerSpiner", "rizer@spiri.com", "cucucyda", false);
//        Person person11 = service.registerPerson(person1);
//        System.out.println(person11.getPassword());
//        newParty.getPeople().add(person1);
//        String re = service.addPerson(newParty);
//        System.out.println(re);

//        Person person = service.loginPerson(person1);
//        System.out.println(person.getEmail());
//
//        List<Party> parties = service.getParties(person1);
//        for (Party party: parties)
//        {
//            System.out.println(parties.toString());
//        }


        List<Person> people = service.searchPerson("rizer@spiri.com");
        for (Person person:people)
        {
            System.out.println(person.getUsername());
        }
        System.out.println("sssssssss");
    }
}