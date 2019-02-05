package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class listStudentsController implements Initializable {
    @FXML public TableView allStudents;
    @FXML public TableColumn<Students,Integer> colSrNo;
    @FXML public TableColumn <Students,String>colStudentId;
    @FXML public TableColumn <Students,String>colStudentName;
    @FXML public TableColumn <Students,String>colDepartment;
    @FXML public TableColumn <Students,String>colYear;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colStudentName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colSrNo.setCellValueFactory(cellData -> cellData.getValue().srNoProperty().asObject());
        colStudentId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        colDepartment.setCellValueFactory(cellData -> cellData.getValue().deptProperty());
        colYear.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        ResultSet rs = Datasource.dbExecute("SELECT * FROM students ORDER BY id");
        populateTable(rs);

    }


    public void populateTable(ResultSet rs) {
        try {
            ObservableList<Students> students = FXCollections.observableArrayList();
            int sr = 0;
            while (rs.next()) {
                sr++;
                Students b = new Students();
                b.setSrNo(sr);
                b.setId(rs.getString("id"));
                b.setName(rs.getString("name"));
                b.setDept(rs.getString("department"));
                b.setYear(rs.getString("year"));
                students.add(b);
            }
            allStudents.setItems(students);
        } catch (SQLException a) {
        }
    }
}
