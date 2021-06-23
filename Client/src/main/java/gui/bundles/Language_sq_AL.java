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

            //title for alerts
            {"new location","Vendndodhje e re"},
            {"create new person","Shtimi i një personi të ri"},
            {"update person","Azhurnimi i objektit"},
            {"signin","Kontrolloni"},

            //alerts add and update
            {"success add","Objekti u shtua me sukses"},
            {"add error","Gabim në shtimin e objektit në bazën e të dhënave"},
            {"name error empty","Ju nuk mund të vendosni një vijë të zbrazët në këtë fushë, ju lutemi shkruani detajet."},
            {"name error validate","Emri nuk mund të përmbajë numra ose karaktere speciale. Kontrolloni që ato mungojnë në rreshtin e futur dhe futni përsëri emrin."},
            {"name error input","Formati i hyrjes është i pavlefshëm, futni emrin përsëri."},
            {"height error number","Lartësia nuk mund të jetë më pak se zero, ju lutemi shkruani lartësinë përsëri."},
            {"height error input","Format i gabuar për futjen e lartësisë, futni një numër ose mos futni asgjë."},
            {"weight error number","Pesha nuk mund të jetë më pak se zero, ju lutemi futni përsëri peshën."},
            {"weight error input","Format i pavlefshëm i hyrjes në peshë, futni një numër ose mos futni asgjë."},
            {"passport error empty","Pasaporta nuk mund të jetë bosh, ju lutemi shkruani të dhëna."},
            {"passport error length","Të dhënat e pasaportës nuk përshtaten në gjatësi, futni përsëri të dhënat (10-27 shifra)."},
            {"passport error validate","Të dhënat e pasaportës duhet të përbëhen vetëm nga numra, futni përsëri të dhënat."},
            {"passport error input","Format i pavlefshëm i hyrjes, futni përsëri të dhënat e pasaportës tuaj."},
            {"hair error empty","Zgjidhni ngjyrën e flokëve, kjo është një fushë e kërkuar."},
            {"hair error input","Provoni të futni përsëri ngjyrën e flokëve tuaj."},
            {"location error empty","Të gjitha koordinatat e vendndodhjes së re duhet të plotësohen, ju lutemi shkruani numrat."},
            {"location error input","Format i pavlefshëm i hyrjes, futni përsëri koordinatat e vendit. Kontrolloni që të mos ketë shkronja në vargun e futur."},
            {"coordinates error empty","Të gjitha koordinatat e personave duhet të plotësohen, ju lutemi shkruani numrat."},
            {"coordinates error input","Format i pavlefshëm i hyrjes, futni përsëri koordinatat e personit. Kontrolloni që të mos ketë shkronja në vargun e futur."},
            {"creator error", "Objekti nuk mund të azhurnohet sepse ju nuk jeni krijuesi i saj."},

            //alerts login and sign in
            {"login error short","Identifikimi është shumë i shkurtër"},
            {"password error short","Keni futur një fjalëkalim shumë të shkurtër"},
            {"password error validate","Fjalëkalimi përmban karaktere të pavlefshëm"},
            {"log in error","Keni futur një emër përdoruesi dhe / ose fjalëkalim të pasaktë. Përsëritni hyrjen"},
            {"success register","Ju jeni regjistruar me sukses."},
            {"register error","Impossibleshtë e pamundur të regjistroheni, një hyrje e tillë tashmë ekziston."},

            //alerts script
            {"script error special file", "Skedari special nuk mund të kalohet si skenar. Fut përsëri komandën me një argument të ri."},
            {"error error recursive 1", "Një skedar i vetvetes u gjet në skenar, fshi rreshtin c ekzekutoj_script"},
            {"error error recursive 2", "Një rikurs u gjet në një nga thirrjet për komandën ekzekutoj_shkrim në skedar, komanda u anashkalua. Fshi rreshtin c ekzekutoj_script"},
            {"script error file", "Skedari nuk u gjet. Ju lutemi sigurohuni që keni futur rrugën e saktë të skedarit dhe futni përsëri komandën."},
            {"script error command", "Një gabim ndodhi gjatë leximit të komandës së skriptit."},
            {"script error args", "Komanda ekzekutoj_script duhet të ketë një argument - shtegun drejt skedarit. Vendosni shtegun për vetëm 1 skedar."},
            {"script success", "Skenari u ekzekutua me sukses"},

            //alerts remove passport
            {"passport error arguments", "Komanda duhet të ketë saktësisht një argument - ID të pasaportës. Vendosni përsëri komandën."},
            {"passport error minus", "ID nuk mund të jetë negativ. Ju lutemi futni përsëri komandën"},
            {"passport remove success", "Objektet me këtë Pasaportë janë hequr me sukses nga koleksioni"},
            {"passport no id", "Nuk ka artikuj me këtë PassportID në koleksion."},
            {"passport not remove from db", "Jo të gjithë artikujt e koleksionit u hoqën, pasi disa nuk mund të hiqeshin në bazën e të dhënave."},
            {"passport remove error", "Artikujt me këtë Pasaportë nuk u fshinë, sepse nuk ka qasje në to dhe/ose ndodhi një gabim gjatë fshirjes nga baza e të dhënave"},

            //alerts remove id
            {"id error arguments", "Komanda duhet të ketë saktësisht një argument - ID-në e personit. Fut përsëri komandën."},
            {"id error minus", "ID nuk mund të jetë negativ. Ju lutemi futni përsëri komandën."},
            {"id error validate", "Një varg numrash duhet të kalojë si argument. Fut përsëri komandën."},
            {"id no id", "Nuk ka asnjë artikull me këtë ID në koleksion."},
            {"id remove success", "Objekti me ID-në e dhënë është hequr me sukses nga koleksioni."},
            {"id creator error", "Artikulli nuk u fshi sepse nuk je pronari i tij"},
            {"id db error", "Artikulli nuk është fshirë sepse nuk mund të fshihet në bazën e të dhënave."},

            //other
            {"passport count answer", "- numri i njerëzve me një vlerë më të ulët të pasaportës"},
            {"error", ""},
            {"weight answer", "- shuma e vlerave të fushës së peshës të të gjithë elementëve të koleksionit"},
            {"clear success", "Artikujt që krijuat u hoqën me sukses nga koleksioni."},
            {"clear error", "Artikujt nuk u fshinë sepse ndodhi një gabim gjatë fshirjes nga baza e të dhënave"},
            {"head empty", "Koleksioni është bosh, elementi i parë nuk mund të shfaqet"},
            {"head answer", "Elementi i parë i koleksionit të renditur të renditur (alfabetik): \n"},
            {"info answer", "Lloji i artikullit: Person\nLloji i koleksionit: Lista e Linked\nNumri i artikujve:"},

            //help
            {"help answer", "Komandat e disponueshme për ju:\n" +
                    "Shto: shto një artikull të ri në koleksion\n"  +
                    "Shto nëse më pak: shto një artikull të ri në koleksion nëse ID-ja e tij është më pak se ID-ja më e vogël e këtij koleksioni\n" +
                    "Skript: lexoni dhe ekzekutoni skriptin nga skedari i specifikuar (zëvendësoni emrin e skedarit me rrugën drejt skedarit). Skripti përmban komanda në të njëjtën formë në të cilën ato futen në modalitetin ndërveprues.\n" +
                    "Hiq me pasaportë: hiq nga koleksioni të gjithë artikujt vlera e fushës së pasaportës së të cilave është ekuivalente me atë të specifikuar\n" +
                    "Pastro: pastro koleksionin\n" +
                    "Hiq: hiq një artikull nga koleksioni me id e tij\n" +
                    "Azhurnim: azhurnoni vlerën e artikullit të koleksionit ID-ja e të cilit është e barabartë me atë të dhënë\n" +
                    "Shto nëse është më i madh: shto një artikull të ri në koleksion nëse ID i tij është më i madh se ID i artikullit më të madh në këtë koleksion\n" +
                    "Njerëzit me pasaporta më të vogla: shtypni numrin e artikujve vlera e fushës së pasaportës së të cilëve është më e vogël se ajo e specifikuar\n" +
                    "E para: shfaq elementin e parë të koleksionit\n" +
                    "Ndihmë: shfaq ndihmën për komandat e disponueshme\n" +
                    "Shuma e peshës: shtyp shumën e vlerave të fushës së peshës për të gjithë elementët e koleksionit\n" +
                    "Informacion: shtyp informacion në lidhje me mbledhjen (lloji, data e fillimit, numri i elementeve, etj.) Në rrjedhën standarde të daljes"}
    };

    protected Object[][] getContents() {
        return contents;
    }

}
