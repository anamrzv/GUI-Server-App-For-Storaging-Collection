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
            {"exit", "Edek"},

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
            {"firstLabel", "Zadajte podrobnosti osoby"},
            {"secondLabel", "Povinné polia sú označené hviezdičkami"},
            {"thirdLabel", "Ak ste sa rozhodli pridať"},
            {"fourthLabel", "nové umiestnenie"},
            {"fifthLabel", "Pridajte typ:"},
            {"name", "Názov*:"},
            {"height", "Výška:"},
            {"weight", "Váha: "},
            {"passport", "Cestovný pas*:"},
            {"hair", "Vlasy*:"},
            {"x", "Koord. X*:"},
            {"y", "Koord. Y*:"},
            {"location", "Poloha*:"},
            {"title", "Názov:"},
            {"simple add", "Jednoduché doplnenie"},
            {"add if max", "Pridajte, ak je maximum"},
            {"add if min", "Pridajte, ak je minimum"},
            {"create", "Vytvor"},

            //count less than passport
            {"input passport", "Zadajte číslo pasu."},
            {"rules of count", "Počítajú sa všetky osoby s číslom pasu nižším ako je uvedené."},
            {"num of passport", "Číslo pasu"},
            {"ready", "Hotový"},

            //filter
            {"set up filter", "Nainštalujte filter:"},
            {"less", "Menej"},
            {"more", "Viac"},
            {"argument", "Argument"},

            //remove
            {"input id", "Zadajte ID."},
            {"rules of remove", "Osoba s daným ID bude odstránená."},
            {"rules of remove passport", "Všetci ľudia s daným číslom pasu budú odstránení."},

            //script
            {"script rules", "Zadajte cestu k súboru skriptu."},
            {"execute", "Vykonať"},

            //update
            {"update rules", "Aktualizujte požadované polia."},
            {"fifth label update", "ID (po zadaní stlačte kláves Enter):"},

            //title for alerts
            {"new location", "Nové umiestnenie"},
            {"create new person", "Pridáva sa nová osoba"},
            {"update person", "Aktualizácia objektu"},
            {"signin", "Prihlásiť sa"},

            //alerts add and update
            {"success add", "Objekt bol úspešne pridaný"},
            {"add error", "Chyba pri pridávaní objektu do databázy"},
            {"name error empty", "Do tohto poľa nemôžete zadať prázdny riadok, zadajte podrobnosti."},
            {"name error validate", "Meno nesmie obsahovať čísla a špeciálne znaky. Skontrolujte, či v zadanom riadku chýbajú, a zadajte meno znova."},
            {"name error input", "Neplatný vstupný formát, znova zadajte názov."},
            {"height error number", "Výška nemôže byť menšia ako nula, zadajte výšku znova."},
            {"height error input", "Nesprávny formát na zadanie výšky, zadanie čísla alebo nič."},
            {"weight error number", "Hmotnosť nemôže byť menšia ako nula, zadajte hmotnosť znova."},
            {"weight error input", "Neplatný formát zadávania hmotnosti, zadajte číslo alebo nič nezadávajte."},
            {"passport error empty", "Pas nemôže byť prázdny, zadajte údaje."},
            {"passport error length", "Údaje z pasu sa nezmestia do dĺžky, zadajte údaje znova (10 - 27 číslic)."},
            {"passport error validate", "Údaje z pasu by sa mali skladať iba z čísel, údaje zadajte znova."},
            {"passport error input", "Neplatný vstupný formát, znova zadajte údaje z pasu."},
            {"hair error empty", "Vyberte si farbu vlasov, toto je povinné pole."},
            {"hair error input", "Skúste znova zadať farbu vlasov."},
            {"location error empty", "Musia byť vyplnené všetky súradnice nového miesta, zadajte čísla."},
            {"location error input", "Neplatný vstupný formát. Znova zadajte súradnice miesta. Skontrolujte, či v zadanom reťazci nie sú žiadne písmená."},
            {"coordinates error empty", "Musia byť vyplnené všetky súradnice osôb, zadajte čísla."},
            {"coordinates error input", "Neplatný vstupný formát. Znova zadajte súradnice osoby. Skontrolujte, či v zadanom reťazci nie sú žiadne písmená."},
            {"creator error", "Objekt nie je možné aktualizovať, pretože nie ste jeho tvorcom."},
            {"success update", "Objekt bol úspešne obnoviť"},

            //alerts login and sign in
            {"login error short", "Prihlásenie je príliš krátke"},
            {"password error short", "Zadali ste príliš krátke heslo"},
            {"password error validate", "Heslo obsahuje neplatné znaky"},
            {"log in error", "Zadali ste nesprávne používateľské meno alebo heslo. Opakujte prihlásenie"},
            {"success register", "Ste úspešne zaregistrovaní."},
            {"register error", "Nie je možné vás zaregistrovať, takéto prihlásenie už existuje."},

            //alerts script
            {"script error special file", "Špeciálny súbor nie je možné odovzdať ako skript. Zadajte príkaz znova s novým argumentom."},
            {"script error recursive 1", "V skripte sa našla rekurzia, odstráňte riadok c execute_script"},
            {"script error recursive 2", "V jednom z volaní príkazu execute_script v súbore bola nájdená rekurzia, príkaz bol preskočený. Odstráňte riadok c execute_script"},
            {"script error file", "Súbor sa nenašiel. Uistite sa, že ste zadali správnu cestu k súboru a zadajte príkaz znova."},
            {"script error command", "Pri čítaní príkazu script sa vyskytla chyba."},
            {"script error args", "Príkaz execute_script musí mať jeden argument - cestu k súboru. Zadajte cestu iba k 1 súboru."},
            {"script success", "Skript bol úspešne vykonaný"},

            //alerts remove passport
            {"passport error arguments", "Príkaz musí mať presne jeden argument - ID pasu. Zadajte príkaz znova."},
            {"passport error minus", "ID nemôže byť záporné. Zadajte príkaz znova"},
            {"passport remove success", "Objekty s týmto PassportID boli úspešne odstránené zo zbierky"},
            {"passport no id", "V zbierke nie sú žiadne položky s týmto PassportID."},
            {"passport not remove from db", "Nie všetky položky zbierky boli odstránené, pretože niektoré nebolo možné odstrániť z databázy."},
            {"passport remove error", "Položky s týmto PassportID neboli odstránené, pretože k nim nie je prístup a/alebo sa vyskytla chyba pri mazaní z databázy"},

            //alerts remove id
            {"id error arguments", "Príkaz musí mať presne jeden argument - ID osoby. Zadajte príkaz znova."},
            {"id error minus", "ID nemôže byť záporné. Zadajte príkaz znova."},
            {"id error validate", "Reťazec čísel musí byť zadaný ako argument. Znova zadajte príkaz."},
            {"id no id", "V zbierke nie je žiadna položka s týmto ID."},
            {"id remove success", "Objekt s daným ID bol úspešne odstránený zo zbierky."},
            {"id creator error", "Položka nebola odstránená, pretože nie ste jej vlastníkom"},
            {"id db error", "Položka nebola odstránená, pretože ju nemožno vymazať z databázy."},

            //other
            {"passport count answer", "- počet ľudí s nižšou hodnotou pasu"},
            {"error", "Chyba"},
            {"weight answer", "- súčet hodnôt váhového poľa všetkých prvkov kolekcie"},
            {"clear success", "Položky, ktoré ste vytvorili, boli úspešne odstránené zo zbierky."},
            {"clear error", "Položky neboli odstránené, pretože sa vyskytla chyba pri mazaní z databázy"},
            {"head empty", "Zbierka je prázdna, prvý prvok nie je možné zobraziť"},
            {"head answer", "Prvý prvok predvolenej triedenej (abecedne) zbierky: \n"},
            {"info answer", "Typ položky: Osoba\nTyp zbierky: LinkedList\nPočet položiek: "},

            //help
            {"help answer", "Príkazy, ktoré máte k dispozícii:\n" +
                    "Pridať: pridať novú položku do zbierky\n" +
                    "Pridať, ak menej: pridať novú položku do zbierky, ak je jej ID menšie ako najmenšie ID tejto zbierky\n" +
                    "Skript: načíta a vykoná skript zo zadaného súboru (nahraďte názov_súboru cestou k súboru). Skript obsahuje príkazy v rovnakom tvare, v akom sú zadávané v interaktívnom režime.\n" +
                    "Odstrániť pomocou pasu: odstráni z kolekcie všetky položky, ktorých hodnota poľa passportID je ekvivalentná zadanej hodnote\n" +
                    "Vymazať: vymazať zbierku\n" +
                    "Odstrániť: odstrániť položku zo zbierky podľa jej\n" +
                    "Aktualizácia: aktualizuje hodnotu položky zbierky, ktorej id sa rovná dane\n" +
                    "Pridať, ak je väčšie: pridať novú položku do kolekcie, ak je jej ID väčšie ako ID najväčšej položky v tejto zbierke\n" +
                    "Ľudia s menším pasom: vytlačiť počet položiek, ktorých hodnota poľa passportID je menšia ako zadan\n" +
                    "Prvý: zobrazí prvý prvok kolekcie\n" +
                    "Pomoc: zobrazí pomoc pre dostupné príka\n" +
                    "Súčet hmotnosti: vypíše súčet hodnôt poľa hmotnosti pre všetky prvky kolekcie\n" +
                    "Info: tlač informácií o kolekcii (typ, dátum inicializácie, počet prvkov atď.) Do štandardného výstupného toku“"}
    };

    protected Object[][] getContents() {
        return contents;
    }
}
