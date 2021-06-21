package bundles;

import java.util.ListResourceBundle;

public class Language_sq_AL extends ListResourceBundle {
    private static final Object[][] contents = {
            {"authorisation", "Autorizimi"},
            {"login", "Hyrja"},
            {"password", "Fjalëkalimi"},
            {"log in", "Për të hyrë brenda"},
            {"sign in", "Regjistrohem"}
    };

    protected Object[][] getContents() {
        return contents;
    }
}
