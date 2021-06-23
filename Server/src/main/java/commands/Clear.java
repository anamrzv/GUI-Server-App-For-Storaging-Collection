package commands;

import database.DataBaseManager;
import other.Person;
import other.ServerResponse;
import other.CollectionsKeeper;

import java.util.LinkedList;
import java.util.List;

/**
 * Команда очищает коллекцию
 */
public class Clear extends Command {

    /**
     * Конструктор - создание нового объекта
     *
     * @param dc - обработчик команд
     */
    public Clear(CollectionsKeeper dc) {
        super(dc);
    }

    /**
     * Главный метод класса, запускает команду
     *
     * @param args Параметры командной строки
     * @return true/false Успешно ли завершилась команда
     */
    @Override
    public ServerResponse execute(DataBaseManager manager, List<String> args) {
        LinkedList<Person> people = dc.getPeople();
        if (args.size() == 2) {
            List<Person> deletedPeople = manager.deleteByLoginFromDB(args);
            if (deletedPeople != null) {
                people.removeAll(deletedPeople);
                return ServerResponse.builder().message("clear success").command("clear").build();
            } else return ServerResponse.builder().error("clear error").build();
        } else {
            return ServerResponse.builder().error("error").build();
        }
    }

    /**
     * Возвращает имя команды
     *
     * @return имя
     */
    @Override
    public String getName() {
        return "clear";
    }

    /**
     * Возвращает описание команды
     *
     * @return описание
     */
    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }
}
