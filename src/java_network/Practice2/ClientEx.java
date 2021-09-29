package java_network.Practice2;


import java.io.*;
import java.net.*;

public class ClientEx {

    public static void main(String[] args) {
        BufferedReader in = null;
        BufferedReader stin = null;
        BufferedWriter out = null;

        Socket socket = null;

        try {
            socket = new Socket("localhost", 4321);


            stin = new BufferedReader(new InputStreamReader(System.in));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String outputMessage;
            while (true) {
                outputMessage = stin.readLine();
                if (outputMessage.equalsIgnoreCase("bye")) {
                    out.write(outputMessage);
                    out.flush();
                    break;
                }
                out.write("client>"+outputMessage+"\n");
                out.flush();

                String inputMessage = in.readLine();
                System.out.println(inputMessage);

            }
        } catch (IOException e) {
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


