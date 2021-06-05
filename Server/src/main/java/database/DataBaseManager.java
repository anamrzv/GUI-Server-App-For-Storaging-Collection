package database;

import other.*;
import other.CollectionsKeeper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.*;
import java.util.*;
import java.sql.DriverManager;

public class DataBaseManager {
    private static final String pepper = "7Hv@*!1rdSQ9";
    private static final Random RANDOM = new SecureRandom();
    private Scanner scanner;
    private final String URL = "jdbc:postgresql://localhost:5638/studs";
    private String LOGIN;
    private String PASSWORD;
    private final String DB_DRIVER = "org.postgresql.Driver";
    private Connection connection;

    public DataBaseManager() {
        try {
            Class.forName(DB_DRIVER);
            System.out.println("PostgreSQL JDBC Driver успешно поключен");
            try {
                String dir = System.getenv("start7");
                if (dir == null) {
                    dir = "C:/Users/Ana/Programming/lab7/src/main/resources/info.txt";
                }
                scanner = new Scanner(new FileReader(dir));
            } catch (FileNotFoundException e) {
                System.out.println("Файл с данными для входа в базу данных не найден");
                System.exit(-1);
            }
            try {
                this.LOGIN = scanner.nextLine().trim();
                this.PASSWORD = scanner.nextLine().trim();
            } catch (NoSuchElementException e) {
                System.out.println("В файле не найдены данные для входа. Обновите файл.");
                System.exit(-1);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("PostgreSQL JDBC Driver не найден. Включите его в свой library path ");
            return;
        }
    }

    public void connectToDB() {
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            System.out.println("Подключение к базе данных установлено");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось подключиться к базе данных");
            System.exit(-1);
        }
    }

    public String addPersonToDB(Person person, String option, List<String> loginAndPassword) {
        String query;
        if (option.equals("add")) {
            query = "INSERT INTO people (name, height, weight, passportid, haircolor, locationname, locationx, locationy, locationz, coordinatesx, coordinatesy, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            query = "UPDATE people SET name=?, height=?, weight=?, passportid=?, haircolor=?, locationname=?, locationx=?, locationy=?, locationz=?, coordinatesx=?, coordinatesy=? WHERE owner=? AND id=?";
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setLong(2, person.getHeight());
            preparedStatement.setLong(3, person.getWeight());
            preparedStatement.setString(4, person.getPassportID());
            preparedStatement.setString(5, person.getHairColor().getDescription());
            preparedStatement.setString(6, person.getLocation().getName());
            preparedStatement.setInt(7, person.getLocation().getX());
            preparedStatement.setFloat(8, person.getLocation().getY());
            preparedStatement.setDouble(9, person.getLocation().getZ());
            preparedStatement.setFloat(10, person.getCoordinates().getX());
            preparedStatement.setDouble(11, person.getCoordinates().getY());
            preparedStatement.setString(12, loginAndPassword.get(0));
            if (option.equals("update")) preparedStatement.setLong(13, person.getId());
            int touchedRows = preparedStatement.executeUpdate();
            preparedStatement.close();
            if (option.equals("update") && touchedRows == 0) {
                return "Нельзя обновить элемент, т.к. вы не являетесь его создаталем";
            } else if (option.equals("update")) return "Объект успешно обновлен";
            else return "Объект успешно добавлен";
        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
            return "Произошла ошибка при добавлении объекта в базу данных, объект не добавлен в коллекцию";
        }
    }

    public CollectionsKeeper loadCollectionFromDB() {
        CollectionsKeeper collectionsKeeper = new CollectionsKeeper();
        LinkedList<Person> people = collectionsKeeper.getPeople();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PEOPLE");
            ResultSet result = preparedStatement.executeQuery();
            if (result != null) {
                while (result.next()) {
                    Person person = handleDBResult(result);
                    people.add(person);
                    boolean alreadyLocation = false;
                    for (Location loc : collectionsKeeper.getReadyLocations().values()) {
                        if (person.getLocation().equals(loc)) {
                            alreadyLocation = true;
                            break;
                        }
                    }
                    if (!alreadyLocation) collectionsKeeper.addLocation(person.getLocation());
                }
                preparedStatement.close();
                return collectionsKeeper;
            } else return null;
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }

    private Person handleDBResult(ResultSet result) {
        try {
            String name = result.getString("name");
            long height = result.getLong("height");
            long weight = result.getLong("weight");
            String passportID = result.getString("passportid"); //будет работать если в таблице лонг?
            Color hairColor = Color.valueOf(result.getString("haircolor"));
            String locationName = result.getString("locationname");
            int locationX = result.getInt("locationx");
            float locationY = result.getFloat("locationy");
            double locationZ = result.getDouble("locationz");
            float coordX = result.getFloat("coordinatesx");
            double coordY = result.getDouble("coordinatesy");
            long id = result.getLong("id");
            Location location = new Location();
            location.setLocation(locationX, locationY, locationZ, locationName);
            Coordinates coordinates = new Coordinates();
            coordinates.setCoordinatesFirst(coordX, coordY);
            Person person = new Person(id, name, height, weight, passportID, hairColor, location, coordinates);
            return person;
        } catch (SQLException e) {
            System.out.println("Ошибка при чтении коллекции с базы данных people.");
            return null;
        }
    }

    public int deleteByIdFromBD(Person person, List<String> loginAndPassword) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM people WHERE id=? AND owner=?");
            preparedStatement.setLong(1, person.getId());
            preparedStatement.setString(2, loginAndPassword.get(0));
            int touchedRows = preparedStatement.executeUpdate();
            if (touchedRows == 0) {
                preparedStatement.close();
                return 0;
            }
            preparedStatement.close();
            return 1;
        } catch (SQLException e) {
            return -1;
        }
    }

    public List<Person> deleteByLoginFromDB(List<String> loginAndPassword) {
        try {
            List<Person> deletedPeople = new LinkedList<>();
            ResultSet result = selectByLoginFromDB(loginAndPassword);
            if (result != null) {
                while (result.next()) {
                    Person person = handleDBResult(result);
                    deletedPeople.add(person);
                }
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM people WHERE owner=?");
                preparedStatement.setString(1, loginAndPassword.get(0));
                preparedStatement.executeUpdate();
                preparedStatement.close();
                return deletedPeople;
            } else return null;
        } catch (SQLException e) {
            return null;
        }
    }

    private ResultSet selectByLoginFromDB(List<String> loginAndPassword) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM people WHERE owner=?");
            preparedStatement.setString(1, loginAndPassword.get(0));
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            return null;
        }
    }

    public ServerResponse addUserToDB(List<String> loginAndPassword) {
        try {
            PreparedStatement checkLogin = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE login = ?");
            checkLogin.setString(1, loginAndPassword.get(0));
            ResultSet result = checkLogin.executeQuery();
            result.next();
            int count = result.getInt(1);
            checkLogin.close();
            PreparedStatement preparedStatement = null;
            if (count == 0) {
                preparedStatement = connection.prepareStatement("INSERT INTO users (login, password, salt) VALUES (?, ?, ?)");
                preparedStatement.setString(1, loginAndPassword.get(0));
                if (!loginAndPassword.get(1).equals("")) {
                    byte[] salt = new byte[16];
                    RANDOM.nextBytes(salt);
                    String saltString = new String(salt, StandardCharsets.UTF_8);
                    preparedStatement.setString(2, PasswordHasher.encryptStringSHA(pepper + loginAndPassword.get(1) + saltString));
                    preparedStatement.setString(3, saltString);
                } else {
                    preparedStatement.setString(2, null);
                    preparedStatement.setString(3, null);
                }
                preparedStatement.executeUpdate();
                preparedStatement.close();
                return ServerResponse.builder().command("register").message("Пользователь успешно зарегестрирован").build();
            } else {
                return ServerResponse.builder().command("register").error("Пользователь с таким логином существует. Повторите попытку регистрации.").build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ServerResponse.builder().command("register").error("Ошибка при добавлении пользователя в базу данных.").build();
        }
    }

    public ServerResponse checkUserInDB(List<String> loginAndPassword) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
            preparedStatement.setString(1, loginAndPassword.get(0));
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                String encodedPassword = result.getString("password");
                String saltString = result.getString("salt");
                if (encodedPassword == null && saltString == null) {
                    preparedStatement.close();
                    return ServerResponse.builder().command("login").message("Вы успешно вошли в систему").build();
                } else {
                    String toBeCheckedPassword = PasswordHasher.encryptStringSHA(pepper + loginAndPassword.get(1) + saltString);
                    if (toBeCheckedPassword.equals(encodedPassword)) {
                        preparedStatement.close();
                        return ServerResponse.builder().command("login").message("Вы успешно вошли в систему").build();
                    } else {
                        preparedStatement.close();
                        return ServerResponse.builder().command("login").error("Неверный пароль, вход в систему отклонен").build();
                    }
                }
            } else {
                preparedStatement.close();
                return ServerResponse.builder().command("login").error("Пользователь с таким логином отсутствует").build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ServerResponse.builder().command("register").error("Ошибка при аутентификации пользователя").build();
        }
    }


}
