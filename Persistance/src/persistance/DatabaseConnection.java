package persistance;

import com.google.gson.Gson;
import models.Package;
import models.Party;
import utility.persistence.MyDatabase;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class DatabaseConnection implements Runnable {

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "08191";

    private MyDatabase db;
    private BufferedReader in;
    private PrintWriter out;

    private ObjectOutputStream out2;
    private ObjectInputStream in2;

    public DatabaseConnection(Socket socket) throws IOException {


        in2 = new ObjectInputStream(socket.getInputStream());
        out2 = new ObjectOutputStream(socket.getOutputStream());

        try {
            this.db = new MyDatabase(DRIVER, URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }





    public Party createParty(Party party) throws SQLException {

        String command ="INSERT INTO partyplanner.Party_table(partyTitle, description, address) VALUES\n" +
                "(?,?,?);";
        db.update(command, party.getPartyTitle(), party.getDescription(), party.getAddress());

        //needs to be changed with the actual thing
        return party;
    }

    @Override
    public void run() {

        try {
            Package packageR = (Package) in2.readObject();

            switch (packageR.getCommand())
            {
                case "createParty":
                    {

                        Object[]  objects = packageR.getObjects();
                        Party party = (Party) objects[0];
                        Party returnParty = createParty(party);
                        out2.writeObject(returnParty);
                        break;
                    }
                default: return;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Hellllllllllllllo");
        }

    }


}
