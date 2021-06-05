package commands;

import database.DataBaseManager;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;


/**
 * Команда удаляет объект с данным значением id
 */
public class RemoveByID extends Command {

    private DataBaseManager manager;

    /**
     * Конструктор - создание нового объекта
     *
     * @param dc - обработчик команд
     */
    public RemoveByID(CollectionsKeeper dc) {
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
        this.manager = manager;
        if (args.size() != 2) {
            if (args.size() != 3) {
                return ServerResponse.builder().error("У команды remove_by_id должен быть ровно один аргумент - ID персоны. Введите команду снова.").command("remove_by_id").build();
            }
            long id;
            try {
                id = Long.parseLong(args.get(2));
                if (id < 0)
                    return ServerResponse.builder().error("ID не может быть отрицательным числом. Введите команду снова.").command("remove_by_id").build();
            } catch (Exception e) {
                return ServerResponse.builder().error("В качестве аргумента должна быть передана строка из цифр. Введите команду снова.").command("remove_by_id").build();
            }
            LinkedList<Person> people = dc.getPeople();
            Person person = people.stream()
                    .filter(x -> x.getId().equals(id))
                    .findFirst()
                    .orElse(null);
            int result;
            if (person == null)
                return ServerResponse.builder().message("Элемента с таким ID нет в коллекции.").command("remove_by_id").build();
            else {
                result = manager.deleteByIdFromBD(person, args);
                if (result == 1) {
                    people.remove(person);
                    return ServerResponse.builder().message("Объект с ID " + id + " успешно удален из коллекции.").command("remove_by_id").build();
                } else if (result == 0) {
                    return ServerResponse.builder().error("Элемент не удален, т.к. вы не являетесь его владельцем").command("remove_by_id").build();
                } else
                    return ServerResponse.builder().error("Элемент не удален, т.к. невозможно удалить его в базе данных.").command("remove_by_id").build();
            }
        } else {
            return ServerResponse.builder().error("У команды remove_by_id должен быть один аргумент - ID персоны. Введите команду снова.").command("remove_by_id").build();
        }
    }

    /**
     * Возвращает имя команды
     *
     * @return имя
     */
    @Override
    public String getName() {
        return "remove_by_id";
    }

    /**
     * Возвращает описание команды
     *
     * @return описание
     */
    @Override
    public String getDescription() {
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }
}
