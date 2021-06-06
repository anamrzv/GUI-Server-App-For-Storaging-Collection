package commands;

import database.DataBaseManager;
import lombok.Setter;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.List;

/**
 * Абстрактный класс - любая команда
 */
@Setter
public abstract class Command {

    /**
     * Поле - обработчик команд
     */
    public CollectionsKeeper dc;

    protected Person person;

    /**
     * Конструктор - создание нового объекта
     *
     * @param dc - обработчик команд
     */
    Command(CollectionsKeeper dc) {
        this.dc = dc;
    }

    /**
     * Главный метод класса, запускает команду
     *
     * @param args Параметры командной строки
     * @return true/false Успешно ли завершилась команда
     */
    public ServerResponse execute(List<String> args){
        return null;
    }

    public ServerResponse execute(DataBaseManager manager, List<String> args){
        return null;
    }

    /**
     * Возвращает имя команды
     *
     * @return имя
     */
    public abstract String getName();

    /**
     * Возвращает описание команды
     *
     * @return описание
     */
    public abstract String getDescription();
}
