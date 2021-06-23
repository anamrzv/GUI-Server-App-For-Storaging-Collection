package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import newclient.ClientHandler;
import other.ServerResponse;

public class RemoveIdController extends Controller {

    private ClientHandler clientHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField IDField;

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
        translateLabel(label, "input id", clientHandler);
        translateLabel(labelTwo, "rules of remove", clientHandler);
        translateButton(readyButton, "ready", clientHandler);
        label.setAlignment(Pos.CENTER);
        labelTwo.setAlignment(Pos.CENTER);

        readyButton.setOnAction(event -> {
            String ID = IDField.getText().trim();
            List<String> args = new LinkedList<>();
            args.add(ID);
            clientHandler.setCommandArguments(args);

            clientHandler.sendCommand("remove_by_id");
            ServerResponse answer = null;
            while (answer == null) {
                try {
                    answer = clientHandler.getAnswer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (answer.getError() == null) {
                showAlert(Alert.AlertType.INFORMATION, clientHandler.getEncodedBundleString("removeID") , clientHandler.getEncodedBundleString(answer.getMessage()) , "");
                clientHandler.sendCommand("show");
                try {
                    clientHandler.getPeopleAnswer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString("removeID"), clientHandler.getEncodedBundleString(answer.getError()),  "");
        });

        toMapButton.setOnAction(event->{
            switchToWindow("/map.fxml", toMapButton);
        });

        toCommandsButton.setOnAction(event->{
            switchToWindow("/commands.fxml", toCommandsButton);
        });
    }
}
