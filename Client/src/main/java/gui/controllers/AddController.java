package gui.controllers;

import java.io.IOException;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import newclient.ClientHandler;
import newclient.Creation;
import other.Location;
import other.Person;
import other.ServerResponse;

public class AddController extends Controller {

    private ClientHandler clientHandler;

    @FXML
    private TextField nameFiled;

    @FXML
    private Label firstLabel;

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
    private Label secondLabel;

    @FXML
    private Label nameL;

    @FXML
    private Label heightL;

    @FXML
    private Label weightL;

    @FXML
    private Label passportL;

    @FXML
    private ComboBox<String> hairField;

    @FXML
    private Label hairL;

    @FXML
    private Label locationL;

    @FXML
    private ComboBox<String> locationField;

    @FXML
    private Label xL;

    @FXML
    private Label yL;

    @FXML
    private Label thirdLabel;

    @FXML
    private Label fourthLabel;

    @FXML
    private Label locnameL;

    @FXML
    private Label locxL;

    @FXML
    private Label locyL;

    @FXML
    private Label locZL;

    @FXML
    private ComboBox<String> kindOfAddBox;

    @FXML
    private Label fifthLabel;

    @FXML
    private Label userInfoLable;

    @FXML
    private Button toCommandsButton;

    @FXML
    private Button toMapButton;

    @FXML
    private Button toTableButton;


    private Map<Integer, Location> readyLocations = new HashMap<>();

    private ObservableList<String> locations = FXCollections.observableArrayList();

    @FXML
    void initialize() throws IOException {
        clientHandler = ClientHandler.getInstance(args);
        userInfoLable.setText(clientHandler.getCurrentBundle().getString("user") + clientHandler.getLogin());
        translateButton(toTableButton, "to table", clientHandler);
        translateButton(toMapButton, "map", clientHandler);
        translateButton(toCommandsButton, "to commands list", clientHandler);
        translateLabel(firstLabel, "firstLabel", clientHandler);
        translateLabel(secondLabel, "secondLabel", clientHandler);
        translateLabel(thirdLabel, "thirdLabel", clientHandler);
        translateLabel(fourthLabel, "fourthLabel", clientHandler);
        translateLabel(fifthLabel, "fifthLabel", clientHandler);
        translateLabel(nameL, "name", clientHandler);
        translateLabel(heightL, "height", clientHandler);
        translateLabel(weightL, "weight", clientHandler);
        translateLabel(passportL, "passport", clientHandler);
        translateLabel(hairL, "hair", clientHandler);
        translateLabel(xL, "x", clientHandler);
        translateLabel(yL, "y", clientHandler);
        translateLabel(locationL, "location", clientHandler);
        translateLabel(locnameL, "title", clientHandler);
        translateButton(readyButton, "create", clientHandler);
        firstLabel.setAlignment(Pos.CENTER);
        secondLabel.setAlignment(Pos.CENTER);

        ObservableList<String> addOptions = FXCollections.observableArrayList(clientHandler.getEncodedBundleString("simple add"), clientHandler.getEncodedBundleString("add if max"), clientHandler.getEncodedBundleString("add if min"));
        kindOfAddBox.setItems(addOptions);
        kindOfAddBox.setValue(clientHandler.getEncodedBundleString("simple add"));

        ObservableList<String> colors = FXCollections.observableArrayList("YELLOW", "WHITE", "BROWN", "ORANGE");
        hairField.setItems(colors);

        createLocationsList();
        locationField.setItems(locations);

        toMapButton.setOnAction(event -> {
            switchToWindow("/map.fxml", toMapButton);
        });

        toTableButton.setOnAction(event -> {
            switchToWindow("/main.fxml", toTableButton);
        });

        toCommandsButton.setOnAction(event -> {
            switchToWindow("/commands.fxml", toCommandsButton);
        });

        readyButton.setOnAction(event -> {
            ServerResponse response = readFromWindow();
            if (response.getError() != null)
                showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString("create new person"), clientHandler.getEncodedBundleString(response.getError()), "");
            else {
                clientHandler.setPerson(response.getPersonList().get(0));
                String commandName = "";
                if (kindOfAddBox.getValue().equals(clientHandler.getEncodedBundleString("simple add"))) commandName = "add";
                else if (kindOfAddBox.getValue().equals(clientHandler.getEncodedBundleString("add if max"))) commandName = "add_if_max";
                else if (kindOfAddBox.getValue().equals(clientHandler.getEncodedBundleString("add if min"))) commandName = "add_if_min";
                clientHandler.sendCommand(commandName);
                ServerResponse answer = null;
                while (answer == null) {
                    try {
                        answer = clientHandler.getAnswer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (answer.getError() == null) {
                    clientHandler.sendCommand("show");
                    try {
                        clientHandler.getPeopleAnswer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    showAlert(Alert.AlertType.INFORMATION, clientHandler.getEncodedBundleString("create new person"), clientHandler.getEncodedBundleString(answer.getMessage()), "");
                } else showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString("create new person"), clientHandler.getEncodedBundleString(answer.getError()), "");
            }
        });

    }

    public ServerResponse readFromWindow() {
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

    private void createLocationsList() throws IOException {
        List<Person> readyPeople = clientHandler.getPeople();
        boolean alreadyLocation = false;
        for (Person p : readyPeople) {
            Location currentLocation = p.getLocation();
            for (Location l : readyLocations.values()) {
                if (currentLocation.equals(l)) {
                    alreadyLocation = true;
                    break;
                }
            }
            if (!alreadyLocation) {
                readyLocations.put(readyLocations.size() + 1, currentLocation);
                alreadyLocation = false;
            }
        }
        for (Location l : readyLocations.values()) {
            locations.add(l.getName());
        }
        locations.add(clientHandler.getEncodedBundleString("new location"));
    }
}
