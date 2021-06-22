package newclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
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
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

@Setter
@Getter
public class ClientHandler {

    public static ClientHandler instance;

    private Socket clientSocket;
    private OutputStream out;
    private InputStream in;

    private String[] args;
    private String login;
    private String password;

    private List<Person> people;
    private List<String> commandArguments;
    private Person person;

    private long idForUpdate;
    private boolean idIsSet = false;

    private ResourceBundle currentBundle;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private ClientHandler(String[] args) {
        this.args = args;
        connect();
    }

    public static ClientHandler getInstance(String[] args) {
        if (instance == null) {
            instance = new ClientHandler(args);
        }
        return instance;
    }

    public void connect() {
        int port = 6667;
        if (args.length != 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.out.println("Порт должен быть числом");
                System.exit(-1);
            }
            if (port <= 0) {
                System.out.println("Порт не может быть отрицательным.");
                System.exit(-1);
            } else if (port > 65535) {
                System.out.println("Порт должен лежать в пределах 1-65535");
                System.out.println(-1);
            }
        }
        try {
            clientSocket = new Socket("localhost", port);
            out = clientSocket.getOutputStream();
            in = clientSocket.getInputStream();
            System.out.println("Клиент создан");
            sendCommand("show");
            getPeopleAnswer();
        } catch (PortUnreachableException e) {
            System.out.println("Не удалось получить данные по указанному порту/сервер не доступен");
            System.exit(-1);
        } catch (UnknownHostException e) {
            System.out.println("Неизвестный хост");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Ошибка при подключении к серверу. Выберите другой порт.");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("Ошибка при создании сокета");
        }
    }

    public void sendCommand(String commandName) {
        if (clientSocket.isConnected()) {
            Message message = Message.builder().commandName(commandName).build();
            if (commandArguments == null) {
                commandArguments = new LinkedList<>();
            }
            commandArguments.add(0, password);
            commandArguments.add(0, login);
            message.setCommandArgs(commandArguments);
            if (commandName.equals("add")||commandName.equals("add_if_max")||commandName.equals("add_if_min")||commandName.equals("update")){
                message.setPerson(person);
            }
            try {
                out.write(OBJECT_MAPPER.writeValueAsBytes(message));
                out.flush();
                commandArguments = null;
                person = null;
            } catch (JsonProcessingException e) {
                System.out.println("Ошибка при обработке запроса");
            } catch (IOException e) {
                System.out.println("Ошибка при обработке запроса");
            }

        }
    }

    public ServerResponse getAnswer() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(5120);
        buffer.clear();
        int serverAnswer = in.read(buffer.array());
        if (serverAnswer > 0) {
            ServerResponse sr = OBJECT_MAPPER.readValue(buffer.array(), ServerResponse.class);
            return sr;
        }
        buffer.flip();
        return null;
    }

    public String getPeopleAnswer() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(5120);
        int serverAnswer = 0;
        do {
            buffer.clear();
            serverAnswer = in.read(buffer.array());
            if (serverAnswer > 0) {
                ServerResponse sr = OBJECT_MAPPER.readValue(buffer.array(), ServerResponse.class);
                people = sr.getPersonList();
            }
        } while (serverAnswer <= 0);
        return null;
    }

    public String getEncodedBundleString(String key) {
        if (currentBundle == null) {
            throw new RuntimeException("Bundle is not specified");
        }
        return new String(currentBundle.getString(key).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

}
