package bundles;

import java.util.ListResourceBundle;

public class Language_en_CA extends ListResourceBundle {
    private static final Object[][] contents = {
            {"authorisation", "Аuthorisation"},
            {"login", "Login"},
            {"password", "Password"},
            {"log in", "Log in"},
            {"sign in", "Sign in"}
    };

    protected Object[][] getContents(){
        return contents;
    }
}
