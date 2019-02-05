package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class addBookController implements Initializable {
    @FXML
    public TextField bookId;
    @FXML
    public TextField bookName;
    @FXML
    public TextField author;
    @FXML
    public TextField year;
    @FXML
    public ComboBox<String> dept;
    @FXML
    public Button add;
    @FXML
    public Button btnReset;

    public void addBook(ActionEvent actionEvent) {
        String id = bookId.getText();
        String name = bookName.getText();
        String dep = dept.getValue();
        String strYear = year.getText();
        String aut=author.getText();
        if(!id.isEmpty() && !name.isEmpty() && !dep.isEmpty() && !strYear.isEmpty() && !aut.isEmpty()) {
            String sql = "INSERT INTO books values('" + id + "','" + name + "','" +aut + "','" + strYear +"','"+dep +"','On Shelf')";
            try {
                Datasource.executeQuery(sql);
            } catch (SQLException e1) {
            }
            Alert a= new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Book added successfully.");
            a.setHeaderText(null);
            a.setTitle("Information");
            a.showAndWait();
            btnReset.fire();
        }
        else {
            Alert a= new Alert(Alert.AlertType.WARNING);
            a.setContentText("Fill all the fields before submitting");
            a.setHeaderText(null);
            a.setTitle("Warning");
            a.showAndWait();

        }
    }

    public void setBtnReset(ActionEvent event) {
        bookId.setText("");
        bookName.setText("");
        author.setText("");
        year.setText("");
    }

    ObservableList<String> deptList = FXCollections.observableArrayList("Computer", "Mechanical", "Civil", "Electronics", "Electrical", "Production");
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dept.setItems(deptList);
    }

}
