package model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

public class MainDatabase {
    public static void main(String[] args) throws IOException {

        final int PORT = 1999;
        ServerSocket welcomeSocket= new ServerSocket(PORT);
        DatabaseConnection connection = new DatabaseConnection();
        while (true){
            System.out.println("Waiting for a client...");
            Socket socket = welcomeSocket.accept();
            connection.setSocket(socket);
            Thread clientThread= new Thread(connection);
            clientThread.start();}


//        DatabaseInterface db = new DatabaseConnection();
    }
}