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

public class listBooksController implements Initializable {
    @FXML public TableView allBooks;
    @FXML public TableColumn<Books, Integer> colSrNo;
    @FXML public TableColumn<Books,String> colBookId;
    @FXML public TableColumn<Books,String> colBookName;
    @FXML public TableColumn<Books,String> colAuthor;
    @FXML public TableColumn<Books,String> colDept;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colAuthor.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        colBookName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colSrNo.setCellValueFactory(cellData -> cellData.getValue().srNoProperty().asObject());
        colBookId.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty());
        colDept.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        ResultSet rs = Datasource.dbExecute("SELECT * FROM books ORDER BY bookId");
        populateTable(rs);
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
                b.setDepartment(rs.getString("department"));
                books.add(b);
            }
            allBooks.setItems(books);
        } catch (SQLException a) { }
    }
}
