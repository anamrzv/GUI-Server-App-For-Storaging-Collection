package gui.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import newclient.ClientHandler;
import other.Person;
import other.ServerResponse;

import java.io.IOException;
import java.util.*;

public class MapController extends Controller {

    private ClientHandler clientHandler;

    @FXML
    private Pane paneForDrawing;

    @FXML
    private Label userInfoLable;

    @FXML
    private Button toCommandsButton;

    @FXML
    private Button toTableButton;

    private final HashMap<String, Color> users = new HashMap<>();

    private final HashMap<Circle, Person> circlePeople = new HashMap<>();

    @FXML
    void initialize() {
        clientHandler = ClientHandler.getInstance(args);
        userInfoLable.setText(clientHandler.getCurrentBundle().getString("user") + clientHandler.getLogin());
        translateButton(toTableButton, "to table", clientHandler);
        translateButton(toCommandsButton, "to commands list", clientHandler);

        setChangeSizeListeners();
        circlePeople.clear();
        paneForDrawing.getChildren().clear();
        List<Person> people = clientHandler.getPeople();
        for (Person p : people) {
            setupPerson(p);
        }

        toCommandsButton.setOnAction(event -> switchToWindow("/commands.fxml", toCommandsButton));

        toTableButton.setOnAction(event -> switchToWindow("/main.fxml", toTableButton));

    }

    private void setChangeSizeListeners() {
        paneForDrawing.widthProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) -> {
            paneForDrawing.getChildren().clear();
            for (Person person : clientHandler.getPeople()) {
                setupPerson(person);
            }
        });

        paneForDrawing.heightProperty().addListener((ChangeListener<? super Number>) (observalbe, oldValue, newValue) -> {
            paneForDrawing.getChildren().clear();
            for (Person person : clientHandler.getPeople()) {
                setupPerson(person);
            }
        });
    }

    private void setupPerson(Person person) {
        new Random();
        if (!users.containsKey(person.getCreator())) {
            users.put(person.getCreator(), Color.color(Math.random(), Math.random(), Math.random()));
        }
        float radius;
        if (person.getHeight() > 0) {
            radius = person.getHeight() / 70f;
        } else radius = 1;
        Circle circle = new Circle(radius * (paneForDrawing.getHeight() + paneForDrawing.getWidth() * 0.8) / 100);
        setCoordinates(circle, person.getCoordinates().getX(), person.getCoordinates().getY());

        circle.setStroke(users.get(person.getCreator()));
        circle.setFill(users.get(person.getCreator()).deriveColor(1, 1, 1, 0.7));
        circle.setOnMousePressed(event -> showInfo(event.getSource()));
        circlePeople.put(circle, person);

        paneForDrawing.getChildren().add(circle);

        ParallelTransition pt = new ParallelTransition();

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(1000));
        scaleTransition.setNode(circle);
        scaleTransition.setByY(1.1);
        scaleTransition.setByX(1.1);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);

        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1000));

        fade.setFromValue(0.1);
        fade.setToValue(10);

        fade.setCycleCount(1);
        fade.setNode(circle);
        pt.getChildren().add(scaleTransition);
        pt.getChildren().add(fade);
        pt.play();
    }

    private void setCoordinates(Circle circle, float x, double y) {
        double newX = x * paneForDrawing.getWidth() / 250 + paneForDrawing.getWidth() / 2;
        double newY = -y * paneForDrawing.getHeight() / 250 + paneForDrawing.getWidth() / 2;
        circle.setLayoutX(newX);
        circle.setLayoutY(newY);
    }

    private void showInfo(Object source) {
        Circle selectedCircle = ((Circle) (source));
        Person selectedPerson = circlePeople.get(selectedCircle);
        long id = selectedPerson.getId();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information about the object");
        alert.setHeaderText(selectedPerson + selectedPerson.getCreationDateString(clientHandler.getCurrentBundle().getLocale()));

        ButtonType close = new ButtonType("Exit"); //LANGUAGES
        ButtonType update = new ButtonType("Update");
        ButtonType delete = new ButtonType("Remove");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(delete, update, close);

        Optional<ButtonType> option = alert.showAndWait();
        if (option.isPresent() && option.get() != close) {
            if (option.get() == update) {
                clientHandler.setIdForUpdate(id);
                clientHandler.setIdIsSet(true);
                switchToWindow("/update.fxml", toCommandsButton);
            } else if (option.get() == delete) {
                List<String> args = new LinkedList<>();
                args.add(Long.toString(id));
                clientHandler.setCommandArguments(args);
                clientHandler.sendCommand("remove_by_id");
                ServerResponse answer = null;
                while (answer == null) {
                    try {
                        answer = clientHandler.getAnswer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (answer.getError() == null) {
                    ScaleTransition scaleTransition = new ScaleTransition();
                    scaleTransition.setDuration(Duration.millis(1000));
                    scaleTransition.setNode(selectedCircle);
                    scaleTransition.setByY(-1.1);
                    scaleTransition.setByX(-1.1);
                    scaleTransition.setCycleCount(1);
                    scaleTransition.play();
                    scaleTransition.setOnFinished(finish -> {
                        paneForDrawing.getChildren().remove(selectedCircle);
                        circlePeople.remove(selectedCircle);
                    });

                    clientHandler.sendCommand("show");
                    try {
                        clientHandler.getPeopleAnswer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else showAlert(Alert.AlertType.ERROR, "Remove person this id", answer.getError(), "");
            }
        } else {
            alert.close();
        }
    }

}