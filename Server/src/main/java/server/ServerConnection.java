package server;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import database.DataBaseManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import other.CollectionsKeeper;
import other.Message;
import other.ServerResponse;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@Slf4j
public class ServerConnection {


    private static boolean listenerIsAlive = true;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static CollectionsKeeper collectionsKeeper = null;
    private static Selector selector;
    private static int connectionCount = 0;
    private static DataBaseManager dataBase;

    public static void main(String[] args) {
        dataBase = new DataBaseManager();
        try {
            dataBase.connectToDB();
        } catch (Exception e) {
            System.out.println("Подсоединение отклонено. Убедитесь, что создан туннель к базе данных с локальной машины");
        }
        ServerListener serverListener;
        int port = 6667;
        try {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Экстренное закрытие сервера.");
                log.info("Emergency closing of server");
            }));

            if (args.length != 0) {
                try {
                    port = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    System.out.println("Порт должен быть числом");
                    log.error("Port is not a number");
                    System.exit(-1);
                }
                if (port <= 0) {
                    System.out.println("Порт не может быть отрицательным.");
                    log.error("Port is negative");
                    System.exit(-1);
                } else if (port > 65535) {
                    System.out.println("Порт должен лежать в пределах 1-65535");
                    log.error("Port has incorrect value");
                    System.out.println(-1);
                }
            }
            selector = Selector.open();
            log.info("Selector opened");
            serverListener = new ServerListener(port);
            log.info("Server started");
            System.out.println("Server started");
            serverListener.start();
        } catch (IOException e) {
            System.out.println("Ошибка при создании сервера");
            log.error("Error while creating server");
        }
    }

    static class ServerListener extends Thread { //чтение запросов с Thread
        private final ServerSocketChannel ssChannel;
        int port;

        public ServerListener(int port) throws IOException {
            this.port = port;
            ssChannel = ServerSocketChannel.open();
            ssChannel.configureBlocking(false);
            try {
                ssChannel.socket().bind(new InetSocketAddress(port));
                log.info("Port " + port + " bounded");
            } catch (IOException e) {
                System.out.println("Невозможно прослушать порт. Выберите другой и перезапустите сервер.");
                log.error("Can't listen port " + port);
                System.exit(-1);
            }
        }

        @SneakyThrows
        @Override
        public void run() {
            ssChannel.register(selector, ssChannel.validOps());
            while (true) {
                while (listenerIsAlive) {
                    try {
                        SocketChannel socketChannel = ssChannel.accept();
                        if (socketChannel != null) {
                            System.out.printf("Установлено новое подключение: %s%n", socketChannel.getRemoteAddress());
                            log.info("New connection created: {}", socketChannel.getRemoteAddress());
                            socketChannel.configureBlocking(false);
                            connectionCount += 1;
                            if (connectionCount == 1) {
                                collectionsKeeper = dataBase.loadCollectionFromDB();
                                if (collectionsKeeper == null) {
                                    System.out.println("Ошибка при загрузке коллекции из базы данных");
                                    log.error("Collection was not downloaded from 'people database'");
                                } else {
                                    log.info("Collection downloaded from 'people database' successfully");
                                }
                            }
                            ExecutorService executor = Executors.newCachedThreadPool();
                            executor.submit(new ClientHandler(socketChannel));
                        }
                    } catch (IOException e) {
                        try {
                            System.out.println("Соединение с клиентом отсутствует");
                            log.error("Connection with client is lost");
                            listenerIsAlive = false;
                            ssChannel.close();
                        } catch (IOException ioException) {
                            System.out.println("Ошибка при закрытии сервера");
                            log.error("Error while closing server");
                        }
                    }
                }
            }
        }
    }

    static class ClientHandler implements Runnable { //отправка ответа
        private final SocketChannel sChannel;

        public ClientHandler(SocketChannel sChannel) {
            this.sChannel = sChannel;
        }

        @SneakyThrows
        @Override
        public void run() {
            sChannel.configureBlocking(false);
            /*if (sChannel.isOpen()) {
                sChannel.write(ByteBuffer.wrap(OBJECT_MAPPER.writeValueAsBytes(collectionsKeeper)));
                log.info("CollectionsKeeper sent");
            }*/
            while (sChannel.isOpen()) {
                try {
                    ByteBuffer buffer = ByteBuffer.allocate(5120);
                    while (true) {
                        buffer.clear();
                        int read = sChannel.read(buffer); // non-blocking
                        if (read < 0) {
                            break;
                        }
                        if (read > 0) {
                            ExecutorService executor = Executors.newCachedThreadPool();
                            Future<ServerResponse> future = executor.submit(new HandleClientRequest(OBJECT_MAPPER.readValue(buffer.array(), Message.class)));
                            while (!future.isDone()) {
                                log.info("Calculating...");
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    System.out.println("Interrupted exception while sleeping");
                                }
                            }
                            log.info("Got {}", OBJECT_MAPPER.readValue(buffer.array(), Message.class));
                            System.out.println("Got "+OBJECT_MAPPER.readValue(buffer.array(), Message.class));
                            ServerResponse serverResponse = future.get();
                            sChannel.write(ByteBuffer.wrap(OBJECT_MAPPER.writeValueAsBytes(serverResponse)));
                            log.info("Answer sent");
                            if (serverResponse.getMessage() != null && serverResponse.getMessage().equals("Disconnected successfully")) {
                                log.info("Client {} disconnected", sChannel.getRemoteAddress());
                                System.out.println("Клиент " + sChannel.getRemoteAddress() + " отсоединен");
                                if (serverResponse.getError() != null) System.out.println(serverResponse.getError());
                                sChannel.close();
                                break;
                            }
                        }
                        buffer.flip();
                    }
                } catch (Exception e) {
                    try {
                        e.printStackTrace();
                        System.out.println("Соединение с клиентом" + sChannel.getRemoteAddress() + " экстренно прекращено");
                        log.info("Emergency disconnection from client " + sChannel.getRemoteAddress());
                        sChannel.close();
                    } catch (IOException ioException) {
                        System.out.println("Ошибка при закрытии клиента");
                    }
                }
            }
        }
    }

    /**
     * Обработка сообщения от клиента
     */
    static class HandleClientRequest implements Callable<ServerResponse> {
        private final Message message;

        public HandleClientRequest(Message message) {
            this.message = message;
        }

        public ServerResponse call() { //обработка запроса
            CommandHandler command;
            if (message == null) {
                log.error("The client sent incorrect data: empty");
                return ServerResponse.builder().error("Был передан неправильный (пустой) запрос").build();
            }
            if (message.getCommandName().equalsIgnoreCase("exit")) {
                connectionCount -= 1;
                return ServerResponse.builder().message("Отключение от сервера проведено успешно").build();
            } else if (message.getCommandName().equals("add") || message.getCommandName().equals("add_if_max") || message.getCommandName().equals("add_if_min")) {
                command = new CommandHandler(message.getCommandName(), message.getPerson(), message.getCommandArgs(), collectionsKeeper);
            } else if (message.getCommandName().equals("update")) {
                command = new CommandHandler(message.getCommandName(), message.getPerson(), message.getCommandArgs(), collectionsKeeper);
            } else {
                command = new CommandHandler(message.getCommandName(), message.getCommandArgs(), collectionsKeeper);
            }
            command.setManager(dataBase);
            return command.setRun();
        }
    }
}