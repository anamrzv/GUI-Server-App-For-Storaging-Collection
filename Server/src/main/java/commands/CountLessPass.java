package commands;

import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * Команда считает кол-во объектов со значением passport id меньше заданного
 */
public class CountLessPass extends Command {

    /**
     * Конструктор - создание нового объекта
     *
     * @param dc - обработчик команд
     */
    public CountLessPass(CollectionsKeeper dc) {
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
        Long id;
        if (args.size() != 3) {
            return ServerResponse.builder().error("У команды count_less_than_passport_id должен быть один аргумент - ID паспорта. Введите команду снова.").command("count_less_than_passport_id").build();
        }
        try {
            id = Long.parseLong(args.get(2));
            if (id < 0) {
                return ServerResponse.builder().error("ID паспорта не может быть отрицательным числом. Введите команду снова.").command("count_less_than_passport_id").build();
            }
        } catch (Exception e) {
            return ServerResponse.builder().error("В качестве аргумента должна быть передана строка из цифр.\n Если строка составлена правильно, то передано слишком большое число. Введите команду снова.").command("count_less_than_passport_id").build();
        }
        LinkedList<Person> people = dc.getPeople();
        int res = (int) people.stream()
                .filter(x -> x.getPassportAsLong() < id)
                .count();
        return ServerResponse.builder().message(res + " - число элементов, значение поля passportID которых меньше " + id).command("count_less_than_passport_id").build();
    }

    /**
     * Возвращает имя команды
     *
     * @return имя
     */
    @Override
    public String getName() {
        return "count_less_than_passport_id";
    }

    /**
     * Возвращает описание команды
     *
     * @return описание
     */
    @Override
    public String getDescription() {
        return "count_less_than_passport_id passportID : вывести количество элементов, значение поля passportID которых меньше заданного";
    }
}
