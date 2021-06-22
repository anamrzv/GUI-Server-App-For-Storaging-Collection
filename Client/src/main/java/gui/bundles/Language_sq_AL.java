package gui.bundles;

import java.util.ListResourceBundle;

public class Language_sq_AL extends ListResourceBundle {
    private static final Object[][] contents = {
            {"authorisation", "Autorizimi"},
            {"login", "Hyrja"},
            {"password", "Fjalekalimi"},
            {"log in", "Per te hyre brenda"},
            {"sign in", "Regjistrohem"},

            //sign in.fxml
            {"registration", "Regjistrohuni"},
            {"rules", "Perdorni letra, numra, pika, nenvizo latinisht"},
            {"loginRules", "Hyrja* (nga 4 karaktere)"},
            {"passwordRules", "Fjalekalimi (nga 3 karaktere)"},
            {"to authorisation", "Per autorizim"},

            //table
            {"reset", "Rivendos"},
            {"filter", "Filtri"},
            {"to commands list", "Ne listen e komandave"},
            {"user", "Perdorues: "},
            {"map", "Harta"},

            //commands buttons
            {"add", "Shtoni në"},
            {"update", "Rifresko"},
            {"removeID", "Fshij"},
            {"removePass", "Fshi me pasaportë"},
            {"clear", "Qartë"},
            {"countPass", "Numëroni më pak pasaportë"},
            {"weightSum", "Shuma e peshës"},
            {"script", "Skenari"},
            {"to table", "Në tryezë"},
            {"help", "Ndihmoni"},
            {"head", "Së pari"},
            {"info", "Info"},
            {"addLabel", "Shtimi i një artikulli"},
            {"removeLabel", "Heqja e një sendi"},
            {"otherLabel", "Komandat e tjera"},

            //add
            {"firstLabel", "Vendosni detajet e personit"},
            {"secondLabel", "Fushat e kërkuara janë shënuar me yje"},
            {"thirdLabel", "Nëse keni zgjedhur të shtoni"},
            {"fourthLabel", "vendndodhja e re"},
            {"fifthLabel", "Shto llojin:"},
            {"name", "Emrin*:"},
            {"height", "Lartësia:"},
            {"weight", "Pesha: "},
            {"passport", "Pasaporta*:"},
            {"hair", "Flokët*:"},
            {"x", "Koord. X*:"},
            {"y", "Koord. Y*:"},
            {"location", "Vendndodhja*:"},
            {"title", "Emrin"},
            {"simple add", "Shtesë e thjeshtë"},
            {"add if max", "Shtoni nëse është maksimumi"},
            {"add if min", "Shtoni nëse është minimumi"},
            {"create", "Krijoni një"},

            //count less than passport
            {"input passport", "Futni pasaportën tuaj."},
            {"rules of count", "Do të numërohen të gjithë njerëzit me një numër pasaporte më pak se ai i specifikuar."},
            {"num of passport", "ID e pasaportës"},
            {"ready", "Bërë"},

            //filter
            {"set up filter", "Instaloni filtrin:"},
            {"less", "Më pak"},
            {"more", "Më shumë"},
            {"argument", "Argument"},

            //remove
            {"input id", "Hyj ID."},
            {"rules of remove", "Personi me ID të dhënë do të fshihet."},
            {"rules of remove passport", "Të gjithë njerëzit me numrin e dhënë të pasaportës do të fshihen."},

            //script
            {"script rules","Futni shtegun për te skedari i skriptit."},
            {"execute","Ekzekutuar"},

            //update
            {"update rules","Përditësoni fushat që ju nevojiten."},
            {"fifth label update","ID (pasi të futni, shtypni Enter):"},

    };

    protected Object[][] getContents() {
        return contents;
    }

}
