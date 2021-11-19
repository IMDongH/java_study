package Practice;
import java.io.*;
import java.net.*;
import java.util.Vector;
public class In_game_Server {
	
	ServerSocket listener = null;
	
	Vector<CCUser>alluser;
	Vector<CCUser>waituser;
	Vector<Room> room;
	
	public static void main(String[] args) {
		In_game_Server server = new In_game_Server();
		
		server.alluser = new Vector<>();
		server.waituser = new Vector<>();
		server.room = new Vector<>();
		
		try {
			server.listener = new ServerSocket(9999);// ���� ���� ����
			System.out.println("[Server]���� ���� �غ� �Ϸ�");
			
			while(true)
			{
				Socket socket = server.listener.accept(); // Ŭ���̾�Ʈ�κ��� ���� ��û ���
				CCUser c = new CCUser(socket,server);
				
				c.start();
			}
		

		}catch(SocketException e) {
			System.out.println("[Server]���� ���� ����:"+e.toString());
		}catch (IOException e) {

			System.out.println(e.getMessage());

		} 
	}
}