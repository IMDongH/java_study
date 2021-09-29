package java_network.practice3;

import java.io.*;
import java.net.*;
public class DateClient {
    public static void main(String[] args) throws Exception {
        BufferedReader in=null;
        Socket socket = null;
        try {

            socket = new Socket("localhost", 5555);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputMessage = in.readLine();
            System.out.println(inputMessage);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("error.");
            }
        }


    }
}
