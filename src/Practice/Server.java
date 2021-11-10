package Practice;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

public class Server {
    static final int port = 10032;
    static int user_num = 0;
    static Accept accept_thread;
    static Vector<Client_Handler> user_list = new Vector<>();
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(port);
        accept_thread = new Accept(serverSocket);
        Thread at = new Thread(accept_thread);
        at.start();

    }
    public void accept_deliver(String uname) {
        accept_thread.name_setter(uname);
    }
    public void Create_Handler(Socket user, DataInputStream dis, DataOutputStream dos, String userID, boolean canPlay) throws IOException {
        Client_Handler client_handler = new Client_Handler(user, dis, dos, userID, true);
        user_list.add(client_handler);
        System.out.println("new user : " + client_handler.userID);
        Thread t = new Thread(client_handler);
        t.start();
    }
}

class Client_Handler implements Runnable {
    Socket user;
    final DataInputStream dis;
    final DataOutputStream dos;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String userID;
    boolean CanPlay;
    StringTokenizer st;
    //유저 정보 필요한거 더 추가하기. 고유번호
    //로그인 후 서버가 만들어 줄 클라이언트의 정보에 대한 생성자.
    public Client_Handler(Socket user, DataInputStream dis, DataOutputStream dos, String userID, boolean CanPlay) throws IOException {
        this.user = user;
        this.dis = dis;
        this.dos = dos;
        this.userID = userID;
        this.CanPlay = CanPlay;
    }
    @Override
    public void run() {
        String msg;
        try {
            while (true) {
                msg = dis.readUTF();
                //customed protocol
                MSG_Processor(msg);
                /*if (!MSGCheck(msg)) {
                    System.out.println(this.userID + " : " + msg);
                    // 일단은 입력받은 메세지 모두에게 전달, 여기서 귓속말, 혹은 게임초대 등의 기능을 넣고싶으면 프로토콜을 정의해야한다.
                    for (Client_Handler User : NET_term_Server.user_list) {
                        User.dos.writeUTF(this.userID + "##" + msg);
                    }
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dis.close();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MSG_Processor(String msg) throws IOException {
        st = new StringTokenizer(msg, "##");
        String tempHeader = st.nextToken();
        String store = null;
        if(tempHeader.equals(TAG.CHAT.name())) {
            msg = userID + "##" + st.nextToken();
            System.out.println(msg);
            for(Client_Handler User : Server.user_list) {
                User.dos.writeUTF(msg);
            }
        }
        else if(tempHeader.equals(TAG.INVITE.name())) {
            //이 경우엔 HEADER를 INVITE_REPLY로 설정하고 다시 Client에게 보낸 뒤 유저의 정보를 받아온다.
            System.out.println(msg);
            dos.writeUTF(TAG.INVITE_REPLY.name() + "##" + "초대하실 유저의 이름을 입력해주세요");
        }
        else if(tempHeader.equals(TAG.SHOW_INFO.name())) {
        }
        else if(tempHeader.equals(TAG.INVITE_REPLY.name())) {
            System.out.println(msg);
            store = st.nextToken();
            for(Client_Handler User : Server.user_list) {
                if(User.userID.equals(store)) {
                    User.dos.writeUTF(TAG.INVITE_REQUEST.name()+"##"+this.userID+"##"+"want to invite you");
                    // 초청을 수락할 경우 초대를 보낸 유저와 초대를 받은 유저 둘의 정보가 필요하므로 초대를 보낸 유저의 정보를 저장.
                    break;
                }
            }
        }
        else if(tempHeader.equals(TAG.INVITE_PD.name())) {
            String inviter = st.nextToken();
            if(st.nextToken().equals("1")) {
                for(Client_Handler User : Server.user_list) {
                    if(User.userID.equals(inviter)) {
                        User.dos.writeUTF(TAG.INVITE_PERMIT.name() + "##" + "초대 수락, 게임방으로 이동합니다.");
                        this.dos.writeUTF(TAG.INVITE_PERMIT.name() + "##" + "초대 수락, 게임방으로 이동합니다.");
                        User.CanPlay = false;
                        this.CanPlay = false;
                        break;
                    }
                }
            }
            else {
                for(Client_Handler User : Server.user_list) {
                    if (User.userID.equals(inviter)) {
                        User.dos.writeUTF(TAG.INVITE_DENY.name() + "##" + this.userID + " 님이 초대를 거절하셨습니다.");
                        break;
                    }
                }
            }
        }
    }
}

