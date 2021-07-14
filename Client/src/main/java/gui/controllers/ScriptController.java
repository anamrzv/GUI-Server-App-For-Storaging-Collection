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

public class ScriptController extends Controller {

    private ClientHandler clientHandler;

    @FXML
    private Button readyButton;

    @FXML
    private TextField textField;

    @FXML
    private Label userInfoLable;

    @FXML
    private Button toCommandsButton;

    @FXML
    private Button toMapButton;

    @FXML
    private Label label;

    @FXML
    void initialize() {
        clientHandler = ClientHandler.getInstance(args);
        userInfoLable.setText(clientHandler.getCurrentBundle().getString("user") + clientHandler.getLogin());
        translateButton(toMapButton, "map", clientHandler);
        translateButton(toCommandsButton, "to commands list", clientHandler);
        translateLabel(label, "script rules", clientHandler);
        translateButton(readyButton, "execute", clientHandler);
        label.setAlignment(Pos.CENTER);

        readyButton.setOnAction(event -> {
            String pathToScript = textField.getText();
            List<String> file = new LinkedList<>();
            file.add(pathToScript);
            clientHandler.setCommandArguments(file);
            clientHandler.sendCommand("execute_script");
            ServerResponse answer = null;
            while (answer == null) {
                    answer = clientHandler.getAnswerToCommand();
                    System.out.println(answer);
            }
            if (answer.getError() == null) {
                showAlert(Alert.AlertType.INFORMATION, clientHandler.getEncodedBundleString("script"), answer.getMessage(), "");
            } else
                showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString("script"), answer.getError(), "");
        });

        toMapButton.setOnAction(event -> switchToWindow("/map.fxml", toMapButton));

        toCommandsButton.setOnAction(event -> switchToWindow("/commands.fxml", toCommandsButton));
    }
}
