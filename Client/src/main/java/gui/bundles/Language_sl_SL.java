package gui.bundles;

import java.util.ListResourceBundle;

public class Language_sl_SL extends ListResourceBundle {
    private static final Object[][] contents = {
            {"authorisation", "Povolenie"},
            {"login", "Prihlasit sa"},
            {"password", "Heslo"},
            {"log in", "Vstupit"},
            {"sign in", "Registrovat"},

            //sign in.fxml
            {"registration", "Nahlasit sa"},
            {"rules", "Pouzivajte latinske pismena, cislice, bodku a podciarkovnik"},
            {"loginRules", "Prihlasenie* (od 4 znakov)"},
            {"passwordRules", "Heslo (od 3 znakov)"},
            {"to authorisation", "K autorizacii"},

            //table
            {"reset", "Resetovat"},
            {"filter", "Filtrovat"},
            {"to commands list", "Do zoznamu prikazov"},
            {"user", "Pouzivatel: "},
            {"map", "Mapa"},

            //commands buttons
            {"add", "Pridať k"},
            {"update", "Obnoviť"},
            {"removeID", "Odstrániť"},
            {"removePass", "Vymazať podľa pasu"},
            {"clear", "Jasný"},
            {"countPass", "Počítajte menej pasu"},
            {"weightSum", "Súčet hmotnosti"},
            {"script", "Scenár"},
            {"to table", "K stolu"},
            {"help", "Pomoc"},
            {"head", "Тajprv"},
            {"info", "Info"},
            {"addLabel", "Pridanie položky"},
            {"removeLabel", "Odstraňuje sa položka"},
            {"otherLabel", "Ostatné príkazy"},

            //add
            {"firstLabel","Zadajte podrobnosti osoby"},
            {"secondLabel","Povinné polia sú označené hviezdičkami"},
            {"thirdLabel","Ak ste sa rozhodli pridať"},
            {"fourthLabel","nové umiestnenie"},
            {"fifthLabel","Pridajte typ:"},
            {"name","Názov*:"},
            {"height","Výška:"},
            {"weight","Váha: "},
            {"passport","Cestovný pas*:"},
            {"hair","Vlasy*:"},
            {"x","Koord. X*:"},
            {"y","Koord. Y*:"},
            {"location","Poloha*:"},
            {"title","Názov:"},
            {"simple add","Jednoduché doplnenie"},
            {"add if max","Pridajte, ak je maximum"},
            {"add if min", "Pridajte, ak je minimum"},
            {"create", "Vytvor"},

            //count less than passport
            {"input passport","Zadajte číslo pasu."},
            {"rules of count","Počítajú sa všetky osoby s číslom pasu nižším ako je uvedené."},
            {"num of passport","Číslo pasu"},
            {"ready","Hotový"},

            //filter
            {"set up filter","Nainštalujte filter:"},
            {"less","Menej"},
            {"more","Viac"},
            {"argument","Argument"},

            //remove
            {"input id","Zadajte ID."},
            {"rules of remove","Osoba s daným ID bude odstránená."},
            {"rules of remove passport", "Všetci ľudia s daným číslom pasu budú odstránení."},

            //script
            {"script rules","Zadajte cestu k súboru skriptu."},
            {"execute","Vykonať"},

            //update
            {"update rules","Aktualizujte požadované polia."},
            {"fifth label update","ID (po zadaní stlačte kláves Enter):"},

    };

    protected Object[][] getContents() {
        return contents;
    }
}
