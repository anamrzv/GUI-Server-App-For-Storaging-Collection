package commands;

import database.DataBaseManager;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * Команда удаляет из коллекции все объекты с passport id меньше заданного
 */
public class RemoveByPass extends Command {

    DataBaseManager manager;

    /**
     * Конструктор - создание нового объекта
     *
     * @param dc - обработчик команд
     */
    public RemoveByPass(CollectionsKeeper dc) {
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
                return ServerResponse.builder().error("passport error arguments").command("remove_by_passport_id").build();
            }
            long id;
            try {
                id = Long.parseLong(args.get(2));
                if (id < 0)
                    return ServerResponse.builder().error("passport error minus").command("remove_by_passport_id").build();
            } catch (Exception e) {
                return ServerResponse.builder().error("passport error validate").command("remove_by_passport_id").build();
            }
            LinkedList<Person> people = dc.getPeople();
            int count = 0;
            int unsuccess = 0;
            int noAcces = 0;
            do {
                Person person = people.stream()
                        .filter(x -> x.getPassportAsLong().equals(id))
                        .findFirst()
                        .orElse(null);
                if (person != null) {
                    int result = manager.deleteByIdFromBD(person, args);
                    if (result == 1) {
                        people.remove(person);
                        count++;
                    } else if (result == -1) {
                        people.remove(person);
                        unsuccess++;
                    } else {
                        people.remove(person);
                        noAcces++;
                    }
                } else break;
            } while (true);
            LinkedList<Person> newCollection = manager.loadCollectionFromDB().getPeople();
            people.clear();
            people.addAll(newCollection);
            if (count != 0 && unsuccess == 0 && noAcces == 0)
                return ServerResponse.builder().message("passport remove success").command("remove_by_passport_id").build();
            else if (count == 0 && unsuccess == 0 && noAcces == 0)
                return ServerResponse.builder().message("passport no id").command("remove_by_passport_id").build();
            else if (count != 0 && unsuccess != 0)
                return ServerResponse.builder().message("passport not remove from db").command("remove_by_passport_id").build();
            else
                return ServerResponse.builder().error("passport remove error").command("remove_by_passport_id").build();
        } else {
            return ServerResponse.builder().error("passport error arguments").command("remove_by_passport_id").build();
        }
    }


    /**
     * Возвращает имя команды
     *
     * @return имя
     */
    @Override
    public String getName() {
        return "remove_all_by_passport_id";
    }

    /**
     * Возвращает описание команды
     *
     * @return описание
     */
    @Override
    public String getDescription() {
        return "remove_all_by_passport_id passportID : удалить из коллекции все элементы, значение поля passportID которого эквивалентно заданному";
    }
}
