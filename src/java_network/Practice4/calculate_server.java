package java_network.Practice4;
import java.util.*;
import java.io.*;
import java.net.*;
public class calculate_server {
    public static void main(String[] args) {
        ServerSocket listener = null;
        Socket socket=null;
        Scanner scan = null;
        DataInputStream br =null;
        DataOutputStream bw=null;

        try{
            listener = new ServerSocket(1009);
            System.out.println("Socket ready.");
            socket = listener.accept();
            System.out.println("Socket established!");
            bw = new DataOutputStream(socket.getOutputStream());
            br = new DataInputStream(socket.getInputStream());
            String str;
            while(true){

            str =  br.readUTF();
            if(str.equalsIgnoreCase("bye"))
            {
                System.out.println("end");
                break;
            }

            StringTokenizer st = new StringTokenizer(str);
                System.out.println(str);

            if(str.charAt(1)=='+')
            {
                int num=0;
                num= str.charAt(0)-48+(str.charAt(2)-48);
                bw.writeInt(num);
                bw.flush();
            }
            else if(str.charAt(1)=='-')
            {
                int num=0;
                num= str.charAt(0)-48-(str.charAt(2)-48);
                bw.writeInt(num);
                bw.flush();
            }
            else if(str.charAt(1)=='*')
            {
                int num=0;
                num= (str.charAt(0)-48)*(str.charAt(2)-48);
                bw.writeInt(num);
                bw.flush();
            }

            }
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        finally{
           try {
                socket.close();
                listener.close();
            } catch (IOException e) {
                System.out.println("error.");
            }
        }
    }
}
