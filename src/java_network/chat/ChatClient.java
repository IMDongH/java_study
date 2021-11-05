package java_network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

    public static void main(String[] args) {
        Socket sck = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        boolean endFlag = false;
        String id = null;
        String code = null;
        try {
            //서버의 소캣번호 입력
            sck = new Socket("127.0.0.1", 1525);
            pw = new PrintWriter(new OutputStreamWriter(sck.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(sck.getInputStream()));
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            //code목록 읽어오기-서버에서 (123a,123b,456a,456p)의 보기를 준다. 클라이언트는 여기서 선택
            //123, 456이 실질적 방번호
            System.out.println("코드읽어오기");
            String str = br.readLine();
            System.out.println(str);
            //방번호 입력받기
            code = keyboard.readLine();
            pw.println(code);//code 스트링에 담아보내기
            pw.flush();
            System.out.println("==========="+code+"님의 대화창=========");
            //서버로 부터 계속 읽어오는 스레드 실행
            InputThread it = new InputThread(sck,br);
            it.start();
            String line = null;
            while((line = keyboard.readLine())!=null)
            {
                pw.println(code+"/"+line);
                pw.flush();
                if(line.equals("quit"))
                {
                    System.out.println("시스템을 종료합니다.");
                    endFlag = true;
                    break;
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(br != null)
                    br.close();
                if(pw != null)
                    pw.close();
                if(sck != null)
                    sck.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
class InputThread extends Thread
{
    Socket sck = null;
    BufferedReader br = null;
    public InputThread(Socket sck, BufferedReader br) {
        this.sck = sck;
        this.br = br;
    }
    public void run()//스레드로 서버로부터 계속 읽어오기
    {
        try {
            String line = null;
            //null값이 아니면 계속 읽어다 출력해주기
            while((line = br.readLine()) !=null)
            {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("시스템을 종료합니다.");
        }finally {
            try {
                if(sck != null)
                    sck.close();
                if(br !=null)
                    br.close();

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}