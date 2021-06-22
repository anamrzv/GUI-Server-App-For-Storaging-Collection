package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import newclient.ClientHandler;
import other.Person;

public class FilterController extends Controller {

    private ClientHandler clientHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField argumentField;

    @FXML
    private Button moreButton;

    @FXML
    private ComboBox<String> kindOfFieldBox;

    @FXML
    private Button lessButton;

    @FXML
    private Label userInfoLable;

    @FXML
    private Button toCommandsButton;

    @FXML
    private Button toMapButton;

    @FXML
    private Button toTableButton;

    @FXML
    private Label label;

    @FXML
    void initialize() {
        clientHandler = ClientHandler.getInstance(args);
        userInfoLable.setText(clientHandler.getCurrentBundle().getString("user") + clientHandler.getLogin());
        translateLabel(label, "set up filter", clientHandler);
        translateButton(toTableButton, "to table", clientHandler);
        translateButton(toMapButton, "map", clientHandler);
        translateButton(toCommandsButton, "to commands list", clientHandler);
        translateButton(lessButton, "less", clientHandler);
        translateButton(moreButton, "more", clientHandler);
        argumentField.setPromptText(clientHandler.getEncodedBundleString("argument"));
        label.setAlignment(Pos.CENTER);

        ObservableList<String> options = FXCollections.observableArrayList("Name", "Height", "Weight", "Passport", "Hair Color", "Location", "Loc. X", "Loc. Y", "Loc. Z", "Coord. X", "Coord. Y", "Date", "ID");
        kindOfFieldBox.setItems(options);

        moreButton.setOnAction(event -> {
            sort("more");
        });

        lessButton.setOnAction(event -> {
            sort("less");
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

    private void sort(String method) {
        String column = kindOfFieldBox.getValue();
        List<Person> toFilterPeople = clientHandler.getPeople();
        List<Person> filteredPeople = null;
        String argument = argumentField.getText().trim();
        Long numberArgument = null;
        LocalDate dateArgument = null;
        if (!column.equals("Name") && !column.equals("Hair Color") && !column.equals("Location") && !column.equals("Date")) {
            try{
                numberArgument = Long.parseLong(argument);
            } catch (NumberFormatException e){
                showAlert(Alert.AlertType.ERROR, "Filter", "Неправильный ввод числв. Убедитесь, что вы ввели число, а не буквы/слово", "");
            }
        } else if (column.equals("Date")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            try {
                dateArgument = LocalDate.parse(argument, formatter);
            } catch (DateTimeParseException e){
                showAlert(Alert.AlertType.ERROR, "Filter", "Неправильный ввод даты. Введите дату в формате dd/mm/yyyy", "");
            }
        }

        if (column.equals("Name")) {
            filteredPeople = toFilterPeople.stream()
                    .filter(x -> x.getName().compareTo(argument) >= 0)
                    .collect(Collectors.toList());
        } else if (column.equals("Hair Color")) {
            filteredPeople = toFilterPeople.stream()
                    .filter(x -> x.getHairColor().getDescription().compareToIgnoreCase(argument) >= 0)
                    .collect(Collectors.toList());
        } else if (column.equals("Location")) {
            filteredPeople = toFilterPeople.stream()
                    .filter(x -> x.getLocationName().compareTo(argument) >= 0)
                    .collect(Collectors.toList());
        } else if (dateArgument != null && column.equals("Date")) {
            LocalDate finalDateArgument = dateArgument;
            filteredPeople = toFilterPeople.stream()
                    .filter(x -> x.getCreationDate().compareTo(finalDateArgument) >= 0)
                    .collect(Collectors.toList());
        } else if (column.equals("Height")) {
            Long finalNumberArgument = numberArgument;
            filteredPeople = toFilterPeople.stream()
                    .filter(x -> (Long) x.getHeight() != null)
                    .filter(x -> x.getHeight() >= finalNumberArgument)
                    .collect(Collectors.toList());
        } else if (column.equals("Weight")) {
            Long finalNumberArgument = numberArgument;
            filteredPeople = toFilterPeople.stream()
                    .filter(x -> (Long) x.getWeight() != null)
                    .filter(x -> x.getWeight() >= finalNumberArgument)
                    .collect(Collectors.toList());
        } else if (column.equals("Passport")) {
            Long finalNumberArgument = numberArgument;
            filteredPeople = toFilterPeople.stream()
                    .filter(x -> x.getPassportAsLong() >= finalNumberArgument)
                    .collect(Collectors.toList());
        } else if (column.equals("Loc. X")) {
            Long finalNumberArgument = numberArgument;
            filteredPeople = toFilterPeople.stream()
                    .filter(x -> x.getLocationX() >= finalNumberArgument)
                    .collect(Collectors.toList());
        } else if (column.equals("Loc. Y")) {
            Long finalNumberArgument = numberArgument;
            filteredPeople = toFilterPeople.stream()
                    .filter(x -> x.getLocationY() >= finalNumberArgument)
                    .collect(Collectors.toList());
        } else if (column.equals("Loc. Z")) {
            Long finalNumberArgument = numberArgument;
            filteredPeople = toFilterPeople.stream()
                    .filter(x -> x.getLocationZ() >= finalNumberArgument)
                    .collect(Collectors.toList());
        } else if (column.equals("Coord. X")) {
            Long finalNumberArgument = numberArgument;
            filteredPeople = toFilterPeople.stream()
                    .filter(x -> x.getCoordinateX() >= finalNumberArgument)
                    .collect(Collectors.toList());
        } else if (column.equals("Coord. Y")) {
            Long finalNumberArgument = numberArgument;
            filteredPeople = toFilterPeople.stream()
                    .filter(x -> x.getCoordinateY() >= finalNumberArgument)
                    .collect(Collectors.toList());
        } else if (column.equals("ID")) {
            Long finalNumberArgument = numberArgument;
            filteredPeople = toFilterPeople.stream()
                    .filter(x -> x.getId() >= finalNumberArgument)
                    .collect(Collectors.toList());
        }
        if (filteredPeople != null && method.equals("less")) {
            toFilterPeople.removeAll(filteredPeople);
            clientHandler.setPeople(toFilterPeople);
        } else if (filteredPeople != null) {
            clientHandler.setPeople(filteredPeople);
        }
    }
}
