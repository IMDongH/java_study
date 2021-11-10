import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class GUISample {
    public static void main(String[] args) {
        MyFrame f = new MyFrame();
        f.clientID();
    }
}

class MyFrame extends JFrame {
    //private GUIClientThread guiClientThread;
    private BufferedReader br;
    private BufferedWriter bw;
    private JPanel panel;
    private JTextField clientID, inputText;
    private JButton button;
    private JLabel label;
    private JDialog dialog;
    private Dimension frameSize, screenSize;
    private JTextArea chatting, clientList;
    private JScrollPane scrollPane;
    public MyFrame() {
        setSize(500,405);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("채팅 프로그램");
        screenSizeLocation();
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        br = new BufferedReader(new InputStreamReader(System.in));

        // true로 parameter를 설정하면 JDialog의 버튼을 누르기 전에는 Parent Frame 클릭이 불가능하다.
        dialog = new JDialog(this, "아이디 입력", true);

        // 채팅화면
        setLayout(new BorderLayout());
        panel = new JPanel();
        panel.setLayout(null);  //배치관리자 설정 안함 - 절대 위치 사용
        chatting = new JTextArea();
        chatting.setEditable(false);        //이미 보낸 채팅을 편집하면 안되니 편집 불가능하게 설정.
        scrollPane = new JScrollPane(chatting, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        label = new JLabel();
        label.setText("####사용자 목록#####");
        clientList = new JTextArea();
        clientList.setEditable(false);      //마찬가지로 편집 불가능하게.
        //guiClientThread = new GUIClientThread(socket, chatting);
        inputText = new JTextField();
        button = new JButton("보내기");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String msg = inputText.getText();
                try {
                    chatting.append(msg + "\n");
                    bw.write(msg + "\n");
                    bw.flush();
                }catch(IOException e) {
                    e.printStackTrace();
                }
                inputText.setText("");      //JTextField 초기화
            }
        });
        //컴포넌트 절대 위치 정하기
        // - setBounds(x좌표, y좌표, 폭, 높이)
        //////////////////////////////////////////////////////
        scrollPane.setBounds(4,4,340,330);
        label.setBounds(349,4,131,15);
        clientList.setBounds(349,20,131,314);
        inputText.setBounds(4,339,393,25);
        button.setBounds(400,339,80,25);
        ///////////////////////////////////////////////////////

        panel.add(scrollPane);
        panel.add(label);
        panel.add(clientList);
        panel.add(inputText);
        panel.add(button);

        this.add(panel);
        this.setVisible(true);
    }
    public void screenSizeLocation() {
        frameSize = this.getSize();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
    }
    // JDialog 창 띄우기 - Client ID 입력
    public void clientID() {
        dialog.setLayout(new FlowLayout());
        dialog.setSize(230, 105);
        dialog.setLocationRelativeTo(this);     //JFrame(부모) 가운데 위치
        label = new JLabel("아이디", JLabel.CENTER);
        clientID = new JTextField(13);
        button = new JButton("확인");
        dialog.add(label);
        dialog.add(clientID);
        dialog.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try{
                    String tempID = clientID.getText();
                    bw.write(tempID + "\n");
                    bw.flush();
                    clientList.append(tempID);
                    //guiClientThread.start();
                }catch(IOException e) {
                    e.printStackTrace();
                }
                    dialog.setVisible(false);

            }
        });
        dialog.setVisible(true);
    }
}
