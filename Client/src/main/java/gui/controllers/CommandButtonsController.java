package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.GUIMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import newclient.ClientHandler;
import other.ServerResponse;

public class CommandButtonsController {

    private ClientHandler clientHandler;

    private String[] args = GUIMain.port;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button addMaxButton;

    @FXML
    private Button addMinButton;

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
    void initialize() {
        clientHandler = ClientHandler.getInstance(args);
        userInfoLable.setText("Пользователь: "+clientHandler.getLogin());

        helpButton.setOnAction(event->{
            clientHandler.sendCommand("help");
            ServerResponse answer = null;
            while (answer == null) {
                try {
                    answer = clientHandler.getAnswer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (answer.getError()==null){
                showAlert(Alert.AlertType.INFORMATION, "Справка", answer.getMessage(), "");
            } else showAlert(Alert.AlertType.ERROR, "Справка", "Ошибка", "");

        });

        infoButton.setOnAction(event->{
            clientHandler.sendCommand("info");
            ServerResponse answer = null;
            while (answer == null) {
                try {
                    answer = clientHandler.getAnswer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (answer.getError()==null){
                showAlert(Alert.AlertType.INFORMATION, "Информация", answer.getMessage(), "");
            } else showAlert(Alert.AlertType.ERROR, "Справка", "Ошибка", "");

        });

        toTableButton.setOnAction(event->{
            toTableButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });



    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
