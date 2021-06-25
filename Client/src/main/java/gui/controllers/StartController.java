package gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import newclient.ClientHandler;
import other.ServerResponse;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartController extends Controller {

    private ClientHandler clientHandler;


    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signinButton;

    @FXML
    private ComboBox<String> languageBox;

    @FXML
    private Label autorisationLabel;

    @FXML
    void initialize() {
        clientHandler = ClientHandler.getInstance(args);

        //Get login and password
        loginButton.setOnAction(event -> {
            String login = loginField.getText().trim();
            String password = passwordField.getText().trim();
            loginUser(login, password);
        });

        //If registration button is clicked, switch to registration window
        signinButton.setOnAction(event -> switchToWindow("/signin.fxml", signinButton));

        ObservableList<String> languages = FXCollections.observableArrayList("Русский", "English", "Slovenčina", "Shqiptare");
        languageBox.setItems(languages);
        if (clientHandler.getCurrentBundle() == null) {
            clientHandler.setCurrentBundle(ResourceBundle.getBundle("gui.bundles.Language", Locale.forLanguageTag("ru-RU")));
        } else changeLanguage();

        languageBox.setOnAction(event -> {
            String language = languageBox.getValue().trim();
            switch (language) {
                case "English":
                    clientHandler.setCurrentBundle(ResourceBundle.getBundle("gui.bundles.Language", Locale.forLanguageTag("en-CA")));
                    break;
                case "Slovenčina":
                    clientHandler.setCurrentBundle(ResourceBundle.getBundle("gui.bundles.Language", Locale.forLanguageTag("sl-SL")));
                    break;
                case "Shqiptare":
                    clientHandler.setCurrentBundle(ResourceBundle.getBundle("gui.bundles.Language", Locale.forLanguageTag("sq-AL")));
                    break;
                case "Русский":
                    clientHandler.setCurrentBundle(ResourceBundle.getBundle("gui.bundles.Language", Locale.forLanguageTag("ru-RU")));
                    break;
            }
            changeLanguage();
        });

    }

    private void changeLanguage() {
        autorisationLabel.setText(clientHandler.getEncodedBundleString("authorisation"));
        loginField.setPromptText(clientHandler.getEncodedBundleString("login"));
        passwordField.setPromptText(clientHandler.getEncodedBundleString("password"));
        loginButton.setText(clientHandler.getEncodedBundleString("log in"));
        signinButton.setText(clientHandler.getEncodedBundleString("registration"));
        autorisationLabel.setAlignment(Pos.CENTER);
    }

    private void loginUser(String login, String password) {
        boolean noError = false;
        if (login.length() < 4) {
            showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString("login"), clientHandler.getEncodedBundleString("login error short"), "");
        } else {
            clientHandler.setLogin(login);
            if (password.length() < 3 && password.length() > 0)
                showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString("login"), clientHandler.getEncodedBundleString("password error short"), "");
            else if (password.length() != 0) {
                Pattern pattern = Pattern.compile("[a-zA-z.\\d_]{3,}");
                Matcher matcher = pattern.matcher(password);
                if (matcher.matches()) {
                    clientHandler.setPassword(password);
                    clientHandler.sendCommand("login");
                    noError = true;
                } else
                    showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString("login"), clientHandler.getEncodedBundleString("password error validate"), "");
            } else {
                clientHandler.setPassword(password);
                clientHandler.sendCommand("login");
                noError = true;
            }
        }
        if (noError) {
            ServerResponse answer = null;
            while (answer == null) {
                try {
                    answer = clientHandler.getAnswer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (answer.getError() == null) {
                loginButton.getScene().getWindow().hide();
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
                stage.setResizable(false);
                stage.showAndWait();
            } else
                showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString("login"), clientHandler.getEncodedBundleString("log in error"), "");
        }
    }
}
