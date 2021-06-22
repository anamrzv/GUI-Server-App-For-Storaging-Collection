package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import newclient.ClientHandler;
import other.Color;
import other.Person;

public class MainController extends Controller {

    private ClientHandler clientHandler;

    private ObservableList<Person> observableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Person> peopleTable;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<Person, String> nameColumn;
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
    private Button toFilterButton;
    @FXML
    private Button resetTableButton;
    @FXML
    private Button toMapButton;

    @FXML
    void initialize() {
        clientHandler = ClientHandler.getInstance(args);

        toFilterButton.setText(clientHandler.getCurrentBundle().getString("filter"));
        resetTableButton.setText(clientHandler.getCurrentBundle().getString("reset"));
        toMapButton.setText(clientHandler.getCurrentBundle().getString("map"));
        toCommandsButton.setText(clientHandler.getCurrentBundle().getString("to commands list"));
        userInfoLable.setText(clientHandler.getCurrentBundle().getString("user")+clientHandler.getLogin());

        fillTable();
        peopleTable.setEditable(true);

        toCommandsButton.setOnAction(event -> {
            switchToWindow("/commands.fxml", toCommandsButton);
        });

        toFilterButton.setOnAction(event -> {
            switchToWindow("/filter.fxml", toFilterButton);
        });

        toMapButton.setOnAction(event->{
            switchToWindow("/map.fxml", toMapButton);
        });

        resetTableButton.setOnAction(event -> {
            clientHandler.sendCommand("show");
            try {
                clientHandler.getPeopleAnswer();
            } catch (IOException e) {
                e.printStackTrace();
            }
            observableList.addAll(clientHandler.getPeople());
            switchToWindow("/main.fxml", resetTableButton);
        });

        peopleTable.setRowFactory(tv -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Person chosenPerson = row.getItem();
                    showDataPopup(chosenPerson.getId());
                }
            });
            return row;
        });

    }

    private void showDataPopup(long id) {
        clientHandler.setIdForUpdate(id);
        clientHandler.setIdIsSet(true);
        switchToWindow("/update.fxml", resetTableButton);
    }

    private void fillTable() {
        observableList.addAll(clientHandler.getPeople());

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
        peopleTable.getItems().addAll(observableList);
    }
}
