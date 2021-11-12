package Practice;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class LoginController implements Initializable{
    Database db = new Database();
    @FXML private TextField id;
    @FXML private PasswordField password;
    @FXML private Button SignUpBtn;
    @FXML private Button loginBtn;
    @FXML private Button exit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SignUpBtn.setOnAction(e->SignUpAction(e));//회원가입 버튼을 누르면 SignUpAction 함수 실행
        loginBtn.setOnAction(e->loginAction(e));//로그인 버튼을 누르면 loginAction 함수 실행
    }


    public void SignUpAction(ActionEvent event){
        try{
            Parent SignUp = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
            StackPane root = (StackPane) SignUpBtn.getScene().getRoot();
            root.getChildren().add(SignUp);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void handleBtnAction(ActionEvent e){
        Platform.exit();
    }


    public void loginAction(ActionEvent event){
        String uId = id.getText();
        String upassword = password.getText();
      /*  String jdbcUrl =  "jdbc:mysql://localhost/game";
        String dbId = "root";
        String dbPw = "1007";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";*/
        try {
            Database db = new Database();
            if (!db.loginCheck(uId, upassword)) {//로그인 정보가 잘못되었을경우
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("잘못 입력 되었습니다..");
                alert.show();
            } else {
            /* Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
            sql = "select id,password from guest";
           pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uId);
            pstmt.setString(2, upassword);
            rs = pstmt.executeQuery(sql);*/
                
                Server server = new Server();
                server.accept_deliver(uId);
                Parent mainPage = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
                StackPane root = (StackPane) loginBtn.getScene().getRoot();
                root.getChildren().add(mainPage);


            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
