package commands;

import database.DataBaseManager;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * Команда обновляет значения полей объекта коллекции с заданным id
 */
public class Update extends Command {

    private final LinkedList<Person> people = dc.getPeople();
    private DataBaseManager manager;

    /**
     * Конструктор - создание нового объекта
     *
     * @param dc - обработчик команд
     */
    public Update(CollectionsKeeper dc) {
        super(dc);
    }

    /**
     * Главный метод класса, запускает команду
     *
     * @return true/false Успешно ли завершилась команда
     */

    public ServerResponse execute(DataBaseManager manager, List<String> args) {
        this.manager = manager;
        String result = manager.addPersonToDB(person, "update", args);
        if (result.equals("Объект успешно обновлен")) {
            LinkedList<Person> newCollection = manager.loadCollectionFromDB().getPeople();
            people.clear();
            people.addAll(newCollection);
            return ServerResponse.builder().message(result).command("update").build();
        } else
            return ServerResponse.builder().error(result).command("update").build();
    }


    /**
     * Возвращает имя команды
     *
     * @return имя
     */
    @Override
    public String getName() {
        return "update";
    }

    /**
     * Возвращает описание команды
     *
     * @return описание
     */
    @Override
    public String getDescription() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }
}
