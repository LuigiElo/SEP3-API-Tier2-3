package server;

import domain.Party;
import domain.Person;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {

        System.out.println("ss");
        PartyPlannerService service = new PartyPlannerService();

        System.out.println("ssss");
        //Party newParty = new Party("PartyTitle", "TestForDatabase", "VIAUniversity", 1, "24-11-2019", "9:34", false);
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


//        List<Person> people = service.searchPerson("rizer@spiri.com");
//        for (Person person:people)
//        {
//            System.out.println(person.getUsername());
//        }
//        System.out.println("sssssssss");


//        Item item1 = new Item(23, "Chips");
//        newParty.getItems().add(item1);
//        Item item113 = new Item(7.9, "Faxe Kondi");
//        newParty.getItems().add(item113);
//
//        Item item11 = new Item(8.899999618530273, "Coca Cola");
//        newParty.getItems().add(item11);
//        String sr =service.addItems(newParty);
//        System.out.println(sr);


        //-------Privacy of party added

       // Party partyP = new Party("TestParty", "TestForDatabase2", "VIA UNI", 10,"29/11/2019", "12:00", true);
//        Party p = service.createParty(partyP);

//        System.out.println(p.toString());

    }
}
