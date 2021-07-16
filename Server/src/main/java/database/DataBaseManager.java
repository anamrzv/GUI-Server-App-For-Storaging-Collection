package database;

import lombok.extern.slf4j.Slf4j;
import other.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class DataBaseManager {
    private String URL;
    private String LOGIN;
    private String PASSWORD;
    private Connection connection;
    private Scanner scanner;

    private static final String pepper = "7Hv@*!1rdSQ9";
    private static final Random RANDOM = new SecureRandom();

    public DataBaseManager() {
        try {
            String DB_DRIVER = "org.postgresql.Driver";
            Class.forName(DB_DRIVER);
            log.info("PostgreSQL JDBC Driver успешно поключен");
            connectScannerToFile();
            readUserDataFromFile();
        } catch (ClassNotFoundException e) {
            log.error("PostgreSQL JDBC Driver не найден. Включите его в свой library path ");
            System.exit(-1);
        }
    }

    private void connectScannerToFile(){
        try {
            String dir = System.getenv("start7");
            if (dir == null) {
                dir = "C:/Users/Ana/Programming/lab-work-7/src/main/resources/info.txt";
            }
            scanner = new Scanner(new FileReader(dir));
        } catch (FileNotFoundException e) {
            log.error("Файл с данными для входа в базу данных не найден");
            System.exit(-1);
        }
    }

    private void readUserDataFromFile(){
        try {
            this.LOGIN = scanner.nextLine().trim();
            this.PASSWORD = scanner.nextLine().trim();
            this.URL = scanner.nextLine().trim();
        } catch (NoSuchElementException e) {
            log.error("В файле не найдены данные для входа. Обновите файл.");
            System.exit(-1);
        }
    }

    public void connectToDB() {
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            log.info("Подключение к базе данных установлено");
        } catch (SQLException e) {
            log.error("Не удалось подключиться к базе данных по указанным данным пользователя");
            System.exit(-1);
        }
    }

    public String addPersonToDB(Person person, String option, List<String> loginAndPassword) {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        String query;
        if (option.equals("add")) {
            query = "INSERT INTO people (name, height, weight, passportid, haircolor, locationname, locationx, locationy, locationz, coordinatesx, coordinatesy, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            query = "UPDATE people SET name=?, height=?, weight=?, passportid=?, haircolor=?, locationname=?, locationx=?, locationy=?, locationz=?, coordinatesx=?, coordinatesy=? WHERE owner=? AND id=?";
        }
        lock.writeLock().lock();
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
                return "creator error";
            } else if (option.equals("update")) return "success update";
            else return "success add";
        } catch (SQLException e) {
            return "add error";
        } finally {
            lock.writeLock().unlock();
        }
    }

    public CollectionsKeeper loadCollectionFromDB() {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        CollectionsKeeper collectionsKeeper = new CollectionsKeeper();
        LinkedList<Person> people = collectionsKeeper.getPeople();
        lock.writeLock().lock();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PEOPLE");
            ResultSet result = preparedStatement.executeQuery();
            if (result != null) {
                while (result.next()) {
                    Person person = getPersonFromResultSet(result);
                    people.add(person);
                    boolean alreadyLocation = false;
                    for (Location loc : collectionsKeeper.getReadyLocations().values()) {
                        if (person != null && person.getLocation().equals(loc)) {
                            alreadyLocation = true;
                            break;
                        }
                    }
                    if (person != null && !alreadyLocation) collectionsKeeper.addLocation(person.getLocation());
                }
                preparedStatement.close();
                return collectionsKeeper;
            } else return null;
        } catch (SQLException e) {
            log.error("Error while accessing DB");
            return null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    private Person getPersonFromResultSet(ResultSet result) {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        try {
            String name = result.getString("name");
            long height = result.getLong("height");
            long weight = result.getLong("weight");
            String passportID = result.getString("passportid");
            Color hairColor = Color.valueOf(result.getString("haircolor"));
            String locationName = result.getString("locationname");
            int locationX = result.getInt("locationx");
            float locationY = result.getFloat("locationy");
            double locationZ = result.getDouble("locationz");
            float coordX = result.getFloat("coordinatesx");
            double coordY = result.getDouble("coordinatesy");
            long id = result.getLong("id");
            String creator = result.getString("owner");
            LocalDate creationDate = result.getDate("date").toLocalDate();
            Location location = new Location();
            location.setLocation(locationX, locationY, locationZ, locationName);
            Coordinates coordinates = new Coordinates();
            coordinates.setCoordinatesFirst(coordX, coordY);
            return new Person(id, name, height, weight, passportID, hairColor, location, coordinates, creationDate, creator);
        } catch (SQLException e) {
            System.out.println("Ошибка при чтении коллекции с базы данных people.");
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }

    public int deleteByIdFromBD(Person person, List<String> loginAndPassword) {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();
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
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<Person> deleteByLoginFromDB(List<String> loginAndPassword) {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();
        try {
            List<Person> deletedPeople = new LinkedList<>();
            ResultSet result = selectByLoginFromDB(loginAndPassword);
            if (result != null) {
                while (result.next()) {
                    Person person = getPersonFromResultSet(result);
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
        } finally {
            lock.writeLock().unlock();
        }
    }

    private ResultSet selectByLoginFromDB(List<String> loginAndPassword) {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM people WHERE owner=?");
            preparedStatement.setString(1, loginAndPassword.get(0));
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }

    public ServerResponse tryToAddUserToDB(List<String> loginAndPassword) {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();
        try {
            PreparedStatement checkLogin = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE login = ?");
            checkLogin.setString(1, loginAndPassword.get(0));
            ResultSet result = checkLogin.executeQuery();
            result.next();
            int userWithThisLogin = result.getInt(1);
            checkLogin.close();
            PreparedStatement preparedStatement;
            if (userWithThisLogin == 0) {
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
                return ServerResponse.builder().command("register").error("Пользователь с таким логином уже существует. Повторите попытку регистрации с другим логинм.").build();
            }
        } catch (SQLException e) {
            return ServerResponse.builder().command("register").error("Ошибка при добавлении пользователя в базу данных.").build();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public ServerResponse checkUserInDB(List<String> loginAndPassword) {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
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
                    if (toBeCheckedPassword != null && toBeCheckedPassword.equals(encodedPassword)) {
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
        } finally {
            lock.readLock().unlock();
        }
    }


}
