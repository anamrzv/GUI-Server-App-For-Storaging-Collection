package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class StartController extends Controller {

    private ClientHandler clientHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
        signinButton.setOnAction(event -> {
            switchToWindow("/signin.fxml", signinButton);
        });

        ObservableList<String> languages = FXCollections.observableArrayList("Русский", "English", "Slovenčina", "Shqiptare");
        languageBox.setItems(languages);
        if (clientHandler.getCurrentBundle() == null) {
            clientHandler.setCurrentBundle(ResourceBundle.getBundle("gui.bundles.Language", Locale.forLanguageTag("ru-RU")));
        } else changeLanguage();

        languageBox.setOnAction(event -> {
            String language = languageBox.getValue().trim();
            if (language.equals("English"))
                clientHandler.setCurrentBundle(ResourceBundle.getBundle("gui.bundles.Language", Locale.forLanguageTag("en-CA")));
            else if (language.equals("Slovenčina"))
                clientHandler.setCurrentBundle(ResourceBundle.getBundle("gui.bundles.Language", Locale.forLanguageTag("sl-SL")));
            else if (language.equals("Shqiptare"))
                clientHandler.setCurrentBundle(ResourceBundle.getBundle("gui.bundles.Language", Locale.forLanguageTag("sq-AL")));
            else
                clientHandler.setCurrentBundle(ResourceBundle.getBundle("gui.bundles.Language", Locale.forLanguageTag("ru-RU")));
            changeLanguage();
        });

    }

    private void changeLanguage() {
        autorisationLabel.setText(clientHandler.getEncodedBundleString("authorisation"));
        loginField.setPromptText(clientHandler.getEncodedBundleString("login"));
        passwordField.setPromptText(clientHandler.getEncodedBundleString("password"));
        loginButton.setText(clientHandler.getEncodedBundleString("log in"));
        signinButton.setText(clientHandler.getEncodedBundleString("sign in"));
        autorisationLabel.setAlignment(Pos.CENTER);
    }

    private void loginUser(String login, String password) {
        boolean noError = false;
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
                    clientHandler.sendCommand("login");
                    noError = true;
                } else
                    showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка при вводе пароля", "Пароль содержит недопустимые символы");
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
                showAlert(Alert.AlertType.INFORMATION, "Вход", "Вход", "Вы успешно вошли в систему");
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
                stage.showAndWait();
            } else showAlert(Alert.AlertType.ERROR, "Вход отклонен", "Вход отклонён", "Повторите вход");
        }
    }
}
