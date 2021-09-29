package java_network.practice_date;

import java.io.*;
import java.net.*;
import java.util.Date;

public class DateServer
{
    public static void main(String[] args) throws IOException {
        ServerSocket welcomeSocket;
        String clientSentence;
        String capitalizedSentence;
        int nPort;
        nPort = 6789;
        welcomeSocket = new ServerSocket(nPort);
        System.out.println("Server start.. (port#=" + nPort + ")\n");

        while(true) {
            Socket connectionSocket = welcomeSocket.accept();
            DataOutputStream  outToClient =
                    new DataOutputStream(connectionSocket.getOutputStream());

            capitalizedSentence = new Date().toString();

            outToClient.writeBytes(capitalizedSentence);
        }
    }
}

