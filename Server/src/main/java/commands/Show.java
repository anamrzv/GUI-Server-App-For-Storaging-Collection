package commands;

import other.CollectionsKeeper;
import other.Person;
import other.PersonSizeComparator;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда выводит в консоль все элементы коллекции в строковом представлении
 */
public class Show extends Command {

    private String response = "Коллекция People:";

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
        if (args.size() == 2) {
            LinkedList<Person> people = dc.getPeople();
            if (people.size() == 0)
                return ServerResponse.builder().message("Коллекция People пуста.").command("show").build();
            else {
                PersonSizeComparator comparator = new PersonSizeComparator();
                people.stream()
                        .sorted(comparator)
                        .forEach(p -> response += "\n" + p);
                people = people.stream()
                        .sorted(comparator)
                        .collect(Collectors.toCollection(LinkedList::new));
            }
            return ServerResponse.builder().message(response).command("show").build();
        } else {
            return ServerResponse.builder().error("У команды show нет аргументов. Введите команду снова.").command("show").build();
        }
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
