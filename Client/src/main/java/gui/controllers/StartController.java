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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import newclient.ClientHandler;
import other.Message;

public class StartController {

    private ClientHandler clientHandler;
    private String[] args = GUIMain.port;

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
    void initialize() {
        loginButton.setOnAction(event -> {
            String login = loginField.getText().trim();
            String password = passwordField.getText().trim();
            loginUser(login, password);
        });

        signinButton.setOnAction(event -> {
            signinButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/signin.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }

    private void loginUser(String login, String password) {
        clientHandler = new ClientHandler(this, args);
        clientHandler.connect();
        if (login.length() < 4) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка при вводе логина", "Слишком короткий логин");
        } else {
            System.out.println("логин подошел круто");
            clientHandler.setLogin(login);
            if (password.length() < 3 && password.length() > 0)
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка при вводе пароля", "Слишком короткий пароль");
            else if (password.length() != 0) {
                Pattern pattern = Pattern.compile("[a-zA-z.\\d_]{3,}");
                Matcher matcher = pattern.matcher(password);
                if (matcher.matches()) {
                    System.out.println("круто");
                    clientHandler.setPassword(password);
                    clientHandler.sendMessage(Message.builder().commandName("login").build());
                } else
                    showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка при вводе пароля", "Пароль содержит недопустимые символы");
            } else {
                System.out.println("пустой пароль норм");
                clientHandler.setPassword(password);
                clientHandler.sendMessage(Message.builder().commandName("login").build());
            }
        }
        String answer="";
        while (answer.isEmpty()){
            try{
                answer = clientHandler.getAnswer();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if (answer.equals("success")) showAlert(Alert.AlertType.INFORMATION, "Вход", "Вход", "Вы успешно вошли в систему");
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
