package commands;

import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * Команда выводит первый элемент коллекции
 */
public class Head extends Command {

    /**
     * Поле - связный список объектов Person
     */
    LinkedList<Person> people;

    /**
     * Конструктор - создание нового объекта
     *
     * @param dc - обработчик команд
     */
    public Head(CollectionsKeeper dc) {
        super(dc);
    }

    /**
     * Главный метод класса, запускает команду
     *
     * @param args Параметры командной строки
     * @return true/false Успешно ли завершилась команда
     */
    @Override
    public ServerResponse execute(List<String> args) {
        if (args.size() == 2) {
            people = dc.getPeople();
            if (people.size() == 0)
                return ServerResponse.builder().message("head empty").command("head").build();
            else {
                return ServerResponse.builder().message(people.get(0).toString()).command("head").build();
            }
        } else {
            return ServerResponse.builder().error("error").command("head").build();
        }
    }

    /**
     * Возвращает имя команды
     *
     * @return имя
     */
    @Override
    public String getName() {
        return "head";
    }

    /**
     * Возвращает описание команды
     *
     * @return описание
     */
    @Override
    public String getDescription() {
        return "head : вывести первый элемент коллекции";
    }
}
