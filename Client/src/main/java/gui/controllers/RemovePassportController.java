package gui.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import newclient.ClientHandler;
import other.ServerResponse;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class RemovePassportController extends Controller {

    private ClientHandler clientHandler;

    @FXML
    private TextField passportIDField;

    @FXML
    private Button readyButton;

    @FXML
    private Label userInfoLable;

    @FXML
    private Button toCommandsButton;

    @FXML
    private Button toMapButton;

    @FXML
    private Label label;

    @FXML
    private Label labelTwo;

    @FXML
    void initialize() {
        clientHandler = ClientHandler.getInstance(args);
        userInfoLable.setText(clientHandler.getCurrentBundle().getString("user") + clientHandler.getLogin());
        translateButton(toMapButton, "map", clientHandler);
        translateButton(toCommandsButton, "to commands list", clientHandler);
        translateLabel(label, "input passport", clientHandler);
        translateLabel(labelTwo, "rules of remove passport", clientHandler);
        passportIDField.setPromptText(clientHandler.getEncodedBundleString("num of passport"));
        translateButton(readyButton, "ready", clientHandler);
        label.setAlignment(Pos.CENTER);
        labelTwo.setAlignment(Pos.CENTER);

        readyButton.setOnAction(event -> {
            String passportID = passportIDField.getText().trim();
            List<String> args = new LinkedList<>();
            args.add(passportID);
            clientHandler.setCommandArguments(args);

            clientHandler.sendCommand("remove_all_by_passport_id");
            ServerResponse answer = null;
            while (answer == null) {
                try {
                    answer = clientHandler.getAnswer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (answer.getError() == null) {
                clientHandler.sendCommand("show");
                try {
                    clientHandler.getPeopleAnswer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                showAlert(Alert.AlertType.INFORMATION, clientHandler.getEncodedBundleString("removePass"), clientHandler.getEncodedBundleString(answer.getMessage()), "");
            } else
                showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString("removePass"), clientHandler.getEncodedBundleString(answer.getError()), "");
        });

        toMapButton.setOnAction(event -> switchToWindow("/map.fxml", toMapButton));

        toCommandsButton.setOnAction(event -> switchToWindow("/commands.fxml", toCommandsButton));

    }
}
