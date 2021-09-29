package java_network.Practice4;
import java.util.*;
import java.io.*;
import java.net.*;

public class calculate_client {
    public static void main(String[] args) {
        DataInputStream br =null;
        DataOutputStream bw=null;
        Scanner scan = null;

        Socket socket = null;
        try{
            socket = new Socket("127.0.0.1",1009);
            bw = new DataOutputStream(socket.getOutputStream());
            br = new DataInputStream(socket.getInputStream());
            while(true) {
                String a = "";
                scan = new Scanner(System.in);
                System.out.print("계산식 입력>> ");
                String str = scan.nextLine();

                StringTokenizer st = new StringTokenizer(str);
                while (st.hasMoreTokens()) {
                    a = a + st.nextToken();

                }

                bw.writeUTF(a);
                bw.flush();
                if(a.equals("bye"))
                {
                    System.out.println("end");
                    break;
                }
                int num = br.readInt();
                System.out.println("계산 결과 : " + num);

            }        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("error.");
            }
        }
    }
}
