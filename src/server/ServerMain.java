package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain
{
    public static void main(String[] args)
    {
        ServerSocket serverSocket;
        Socket clientSocket;

        try
        {
            System.out.println("Starting CoAP Server...");
            serverSocket = new ServerSocket(5683);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while creating socket... Terminating");
            return;
        }

        while (true)
        {
            try
            {
                System.out.println("Waiting for connections...");
                clientSocket = serverSocket.accept();

                System.out.println("Launching thread for client at "+clientSocket.getInetAddress().toString());
                ServerThread thread = new ServerThread(clientSocket);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error while creating socket... Terminating");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error while creating socket... Terminating");
                return;
            }
        }
    }
}
