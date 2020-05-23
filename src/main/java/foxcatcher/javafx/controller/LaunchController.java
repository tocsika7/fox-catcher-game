package foxcatcher.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.IOException;


import javafx.scene.*;

@Slf4j
public class LaunchController {

    @Inject
    FXMLLoader fxmlLoader;

    @FXML
    private TextField player1NameTextField;

    @FXML
    private TextField player2NameTextField;

    @FXML
    private Label errorLabel;


    public void startAction(ActionEvent actionEvent) throws IOException {
        if ((player1NameTextField.getText().isEmpty())||(player2NameTextField.getText().isEmpty())) {
            errorLabel.setText("Enter your name!");
        }else {
                fxmlLoader.setLocation(getClass().getResource("/fxml/game.fxml"));
                Parent root = fxmlLoader.load();
                fxmlLoader.<GameController>getController().setPlayer1Name(player1NameTextField.getText());
                fxmlLoader.<GameController>getController().setPlayer2Name(player2NameTextField.getText());
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
                log.info("The players names are set to {}, loading game scene", player1NameTextField.getText(), player2NameTextField.getText());
            }
        }
    }

