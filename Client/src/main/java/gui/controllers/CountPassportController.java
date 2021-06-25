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

public class CountPassportController extends Controller {

    private ClientHandler clientHandler;

    @FXML
    private TextField passportIDField;

    @FXML
    private Label inputPassportLabel;

    @FXML
    private Label countPassportLabel;

    @FXML
    private Button readyButton;

    @FXML
    private Label userInfoLable;

    @FXML
    private Button toCommandsButton;

    @FXML
    private Button toMapButton;


    @FXML
    void initialize() {
        clientHandler = ClientHandler.getInstance(args);
        userInfoLable.setText(clientHandler.getCurrentBundle().getString("user") + clientHandler.getLogin());
        translateLabel(inputPassportLabel, "input passport", clientHandler);
        translateLabel(countPassportLabel, "rules of count", clientHandler);
        passportIDField.setPromptText(clientHandler.getEncodedBundleString("num of passport"));
        translateButton(readyButton, "ready", clientHandler);
        translateButton(toCommandsButton, "to commands list", clientHandler);
        translateButton(toMapButton, "map", clientHandler);
        inputPassportLabel.setAlignment(Pos.CENTER);
        countPassportLabel.setAlignment(Pos.CENTER);

        readyButton.setOnAction(event -> {
            String passportID = passportIDField.getText().trim();
            List<String> args = new LinkedList<>();
            args.add(passportID);
            clientHandler.setCommandArguments(args);

            clientHandler.sendCommand("count_less_than_passport_id");
            ServerResponse answer = null;
            while (answer == null) {
                try {
                    answer = clientHandler.getAnswer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (answer.getError() == null) {
                showAlert(Alert.AlertType.INFORMATION, clientHandler.getEncodedBundleString("countPass"), answer.getMessage() + clientHandler.getEncodedBundleString("passport count answer"), "");
            } else
                showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString("countPass"), clientHandler.getEncodedBundleString(answer.getError()), "");
        });

        toMapButton.setOnAction(event -> switchToWindow("/map.fxml", toMapButton));

        toCommandsButton.setOnAction(event -> switchToWindow("/commands.fxml", toCommandsButton));

    }

}
