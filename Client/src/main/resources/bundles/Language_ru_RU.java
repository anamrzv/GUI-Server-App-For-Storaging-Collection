package bundles;

import java.util.ListResourceBundle;

public class Language_ru_RU extends ListResourceBundle {

    private static final Object[][] contents = {
            //start.fxml
            {"authorisation", "Авторизация"},
            {"login", "Логин"},
            {"password", "Пароль"},
            {"log in", "Войти"},
            {"sign in", "Зарегестрироваться"}


    };

    protected Object[][] getContents(){
        return contents;
    }
}
