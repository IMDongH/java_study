package Practice;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Vector;

class CCUser extends Thread{
	In_game_Server server;
	Socket socket;
	
	Vector<CCUser>auser;
	Vector<CCUser>wuser;
	Vector<Room>room;
	
	Scanner stin = null;
	OutputStream os;
	DataOutputStream dos;
	InputStream is;
	DataInputStream dis;
	
	String msg;
	String nickname;
	String title;
	
	Room myRoom;
	
	CCUser(Socket _s,In_game_Server _ss)
	{
		this.socket = _s;
		this.server= _ss;
		
		auser = server.alluser;
		wuser = server.waituser;
		room = server.room;
	}
	
	public void run()
	{
		try {
			System.out.println("[Server] Ŭ���̾�Ʈ ���� > "+this.socket.toString());
		
			is = this.socket.getInputStream();
			dis = new DataInputStream(is);// Ŭ���̾�Ʈ�κ����� �Է� ��Ʈ��
			os = this.socket.getOutputStream();
			dos = new DataOutputStream(os);// Ŭ���̾�Ʈ���� ��� ��Ʈ��
			
			dos.writeUTF("enter your nickname:");
			nickname = dis.readUTF();
			auser.add(this);
			wuser.add(this);
			System.out.println(connectedUser());
			
			dos.writeUTF("1.make room  3.enter room");
			String select = dis.readUTF();
			int select_num = Integer.parseInt(select);
			
			if(select_num == 1)
			{
				create_room();
				System.out.println(myRoom.RValue);
				play_game(myRoom);
				
				//�渶�� �������� �޵��� room��ü�� number�ɹ��� �����ߴ�
			}
			if(select_num == 3)
			{
				dos.writeUTF(roomInfo());
				dos.writeUTF("input room_name:");
				String room_name = dis.readUTF();
				
				enter_room(room_name);
			
			}
			
		}catch(IOException e)
		{
			System.out.println("[Server] ����� ���� > "+e.toString());
		}
	}
	
	void sendRoom(String title,String nickname,String msg)//�濡 �ִ� ��ο��� ä�ó��� �˷��ֱ�
	{
		for(int i=0;i<room.size();i++)
		{
			if(title.equals(room.get(i).title))
			{
				for(int j=0;j<room.get(i).ccu.size();j++)
				{
					try {
						room.get(i).ccu.get(j).dos.writeUTF(nickname+":"+msg);
					}catch(IOException e) {
						room.get(i).ccu.remove(j--);
					}
					
				}
			}
			
		}
	}

	String roomUser(String title) {//���� �濡 �ִ� ���� 
		
		String msg="";
		
		for(int i=0;i<room.size();i++)
		{
			if(title.equals(room.get(i).title))
			{
				for(int j=0;j<room.get(i).ccu.size();j++)
				{
					msg = msg + room.get(i).ccu.get(j).nickname+" ";
				}
			}
			
		}
		return msg;
		
	}
	String connectedUser() {//���� ������ �������� ����
		String msg = "";
		for(int i=0;i<auser.size();i++)
		{
			msg = msg+ auser.get(i).nickname+" ";
		}
		return msg;
	}
	String roomInfo() {//�� ����Ʈ ���̸��� ������ �г���
		String msg = "�� ����Ʈ:";
		for(int i=0;i<room.size();i++)
		{
			msg=msg+room.get(i).title;
			
		}
		return msg;
	}
	
	
	void create_room()//�� �����
	{
		myRoom = new Room();
		myRoom.title = nickname;
		myRoom.count++;
		myRoom.RValue = (int)(Math.random()*100);
		room.add(myRoom);
		
		myRoom.ccu.add(this);
		sendRoom(myRoom.title,"[Server]","������Ϸ�");
		System.out.println("[Server] "+nickname+"�� ����");
		sendRoom(myRoom.title,"[Server]","���� ���ο� ���� : "+roomUser(nickname));

	}
	void enter_room(String room_name)//�濡 ����
	{
		for(int i=0;i<room.size();i++)
		{
			if(room_name.equals(room.get(i).title))
			{
				room.get(i).count++;
				room.get(i).ccu.add(this);
				
				System.out.println(nickname+"���� "+room.get(i).title+"�� �����Ͻʴϴ�.");
				
				sendRoom(room.get(i).title,"[Server]",nickname+"����Ϸ�");
				play_game(room.get(i));
			
			}
			else {
				//dos.writeUTF("no room");
				
			}
	}
		
}
	void play_game(Room myRoom)//���� 
	{
		while(true)
		{
			try {//���� ������ ī���Ͱ� 0�̸� ���̻� �Է��� �� ���ٴ� ���� ��� �ƴϸ� ������� ��� 
				
				msg = dis.readUTF();
				
				sendRoom(myRoom.title,nickname,msg);
				
				int transValue;
				transValue = Integer.parseInt(msg);
				if(transValue>myRoom.RValue)
				{
					
					sendRoom(myRoom.title,"[Server]","DOWN!");
				}
				else if(transValue<myRoom.RValue)
				{
					sendRoom(myRoom.title,"[Server]","UP!");
				}
				else
				{
					sendRoom(myRoom.title,"[Server]","SAME!");
					sendRoom(myRoom.title," "," ");
					sendRoom(myRoom.title,nickname,"Win");
					//���� �����ؼ� ���� �����ϱ� 
					
				}	
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
		
		}
		
		
	}
	}
