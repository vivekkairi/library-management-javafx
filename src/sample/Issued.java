package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Issued {
    public IntegerProperty srNo;
    public StringProperty bookId;
    public StringProperty name;
    public StringProperty author;
    public StringProperty doi;
    public StringProperty studentId;

    public String getStudentId() {
        return studentId.get();
    }

    public StringProperty studentIdProperty() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId.set(studentId);
    }

    Issued() {
        this.srNo = new SimpleIntegerProperty();
        this.bookId = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.author = new SimpleStringProperty();
        this.doi = new SimpleStringProperty();
        this.studentId = new SimpleStringProperty();
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

    public String getDoi() {
        return doi.get();
    }

    public StringProperty doiProperty() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi.set(doi);
    }
}
