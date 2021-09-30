package java_network.HW1;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class CalInCloudServer {
    static String IpAddress = "127.0.0.1";
    static String PortN = "5678";
    public static void main(String[] args)  {
        ServerSocket listener = null; //서버소켓 객체 생성
        Socket socket = null; //client와 연결할 소켓 생성
        DataInputStream br =null;
        DataOutputStream bw=null;
        try{
            //파일 객체 생성
            File file = new File("C:\\Users\\idh10\\Desktop\\github\\java_study\\java\\src\\java_network\\HW1\\test.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            if(file.isFile() && file.canWrite()){
                //서버의 IP와 PORT Number를 파일에 입력
                bufferedWriter.write(IpAddress);
                bufferedWriter.newLine();
                bufferedWriter.write(PortN);

                bufferedWriter.close();
            }
        }catch (IOException e) {
            System.out.println(e);
        }



        try{
            listener = new ServerSocket(5678);
            System.out.println("Socket ready.");
            socket = listener.accept();//클라이언트와 연결
            System.out.println("Connection established!");
            bw = new DataOutputStream(socket.getOutputStream());
            br = new DataInputStream(socket.getInputStream());

            while(true) {
                String cal[] = new String[3]; // client에서 오는 string을 split하여 저장할 string[]
                String Rstr = ""; //client로 부터 오는 string저장
                String Wstr = ""; // client로 내보낼 stirng 저장
                String str =""; // server에서 출력을 위한 string
                int i=0;
                Rstr = br.readUTF(); // client로부터 온 string 입력
                System.out.println(Rstr);
                StringTokenizer st = new StringTokenizer(Rstr); //client로 부터 받은 string을 나누어주기 위한 객체
                while (st.hasMoreTokens())//더이상 token이 없을때
                {
                    i++;
                    if(i==4)//client에서 온 메세지가 규칙을 어겼을경우
                    {
                        break;
                    }
                    cal[i-1] = st.nextToken();
                }
                try{
                if(i>3)//client에서 온 메세지가 "operation number1 number2" 규약을 어겼을경우
                {
                    Exception e = new Exception(("Too many arguments"));
                    throw e;
                }
                else if(i==1)//client에서 한 단어만 왔을경우
                {
                    if(cal[0].equals("EXIT"))//EXIT이라고 client가 치면 프로그램 종료됨
                    {
                        Exception e = new Exception(("Calculation is end!"));
                        throw e;
                    }
                    else//EXIT이 아닌경우
                    {
                        Exception e = new Exception(("Client didn't keep the form."));
                        throw e;
                    }
                }
                else if(i==3)//client에서 3단어 왔을경우
                {
                    if(cal[0].equals("ADD"))
                    {
                        int num = Integer.parseInt(cal[1])+Integer.parseInt(cal[2]);
                        Wstr = "Answer : " + Integer.toString(num);
                        str = cal[1] + " + " + cal[2] + " = " + Integer.toString(num);
                    }
                    else if(cal[0].equals("DIV"))
                    {
                        if(cal[1].equals("0")||cal[2].equals("0"))//client에서 온 수식이 나누기인데 값 중 0 이 있을경우
                        {
                            Exception e = new Exception("diveded by zero!");
                            throw e;
                        }
                        int num = Integer.parseInt(cal[1])/Integer.parseInt(cal[2]);
                        Wstr = "Answer : " + Integer.toString(num);
                        str = cal[1] + " / " + cal[2] + " = " + Integer.toString(num);
                    }
                    else if(cal[0].equals("MINUS"))
                    {
                        int num = Integer.parseInt(cal[1])-Integer.parseInt(cal[2]);
                        Wstr = "Answer : " + Integer.toString(num);
                        str = cal[1] + " - " + cal[2] + " = " + Integer.toString(num);
                    }
                    else if(cal[0].equals("MUL"))
                    {
                        int num = Integer.parseInt(cal[1])*Integer.parseInt(cal[2]);
                        Wstr = "Answer : " + Integer.toString(num);
                        str = cal[1] + " * " + cal[2] + " = " + Integer.toString(num);
                    }
                    else//client에서 온 문장이 더하기,나누기,뺼셈,곱하기 들을 따르지 않을 경우
                    {
                        Exception e = new Exception("Client didn't keep the form.");
                        throw e;
                    }
                }
                else// 이외에 규약을 지키지 않은경우
                {
                    Exception e = new Exception(("Client didn't keep the form."));
                    throw e;
                }
                }
                catch(Exception e){
                    if(cal[0].equals("EXIT"))
                    {
                        str = e.getMessage();
                        Wstr = e.getMessage();
                        break;
                    }
                   else//client에서 규약을 지키지 않은 입력의 경우 그에 맞는 예외처리 결과를 string들에 저장
                    {str = "Incorrect :"+e.getMessage();
                   Wstr = "Error message : "+e.getMessage();
                }}
                finally {//결과를 출력하고 client로 보내줌
                    System.out.println(str);
                    bw.writeUTF(Wstr);
                    bw.flush();
                }

            }
        }
        catch (IOException r)//서버 와 클라이언트 간의 통신에서 예외가 발생한 경우
        {
            System.out.println(r.getMessage());
        }
        finally {
            try {
                socket.close();//소켓종료
            } catch (IOException f) {
                System.out.println("error.");
            }
        }
    }
}
