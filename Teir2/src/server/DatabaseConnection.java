package server;

import domain.Package;
import domain.Party;
import domain.Person;

import javax.net.ssl.HostnameVerifier;
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

    public Party createParty(Package packageT) throws IOException {

        createSocket();
        out.writeObject(packageT);

        try {
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

    public String addPerson(Package packageT) {

        createSocket();
        try {
            out.writeObject(packageT);
            String result = in.readUTF();
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
            System.out.println("The list is emphy or smth is wrong");
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
}
