package newclient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import gui.controllers.MainController;
import gui.controllers.StartController;
import lombok.Setter;
import other.Message;
import other.ServerResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.PortUnreachableException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

@Setter
public class ClientHandler {

    public static ClientHandler instance;

    private Socket clientSocket;
    private OutputStream out;
    private InputStream in;
    private String[] args;
    private String login;
    private String password;


    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private ClientHandler(String[] args) {
        this.args = args;
        connect();
    }

    public static ClientHandler getInstance(String[] args){
        if (instance==null) {
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


    public void sendMessage(Message message) {
        if (clientSocket.isConnected()) {
            try {
                List<String> userInfoList = new LinkedList<>();
                userInfoList.add(login);
                userInfoList.add(password);
                message.setCommandArgs(userInfoList);
                out.write(OBJECT_MAPPER.writeValueAsBytes(message));
                out.flush();
            } catch (Exception e) {
                System.out.println("Ошибка при отправке сообщения");
                e.printStackTrace();
            }
        }
    }

    public String getAnswer() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(5120);
        buffer.clear();
        int serverAnswer = in.read(buffer.array());
        if (serverAnswer > 0) {
            ServerResponse sr = OBJECT_MAPPER.readValue(buffer.array(), ServerResponse.class);
            if (sr.getError() == null) {
                return "success";
            } else return sr.getError();
        }
        buffer.flip();
        return "";
    }

}
