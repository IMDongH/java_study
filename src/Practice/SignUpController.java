package Practice;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class SignUpController implements Initializable{
    @FXML private AnchorPane login;
    @FXML private TextField name;
    @FXML private TextField id;
    @FXML private PasswordField password;
    @FXML private TextField email;
    @FXML private TextField personL;
    @FXML private TextField nickname;
    @FXML private Button SignUpBtn;
    @FXML private Button cancelBtn;
    @FXML private Button IdExistBtn;
    @FXML private Button NnExistBtn;
    Database db = new Database();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancelBtn.setOnAction(e->cancelAction(e));
        SignUpBtn.setOnAction(e->SignUpAction(e));

    }

    public void cancelAction(ActionEvent e){
        StackPane root = (StackPane) cancelBtn.getScene().getRoot();
        root.getChildren().remove(login);
    }

    public void IdExistAction(ActionEvent e) {
        if(db.overCheckID(id.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("이미 존재하는 아이디 입니다.");
            alert.show();
        }

    }
    public void NnExistAction(ActionEvent e) {
       if(db.overCheckNn(nickname.getText()))
       {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setContentText("이미 존재하는 닉네임 입니다.");
           alert.show();
       }

    }
    
    public void SignUpAction(ActionEvent event){
        String uName = name.getText();
        String uNN = nickname.getText();
        String uPersonL = personL.getText();
        String uemail = email.getText();
        String uId = id.getText();
        String upassword = password.getText();
        
  /*      String jdbcUrl = "jdbc:mysql://localhost/game";
        String dbId = "root";
        String dbPw = "1234";
        Connection conn = null;
        PreparedStatement pstmt = null;
*/
        String sql = "";
        int num = 0;
        new SignUpController();
        String name = uName;
        String id = uId;
        String password = upassword;

        try {
           /* Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
*/
            if(db.overCheckNn(uNN)||(db.overCheckID(uId)))
            {
                if (db.overCheckID(uId))
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("이미 존재하는 아이디 입니다.");
                    alert.show();
                }
               else
                  {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("이미 존재하는 닉네임 입니다.");
                    alert.show();}

            }
           else if(db.join(uId,upassword,uNN,uemail,uPersonL,uName))
           {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setContentText("회원가입에 성공하였습니다.");
               alert.show();
           }
           else
           {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setContentText("잘못 입력 되었습니다..");
               alert.show();
           }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


}
