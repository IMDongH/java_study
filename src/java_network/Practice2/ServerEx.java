package java_network.Practice2;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerEx {

    public static void main(String[] args) {

        ServerSocket listener = null;
        Socket socket = null;
        BufferedReader br = null;
        Scanner stin =null;
        BufferedWriter bw = null;
        try {
            listener = new ServerSocket(4321);
            socket = listener.accept();
            System.out.println("A new connection has been established!");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            stin = new Scanner(System.in);

            String input;

            while(true)
            {
                input = br.readLine();
                if(input.equalsIgnoreCase("bye"))
                {
                    System.out.println("bye");
                    break;
                }
                System.out.println(input);
                    String output = stin.nextLine();
                bw.write("Server>" + output+"\n");
                bw.flush();


            }
            
        }
        catch(IOException e){
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
        }
}

