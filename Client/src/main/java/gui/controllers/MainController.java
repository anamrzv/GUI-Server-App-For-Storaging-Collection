package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import gui.GUIMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import newclient.ClientHandler;
import other.Color;
import other.Person;

public class MainController {

    private ClientHandler clientHandler;

    private String[] args = GUIMain.port;

    @FXML
    private TableView<Person> peopleTable = new TableView<>();
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<Person,String> nameColumn;
    @FXML
    private TableColumn<Person, Long> heightColumn;
    @FXML
    private TableColumn<Person, Long> weightColumn;
    @FXML
    private TableColumn<Person, String> passportColumn;
    @FXML
    private TableColumn<Person, Color> hairColumn;
    @FXML
    private TableColumn<Person, String> locationNameColumn;
    @FXML
    private TableColumn<Person, Integer> locXColumn;
    @FXML
    private TableColumn<Person, Float> locYColumn;
    @FXML
    private TableColumn<Person, Double> locZColumn;
    @FXML
    private TableColumn<Person, Float> coordXColumn;
    @FXML
    private TableColumn<Person, Double> coordYColumn;
    @FXML
    private TableColumn<Person, LocalDate> dateColumn;
    @FXML
    private TableColumn<Person, Long> idColumn;
    @FXML
    private Label userInfoLable;
    @FXML
    private Button toCommandsButton;

    @FXML
    void initialize() {
        clientHandler = ClientHandler.getInstance(args);
        userInfoLable.setText("Пользователь: "+clientHandler.getLogin());
        fillTable();
        peopleTable.setEditable(true);

        toCommandsButton.setOnAction(event ->{
            toCommandsButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/commands.fxml"));
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

    private void fillTable() {
        ObservableList<Person> observableList = FXCollections.observableArrayList();
        clientHandler.sendCommand("show");
        String answer="";
        while (answer.isEmpty()) {
            try {
                answer = clientHandler.getPeopleAnswer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (answer.equals("full")) observableList.addAll(clientHandler.getPeople());
        System.out.println(clientHandler.getPeople());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        coordXColumn.setCellValueFactory(new PropertyValueFactory<>("coordinateX"));
        coordYColumn.setCellValueFactory(new PropertyValueFactory<>("coordinateY"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        passportColumn.setCellValueFactory(new PropertyValueFactory<>("passportID"));
        hairColumn.setCellValueFactory(new PropertyValueFactory<>("hairColor"));
        locationNameColumn.setCellValueFactory(new PropertyValueFactory<>("locationName"));
        locXColumn.setCellValueFactory(new PropertyValueFactory<>("locationX"));
        locYColumn.setCellValueFactory(new PropertyValueFactory<>("locationY"));
        locZColumn.setCellValueFactory(new PropertyValueFactory<>("locationZ"));
        //peopleTable.getColumns().addAll(nameColumn, heightColumn, weightColumn, passportColumn, hairColumn, locationNameColumn, locXColumn, locYColumn, locZColumn, coordXColumn, coordYColumn, dateColumn, idColumn);
        //table.setItems(observableList);
        peopleTable.getItems().addAll(observableList);
    }
}
