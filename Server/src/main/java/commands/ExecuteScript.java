package commands;

import other.CollectionsKeeper;
import other.ServerResponse;
import server.CommandHandler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Команда получает текстовый файл со скриптом и выполняет команды оттуда
 */
public class ExecuteScript extends Command {

    private final CommandHandler ch;

    /**
     * Конструктор - создание нового объекта
     *
     * @param dc - обработчик команд
     */
    public ExecuteScript(CollectionsKeeper dc, CommandHandler ch) {
        super(dc);
        this.ch = ch;
    }

    /**
     * Главный метод класса, запускает команду
     *
     * @param args Параметры командной строки
     * @return true/false Успешно ли завершилась команда
     */
    @Override
    public ServerResponse execute(List<String> args) {
        if (args.size() == 3) {
            try {
                Path path = Paths.get(args.get(2));
                if (Files.exists(path) && !Files.isRegularFile(path)) {
                    return ServerResponse.builder().error("Нельзя передать специальный файл в качестве скрипта. Введите команду снова с новым аргументом.").command("execute_script").build();
                } else {
                    if (dc.getScriptNames().size() == 0) dc.addScriptName(args.get(2));
                    String dir = path.toString();
                    try (BufferedReader br = new BufferedReader(new FileReader(dir))) {
                        Map<String, Command> commands = ch.getCommands();
                        String line;
                        while ((line = br.readLine()) != null) {
                            line = line.trim();
                            if (line.equals("execute_script " + args.get(2))) {
                                return ServerResponse.builder().error("В скрипте обнаружена рекурсия, удалите строку execute_script " + args.get(2)).command("execute_script").build();
                            } else if (line.startsWith("execute_script")) {
                                String cmd = getCommandName(line);
                                List<String> arguments = getArguments(line);
                                if (dc.addScriptName(arguments.get(2))) {
                                    Command command = commands.get(cmd);
                                    command.execute(arguments);
                                } else
                                    return ServerResponse.builder().error("В одном из вызовов команды execute_script в файле обнаружена рекурсия, команда пропущена\nУдалите строку " + arguments.get(2) + "\n").command("execute_script").build();
                            } else {
                                String cmd = getCommandName(line);
                                List<String> arguments = getArguments(line);
                                Command command = commands.get(cmd);
                                command.execute(arguments);
                                System.out.println();
                            }
                        }
                        dc.clearScriptNames();
                    } catch (FileNotFoundException e) {
                        return ServerResponse.builder().error("Файл не найден. Убедитесь, что вы правильно указали путь к файлу и введите команду снова.").command("execute_script").build();
                    } catch (Exception e) {
                        return ServerResponse.builder().error("Произошла ошибка при чтении команды скрипта.").command("execute_script").build();
                    }
                }
            } catch (Exception e) {
                return ServerResponse.builder().error("Ошибка при обработке файла. Проверьте, что команде не был передан специальный файл.").command("execute_script").build();
            }
        } else {
            return ServerResponse.builder().error("У команды execute_script должен быть один аргумент - путь к файлу. Введите команду снова.").command("execute_script").build();
        }
        return ServerResponse.builder().message("Скрипт выполнен").command("execute_script").build();
    }

    /**
     * Метод - возвращает имя команды
     *
     * @param input - строка
     * @return String - имя команды
     */
    public String getCommandName(String input) {
        String[] elements = input.split(" +");
        return elements[0].toLowerCase(); //только название команды
    }

    /**
     * Метод - возвращает аргументы команды
     *
     * @param input - строка
     * @return String[] - аргументы команды
     */
    public List<String> getArguments(String input) {
        List<String> elements = Arrays.stream(input.split(" +")).collect(Collectors.toList());
        if (elements.size() > 1) {
            return elements.stream().skip(1).collect(Collectors.toList());
        } else return null;
    }

    /**
     * Возвращает имя команды
     *
     * @return имя
     */
    @Override
    public String getName() {
        return "execute_script";
    }

    /**
     * Возвращает описание команды
     *
     * @return описание
     */
    @Override
    public String getDescription() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла (вместо file_name укажите путь к файлу). В скрипте содержатся команды в таком же виде, в котором они вводятся в интерактивном режиме.";
    }
}
