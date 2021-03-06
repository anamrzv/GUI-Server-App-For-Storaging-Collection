package gui.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import newclient.ClientHandler;
import other.ServerResponse;

public class CommandButtonsController extends Controller {

    private ClientHandler clientHandler;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button removeIdButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button removePassportButton;

    @FXML
    private Button infoButton;

    @FXML
    private Button headButton;

    @FXML
    private Button weightButton;

    @FXML
    private Button countPassportButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button scriptButton;

    @FXML
    private Label userInfoLable;

    @FXML
    private Button toTableButton;

    @FXML
    private Button mapButton;

    @FXML
    private Label addLabel;

    @FXML
    private Label removeLabel;

    @FXML
    private Label otherLabel;

    @FXML
    void initialize() {
        clientHandler = ClientHandler.getInstance(args);

        translateButton(addButton, "add", clientHandler);
        translateButton(updateButton, "update", clientHandler);
        translateButton(removeIdButton, "removeID", clientHandler);
        translateButton(removePassportButton, "removePass", clientHandler);
        userInfoLable.setText(clientHandler.getCurrentBundle().getString("user") + clientHandler.getLogin());
        translateButton(clearButton, "clear", clientHandler);
        translateButton(countPassportButton, "countPass", clientHandler);
        translateButton(weightButton, "weightSum", clientHandler);
        translateButton(mapButton, "map", clientHandler);
        translateButton(scriptButton, "script", clientHandler);
        translateButton(toTableButton, "to table", clientHandler);
        translateButton(helpButton, "help", clientHandler);
        translateButton(headButton, "head", clientHandler);
        translateButton(infoButton, "info", clientHandler);
        translateLabel(addLabel, "addLabel", clientHandler);
        translateLabel(removeLabel, "removeLabel", clientHandler);
        translateLabel(otherLabel, "otherLabel", clientHandler);
        addLabel.setAlignment(Pos.CENTER);
        removeLabel.setAlignment(Pos.CENTER);
        otherLabel.setAlignment(Pos.CENTER);


        helpButton.setOnAction(event -> getResultByClientHandler("help"));

        infoButton.setOnAction(event -> getResultByClientHandler("info"));

        weightButton.setOnAction(event -> getResultByClientHandler("sum_of_weight"));

        headButton.setOnAction(event -> getResultByClientHandler("head"));

        removePassportButton.setOnAction(event -> switchToWindow("/remove_passport.fxml", removePassportButton));

        toTableButton.setOnAction(event -> switchToWindow("/main.fxml", toTableButton));

        countPassportButton.setOnAction(event -> switchToWindow("/count_passport.fxml", countPassportButton));

        removeIdButton.setOnAction(event -> switchToWindow("/remove_id.fxml", removeIdButton));

        clearButton.setOnAction(event -> getResultByClientHandler("clear"));

        addButton.setOnAction(event -> switchToWindow("/add.fxml", addButton));

        updateButton.setOnAction(event -> switchToWindow("/update.fxml", updateButton));

        scriptButton.setOnAction(event -> switchToWindow("/script.fxml", scriptButton));

        mapButton.setOnAction(event -> switchToWindow("/map.fxml", mapButton));
    }

    private void getResultByClientHandler(String commandName) {
        clientHandler.sendCommand(commandName);
        ServerResponse answer = null;
        while (answer == null) {
            answer = clientHandler.getAnswerToCommand();

        }
        if (answer.getError() == null) {
            switch (answer.getCommand()) {
                case "sum_of_weight":
                    showAlert(Alert.AlertType.INFORMATION, clientHandler.getEncodedBundleString("weightSum"), answer.getMessage() + " " + clientHandler.getEncodedBundleString("weight answer"), "");
                    break;
                case "head":
                    showAlert(Alert.AlertType.INFORMATION, clientHandler.getEncodedBundleString(commandName), clientHandler.getEncodedBundleString("head answer") + answer.getMessage(), "");
                    break;
                case "info":
                    showAlert(Alert.AlertType.INFORMATION, clientHandler.getEncodedBundleString(commandName), clientHandler.getEncodedBundleString("info answer") + answer.getMessage(), "");
                    break;
                default:
                    showAlert(Alert.AlertType.INFORMATION, clientHandler.getEncodedBundleString(commandName), answer.getMessage(), "");
                    break;
            }
        } else
            showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString(commandName), clientHandler.getEncodedBundleString(answer.getError()), "");
    }
}
