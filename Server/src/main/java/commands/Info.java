package commands;

import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * Команда выводит информацию о коллекции в консоль
 */
public class Info extends Command {

    /**
     * Конструктор - создание нового объекта
     *
     * @param dc - обработчик команд
     */
    public Info(CollectionsKeeper dc) {
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
            String response= "Тип коллекции: " + people.getClass()+"\nТип элементов: Person\nКоличество элементов: "+people.size();
            return ServerResponse.builder().message(response).command("info").build();
        } else {
            return ServerResponse.builder().error("У команды info нет аргументов. Введите команду снова.").command("info").build();
        }
    }

    /**
     * Возвращает имя команды
     *
     * @return имя
     */
    @Override
    public String getName() {
        return "info";
    }

    /**
     * Возвращает описание команды
     *
     * @return описание
     */
    @Override
    public String getDescription() {
        return "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
