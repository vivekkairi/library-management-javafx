package sample;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class IssueBookController extends JFrame implements Initializable {

    @FXML
    private Button btnIssue;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnScanBook;
    @FXML
    private Button btnScanStudent;
    @FXML
    private TextField txtBookId;
    @FXML
    private TextField txtBookName;
    @FXML
    private TextField txtStudentId;
    @FXML
    private TextField txtStudentName;
    @FXML
    private TextField txtDate;
    private String url = Datasource.retIp() + "mjpegfeed?640x480";

    static {
        Webcam.setDriver(new IpCamDriver());
    }

    public void setBtnIssue() throws SQLException {
        Stage stage = (Stage) btnReset.getScene().getWindow();
        String name = txtBookName.getText();
        String id = txtBookId.getText();
        String studentId = txtStudentId.getText();
        String doi = txtDate.getText();
        if (!name.isEmpty() && !id.isEmpty() && !studentId.isEmpty()) {
            String sql = "INSERT INTO issued values('" + id + "','" + name + "','" + studentId + "','" + doi + "')";
            try {
                Statement statement = Datasource.retConnection().createStatement();
                statement.executeUpdate(sql);
                String sql1 = "UPDATE books SET status='Issued' where bookId='" + id + "'";
                Datasource.executeQuery(sql1);
            } catch (SQLException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("There's some issue processing your request. Contact Librarian.");
                a.setHeaderText(null);
                a.setTitle("Error");
                a.showAndWait();
                btnReset.fire();
                stage.close();
                return;
            }
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Book issued successfully.");
            a.setHeaderText(null);
            a.setTitle("Information");
            a.showAndWait();
            btnReset.fire();
            stage.close();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Enter all the details.");
            a.setHeaderText(null);
            a.setTitle("Error");
            a.showAndWait();
            btnReset.fire();
        }
    }

    public void setBtnReset() {
        txtBookId.setText("");
        txtBookName.setText("");
        txtStudentId.setText("");
        txtStudentName.setText("");
    }

    public void backScene() {
        Stage stage = (Stage) btnReset.getScene().getWindow();
        stage.close();
    }

    public void setBtnScanBook() throws SQLException, MalformedURLException {

        //web.setViewSize(new Dimension(320, 240));
        //WebcamPanel webcamPanel = new WebcamPanel(web);
        //webcamPanel.setMirrored(false);
        //IssueBookController jFrame = new IssueBookController();
        try {
            IpCamDeviceRegistry.register("DroidCam", url, IpCamMode.PUSH);
            //Alert a = new Alert(Alert.AlertType.ERROR);
            //a.setContentText("Open DroidCam App.");
            //a.setHeaderText(null);
            //a.setTitle("Error");
            //a.showAndWait();
            Webcam webcam = Webcam.getWebcams().get(0);
            webcam.setViewSize(new Dimension(640, 480));
            WebcamPanel panel = new WebcamPanel(webcam);
            JFrame jFrame = new JFrame("Scan the barcode");
            jFrame.add(panel);
            jFrame.pack();
            jFrame.setLocationRelativeTo(null);
            jFrame.setVisible(true);
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
            String txt = txtBookId.getText();
            String sql = "SELECT name FROM books WHERE bookId='" + txt + "'";
            Statement statement = Datasource.retConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.isClosed()) {
                txtBookName.setText(resultSet.getString("name"));
            } else {
                Alert b = new Alert(Alert.AlertType.ERROR);
                b.setContentText("No such books exists.");
                b.setHeaderText(null);
                b.showAndWait();
                Stage stage = (Stage) btnReset.getScene().getWindow();
                stage.close();
                btnReset.fire();
            }
        } catch (WebcamException e) {
            IpCamDeviceRegistry.unregister("DroidCam");
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Open DroidCam App.");
            a.setHeaderText(null);
            a.setTitle("Error");
            a.showAndWait();
        }

    }

    public void setBtnScanStudent() throws SQLException, MalformedURLException {
       /* Webcam web = Webcam.getDefault();
        web.setViewSize(new Dimension(320,240));
        WebcamPanel webcamPanel = new WebcamPanel(web);
        webcamPanel.setMirrored(false);  */
        try {
            IpCamDeviceRegistry.register("DroidCam", url, IpCamMode.PUSH);
            Webcam webcam = Webcam.getWebcams().get(0);
            WebcamPanel panel = new WebcamPanel(webcam);
            JFrame jFrame = new JFrame("Scan the barcode");
            jFrame.add(panel);
            jFrame.pack();
            jFrame.setLocationRelativeTo(null);
            jFrame.setVisible(true);
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
                        txtStudentId.setText(result.getText());
                        jFrame.setVisible(false);
                        jFrame.dispose();
                        webcam.close();
                        IpCamDeviceRegistry.unregister("DroidCam");
                        break;
                    }

                } catch (NotFoundException z) {
                }

            } while (true);
            String txt1 = txtStudentId.getText();
            ResultSet resultSet = Datasource.dbExecute("SELECT name FROM students WHERE id='" + txt1 + "'");
            if (!resultSet.isClosed()) {
                txtStudentName.setText(resultSet.getString("name"));
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Contact Librarian for registration.");
                a.setHeaderText(null);
                a.setTitle("Error");
                a.showAndWait();
                Stage stage = (Stage) btnReset.getScene().getWindow();
                stage.close();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        txtDate.setText(dtf.format(now));
    }
}
