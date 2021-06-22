package gui.bundles;

import java.util.ListResourceBundle;

public class Language_en_CA extends ListResourceBundle {
    private static final Object[][] contents = {
            {"authorisation", "Authorisation"},
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
            {"map", "Map"},

            //commands buttons
            {"add", "Add"},
            {"update", "Update"},
            {"removeID", "Remove"},
            {"removePass", "Remove by Passport"},
            {"clear", "Clear"},
            {"countPass", "Count less then passport"},
            {"weightSum", "Sum of weight"},
            {"script", "Execute script"},
            {"to table", "To table"},
            {"help", "Help"},
            {"head", "Head"},
            {"info", "Info"},
            {"addLabel", "Add new element"},
            {"removeLabel", "Remove element"},
            {"otherLabel", "Other commands"},

            //add
            {"firstLabel","Input data about person"},
            {"secondLabel","Required fields are marked with an asterisk"},
            {"thirdLabel","If you have chosen to add"},
            {"fourthLabel","a new location:"},
            {"fifthLabel","Kind of adding: "},
            {"name","Name*:"},
            {"height","Height:"},
            {"weight","Weight:"},
            {"passport","Passport*:"},
            {"hair","Hair*:"},
            {"x","Coord. X*:"},
            {"y","Coord. Y*:"},
            {"location","Location*:"},
            {"title","Name:"},
            {"simple add","Simple adding"},
            {"add if max","Add if it is maximum"},
            {"add if min", "Add if it is minimum"},
            {"create","Create"},

            //count less than passport
            {"input passport","Input the passport ID."},
            {"rules of count","All people with less than that passport ID will be counted."},
            {"num of passport","Passport ID"},
            {"ready","Ready"},

            //filter
            {"set up filter","Set up filter:"},
            {"less","Less"},
            {"more","More or equal"},
            {"argument","Argument"},

            //remove
            {"input id","Input ID."},
            {"rules of remove","The person with this ID will be removed."},
            {"rules of remove passport", "People with that passport ID will be removed."},

            //script
            {"script rules","Input path to file with script."},
            {"execute","Execute"},

            //update
            {"update rules","Update the fields that you need."},
            {"fifth label update","ID (press Enter after input): "},

    };

    protected Object[][] getContents(){
        return contents;
    }

}
