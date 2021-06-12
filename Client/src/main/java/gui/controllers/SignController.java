package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignController {

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
    void initialize() {

    }
}