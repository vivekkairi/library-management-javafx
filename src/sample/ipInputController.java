package sample;

import com.github.sarxos.webcam.ds.ipcam.IpCamDevice;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.MalformedURLException;

public class ipInputController {
    @FXML
    private TextField ipAddress;
    @FXML
    private Button btnSubmit;

    public void setBtnSubmit(){
        if(!ipAddress.getText().isEmpty()) {
            try {
                IpCamDevice webcam = IpCamDeviceRegistry.register("DroidCam", ipAddress.getText() + "mjpegfeed?640x480", IpCamMode.PUSH);
                Datasource.setIP(ipAddress.getText());
                Stage stage= (Stage) btnSubmit.getScene().getWindow();
                stage.close();
                IpCamDeviceRegistry.unregister(webcam);
            }catch (MalformedURLException e){
                Alert a= new Alert(Alert.AlertType.ERROR);
                a.setContentText("Incorrect IP\nEg: http://192.168.1.3:4747/");
                a.setHeaderText(null);
                a.setTitle("Error");
                a.showAndWait();
                ipAddress.setText("");
            }
        } else{
            Alert a= new Alert(Alert.AlertType.ERROR);
            a.setContentText("Enter IP address.\nEg: http://192.168.1.3:4747/");
            a.setHeaderText(null);
            a.setTitle("Information");
            a.showAndWait();
        }
    }
}
