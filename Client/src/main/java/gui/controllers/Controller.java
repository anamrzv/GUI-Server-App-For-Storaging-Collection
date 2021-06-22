package gui.controllers;

import gui.GUIMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import newclient.ClientHandler;

import java.io.IOException;

public class Controller {

    protected String[] args = GUIMain.port;

    protected void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    protected void switchToWindow(String fxml, Button button){
        button.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    protected void translateLabel(Label label, String key, ClientHandler ch){
        label.setText(ch.getCurrentBundle().getString(key));
    }

    protected void translateButton(Button button, String key, ClientHandler ch){
        button.setText(ch.getCurrentBundle().getString(key));
    }
}
