package gui.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import newclient.ClientHandler;
import other.ServerResponse;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignController extends Controller {

    private ClientHandler clientHandler;

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
        rulesLabel.setText(clientHandler.getEncodedBundleString("rules"));
        registrationLabel.setText(clientHandler.getEncodedBundleString("registration"));
        newLogin.setPromptText(clientHandler.getEncodedBundleString("loginRules"));
        newPassword.setPromptText(clientHandler.getEncodedBundleString("passwordRules"));
        toLoginButton.setText(clientHandler.getEncodedBundleString("to authorisation"));
        newSignButton.setText(clientHandler.getEncodedBundleString("sign in"));
        registrationLabel.setAlignment(Pos.CENTER);
        rulesLabel.setAlignment(Pos.CENTER);

        newSignButton.setOnAction(event -> {
            String login = newLogin.getText().trim();
            String password = newPassword.getText().trim();
            registerUser(login, password);
        });

        toLoginButton.setOnAction(event -> switchToWindow("/start.fxml", toLoginButton));
    }

    private void registerUser(String login, String password) {
        if (login.length() < 4) {
            showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString("signin"), clientHandler.getEncodedBundleString("login error short"), "");
        } else {
            clientHandler.setLogin(login);
            if (password.length() < 3 && password.length() > 0)
                showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString("signin"), clientHandler.getEncodedBundleString("password error short"), "");
            else if (password.length() != 0) {
                Pattern pattern = Pattern.compile("[a-zA-z.\\d_]{3,}");
                Matcher matcher = pattern.matcher(password);
                if (matcher.matches()) {
                    clientHandler.setPassword(password);
                    clientHandler.sendCommand("register");
                } else
                    showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString("signin"), clientHandler.getEncodedBundleString("password error validate"), "");
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
        if (answer.getError() == null) {
            showAlert(Alert.AlertType.INFORMATION, clientHandler.getEncodedBundleString("signin"), clientHandler.getEncodedBundleString("success register"), "");
            switchToWindow("/start.fxml", newSignButton);
        } else {
            showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString("signin"), clientHandler.getEncodedBundleString("register error"), "");
        }
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }
}