package bundles;

import java.util.ListResourceBundle;

public class Language_sq_AL extends ListResourceBundle {
    private static final Object[][] contents = {
            {"authorisation", "Autorizimi"},
            {"login", "Hyrja"},
            {"password", "Fjalëkalimi"},
            {"log in", "Për të hyrë brenda"},
            {"sign in", "Regjistrohem"},

            //sign in.fxml
            {"registration", "Regjistrohuni"},
            {"rules", "Përdorni letra, numra, pika, nënvizo latinisht"},
            {"loginRules", "Hyrja* (nga 4 karaktere)"},
            {"passwordRules", "Fjalëkalimi (nga 3 karaktere)"},
            {"to authorisation", "Për autorizim"},

            //table
            {"reset", "Rivendos"},
            {"filter", "Filtri"},
            {"to commands list", "Në listën e komandave"},
            {"user", "Përdorues"},
    };

    protected Object[][] getContents() {
        return contents;
    }
}
