package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class addStudentController implements Initializable {
    @FXML
    TextField txtStudentId;
    @FXML
    TextField txtStudentName;
    @FXML
    ComboBox<String> dept;
    @FXML
    ComboBox<String> year;
    @FXML
    Button btnAdd;
    @FXML
    Button btnReset;

    ObservableList<String> deptList = FXCollections.observableArrayList("Computer", "Mechanical", "Civil", "Electronics", "Electrical", "Production");
    ObservableList<String> yr = FXCollections.observableArrayList("FE", "SE", "TE", "BE");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dept.setItems(deptList);
        year.setItems(yr);
    }


    public void setBtnAdd(ActionEvent e) {
        String id = txtStudentId.getText();
        String name = txtStudentName.getText();
        String dep = dept.getValue();
        String strYear = year.getValue();
        if(!id.isEmpty()&&!name.isEmpty()&&!dep.isEmpty()&&!strYear.isEmpty()) {
            String sql = "INSERT INTO students values('" + id + "','" + name + "','" + dep + "','" + strYear + "')";
            try {
                Datasource.executeQuery(sql);
            } catch (SQLException e1) {
            }
            Stage secondaryStage = new Stage();
            Parent root1 = null;
            Alert a= new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Information saved successfully.");
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

    public void setBtnReset(ActionEvent e) {
        txtStudentId.setText("");
        txtStudentName.setText("");
        dept.setValue("");
        year.setValue("");
    }


}
