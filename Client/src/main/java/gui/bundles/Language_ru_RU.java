package gui.bundles;

import java.util.ListResourceBundle;

public class Language_ru_RU extends ListResourceBundle {

    private static final Object[][] contents = {
            //start.fxml
            {"authorisation", "Авторизация"},
            {"login", "Логин"},
            {"password", "Пароль"},
            {"log in", "Войти"},
            {"sign in", "Зарегестрироваться"},

            //sign in.fxml
            {"registration", "Регистрация"},
            {"rules", "Можно использовать латинские буквы, цифры, точку и нижнее подчеркивание"},
            {"loginRules", "Логин* (от 4 символов)"},
            {"passwordRules", "Пароль (от 3 символов)"},
            {"to authorisation", "К авторизации"},

            //table
            {"reset", "Сброс"},
            {"filter", "Фильтр"},
            {"to commands list", "К списку команд"},
            {"user", "Пользователь: "},
            {"map", "Карта"},

            //commands buttons
            {"add", "Добавить"},
            {"update", "Обновить"},
            {"removeID", "Удалить"},
            {"removePass", "Удалить по паспорту"},
            {"clear", "Очистить"},
            {"countPass", "Люди с меньшим паспортом"},
            {"weightSum", "Сумма веса"},
            {"script", "Скрипт"},
            {"to table", "К таблице"},
            {"help", "Помощь"},
            {"head", "Первый"},
            {"info", "Инфо"},
            {"addLabel", "Добавление элемента"},
            {"removeLabel", "Удаление элемента"},
            {"otherLabel", "Другие команды"},

            //add
            {"firstLabel", "Введите данные о человеке."},
            {"secondLabel", "Обязательные поля помечены звездочками."},
            {"thirdLabel", "Если Вы выбрали добавление"},
            {"fourthLabel", "новой локации:"},
            {"fifthLabel", "Особенности добавления: "},
            {"name", "Имя*:"},
            {"height", "Рост:"},
            {"weight", "Вес:"},
            {"passport", "Паспорт*:"},
            {"hair", "Волосы*:"},
            {"x", "Коорд. X*:"},
            {"y", "Коорд.  Y*:"},
            {"location", "Локация*:"},
            {"title", "Название:"},
            {"simple add", "Простое добавление"},
            {"add if max", "Добавить, если больше"},
            {"add if min", "Добавить, если меньше"},
            {"create", "Создать"},

            //count less than passport
            {"input passport", "Введите номер паспорта."},
            {"rules of count", "Будут посчитаны все люди, у которых номер паспорта меньше заданного."},
            {"num of passport", "Номер паспорта"},
            {"ready", "Готово"},

            //filter
            {"set up filter", "Установите фильтр:"},
            {"less", "Меньше"},
            {"more", "Больше или равно"},
            {"argument", "Аргумент"},

            //remove
            {"input id", "Введите ID."},
            {"rules of remove", "Будет удален человек, у которого ID совпадает с заданным"},
            {"rules of remove passport", "Будут удалены все люди, у которых номер паспорта совпадает с заданным."},

            //update
            {"update rules","Обновите те поля, которые Вам нужны"},
            {"fifth label update","ID (после ввода нажмите Enter)"},


    };

    protected Object[][] getContents() {
        return contents;
    }

}
