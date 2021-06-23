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
import javafx.geometry.Pos;
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
    private TextField idField;

    @FXML
    private Label fifthLabel;

    @FXML
    private Label userInfoLable;

    @FXML
    private Label locnameL;

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
        translateLabel(nameL, "name", clientHandler);
        translateLabel(heightL, "height", clientHandler);
        translateLabel(weightL, "weight", clientHandler);
        translateLabel(passportL, "passport", clientHandler);
        translateLabel(hairL, "hair", clientHandler);
        translateLabel(xL, "x", clientHandler);
        translateLabel(yL, "y", clientHandler);
        translateLabel(locationL, "location", clientHandler);
        translateLabel(locnameL, "title", clientHandler);
        translateButton(readyButton, "update", clientHandler);
        translateLabel(thirdLabel, "thirdLabel", clientHandler);
        translateLabel(fourthLabel, "fourthLabel", clientHandler);
        translateLabel(firstLabel, "input id", clientHandler);
        translateLabel(secondLabel, "update rules", clientHandler);
        translateLabel(fifthLabel, "fifth label update", clientHandler);
        firstLabel.setAlignment(Pos.CENTER);
        secondLabel.setAlignment(Pos.CENTER);

        if (clientHandler.isIdIsSet() == true) {
            idField.setText(Long.toString(clientHandler.getIdForUpdate()));
            updateFields();
            clientHandler.setIdIsSet(false);
        }

        ObservableList<String> colors = FXCollections.observableArrayList("YELLOW", "WHITE", "BROWN", "ORANGE");
        hairField.setItems(colors);

        createLocationsList();
        locationField.setItems(locations);

        idField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                updateFields();
            }
        });

        readyButton.setOnAction(event -> {
            ServerResponse response = readFromWindow();
            if (response.getError() != null)
                showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString( "update person"), clientHandler.getEncodedBundleString(response.getError()), "");
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
                    clientHandler.sendCommand("show");
                    try {
                        clientHandler.getPeopleAnswer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    showAlert(Alert.AlertType.INFORMATION, clientHandler.getEncodedBundleString( "update person"), clientHandler.getEncodedBundleString(answer.getMessage()), "");
                } else showAlert(Alert.AlertType.ERROR, clientHandler.getEncodedBundleString( "update person"), clientHandler.getEncodedBundleString(answer.getError()), "");
            }
        });

        toMapButton.setOnAction(event -> {
            switchToWindow("/map.fxml", toMapButton);
        });

        toCommandsButton.setOnAction(event -> {
            switchToWindow("/commands.fxml", toCommandsButton);
        });

        toTableButton.setOnAction(event -> {
            switchToWindow("/main.fxml", toTableButton);
        });

    }

    private void updateFields() {
        String index = idField.getText().trim();
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

    private void createLocationsList() {
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
