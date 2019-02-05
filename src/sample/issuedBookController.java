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

public class issuedBookController implements Initializable {
    @FXML public TableView allIssued;
    @FXML public TableColumn<Issued,Integer> colSrNo;
    @FXML public TableColumn<Issued,String> colBookId;
    @FXML public TableColumn<Issued,String> colBookName;
    @FXML public TableColumn<Issued,String> colDoi;
    @FXML public TableColumn<Issued,String> colStudentID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colBookName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colSrNo.setCellValueFactory(cellData -> cellData.getValue().srNoProperty().asObject());
        colBookId.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty());
        colStudentID.setCellValueFactory(cellData -> cellData.getValue().studentIdProperty());
        colDoi.setCellValueFactory(cellData -> cellData.getValue().doiProperty());
        ResultSet rs = Datasource.dbExecute("SELECT * FROM issued ORDER BY bookId");
        populateTable(rs);
    }

    public void populateTable(ResultSet rs) {
        try {
            ObservableList<Issued> books = FXCollections.observableArrayList();
            int sr = 0;
            while (rs.next()) {
                sr++;
                Issued b = new Issued();
                b.setSrNo(sr);
                b.setBookId(rs.getString("bookId"));
                b.setName(rs.getString("name"));
                b.setDoi(rs.getString("dateofissue"));
                b.setStudentId(rs.getString("studentId"));
                books.add(b);
            }
            allIssued.setItems(books);
        } catch (SQLException a) {

        }
    }
}
