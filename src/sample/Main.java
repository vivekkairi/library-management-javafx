package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Just Books Library");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        Stage secondaryStage = new Stage();
        Parent root1 = FXMLLoader.load(getClass().getResource("ipInput.fxml"));
        secondaryStage.setScene(new Scene(root1, 307, 118));
        secondaryStage.initStyle(StageStyle.UNDECORATED);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.show();
    }

    public static void main(String[] args) {
        Datasource.open();
        launch(args);
    }
}
