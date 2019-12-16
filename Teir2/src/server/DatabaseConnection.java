package server;

import domain.*;
import domain.Package;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class DatabaseConnection {

    private final int PORT = 1999;
    private final String HOST = "localHost";


    private Socket socket;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;

    public DatabaseConnection()
    {

    }

    private void createSocket()
    {
        try {
            socket = new Socket(HOST, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("The socket has not been created");
        }


    }

    public String addPerson(Package packageT) {

        createSocket();
        try {
            out.writeObject(packageT); //sending
            String result = (String) in.readObject(); //receive
            socket.close();
            return result;
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return "fail";
        }
    }
    //Maybe these should be returning List<Person>? Or are we just gonna call the getParty()
    //after adding/removing people/items? todo
    public String addPeople(Package packageT) throws Exception {

        createSocket();
        try {
            out.writeObject(packageT); //sending
            String result = (String) in.readObject(); //receive
            System.out.println(result + "is the result of adding people");
            socket.close();
            return result;
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
             throw new Exception("Adding people didn't work");
        }
    }
    //Maybe these should be returning List<Person>? Or are we just gonna call the getParty()
    //after adding/removing people/items? todo
    public String removePeople(Package packageT) throws Exception {

        createSocket();
        try {
            out.writeObject(packageT); //sending
            String result = (String) in.readObject(); //receive
            socket.close();
            return result;
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            throw new Exception("Removing people didn't work");
        }
    }

    public List<Person> searchPersonBySmth(Package packageT) {

        createSocket();
        try{
            out.writeObject(packageT);
            List<Person> list = (List<Person>) in.readObject();
            socket.close();
            return list;
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            System.out.println("The list is empty or smth is wrong");
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }

    }

    public Person registerPerson(Package packageT) {

        createSocket();
        try {
            out.writeObject(packageT);
            Person person = (Person) in.readObject();
            socket.close();
            return person;
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }



    }

    public List<Party> getPartiesBySomething(Package packageT) {
        return null;
    }

    public String setPartyPrivacy(Package packageT) {
        createSocket();

        try {
            out.writeObject(packageT);
            String reply = (String) in.readObject();
            socket.close();
            return reply;
        }
        catch (Exception e) {
            try {
                socket.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    public List<Party> getPartiesForPerson(Package packageT) {
        createSocket();
        try {
            out.writeObject(packageT);
            List<Party> list = (List<Party>) in.readObject();
            socket.close();
            return list;
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();

            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    public Party getParty(Package packageT) {
        createSocket();
        try {
            out.writeObject(packageT);
            Party party = (Party) in.readObject();
            socket.close();
            return party;
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();

            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    public List<Person> getParticipants(Package packageT) {
        return null;
    }

    public List<Item> getItems(Package packageT) {

        createSocket();
        try {
            out.writeObject(packageT);
            List<Item> list = (List<Item>) in.readObject();
            socket.close();
            return list;
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();

            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }

    }

    public List<Person> getPeopleByName(Package packageT){
        return null;
    }

    public String addParticipant(Package packageT) {
        return null;
    }

    public String addItem(Package packageT) {
        return null;
    }

    public Person createPerson(Package packageT) {
        return null;
    } //register

    public Item createItem(Package packageT) {
        return null;
    }

    public Party updateParty(Package packageT) {

        createSocket();

        try {
            out.writeObject(packageT);
            Party party = (Party) in.readObject();
            socket.close();
            return party;
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            System.out.println("Something fucked uppppp!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    public void updatePerson(Package packageT) {}

    public void removeParticipant(Package packageT) {}

    public String removeItems(Package packageT) throws Exception {

            try {
                createSocket();
                out.writeObject(packageT);
                String result = (String) in.readObject();
                socket.close();
                return result;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();

                try {
                    socket.close();
                } catch (IOException ex) {
                }
                throw new Exception("Removing items didn't work");
            }
    }

    public Party createParty(Package packageT) throws IOException {

        createSocket();

        try {
            out.writeObject(packageT);
            Party party = (Party) in.readObject();
            socket.close();
            return party;
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            System.out.println("Something fucked uppppp!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

            socket.close();
            return null;
        }

    }

    public Person login(Package packageT) {
        createSocket();
        try {
            out.writeObject(packageT);
            Person person = (Person) in.readObject();
            socket.close();
            return person;
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException ex) {
                e.printStackTrace();
            }
            return null;
        }


    }

    public String addItems(Package packageT) throws Exception {

        createSocket();
        try{
            out.writeObject(packageT);
            String result = (String) in.readObject();
            socket.close();
            return result;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

            try {
                socket.close();
            } catch (IOException ex) {
            }
            throw new Exception("Adding items didn't work");
        }
    }

    public Party updatePartyP(Package packageT) {

        createSocket();
        try{
            out.writeObject(packageT);
            Party result = (Party) in.readObject();
            socket.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();

            try {
                socket.close();
            } catch (IOException ex) {
            }
            return null;
        }
    }

    public List<Invitation> getInvitations(Package packageT)
    {
        createSocket();
        try{
            out.writeObject(packageT);
            List<Invitation> result = (List<Invitation>) in.readObject();
            socket.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();

            try {
                socket.close();
            } catch (IOException ex) {
            }
            return null;
        }
    }
}
