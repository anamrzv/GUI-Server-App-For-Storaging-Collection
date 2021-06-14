package commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.DataBaseManager;
import other.Person;
import other.ServerResponse;
import other.CollectionsKeeper;

import java.util.LinkedList;
import java.util.List;

/**
 * Команда добавляет новый элемент в коллекцию, если его id меньше значения id наименьшего элемента.
 */
public class AddIfMin extends Command {

    private final LinkedList<Person> people = dc.getPeople();

    /**
     * Конструктор - создание нового объекта
     *
     * @param dc - обработчик команд
     */
    public AddIfMin(CollectionsKeeper dc) {
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
        String result;
        if (people.size() == 0) {
            result = manager.addPersonToDB(person, "add", args);
            if (result.equals("Объект успешно добавлен")){
                LinkedList<Person> newCollection = manager.loadCollectionFromDB().getPeople();
                people.clear();
                people.addAll(newCollection);
                return ServerResponse.builder().message("Объект добавлен в коллекцию, т.к. коллекция была пуста.").command("add_if_min").build();
            } else return ServerResponse.builder().error(result).command("add_if_min").build();
        } else {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            if (gson.toJson(person).length() > gson.toJson(people.getFirst()).length()) {
                return ServerResponse.builder().error("Объект не добавлен в коллекцию, т.к. его длина в формате json больше наименьшей").command("add_if_min").build();
            } else {
                result = manager.addPersonToDB(person, "add", args);
                if (result.equals("Объект успешно добавлен")){
                    LinkedList<Person> newCollection = manager.loadCollectionFromDB().getPeople();
                    people.clear();
                    people.addAll(newCollection);
                    return ServerResponse.builder().message("Объект добавлен в коллекцию, т.к. его длина в формате json меньше наименьшей или совпадает с ней.").command("add_if_min").build();
                } else return ServerResponse.builder().error(result).command("add_if_min").build();
            }
        }
    }

    /**
     * Возвращает имя команды
     *
     * @return имя
     */
    @Override
    public String getName() {
        return "add_if_min";
    }

    /**
     * Возвращает описание команды
     *
     * @return описание
     */
    @Override
    public String getDescription() {
        return "add_if_min :добавить новый элемент в коллекцию, если его id меньше, чем у наименьшего id  этой коллекции";
    }
}
