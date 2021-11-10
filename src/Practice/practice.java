package Practice;
import java.sql.*;
public class practice {


        public static void main(String[] args) throws SQLException {
            String url = "jdbc:mysql://localhost:3306/game";
            String userName = "root";
            String password = "1007";

            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from guest  WHERE id='test'&&password=password('test')");

            resultSet.next();
            String name = resultSet.getString("id");
            name = name +resultSet.getString("password");
            System.out.println(name);

            resultSet.close();
            statement.close();
            connection.close();
        }


}
