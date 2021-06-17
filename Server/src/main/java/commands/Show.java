package commands;

import other.CollectionsKeeper;
import other.Person;
import other.PersonSizeComparator;
import other.ServerResponse;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда выводит в консоль все элементы коллекции в строковом представлении
 */
public class Show extends Command {

    /**
     * Конструктор - создание нового объекта
     *
     * @param dc - обработчик команд
     */
    public Show(CollectionsKeeper dc) {
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
        List<Person> sortedPeople;
        LinkedList<Person> people = dc.getPeople();
        if (people.size() == 0)
            return ServerResponse.builder().personList(null).command("show").build();
        else {
            sortedPeople = people.stream()
                    .sorted(Comparator.comparing(Person::getId))
                    .collect(Collectors.toList());
        }
        return ServerResponse.builder().personList(sortedPeople).command("show").build();
    }

    /**
     * Возвращает имя команды
     *
     * @return имя
     */
    @Override
    public String getName() {
        return "show";
    }

    /**
     * Возвращает описание команды
     *
     * @return описание
     */
    @Override
    public String getDescription() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
