package gui.controllers;

import java.net.URL;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import newclient.ClientHandler;
import other.Person;

public class MapController extends Controller {

    ClientHandler clientHandler;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane paneForDrawing;

    @FXML
    private Label userInfoLable;

    @FXML
    private Button toCommandsButton;

    @FXML
    private Button toTableButton;

    private HashMap<String, Color> users = new HashMap<>();

    private HashMap<Circle, Person> circlePeople = new HashMap<>();

    @FXML
    void initialize() {
        clientHandler = ClientHandler.getInstance(args);
        userInfoLable.setText("Пользователь: " + clientHandler.getLogin());

        setChangeSizeListeners();
        circlePeople.clear();
        paneForDrawing.getChildren().clear();
        List<Person> people = clientHandler.getPeople();
        for (Person p : people){
            setupPerson(p);
        }

        toCommandsButton.setOnAction(event->{
            switchToWindow("/commands.fxml", toCommandsButton);
        });

        toTableButton.setOnAction(event->{
            switchToWindow("/main.fxml", toTableButton);
        });

    }

    private void setChangeSizeListeners(){
        paneForDrawing.widthProperty().addListener((ChangeListener<? super Number>) (observable, oldValue, newValue) ->{
            paneForDrawing.getChildren().clear();
            for (Person person : clientHandler.getPeople()){
                setupPerson(person);
            }
        });

        paneForDrawing.heightProperty().addListener((ChangeListener<? super Number>) (observalbe, oldValue, newValue) ->{
            paneForDrawing.getChildren().clear();
            for (Person person : clientHandler.getPeople()){
                setupPerson(person);
            }
        });
    }

    private void setupPerson(Person person){
        new Random();
        if (!users.containsKey(person.getCreator())){
            users.put(person.getCreator(), Color.color(Math.random(), Math.random(), Math.random()));
        }
        float radius;
        if (person.getHeight()>0) {
             radius = person.getHeight() / 70;
        } else radius = 1;
        Circle circle = new Circle(radius * (paneForDrawing.getHeight() + paneForDrawing.getWidth() * 0.8) / 100);
        setCoordinates(circle, person.getCoordinates().getX(), person.getCoordinates().getY());

        circle.setStroke(users.get(person.getCreator()));
        circle.setFill(users.get(person.getCreator()).deriveColor(1,1,1,0.7));
        circle.setOnMousePressed(event->{
            showInfo(event.getSource());
        });
        circlePeople.put(circle, person);

        paneForDrawing.getChildren().add(circle);
    }

    private void setCoordinates(Circle circle, float x, double y){
        double newX = x * paneForDrawing.getWidth() / 250 + paneForDrawing.getWidth() / 2;
        double newY = -y * paneForDrawing.getHeight() / 250 + paneForDrawing.getWidth() / 2;
        circle.setLayoutX(newX);
        circle.setLayoutY(newY);
    }

    private void showInfo(Object source){
        Circle selectedCircle = ((Circle) (source));
        Person selectedPerson = circlePeople.get(selectedCircle);
        long id = selectedPerson.getId();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information about the object");
        alert.setHeaderText(selectedPerson.toString());

        ButtonType close = new ButtonType("exit"); //LANGUAGES
        ButtonType update = new ButtonType("update");
        ButtonType delete = new ButtonType("removeID");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(delete, update, close);

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == close){
            alert.close();
        } else if (option.get() == update){
            clientHandler.setIdForUpdate(id);
            clientHandler.setIdIsSet(true);
            switchToWindow("/update.fxml", toCommandsButton);
        } else if (option.get() == delete){
            List<String> args = new LinkedList<>();
            args.add(Long.toString(id));
            clientHandler.setCommandArguments(args);
            clientHandler.sendCommand("remove_by_id");
        }
    }

}