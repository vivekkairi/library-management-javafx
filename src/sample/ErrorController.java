package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ErrorController {

    public Button okay_;

    public void okayfunc(ActionEvent actionEvent) {
        Stage stage = (Stage) okay_.getScene().getWindow();
        stage.close();
    }
}
