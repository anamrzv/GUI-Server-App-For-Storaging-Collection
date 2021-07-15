package bundles;

import java.util.ListResourceBundle;

public class Language_sq_AL extends ListResourceBundle {
    private static final Object[][] contents = {
            //help
            {"add description", "Shto: shtoni një artikull të ri në koleksion"},
            {"add_if_min description", "Shto nëse është më pak: shto një artikull të ri në koleksion nëse gjatësia e tij në formatin e vargut është më pak se ose e njëjtë me minimumin"},
            {"add_if_max description", "Shto nëse është më i madh: shto një artikull të ri në koleksion nëse gjatësia e tij në formatin e vargut është më e madhe ose e barabartë me maksimumin"},
            {"execute_script description", "Skriptin: lexo dhe ekzekuto një skenar nga skedari i specifikuar (zëvendëso emrin e skedarit me rrugën drejt skedarit). Skripti përmban komanda në të njëjtën formë në të cilën ato futen në modalitetin ndërveprues"},
            {"update description", "Azhurnimi: azhurnoni vlerën e artikullit të koleksionit ID-ja e të cilit është e barabartë me atë të dhënë"},
            {"remove_by_id description", "Hiq: hiq një artikull nga koleksioni me id e tij"},
            {"remove_all_by_passport_id description", "Hiq me pasaportë: hiq nga koleksioni të gjithë artikujt vlera e fushës pasaportë e të cilave është ekuivalente me atë të specifikuar"},
            {"clear description", "Pastro: pastro koleksionin"},
            {"count_less_than_passport_id description", "Personat me pasaporta më të vogla: shfaqin numrin e artikujve vlera e fushës së pasaportës së të cilëve është më e vogël se ajo e specifikuar"},
            {"sum_of_weight description", "Shuma e peshës: shfaq shumën e vlerave të fushës së peshës për të gjithë elementët e koleksionit"},
            {"help description", "Ndihma: shfaq ndihmën në komandat e disponueshme"},
            {"head description", "Së pari: shfaq elementin e parë të koleksionit"},
            {"info description", "Informacioni: shtyp informacion në lidhje me mbledhjen (lloji, data e fillimit, numri i elementeve, etj.) në rrjedhën standarde të daljes"}
    };

    protected Object[][] getContents() {
        return contents;
    }

}
