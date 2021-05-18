package boardgame;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import javax.inject.Inject;
import java.io.IOException;

public class OpeningController {

    @Inject
    private FXMLLoader fxmlLoader;

    @FXML
    private TextField usernameTextfield_o;
    @FXML
    private TextField usernameTextfield_e;

    @FXML
    private Label errorLabel;

    @FXML
    private void closeButtonAction(){
        Platform.exit();
    }

    public void startAction(ActionEvent actionEvent) throws IOException {
        if (usernameTextfield_o.getText().isEmpty() || usernameTextfield_e.getText().isEmpty()) {
            errorLabel.setText("Please enter your and/or your oponent's name!");
        } else {
            fxmlLoader = new FXMLLoader(getClass().getResource("/ui.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<BoardGameController>getController().setPlayerName(usernameTextfield_e.getText());
            fxmlLoader.<BoardGameController>getController().setOponentName(usernameTextfield_o.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            Logger.info("The users names are: {} {}", usernameTextfield_e.getText(), usernameTextfield_o.getText());
        }
    }

}
