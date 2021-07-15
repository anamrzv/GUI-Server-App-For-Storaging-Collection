package bundles;

import java.util.ListResourceBundle;

public class Language_en_CA extends ListResourceBundle {
    private static final Object[][] contents = {
            //help
            {"add description", "Add: add a new item to the collection"},
            {"add_if_min description", "Add if less: add a new item to the collection if its id is less than the smallest id of this collection"},
            {"add_if_max description", "Add if greater: add a new item to the collection if its id is greater than the id of the largest item in this collection"},
            {"execute_script description", "Script: read and execute the script from the specified file (replace file_name with the path to the file). The script contains commands in the same form in which they are entered interactively"},
            {"update description", "Update: update the value of the collection item whose id is equal to the given one"},
            {"remove_by_id description", "Remove: remove an item from the collection by its id"},
            {"remove_all_by_passport_id description", "Remove by passport: remove from the collection all items whose passportID field value is equivalent to the specified on"},
            {"clear description", "Clear: clear the collection"},
            {"count_less_than_passport_id description", "People with smaller passports: print the number of items whose passportID field value is less than the specified one"},
            {"sum_of_weight description", "Sum of weight: print the sum of the values of the weight field for all elements of the collection"},
            {"help description", "Help: display help for available commands"},
            {"head description", "First: display the first element of the collection"},
            {"info description", "Info: print information about the collection (type, date of initialization, number of elements, etc.) to the standard output stream"}
    };

    protected Object[][] getContents() {
        return contents;
    }

}
