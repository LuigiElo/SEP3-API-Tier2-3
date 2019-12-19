package server;

import domain.*;
import domain.Package;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Java class used in transmitting information and commands to another component of the system through
 * Sockets(using a TCP protocol), for execution and receiving the result of the action;
 */

public class DatabaseConnectionTier2 {

    /***
     * Instances used in defining and creating the Socket connection;
     */
    private final int PORT = 1999;
    private final String HOST = "localHost";

    /**
     * Instances relevant in the transitions of information (sending and receiving);
     * Initially there are set to null to be initialized with new parameters each time there is a need of
     * sending and receiving information
     */
    private Socket socket;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;

    /**
     * Empty constructor
     */
    public DatabaseConnectionTier2()
    {

    }

    /***
     * Creates a new Socket connection and prepares the ObjectOutputStream and  ObjectInputStream
     * for incoming data that needs to be sent or received;
     */
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


    public synchronized String addPerson(Package packageT) {

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


    /**
     * Calls createSockets() in preparation to sending a Package command;
     * Sends out the package using the ObjectOutputStream variable;
     * The method waits for the result (String) which it received using the ObjectInputStream variable;
     * The socket connection is closed and the result is being returned.
     *
     * In the event the procedure fails an exception is being catch.
     * In this variable the method throws an Exception saying "Adding people didn't work";
     *
     * @param packageT
     * @return String
     * @throws Exception
     */
    public synchronized String addPeople(Package packageT) throws Exception {

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


    /**
     * Calls createSockets() in preparation to sending a Package command;
     * Sends out the package using the ObjectOutputStream variable;
     * The method waits for the result (String) which it received using the ObjectInputStream variable;
     * The socket connection is closed and the result is being returned.
     *
     * In the event the procedure fails an exception is being catch.
     * In this variable the method throws an Exception saying "Removing people didn't work";
     * @param packageT
     * @return String
     * @throws Exception
     */
    public synchronized String removePeople(Package packageT) throws Exception {

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

    /**
     * Calls createSockets() in preparation to sending a Package command;
     * Sends out the package using the ObjectOutputStream variable;
     * The method waits for the result List<Person> which it received using the ObjectInputStream variable;
     * The socket connection is closed and the result is being returned.
     *
     * In the event the procedure fails an exception is being catch.
     * In this variable the method returns null;
     * @param packageT
     * @return List<Person></Person>
     */
    public synchronized List<Person> searchPersonBySmth(Package packageT) {

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

    /**
     * Calls createSockets() in preparation to sending a Package command;
     * Sends out the package using the ObjectOutputStream variable;
     * The method waits for the result (Person object with persistence only generated data)
     * which it received using the ObjectInputStream variable;
     * The socket connection is closed and the result is being returned.
     *
     * In the event the procedure fails an exception is being catch.
     * In this variable the method returns null;
     * @param packageT
     * @return Person
     */
    public synchronized Person registerPerson(Package packageT) {

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

    public synchronized List<Party> getPartiesBySomething(Package packageT) {
        return null;
    }

    public synchronized String setPartyPrivacy(Package packageT) {
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

    /**
     * Calls createSockets() in preparation to sending a Package command;
     * Sends out the package using the ObjectOutputStream variable;
     * The method waits for the result List<Party> which it received using the ObjectInputStream variable;
     * The socket connection is closed and the result is being returned.
     *
     * In the event the procedure fails an exception is being catch.
     * In this variable the method returns null;
     * @param packageT
     * @return List<Party></Party>
     */
    public synchronized List<Party> getPartiesForPerson(Package packageT) {
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

    public synchronized Party getParty(Package packageT) {
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

    public synchronized List<Person> getParticipants(Package packageT) {
        return null;
    }

    public synchronized List<Item> getItems(Package packageT) {

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


    /**
     * Calls createSockets() in preparation to sending a Package command;
     * Sends out the package using the ObjectOutputStream variable;
     * The method waits for the result Party which it received using the ObjectInputStream variable;
     * The socket connection is closed and the result is being returned.
     *
     * In the event the procedure fails an exception is being catch.
     * In this variable the method closes the socket connection and returns null;
     * @param packageT
     * @return Party
     */
    public synchronized Party updateParty(Package packageT) {

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

    public void updatePerson(Package packageT) {}

    public synchronized void removeParticipant(Package packageT) {}

    public synchronized String removeItems(Package packageT) throws Exception {

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

    /**
     * Calls createSockets() in preparation to sending a Package command;
     * Sends out the package using the ObjectOutputStream variable;
     * The method waits for the result Party which it received using the ObjectInputStream variable;
     * The socket connection is closed and the result is being returned.
     *
     * In the event the procedure fails an exception is being catch.
     * In this variable the method closes the socket connection and returns null;
     *
     * @param packageT
     * @return
     * @throws IOException
     */
    public synchronized Party createParty(Package packageT) throws IOException {

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

    /**
     * Calls createSockets() in preparation to sending a Package command;
     * Sends out the package using the ObjectOutputStream variable;
     * The method waits for the result Person which it received using the ObjectInputStream variable;
     * The socket connection is closed and the result is being returned.
     *
     * In the event the procedure fails an exception is being catch.
     * In this variable the method closes the socket connection and returns null;
     * @param packageT
     * @return Person
     */
    public synchronized Person login(Package packageT) {
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

    /**
     * Calls createSockets() in preparation to sending a Package command;
     * Sends out the package using the ObjectOutputStream variable;
     * The method waits for the result String which it received using the ObjectInputStream variable;
     * The socket connection is closed and the result is being returned.
     *
     * In the event the procedure fails an exception is being catch.
     * In this variable the method closes the socket connection and returns null;
     * @param packageT
     * @return String
     * @throws Exception
     */
    public synchronized String addItems(Package packageT) throws Exception {

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

    /**
     * Calls createSockets() in preparation to sending a Package command;
     * Sends out the package using the ObjectOutputStream variable;
     * The method waits for the result Party which it received using the ObjectInputStream variable;
     * The socket connection is closed and the result is being returned.
     *
     * In the event the procedure fails an exception is being catch.
     * In this variable the method closes the socket connection and returns null;
     * @param packageT
     * @return Party
     */
    public synchronized Party updatePartyP(Package packageT) {

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

    /**
     * Calls createSockets() in preparation to sending a Package command;
     * Sends out the package using the ObjectOutputStream variable;
     * The method waits for the result Party which it received using the ObjectInputStream variable;
     * The socket connection is closed and the result is being returned.
     *
     * In the event the procedure fails an exception is being catch.
     * In this variable the method closes the socket connection and returns null;
     * @param packageT
     * @return List<Invitation></Invitation>
     */
    public synchronized List<Invitation> getInvitations(Package packageT)
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

    /**
     * Calls createSockets() in preparation to sending a Package command;
     * Sends out the package using the ObjectOutputStream variable;
     * The method waits for the result String which it received using the ObjectInputStream variable;
     * The socket connection is closed and the result is being returned.
     *
     * In the event the procedure fails an exception is being catch.
     * In this variable the method closes the socket connection and returns null;
     * @param packageT
     * @return
     */
    public synchronized String answerInvite(Package packageT) {

        createSocket();
        try{
            out.writeObject(packageT);
            String result = (String) in.readObject();
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
