package java_network.HW1;
import java.io.*;
import java.net.*;
import java.util.*;
public class CalInCloudClient {
    public static void main(String[] args) {

        Socket socket = null;
        DataInputStream br =null;
        DataOutputStream bw=null;
        Scanner scan = null;
        String line[] = new String[2];
        try{
            //파일 객체 생성
            File file = new File("C:\\Users\\idh10\\Desktop\\github\\java_study\\java\\src\\java_network\\HW1\\test.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));


            int i=0;
            while(true){//파일에서 서버 IP와 port number를 불러옴
                if(i==2)
                    break;
                line[i] = bufferedReader.readLine();
                i++;
            }
            bufferedReader.close();


        }catch (FileNotFoundException e) {
            line[0]="127.0.0.1";
            line[1]="5678";

        }catch(IOException e){
            System.out.println(e);
        }



        try{
            socket = new Socket(line[0], Integer.parseInt(line[1])); //서버와 연결
            scan = new Scanner(System.in);
            bw = new DataOutputStream(socket.getOutputStream());
            br = new DataInputStream(socket.getInputStream());

            while(true) {
                String Rstr="";//서버에서 온 string을 저장
                String Wstr="";//서버로 보낼 string을 저장
                Wstr = scan.nextLine();
                bw.writeUTF(Wstr);//입력한 string을 서버로 전송
                bw.flush();

                Rstr=br.readUTF();//서버에서 온 string을 저장
                System.out.println(Rstr);
                if(Rstr.equals("Calculation is end!"))//client에서 EXIT이라고 쳤을경우 종료
                {
                    break;
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error Message : " + e.getMessage());
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
