
package Practice;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class Accept implements Runnable{
    ServerSocket serverSocket;
    Socket client;
    DataInputStream dis;
    DataOutputStream dos;
    String name;
    Server nts = new Server();

    public Accept(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    @Override
    public void run() {
        while(true) {
            try {

                client = serverSocket.accept();
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
                // 지금은 선착순으로 숫자를 부여했지만 후에는 데이터베이스에서 정보 가져와서 넣어주기.
                //name = "user " + ++Server.user_num;
                nts.Create_Handler(client, dis, dos, name, true);
                System.out.println(name);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public void name_setter(String uname) {
        this.name = uname;
    }
}
