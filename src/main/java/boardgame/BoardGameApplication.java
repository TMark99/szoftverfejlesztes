package boardgame;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;

public class BoardGameApplication extends Application {

    @Inject
    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = fxmlLoader.load(getClass().getResource("/opening.fxml"));
        stage.setTitle("Game");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

}
