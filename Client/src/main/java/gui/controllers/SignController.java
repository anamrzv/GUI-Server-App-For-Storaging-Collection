package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gui.GUIMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import newclient.ClientHandler;
import other.Message;
import other.ServerResponse;

public class SignController extends Controller {

    private ClientHandler clientHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField newLogin;

    @FXML
    private PasswordField newPassword;

    @FXML
    private Button newSignButton;

    @FXML
    private Button toLoginButton;

    @FXML
    private Label registrationLabel;

    @FXML
    private Label rulesLabel;

    @FXML
    void initialize() {
        clientHandler = ClientHandler.getInstance(args);
        rulesLabel.setText(clientHandler.getCurrentBundle().getString("rules"));
        registrationLabel.setText(clientHandler.getCurrentBundle().getString("registration"));
        newLogin.setPromptText(clientHandler.getCurrentBundle().getString("loginRules"));
        newPassword.setPromptText(clientHandler.getCurrentBundle().getString("passwordRules"));
        toLoginButton.setText(clientHandler.getCurrentBundle().getString("to authorisation"));

        newSignButton.setOnAction(event -> {
            String login = newLogin.getText().trim();
            String password = newPassword.getText().trim();
            registerUser(login, password);
        });

        toLoginButton.setOnAction(event -> {
            switchToWindow("/start.fxml", toLoginButton);
        });
    }

    private void registerUser(String login, String password) {
        if (login.length() < 4) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка при вводе логина", "Слишком короткий логин");
        } else {
            clientHandler.setLogin(login);
            if (password.length() < 3 && password.length() > 0)
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка при вводе пароля", "Слишком короткий пароль");
            else if (password.length() != 0) {
                Pattern pattern = Pattern.compile("[a-zA-z.\\d_]{3,}");
                Matcher matcher = pattern.matcher(password);
                if (matcher.matches()) {
                    clientHandler.setPassword(password);
                    clientHandler.sendCommand("register");
                } else
                    showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка при вводе пароля", "Пароль содержит недопустимые символы");
            } else {
                clientHandler.setPassword(password);
                clientHandler.sendCommand("register");
            }
        }
        ServerResponse answer = null;
        while (answer == null) {
            try {
                answer = clientHandler.getAnswer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (answer.getError() != null) {
            showAlert(Alert.AlertType.INFORMATION, "Регистрация", "Регистрация", "Вы успешно зарегестрированы");
            switchToWindow("/start.fxml", newSignButton);
        } else {
            showAlert(Alert.AlertType.ERROR, "Регистрация", "Регистрация отклонена", answer.getError());
        }
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }
}