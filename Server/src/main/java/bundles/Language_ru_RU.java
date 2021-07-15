package bundles;

import java.util.ListResourceBundle;

public class Language_ru_RU extends ListResourceBundle {

    private static final Object[][] contents = {
            //help
            {"add description", "Добавить : добавить новый элемент в коллекцию"},
            {"add_if_min description", "Добавить, если меньше :добавить новый элемент в коллекцию, если его длина в формате строки меньше наименьшей или совпадает с ней"},
            {"add_if_max description", "Добавить, если больше : добавить новый элемент в коллекцию, если его длина в формате строки больше наибольшей или совпадает с ней"},
            {"execute_script description", "Скрипт : считать и исполнить скрипт из указанного файла (вместо file_name укажите путь к файлу). В скрипте содержатся команды в таком же виде, в котором они вводятся в интерактивном режиме"},
            {"update description", "Обновить : обновить значение элемента коллекции, id которого равен заданному"},
            {"remove_by_id description", "Удалить : удалить элемент из коллекции по его id"},
            {"remove_all_by_passport_id description", "Удалить по паспорту : удалить из коллекции все элементы, значение поля passportID которого эквивалентно заданному"},
            {"clear description", "Очистить : очистить коллекцию"},
            {"count_less_than_passport_id description", "Люди с меньшим паспортом : вывести количество элементов, значение поля passportID которых меньше заданного"},
            {"sum_of_weight description", "Сумма веса : вывести сумму значений поля weight для всех элементов коллекции"},
            {"help description", "Помощь : вывести справку по доступным командам"},
            {"head description", "Первый : вывести первый элемент коллекции"},
            {"info description", "Инфо : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)"}
    };

    protected Object[][] getContents() {
        return contents;
    }

}
