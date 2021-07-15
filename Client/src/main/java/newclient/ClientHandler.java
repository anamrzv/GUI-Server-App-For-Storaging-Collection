package newclient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import other.Location;
import other.Message;
import other.Person;
import other.ServerResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.PortUnreachableException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Setter
@Getter
@Slf4j
public class ClientHandler {

    public static ClientHandler instance;
    private String[] commandLineArgs;
    private int port = 6667;

    private Socket clientSocket;
    private OutputStream out;
    private InputStream in;
    private ByteBuffer buffer = ByteBuffer.allocate(10000);

    private String login;
    private String password;

    private List<Person> people;
    private List<String> commandArguments = new LinkedList<>();
    private Person personForCommand;
    private Map<Integer, Location> readyLocations = new HashMap<>();

    private long idForUpdate;
    private boolean idIsSet = false;

    private ResourceBundle currentBundle;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private ClientHandler(String[] commandLineArgs) {
        this.commandLineArgs = commandLineArgs;
        checkPort();
        createStreamsAndSocket();
        initializeCollection();
    }

    public static ClientHandler getInstance(String[] args) {
        if (instance == null) {
            instance = new ClientHandler(args);
        }
        return instance;
    }

    private void initializeCollection() {
        sendCommand("show");
        setPeopleAndLocationsLists();
    }

    private void checkPort() {
        try {
            if (commandLineArgs.length != 0) {
                port = Integer.parseInt(commandLineArgs[0]);
                if (port <= 0) {
                    log.error("Порт не может быть отрицательным.");
                    System.exit(-1);
                } else if (port > 65535) {
                    log.error("Порт должен лежать в пределах 1-65535");
                    System.exit(-1);
                }
            }
        } catch (Exception e) {
            log.error("Порт должен быть числом");
            System.exit(-1);
        }
    }

    private void createStreamsAndSocket() {
        try {
            clientSocket = new Socket("localhost", port);
            out = clientSocket.getOutputStream();
            in = clientSocket.getInputStream();
            log.info("Клиент и стримы созданы");
        } catch (PortUnreachableException e) {
            log.error("Не удалось получить данные по указанному порту/сервер не доступен");
            System.exit(-1);
        } catch (UnknownHostException e) {
            log.error("Неизвестный хост");
            System.exit(-1);
        } catch (IOException e) {
            log.error("Ошибка при подключении к серверу. Выберите другой порт.");
            System.exit(-1);
        } catch (Exception e) {
            log.error("Ошибка при создании сокета");
            System.exit(-1);
        }
    }

    public void sendCommand(String commandName) {
        if (clientSocket.isConnected()) {
            try {
                out.write(OBJECT_MAPPER.writeValueAsBytes(createMessageForServer(commandName)));
                out.flush();
                clearFieldsAfterMessage();
            } catch (IOException e) {
                log.error("Ошибка при обработке запроса");
            }
        }
    }

    private void clearFieldsAfterMessage() {
        commandArguments = new LinkedList<>();
        personForCommand = null;
    }

    private Message createMessageForServer(String commandName) {
        Message message = Message.builder().commandName(commandName).build();
        commandArguments.add(0, password);
        commandArguments.add(0, login);
        if (commandName.contains("add") || commandName.equals("update")) {
            message.setPerson(personForCommand);
        } else if (commandName.equals("help")) {
            commandArguments.add(getCurrentBundle().getLocale().toString());
        }
        message.setCommandArgs(commandArguments);
        return message;
    }

    public ServerResponse getAnswerToCommand() {
        try {
            buffer.clear();
            int answerAsInt = in.read(buffer.array());
            if (answerAsInt > 0) {
                return OBJECT_MAPPER.readValue(buffer.array(), ServerResponse.class);
            }
            buffer.flip();
            return null;
        } catch (IOException e) {
            log.error("IOException while reading info from input stream in block 'getAnswerToCommand'");
            return null;
        }
    }

    public void setPeopleAndLocationsLists() {
        ServerResponse response;
        do {
            response = getAnswerToCommand();
        } while (response == null);
        people = response.getPersonList();
        log.info("People list is set first time");
        updateReadyLocations();
        log.info("Locations are updated");
    }

    private void updateReadyLocations() {
        boolean alreadyLocation = false;
        for (Person p : people) {
            Location currentLocation = p.getLocation();
            for (Location l : readyLocations.values()) {
                if (currentLocation.equals(l)) {
                    alreadyLocation = true;
                    break;
                }
            }
            if (!alreadyLocation) {
                readyLocations.put(readyLocations.size() + 1, currentLocation);
            }
            alreadyLocation = false;
        }
    }

    public String getEncodedBundleString(String key) {
        if (currentBundle == null) {
            throw new RuntimeException("Bundle is not specified");
        }
        return new String(currentBundle.getString(key).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }
}