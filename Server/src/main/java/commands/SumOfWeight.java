package commands;

import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * Команда выводит сумму поля 'weight' всех объектов коллекции
 */
public class SumOfWeight extends Command {

    private long sum = 0;

    /**
     * Конструктор - создание нового объекта
     *
     * @param dc - обработчик команд
     */
    public SumOfWeight(CollectionsKeeper dc) {
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
            people.forEach(x -> sum += x.getWeight());
            return ServerResponse.builder().message(Long.toString(sum)).command("sum_of_weight").build();
        } else {
            return ServerResponse.builder().error("error").command("sum_of_weight").build();
        }
    }

    /**
     * Возвращает имя команды
     *
     * @return имя
     */
    @Override
    public String getName() {
        return "sum_of_weight";
    }

    /**
     * Возвращает описание команды
     *
     * @return описание
     */
    @Override
    public String getDescription() {
        return "sum_of_weight : вывести сумму значений поля weight для всех элементов коллекции";
    }
}
