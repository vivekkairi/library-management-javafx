package sample;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ReturnBookController {
    @FXML private Button btnReset;
    @FXML private Button btnScan;
    @FXML private Button btnReturn;
    @FXML private TextField txtBookId;
    @FXML private TextField txtBookName;
    @FXML private TextField txtStudentId;
    @FXML private TextField txtFine;
    private String url =Datasource.retIp()+"mjpegfeed?640x480";
    public void backScene() {
        Stage stage = (Stage) btnReset.getScene().getWindow();
        stage.close();
    }

    public void setBtnReset(ActionEvent e ) {
        txtBookId.setText("");
        txtBookName.setText("");
        txtStudentId.setText("");
        txtFine.setText("");
    }

    public void setBtnReturn(ActionEvent e) throws SQLException {

        String id = txtBookId.getText();
        if(!id.isEmpty()) {
            String sql = "DELETE FROM issued WHERE bookId='" + id + "'";
            Datasource.executeQuery(sql);
            String sql1 = "UPDATE books SET status='On Shelf' where bookId='" + id + "'";
            Datasource.executeQuery(sql1);
            Alert a= new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Book returned successfully.");
            a.setHeaderText(null);
            a.setTitle("Information");
            a.showAndWait();
            Stage stage = (Stage) btnReset.getScene().getWindow();
            stage.close();
        }
        else {
            Alert a= new Alert(Alert.AlertType.ERROR);
            a.setContentText("Scan the book id.");
            a.setHeaderText(null);
            a.setTitle("Error");
            a.showAndWait();
        }
    }

    public void setBtnScan() throws ParseException, MalformedURLException {
        String issueDate;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String today = dtf.format(now);
        try {
            IpCamDeviceRegistry.register("DroidCam", url, IpCamMode.PUSH);
            Webcam webcam = Webcam.getWebcams().get(0);
            WebcamPanel panel = new WebcamPanel(webcam);
            JFrame jFrame = new JFrame("Scan the code");
            jFrame.add(panel);
            jFrame.pack();
            jFrame.setVisible(true);
            jFrame.setLocationRelativeTo(null);
            jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    webcam.close();
                    jFrame.dispose();
                    IpCamDeviceRegistry.unregister("DroidCam");
                }
            });
            do {
                try {
                    BufferedImage image = webcam.getImage();
                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result result = new MultiFormatReader().decode(bitmap);
                    if (result.getText() != null) {
                        txtBookId.setText(result.getText());
                        jFrame.setVisible(false);
                        jFrame.dispose();
                        webcam.close();
                        IpCamDeviceRegistry.unregister("DroidCam");
                        break;
                    }

                } catch (NotFoundException z) {
                }

            } while (true);
            String id = txtBookId.getText();
            try {
                ResultSet resultSet = null;
                Statement stmt = null;
                String sql = "SELECT * FROM issued WHERE bookId='" + id + "'";
                stmt = Datasource.retConnection().createStatement();
                resultSet = stmt.executeQuery(sql);
                if (!resultSet.isClosed()) {
                    txtBookName.setText(resultSet.getString("name"));
                    txtStudentId.setText(resultSet.getString("studentId"));
                    issueDate = resultSet.getString("dateofissue");
                    java.util.Date d1 = new SimpleDateFormat("dd/MM/yyyy").parse(issueDate);
                    Date d2 = (new SimpleDateFormat("dd/MM/yyyy").parse(today));
                    long days = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
                    int fine = (int) days - 21;
                    if (fine > 0) {
                        txtFine.setText(Integer.toString(fine) + "/-");
                    } else txtFine.setText("0");
                } else {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setContentText("No records found. Contact Librarian");
                    a.setHeaderText(null);
                    a.setTitle("Warning");
                    a.showAndWait();
                    btnReset.fire();
                    Stage stage = (Stage) btnReset.getScene().getWindow();
                    stage.close();
                }
            } catch (SQLException e) {
            }
        } catch (WebcamException x) {
            IpCamDeviceRegistry.unregister("DroidCam");
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Open DroidCam app.");
            a.setHeaderText(null);
            a.setTitle("Error");
            a.showAndWait();
        }
    }
}
