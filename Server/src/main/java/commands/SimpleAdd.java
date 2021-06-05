package commands;

import database.DataBaseManager;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * Команда добавляет элемент в коллекцию либо через меню выбора, либо интерпретируя строку json
 */
public class SimpleAdd extends Command {

    private final LinkedList<Person> people = dc.getPeople();
    private DataBaseManager manager;

    /**
     * Конструктор - создание нового объекта
     *
     * @param dc - обработчик команд
     */
    public SimpleAdd(CollectionsKeeper dc) {
        super(dc);
    }

    /**
     * Главный метод класса, запускает команду
     *
     * @return true/false Успешно ли завершилась команда
     */

    public ServerResponse execute(DataBaseManager manager, List<String> args) {
        this.manager = manager;
        String result = manager.addPersonToDB(person, "add", args);
        if (result.equals("Объект успешно добавлен")) {
            LinkedList<Person> newCollection = manager.loadCollectionFromDB().getPeople();
            people.clear();
            people.addAll(newCollection);
            return ServerResponse.builder().message(result).command("add").build();
        } else
            return ServerResponse.builder().error(result).command("add").build();
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "add person: добавить новый элемент в коллекцию, ввод вручную\nadd json_element : добавить новый элемент в коллекцию, автоматическая обработка строки json";
    }
}
