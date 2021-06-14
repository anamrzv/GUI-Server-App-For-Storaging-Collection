package commands;

import other.CollectionsKeeper;
import other.ServerResponse;
import server.CommandHandler;

import java.util.List;
import java.util.Map;

/**
 * Команда выводит описание доступных команд в консоль
 */
public class Help extends Command {

    private final CommandHandler ch;
    private String response = "Доступные вам команды:\n";

    /**
     * Конструктор - создание нового объекта
     *
     * @param ch - обработчик команд
     */
    public Help(CollectionsKeeper dc, CommandHandler ch) {
        super(dc);
        this.ch = ch;
    }

    /**
     * Главный метод класса, запускает команду
     *
     * @param args Параметры командной строки
     * @return true/false Успешно ли завершилась команда
     */
    public ServerResponse execute(List<String> args) {
        if (args.size() == 2) {
            Map<String, Command> commands = ch.getCommands();
            commands.values()
                    .forEach(c -> response += c.getDescription() + "\n");
            //response += "add {json_element} : добавить новый элемент в коллекцию";
            return ServerResponse.builder().message(response).command("help").build();
        } else {
            return ServerResponse.builder().error("У команды help нет аргументов. Введите команду снова.").command("help").build();

        }
    }

    /**
     * Возвращает имя команды
     *
     * @return имя
     */
    public String getName() {
        return "help";
    }

    /**
     * Возвращает описание команды
     *
     * @return описание
     */
    public String getDescription() {
        return "help : вывести справку по доступным командам";
    }
}
