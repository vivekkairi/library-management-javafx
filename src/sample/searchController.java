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

public class searchController implements Initializable{

    @FXML
    private TableColumn<Books, String> colAuthor;
    @FXML
    private TableColumn<Books, String> colName;
    @FXML
    private TableColumn<Books, Integer> colSrNo;
    @FXML
    private TableColumn<Books, String> colStatus;
    @FXML
    private TableView searchTable;
    @FXML
    private Controller controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colAuthor.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colSrNo.setCellValueFactory(cellData -> cellData.getValue().srNoProperty().asObject());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
    }
    public void populateTable(ResultSet rs) {
        try {
            ObservableList<Books> books = FXCollections.observableArrayList();
            int sr = 0;
            while (rs.next()) {
                sr++;
                Books b = new Books();
                b.setSrNo(sr);
                b.setBookId(rs.getString("bookId"));
                b.setAuthor(rs.getString("author"));
                b.setName(rs.getString("name"));
                b.setStatus(rs.getString("status"));
                books.add(b);
            }
            searchTable.setItems(books);
        } catch (SQLException a) {
        }
    }
}
