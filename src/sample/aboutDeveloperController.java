package sample;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class aboutDeveloperController {

    @FXML
    private Button btnTp;

    public void backScene() {
        Stage stage = (Stage) btnTp.getScene().getWindow();
        stage.close();
    }
}
