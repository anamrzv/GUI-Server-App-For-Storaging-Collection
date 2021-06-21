package bundles;

import java.util.ListResourceBundle;

public class Language_en_CA extends ListResourceBundle {
    private static final Object[][] contents = {
            {"authorisation", "Аuthorisation"},
            {"login", "Login"},
            {"password", "Password"},
            {"log in", "Log in"},
            {"sign in", "Sign in"},

            //sign in.fxml
            {"registration", "Registration"},
            {"rules", "Use latin letters, numbers, period, underscore"},
            {"loginRules", "Login* (from 4 symbols)"},
            {"passwordRules", "Password (from 3 symbols)"},
            {"to authorisation", "To authorisation"},

            //table
            {"reset", "Reset"},
            {"filter", "Filter"},
            {"to commands list", "To commands"},
            {"user", "User: "},
    };

    protected Object[][] getContents(){
        return contents;
    }
}
