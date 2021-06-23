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

            //title for alerts
            {"new location","New location"},
            {"create new person","Adding of new person"},
            {"update person","Updating og the object"},
            {"signin","Registration"},

            //alerts add and update
            {"success add","Object was successfully added"},
            {"add error","Error occurred while adding object"},
            {"name error empty","This field can't be empty, please input data."},
            {"name error validate","Name can't include digits and special symbols. Check that they are not in the data and enter name one more time."},
            {"name error input","Invalid input format, enter the name again."},
            {"height error number","Height cannot be less than zero, please enter height again."},
            {"height error input","Wrong format for entering height, enter a number or do not enter anything."},
            {"weight error number","Weight cannot be less than zero, please enter weight again."},
            {"weight error input","Wrong format for entering weight, enter a number or do not enter anything."},
            {"passport error empty","Passport cannot be empty, please enter data."},
            {"passport error length","Passport data does not fit in length, enter the data again (10-27 digits)."},
            {"passport error validate","Passport data should only consist of numbers, enter the data again."},
            {"passport error input","Invalid input format, enter your passport data again."},
            {"hair error empty","Choose your hair color, this is a required field."},
            {"hair error input","Try entering your hair color again."},
            {"location error empty","All coordinates of the new location must be filled in, please enter numbers."},
            {"location error input","Invalid input format, enter the coordinates of the place again. Check that there are no letters in the entered string."},
            {"coordinates error empty","All person coordinates must be filled in, please enter numbers."},
            {"coordinates error input","Invalid input format, enter the coordinates of the person again. Check that there are no letters in the entered string."},
            {"creator error", "The object cannot be updated because you are not its creator."},

            //alerts login and sign in
            {"login error short","The entered login is too short"},
            {"password error short","The entered password is too short"},
            {"password error validate","Password contains invalid characters"},
            {"log in error","You entered an incorrect username and/or password. Repeat login"},
            {"success register","You have been registered."},
            {"register error","Such login already exists, impossible to register you"},

            //alerts script
            {"script error special file", "The special file cannot be passed as a script. Enter the command again with a new argument."},
            {"script error recursive 1", "A recursion to itself was found in the script, delete the line c execute_script"},
            {"script error recursive 2", "A recursion was found in one of the calls to the execute_script command in the file, the command was skipped. Delete the line c execute_script"},
            {"script error file", "File not found. Please make sure you entered the correct file path and enter the command again."},
            {"script error command", "An error occurred while reading the script command."},
            {"script error args", "The execute_script command must have one argument - the path to the file. Enter the path to only 1 file."},
            {"script success", "The script was successfully executed"},

            //alerts remove passport
            {"passport error arguments", "The command must have exactly one argument - passport ID. Enter the command again."},
            {"passport error minus", "ID cannot be negative. Please enter the command again"},
            {"passport remove success", "Objects with this PassportID have been successfully removed from the collection"},
            {"passport no id", "There are no items with this PassportID in the collection."},
            {"passport not remove from db", "Not all collection items were removed, as some could not be removed in the database."},
            {"passport remove error", "Items with this PassportID were not deleted, because there is no access to them and/or an error occurred while deleting from the database"},

            //alerts remove id
            {"id error arguments", "The command must have exactly one argument - the person ID. Enter the command again."},
            {"id error minus", "ID cannot be negative. Please enter the command again."},
            {"id error validate", "A string of numbers must be passed as an argument. Enter the command again."},
            {"id no id", "There is no item with this ID in the collection."},
            {"id remove success", "The object with the given ID has been successfully removed from the collection."},
            {"id creator error", "The item was not deleted because you are not its owner"},
            {"id db error", "The item has not been deleted because it cannot be deleted in the database."},

            //other
            {"passport count answer", "- the number of people with a lower passport value"},
            {"error", ""},
            {"weight answer", "- the sum of the values ​​of the weight field of all elements of the collection"},
            {"clear success", "Items you created were successfully removed from the collection."},
            {"clear error", "Items were not deleted because an error occurred while deleting from the database"},
            {"head empty", "The collection is empty, the first element cannot be displayed"},
            {"head answer", "First element of the default sorted (alphabetical) collection: \n"},
            {"info answer", "Item type: Person\nCollection type: LinkedList\nNumber of items:"},

            //help
            {"help answer","Commands available to you:\n" +
                    "Add: add a new item to the collection\n" +
                    "Add if less: add a new item to the collection if its id is less than the smallest id of this collection\n" +
                    "Script: read and execute the script from the specified file (replace file_name with the path to the file). The script contains commands in the same form in which they are entered interactively.\n" +
                    "Remove by passport: remove from the collection all items whose passportID field value is equivalent to the specified on\n" +
                    "Clear: clear the collection\n" +
                    "Remove: remove an item from the collection by its id\n" +
                    "Update: update the value of the collection item whose id is equal to the given one\n" +
                    "Add if greater: add a new item to the collection if its id is greater than the id of the largest item in this collection\n" +
                    "People with smaller passports: print the number of items whose passportID field value is less than the specified one\n" +
                    "First: display the first element of the collection\n" +
                    "Help: display help for available commands\n" +
                    "Sum of weight: print the sum of the values of the weight field for all elements of the collection\n" +
                    "Info: print information about the collection (type, date of initialization, number of elements, etc.) to the standard output stream"}

    };

    protected Object[][] getContents(){
        return contents;
    }

}
