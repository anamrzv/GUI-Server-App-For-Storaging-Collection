package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import newclient.ClientHandler;
import other.ServerResponse;

public class RemovePassportController extends Controller{

    private ClientHandler clientHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    void initialize() {
        clientHandler = ClientHandler.getInstance(args);
        userInfoLable.setText("Пользователь: " + clientHandler.getLogin());

        readyButton.setOnAction(event->{
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
                showAlert(Alert.AlertType.INFORMATION, "Remove all with this passport", answer.getMessage(), "");
            } else showAlert(Alert.AlertType.ERROR, "Remove all with this passport", answer.getError(), "");
        });

        toMapButton.setOnAction(event->{
            switchToWindow("/map.fxml", toMapButton);
        });

        toCommandsButton.setOnAction(event->{
            switchToWindow("/commands.fxml", toCommandsButton);
        });

    }
}
