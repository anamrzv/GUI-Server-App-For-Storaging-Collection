package bundles;

import java.util.ListResourceBundle;

public class Language_sl_SL extends ListResourceBundle {
    private static final Object[][] contents = {
            {"authorisation", "Povolenie"},
            {"login", "Prihlásiť sa"},
            {"password", "Heslo"},
            {"log in", "Vstúpiť"},
            {"sign in", "Registrovať"},

            //sign in.fxml
            {"registration", "Nahlásiť sa},
            {"rules", "Používajte latinské písmená, číslice, bodku a podčiarkovník"},
            {"loginRules", "Prihlásenie* (od 4 znakov)"},
            {"passwordRules", "Heslo (od 3 znakov)"},
            {"to authorisation", "K autorizácii"},

            //table
            {"reset", "Resetovať"},
            {"filter", "Filtrovať"},
            {"to commands list", "Do zoznamu príkazov"},
            {"user", "Používateľ"},
    };

    protected Object[][] getContents() {
        return contents;
    }
}
