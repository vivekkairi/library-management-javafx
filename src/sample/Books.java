package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Books {
    public IntegerProperty srNo;
    public StringProperty bookId;
    public StringProperty name;
    public StringProperty author;
    public IntegerProperty year;
    public StringProperty department;
    public StringProperty status;

    Books() {
        this.srNo = new SimpleIntegerProperty();
        this.bookId = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.author = new SimpleStringProperty();
        this.year = new SimpleIntegerProperty();
        this.department = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
    }

    public int getSrNo() {
        return srNo.get();
    }

    public IntegerProperty srNoProperty() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo.set(srNo);
    }

    public String getBookId() {
        return bookId.get();
    }

    public StringProperty bookIdProperty() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId.set(bookId);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public int getYear() {
        return year.get();
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public String getDepartment() {
        return department.get();
    }

    public StringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
