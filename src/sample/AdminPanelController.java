package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPanelController  {
    @FXML
    private Button btnAddBook;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnList;
    @FXML
    private Button btnIssued;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnListStudents;
    @FXML
    private Button btnAddStudent;
    @FXML
    private AnchorPane uiPanel;

    public void setBtnAddBook() {
        loadUI("addBook");
    }
    public void setBtnList(){
        loadUI("listBooks");
    }
    public void setBtnListStudent(){
        loadUI("listStudents");
    }
    public void setBtnIssued() {
        loadUI("issuedBooks");
    }
    public void setBtnLogout() {
        Stage stage = (Stage)uiPanel.getScene().getWindow();
        stage.close();
    }
    public void setBtnAddStudent() {
        loadUI("addStudent");
    }
    public void setBtnRemove() {
        loadUI("removeBooks");
    }

    public void loadUI(String s) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(s+".fxml"));
        } catch (IOException e) {
        }
        uiPanel.getChildren().setAll(root);
    }
}
