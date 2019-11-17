package server;

import models.Package;
import models.Party;

import javax.ws.rs.POST;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DatabaseConnection {

    private final int PORT = 6789;
    private final String HOST = "localHost";

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public DatabaseConnection()
    {

    }

    public Party createParty(Package packageT) throws IOException {
        Socket socket = new Socket(HOST, PORT);

        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());

        out.writeObject(packageT);
        try {
            Party party = (Party) in.readObject();
            return party;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something fucked uppppp!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }

        return null;
    }

}
