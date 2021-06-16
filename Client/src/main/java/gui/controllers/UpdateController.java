package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import newclient.ClientHandler;
import newclient.Creation;
import other.Location;
import other.Person;
import other.ServerResponse;

public class UpdateController extends Controller {

    private ClientHandler clientHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nameFiled;

    @FXML
    private Button readyButton;

    @FXML
    private TextField weightField;

    @FXML
    private TextField heightField;

    @FXML
    private TextField yLocationField;

    @FXML
    private TextField coordYField;

    @FXML
    private TextField nameLocationField;

    @FXML
    private TextField zLocationY;

    @FXML
    private TextField passportField;

    @FXML
    private TextField coordXField;

    @FXML
    private TextField xLocationField;

    @FXML
    private ComboBox<String> hairField;

    @FXML
    private ComboBox<String> locationField;

    @FXML
    private TextField idField;

    @FXML
    private Label userInfoLable;

    @FXML
    private Button toCommandsButton;

    @FXML
    private Button toMapButton;

    private Map<Integer, Location> readyLocations = new HashMap<>();

    private ObservableList<String> locations = FXCollections.observableArrayList();

    @FXML
    void initialize() throws IOException {
        clientHandler = ClientHandler.getInstance(args);

        userInfoLable.setText("Пользователь: " + clientHandler.getLogin());

        ObservableList<String> colors = FXCollections.observableArrayList("YELLOW", "WHITE", "BROWN", "ORANGE");
        hairField.setItems(colors);

        createLocationsList();
        locationField.setItems(locations);

        idField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String index = idField.getText().trim();
                clientHandler.sendCommand("show");
                String answer = "";
                while (answer.isEmpty()) {
                    try {
                        answer = clientHandler.getPeopleAnswer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                for (Person p : clientHandler.getPeople()) {
                    if (p.getId().toString().equals(index)) {
                        nameFiled.setText(p.getName());
                        heightField.setText(Long.toString(p.getHeight()));
                        weightField.setText(Long.toString(p.getWeight()));
                        passportField.setText(p.getPassportID());
                        hairField.setValue(p.getHairColor().getDescription());
                        coordXField.setText(Float.toString(p.getCoordinateX()));
                        coordYField.setText(Double.toString(p.getCoordinateY()));
                        locationField.setValue(p.getLocationName());
                    }
                }
            }
        });

        readyButton.setOnAction(event->{
            ServerResponse response= readFromWindow();
            if (response.getError() != null)
                showAlert(Alert.AlertType.ERROR, "Update person", response.getError(), "");
            else {
                Person personWithID = response.getPersonList().get(0);
                personWithID.setId(Long.parseLong(idField.getText()));
                clientHandler.setPerson(personWithID);
                clientHandler.sendCommand("update");
                ServerResponse answer = null;
                while (answer == null) {
                    try {
                        answer = clientHandler.getAnswer();
                        System.out.println(answer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (answer.getError() == null) {
                    showAlert(Alert.AlertType.INFORMATION, "Update person", answer.getMessage(), "");
                } else showAlert(Alert.AlertType.ERROR, "Update person", answer.getError(), "");
            }
        });


        toMapButton.setOnAction(event -> {
            switchToWindow("/map.fxml", toMapButton);
        });

        toCommandsButton.setOnAction(event -> {
            switchToWindow("/commands.fxml", toCommandsButton);
        });
    }

    public ServerResponse readFromWindow(){
        String name = nameFiled.getText().trim();
        String height = heightField.getText().trim();
        String weight = weightField.getText().trim();
        String passport = passportField.getText().trim();
        String hair = hairField.getValue();
        String x = coordXField.getText().trim();
        String y = coordYField.getText().trim();
        String location = locationField.getValue();
        String nameLoc = nameLocationField.getText().trim();
        String xLoc = xLocationField.getText().trim();
        String yLoc = yLocationField.getText().trim();
        String zLoc = zLocationY.getText().trim();
        Creation creation = new Creation(name, height, weight, passport, hair, x, y, location, nameLoc, xLoc, yLoc, zLoc);
        return creation.createNewPerson(readyLocations);
    }

    public void createLocationsList() throws IOException {
        AddController.createLocationsList(clientHandler, readyLocations, locations);
    }

}