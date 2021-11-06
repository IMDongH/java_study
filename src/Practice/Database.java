package Practice;
import java.sql.*;
import java.util.Date;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Database {
    /* 데이터베이스와의 연결에 사용할 변수들 */
    Connection con = null;
    Statement stmt = null;
    String url = "jdbc:mysql://localhost/game";
    String user = "root";
    String passwd = "1007";
    private Object RequestContextHolder;

    Database() {	//Database 객체 생성 시 데이터베이스 서버와 연결한다.
        try {	
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, passwd);
            stmt = con.createStatement();
            System.out.println("[Server] MySQL 서버 연동 성공");	//데이터베이스 연결에 성공하면 성공을 콘솔로 알린다.
        } catch(Exception e) {	//데이터베이스 연결에 예외가 발생했을 때 실패를 콘솔로 알린다.
            System.out.println("[Server] MySQL 서버 연동 실패> " + e.toString());
        }
    }

    //로그인 여부를 확인하는 메소드. 로그인이 되어있으면 false 반환
    boolean loginCheck(String _i, String _p) {
        boolean flag = false;

        //매개변수로 받은 id와 password값을 id와 pw값에 초기화한다.
        String id = _i;
        String pw = _p;

        try {
            //id와 일치하는 비밀번호와 닉네임이 있는지 조회한다.
            String checkingStr = "select * from guest  WHERE password=password('"+pw+"')";
            ResultSet result = stmt.executeQuery(checkingStr);

            int count = 0;
            while(result.next()) {
                System.out.println(result.getString("id"));
                if(id.equals(result.getString("id"))) {	//true일 경우 nickname에 조회한 닉네임에 반환하고 로그인 성공을 콘솔로 알린다.
                    flag=true;
                    System.out.println("[Server] 로그인 성공");
                }

                else {	//false일 경우 flag를 false로
                    flag=false;
                    System.out.println("[Server] 로그인 실패");
                }
                count++;
            }
        } catch(Exception e) {	//조회에 실패했을 때 nickname을 "null"로 초기화. 실패를 콘솔로 알린다.
            flag = false;
            System.out.println("[Server] 로그인 실패 > " + e.toString());
        }

        return flag;
    }

    
    //회원가입을 수행하는 메소드. 회원가입에 성공하면 true, 실패하면 false를 반환한다.
    //닉네임이랑 ID 중복의 경우는 회원가입 버튼을 ID와 닉네임 모두 중복 확인이 되었을때 활성화 하면 되지 않을까 싶은데...
    boolean join(String _id, String _password, String _nickname, String _email, String _personer_link,String _name) {
        boolean flag = false;	//참거짓을 반환할 flag 변수. 초기값은 false

        //매개변수로 받은 각 문자열들을 각 변수에 초기화한다.

        String id = _id;
        String password = _password;
        String nickname = _nickname;
        String email = _email;
        String personer_link = _personer_link;
        String name = _name;

        try {
            //guest 테이블에 각 문자열들을 순서대로 업데이트하는 문장. 승, 패는 초기값을 숫자 0으로 한다.
            String insertStr = "INSERT INTO guest VALUES('" + id + "' ,password('"+password+"') ,'"+ nickname + "' , '"+ email + "' , '" + personer_link + "', 0, 0,0, ' ' ,'" + name + "','',0)";
            stmt.executeUpdate(insertStr);

            flag = true;	//업데이트문이 정상적으로 수행되면 flag를 true로 초기화하고 성공을 콘솔로 알린다.
            System.out.println("[Server] 회원가입 성공");
        } catch(Exception e) {	//회원가입 절차를 수행하지 못하면 flag를 false로 초기화하고 실패를 콘솔로 알린다.
            flag = false;
            System.out.println("[Server] 회원가입 실패 > " + e.toString());
        }

        return flag;	//flag 반환
    }

    
    //아이디가 중복되었는지 확인해주는 메소드. 중복 값이 존재하면 false, 존재하지 않으면 true를 반환한다. - ID 중복확인 버튼에 사용
    boolean overCheckID(String _val) {
        boolean flag = false;    //참거짓을 반환할 flag 변수. 초기값은 false

        String val = _val;

        try {
            //guest 테이블에 존재하는 아이디를 모두 찾는다.
            String selcectStr = "SELECT id FROM guest";
            ResultSet result = stmt.executeQuery(selcectStr);

            int count = 0;
            while (result.next()) {
               String str;
                if (val.equals(result.getString("id"))) {    //val과 같은 것이 존재하면 flag를 true로 변경하고 while 통과
                    flag = true;
                    break;
                } else {    //val과 같은 것이 존재하지 않으면 flag를 false로 변경한다.
                    flag = false;
                }
                count++;
            }
            System.out.println("[Server] 중복 확인 성공");    //정상적으로 수행되었을 때 성공을 콘솔로 알린다.
        } catch (Exception e) {    //정상적으로 수행하지 못하면 실패를 콘솔로 알린다.
            System.out.println("[Server] 중복 확인 실패 > " + e.toString());
        }
        return flag;
    }

    //닉네임이 중복되었는지 확인해주는 메소드. 중복 값이 존재하면 false, 존재하지 않으면 true를 반환한다. - 닉네임 중복확인 버튼에 사용
    boolean overCheckNn(String _val) {
        boolean flag = false;    //참거짓을 반환할 flag 변수. 초기값은 false

        String val = _val;

        try {
            //guest 테이블에 존재하는 닉네임을 모두 찾는다.
            String selcectStr = "SELECT nickname FROM guest";
            ResultSet result = stmt.executeQuery(selcectStr);

            int count = 0;
            while (result.next()) {
                //조회한 닉네임과 val을 비교.
                String str;
                if (val.equals(result.getString("nickname"))) {    //val과 같은 것이 존재하면 flag를 true로 변경한다.
                    flag = true;
                    break;
                } else {    //val과 같은 것이 존재하지 않으면 flag를 false로 변경한다.
                    flag = false;
                }
                count++;
            }
            System.out.println("[Server] 중복 확인 성공");    //정상적으로 수행되었을 때 성공을 콘솔로 알린다.
        } catch (Exception e) {    //정상적으로 수행하지 못하면 실패를 콘솔로 알린다.
            System.out.println("[Server] 중복 확인 실패 > " + e.toString());
        }
        return flag;
    }
    
    /*win횟수 기준으로 내림차순하여 값을 저장한다.  
    ResultSet result = db.viewRank()
    while(result1.next()) {
        String printStr=null;
        printStr = result1.getString("nickname") + " "+result1.getString("win") +" "+result1.getString("draw")+" "+result1.getString("lose");
        //저장된 정보를 불러오는 것
        이렇게 사용해서 ranking 정보 불러와서 출력하면 될듯
        */
    ResultSet viewRank(){
        ResultSet msg = null;
        try {
        String viewStr = "SELECT * FROM guest order by win desc";
        msg = stmt.executeQuery(viewStr);
        }
        catch(Exception e)
        {

        }
        return msg;
    }

        //데이터베이스에 저장된 닉네임의 정보를 조회하는 메소드. 조회한 정보들을 table? 형태로 반환한다.
        //vector에 있는 닉네임 사용해서 반복문 활용해서 이거쓰면 될듯
        /*
        while(벡터로 현재 접속중인 client)
         ResultSet result1 = db.viewinfo(사용자 닉네임) 사용자 닉네임은 어떻게가져오지..
   {     String printStr=null;
        printStr = result1.getString("nickname") + " "+result1.getString("win") +" "+result1.getString("draw")+" "+result1.getString("lose");
   }
         */
    ResultSet viewInfo(String _nn) {
        ResultSet msg = null;

        String nick = _nn;

        try {
            //guest 테이블에서 nick이라는 닉네임을 가진 회원의 정보를 조회한다.
            String viewStr = "SELECT * FROM guest WHERE nickname='" + nick + "' ";
            msg = stmt.executeQuery(viewStr);
            
            System.out.println("[Server] 회원정보 조회 성공");	//정상적으로 수행되면 성공을 콘솔로 알린다.
        } catch(Exception e) {	//정상적으로 수행하지 못하면 실패를 콘솔로 알린다.
            System.out.println("[Server] 회원정보 조회 실패 > " + e.toString());
        }
        return msg;	//회원 정보 반환
    }

    //회원정보를 변경을 수행하는 메소드. 변경에 성공하면 true, 실패하면 false를 반환한다.
    boolean changeInfo(String _nn, String _a, String _v) {
        boolean flag = false;	//참거짓을 반환할 flag 변수. 초기값은 false.

        //매개변수로 받은 정보들을 초기화한다. att는 속성(이름, 이메일, 비밀번호) 구분용이고 val은 바꿀 값.
        String nick = _nn;
        String att = _a;
        String val = _v;

        try {
            //guest 테이블에서 nick이라는 닉네임을 가진 회원의 att(이름, 이메일, 비밀번호)를 val로 변경한다.
            //password를 변경하는 경우가 아닌경우
            if(!(val=="password")) {
                String changeStr = "UPDATE guest SET '" + att + "' = '" + val + "' WHERE nickname='" + nick + "' ";
                stmt.executeUpdate(changeStr);
            }
            else//password를 변경하는 경우 - 암호화해야하기때문임
            {
                String changeStr = "UPDATE guest SET '" + att + "' = password('" + val + "') WHERE nickname='" + nick + "' ";
                stmt.executeUpdate(changeStr);
            }


            flag = true;	//정상적으로 수행되면 flag를 true로 바꾸고 성공을 콘솔로 알린다.
            System.out.println("[Server] 회원정보 변경 성공");
        } catch(Exception e) {	//정상적으로 수행하지 못하면 flag를 false로 바꾸고 실패를 콘솔로 알린다.
            flag = false;
            System.out.println("[Server] 회원정보 변경 실패 > " + e.toString());
        }

        return flag;	//flag 반환
    }

    //전체 회원의 전적을 조회하는 메소드. 모든 회원의 전적을 String 형태로 반환한다. 필요한지는 모르겠음.
    String viewAll() {
        String msg = "";	//전적을 받을 문자열. 초기값은 ""로 한다.

        try {
            //guest 테이블의 닉네임, 승, 패를 모두 조회한다.
            String viewStr = "SELECT nickname, win, draw ,lose FROM guest";
            ResultSet result = stmt.executeQuery(viewStr);

            int count = 0;
            while(result.next()) {
                //기존의 msg에 "닉네임 : n승 n패" 형태의 문자열을 계속해서 추가한다.
                msg = msg + result.getString("nickname") + " : " + result.getInt("win") + "승 " + result.getInt("draw") + "무 " + result.getInt("lose") + "패 "+'\n';
                count++;
            }
            System.out.println("[Server] 전적 조회 성공");	//정상적으로 수행되면 성공을 콘솔로 알린다.
        } catch(Exception e) {	//정상적으로 수행하지 못하면 실패를 콘솔로 알린다.
            System.out.println("[Server] 전적 조회 실패 > " + e.toString());
        }

        return msg;	//msg 반환
    }

    //한 명의 회원의 전적을 조회하는 메소드. 해당 회원의 전적을 String 형태로 반환한다.
    //대기실에서 사용자 표시할때 사용하면 될듯
    String searchUser(String _nn) {
        String msg = "null";	//전적을 받을 문자열. 초기값은 "null"로 한다.

        //매개변수로 받은 닉네임을 초기화한다.
        String nick = _nn;

        try {
            //guest 테이블에서 nick이라는 닉네임을 가진 회원의 승, 패를 조회한다.
            String searchStr = "SELECT win,draw,lose FROM guest WHERE nickname='" + nick + "'";
            ResultSet result = stmt.executeQuery(searchStr);

            int count = 0;
            while(result.next()) {
                //msg에 "닉네임 : n승 n패" 형태의 문자열을 초기화한다.
                msg = nick + " : " + result.getInt("win") + "승 " + result.getInt("draw") + "무 "+ result.getInt("lose") + "패";
                count++;
            }
            System.out.println("[Server] 전적 조회 성공");	//정상적으로 수행되면 성공을 콘솔로 알린다.
        } catch(Exception e) {	//정상적으로 수행하지 못하면 실패를 콘솔로 알린다.
            System.out.println("[Server] 전적 조회 실패 > " + e.toString());
        }

        return msg;	//msg 반환
    }

    //게임 승리 시 전적을 업데이트하는 메소드. 조회 및 업데이트에 성공하면 true, 실패하면 false를 반환한다.
    boolean winRecord(String _nn) {
        boolean flag = false;	//참거짓을 반환할 flag 변수. 초기값은 false.

        //매개변수로 받은 닉네임과 조회한 승리 횟수를 저장할 변수. num의 초기값은 0.
        String nick = _nn;
        int num = 0;

        try {
            //guest 테이블에서 nick이라는 닉네임을 가진 회원의 승리 횟수를 조회한다.
            String searchStr = "SELECT win FROM guest WHERE nickname='" + nick + "'";
            ResultSet result = stmt.executeQuery(searchStr);

            int count = 0;
            while(result.next()) {
                //num에 조회한 승리 횟수를 초기화.
                num = result.getInt("win");
                count++;
            }
            num++;	//승리 횟수를 올림

            //guest 테이블에서 nick이라는 닉네임을 가진 회원의 승리 횟수를 num으로 업데이트한다.
            String changeStr = "UPDATE guest SET win=" + num + " WHERE nickname='" + nick +"'";
            stmt.executeUpdate(changeStr);
            flag = true;	//조회 및 업데이트 성공 시 flag를 true로 바꾸고 성공을 콘솔로 알린다.
            System.out.println("[Server] 전적 업데이트 성공");
        } catch(Exception e) {	//조회 및 업데이트 실패 시 flag를 false로 바꾸고 실패를 콘솔로 알린다.
            flag = false;
            System.out.println("[Server] 전적 업데이트 실패 > " + e.toString());
        }

        return flag;	//flag 반환
    }
    
    
    boolean drawRecord(String _nn) {
        boolean flag = false;	//참거짓을 반환할 flag 변수. 초기값은 false.

        //매개변수로 받은 닉네임과 조회한 무승부 횟수를 저장할 변수. num의 초기값은 0.
        String nick =  _nn;
        int num = 0;

        try {
            //guest 테이블에서 nick이라는 닉네임을 가진 회원의 무승부 횟수를 조회한다.
            String searchStr = "SELECT draw FROM guest WHERE nickname='" + nick + "'";
            ResultSet result = stmt.executeQuery(searchStr);

            int count = 0;
            while(result.next()) {
                //num에 조회한 무승부 횟수를 초기화.
                num = result.getInt("draw");
                count++;
            }
            num++;	//무승부 횟수를 올림

            //guest 테이블에서 nick이라는 닉네임을 가진 회원의 무 횟수를 num으로 업데이트한다.
            String changeStr = "UPDATE guest SET draw=" + num + " WHERE nickname='" + nick +"'";
            stmt.executeUpdate(changeStr);
            flag = true;	//조회 및 업데이트 성공 시 flag를 true로 바꾸고 성공을 콘솔로 알린다.
            System.out.println("[Server] 전적 업데이트 성공");
        } catch(Exception e) {	//조회 및 업데이트 실패 시 flag를 false로 바꾸고 실패를 콘솔로 알린다.
            flag = false;
            System.out.println("[Server] Error: > " + e.toString());
        }

        return flag;	//flag 반환
    }


    //게임 패배 시 전적을 업데이트하는 메소드. 조회 및 업데이트에 성공하면 true, 실패하면 false를 반환한다.
    boolean loseRecord(String _nn) {
        boolean flag = false;	//참거짓을 반환할 flag 변수. 초기값은 false.

        //매개변수로 받은 닉네임과 조회한 패배 횟수를 저장할 변수. num의 초기값은 0.
        String nick =  _nn;
        int num = 0;

        try {
            //guest 테이블에서 nick이라는 닉네임을 가진 회원의 패배 횟수를 조회한다.
            String searchStr = "SELECT lose FROM guest WHERE nickname='" + nick + "'";
            ResultSet result = stmt.executeQuery(searchStr);

            int count = 0;
            while(result.next()) {
                //num에 조회한 패배 횟수를 초기화.
                num = result.getInt("lose");
                count++;
            }
            num++;	//패배 횟수를 올림

            //guest 테이블에서 nick이라는 닉네임을 가진 회원의 승리 횟수를 num으로 업데이트한다.
            String changeStr = "UPDATE guest SET lose=" + num + " WHERE nickname='" + nick +"'";
            stmt.executeUpdate(changeStr);
            flag = true;	//조회 및 업데이트 성공 시 flag를 true로 바꾸고 성공을 콘솔로 알린다.
            System.out.println("[Server] 전적 업데이트 성공");
        } catch(Exception e) {	//조회 및 업데이트 실패 시 flag를 false로 바꾸고 실패를 콘솔로 알린다.
            flag = false;
            System.out.println("[Server] Error: > " + e.toString());
        }

        return flag;	//flag 반환
    }
    Date recordDay()
    {
        Date today = new Date();
        return today;
    }

    String recordDate()
    {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분 ss초");
        String formatedNow = now.format(formatter);
        return formatedNow;
    }



}