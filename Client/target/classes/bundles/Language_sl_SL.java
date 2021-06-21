package bundles;

import java.util.ListResourceBundle;

public class Language_sl_SL extends ListResourceBundle {
    private static final Object[][] contents = {
            {"authorisation", "Povolenie"},
            {"login", "Prihlásiť sa"},
            {"password", "Heslo"},
            {"log in", "Vstúpiť"},
            {"sign in", "Registrovať"}
    };

    protected Object[][] getContents() {
        return contents;
    }
}
