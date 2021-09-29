package java_network.practice3;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Scanner;
public class DateServer {
    public static void main(String[] args) throws IOException {
        ServerSocket listener= null;
        Scanner stin = null;
        Socket socket = null;

        try{
            listener = new ServerSocket(5555);

            System.out.println("Start Server...");
            System.out.println("Waiting for clients");

            socket = listener.accept();
            System.out.println("A new connection has been established!");
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            pw.print(new Date().toString());
            pw.flush();
        }
        catch (IOException e) {

            System.out.println(e.getMessage());

        }
        finally {

            try {
                socket.close();
                listener.close();
            } catch (IOException e) {
                System.out.println("error.");
            }
    }
}}
