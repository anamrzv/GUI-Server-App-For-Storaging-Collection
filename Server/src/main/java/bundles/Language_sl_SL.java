package bundles;

import java.util.ListResourceBundle;

public class Language_sl_SL extends ListResourceBundle {
    private static final Object[][] contents = {
            //help
            {"add description", "Pridať: pridať novú položku do zbierky"},
            {"add_if_min description", "Pridať, ak menej: pridať novú položku do kolekcie, ak je jej dĺžka vo formáte reťazca menšia alebo rovnaká ako minimálna"},
            {"add_if_max description", "Pridať, ak je väčšie: pridať novú položku do kolekcie, ak je jej dĺžka vo formáte reťazca väčšia alebo rovná maximu"},
            {"execute_script description", "Skript: čítanie a vykonávanie skriptov zo zadaného súboru (nahraďte súbor_name cestou k súboru). Skript obsahuje príkazy v rovnakom tvare, v akom sú zadávané v interaktívnom režime"},
            {"description description", "Update: aktualizuje hodnotu položky kolekcie, ktorej ID sa rovná danému"},
            {"remove_by_id description", "Odstrániť: odstrániť položku zo zbierky podľa jej ID"},
            {"remove_all_by_passport_id description", "Odstrániť pomocou pasu: odstrániť z kolekcie všetky položky, ktorých hodnota poľa passportID je ekvivalentná zadanej hodnote"},
            {"clear description", "Vymazať: vyčistiť zbierku"},
            {"count_less_than_passport_id description", "Ľudia s menšími pasmi: zobrazujú počet položiek, ktorých hodnota poľa passportID je menšia ako zadaná"},
            {"sum_of_weight description", "Váhový súčet: zobrazí súčet hodnôt poľa hmotnosti pre všetky prvky kolekcie"},
            {"description help", "Help: zobraziť pomocníka s dostupnými príkazmi"},
            {"head description", "Prvý: zobrazí prvý prvok zbierky"},
            {"info description", "Info: tlač informácií o zbierke (typ, dátum inicializácie, počet prvkov atď.) do štandardného výstupného toku"}
    };

    protected Object[][] getContents() {
        return contents;
    }
}
