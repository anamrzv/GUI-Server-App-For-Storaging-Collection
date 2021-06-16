package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
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

public class CommandButtonsController extends Controller{

    private ClientHandler clientHandler;

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
            getResultByClientHandler("help");
        });

        infoButton.setOnAction(event->{
            getResultByClientHandler("info");
        });

        weightButton.setOnAction(event->{
            getResultByClientHandler("sum_of_weight");
        });

        headButton.setOnAction(event->{
            getResultByClientHandler("head");
        });

        removePassportButton.setOnAction(event->{
            switchToWindow("/remove_passport.fxml", removePassportButton);
        });

        toTableButton.setOnAction(event->{
            switchToWindow("/main.fxml", toTableButton);
        });

        countPassportButton.setOnAction(event->{
            switchToWindow("/count_passport.fxml", countPassportButton);
        });

        removeIdButton.setOnAction(event->{
            switchToWindow("/remove_id.fxml", removeIdButton);
        });

        clearButton.setOnAction(event->{
            getResultByClientHandler("clear");
        });

        addButton.setOnAction(event->{
            switchToWindow("/add.fxml", addButton);
        });

    }

    private void getResultByClientHandler(String commandName){
        clientHandler.sendCommand(commandName);
        ServerResponse answer = null;
        while (answer == null) {
            try {
                answer = clientHandler.getAnswer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (answer.getError()==null){
            showAlert(Alert.AlertType.INFORMATION, commandName.toUpperCase(), answer.getMessage(), "");
        } else showAlert(Alert.AlertType.ERROR, commandName.toUpperCase(), "Ошибка", "");
    }
}