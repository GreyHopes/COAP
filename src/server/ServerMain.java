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
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error while creating socket... Terminating");
                return;
            }
        }
    }
}
