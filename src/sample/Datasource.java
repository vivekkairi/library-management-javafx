package sample;

import javafx.scene.control.Alert;

import java.sql.*;

public class Datasource{

    private static Connection connection;
    public static String ip;

    public static String retIp(){
        return ip;
    }
    public static void setIP(String IP){
        ip=IP;
    }
    public static void open() {
        try {
            String os =System.getProperty("os.name");
            if(os.equals("Linux")){
                connection = DriverManager.getConnection("jdbc:sqlite:"); //PATH to database 
            }else {
                connection = DriverManager.getConnection("jdbc:sqlite:C:\\lib.db");
            }

        } catch (SQLException e) {}
    }
    public static void disconnect() {
       try {
            connection.close();
       } catch (SQLException e) {

       }
    }
    public static Connection retConnection() {
        return connection;
    }

    public static void executeQuery(String str) throws SQLException{
        Statement statement=null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(str);
        } catch (SQLException e ) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("There's some issue processing your request. Contact Librarian.");
            a.setHeaderText(null);
            a.setTitle("Error");
            a.showAndWait();
        }
        finally {
            if (statement!=null) statement.close();
        }
    }
    public static ResultSet dbExecute(String str){
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt=connection.createStatement();
            rs=stmt.executeQuery(str);
            return rs;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
