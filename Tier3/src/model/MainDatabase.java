package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * EndPoint for the persistence component of the system;
 * Reachable through a Socket connection
 */
public class MainDatabase {
    /**
     * main method;
     * In a while the class waits for the connection initiates on another computer;
     * When accepted a thread is being created and runned;
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        final int PORT = 1999;
        ServerSocket welcomeSocket= new ServerSocket(PORT);
        DatabaseConnectionTier3 connection = new DatabaseConnectionTier3();
        while (true){
            System.out.println("Waiting for a client...");
            Socket socket = welcomeSocket.accept();
            connection.setSocket(socket);
            Thread clientThread= new Thread(connection);
            clientThread.start();}
    }
}