package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class removeBooksController{
    @FXML
    private TextField txtBookId;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnReset;

    public void setBtnRemove(ActionEvent e)throws SQLException {
        String id = txtBookId.getText();
        if (!id.isEmpty()) {
            String sql = "DELETE from books where bookId='"+id+"'";
            Statement statement = Datasource.retConnection().createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT * from books WHERE bookId='"+id+"'");
            if(!resultSet.isClosed()) {
                Datasource.executeQuery(sql);
                Alert a= new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("Book removed from database.");
                a.setHeaderText(null);
                a.setTitle("Information");
                a.showAndWait();
                btnReset.fire();
            } else {
                Alert a= new Alert(Alert.AlertType.ERROR);
                a.setContentText("No such book exits.");
                a.setHeaderText(null);
                a.setTitle("Error");
                a.showAndWait();
                btnReset.fire();
            }
        } else {
            Alert a= new Alert(Alert.AlertType.ERROR);
            a.setContentText("Enter book ID.");
            a.setHeaderText(null);
            a.setTitle("Error");
            a.showAndWait();
        }
    }

    public void setBtnReset(ActionEvent e) {
        txtBookId.setText("");
    }
}