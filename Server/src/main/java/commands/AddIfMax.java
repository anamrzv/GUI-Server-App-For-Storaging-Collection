package commands;

import database.DataBaseManager;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class AddIfMax extends SimpleAdd {

    private final LinkedList<Person> people = collectionsKeeper.getPeople();

    public AddIfMax(CollectionsKeeper collectionsKeeper) {
        super(collectionsKeeper);
    }

    @Override
    public ServerResponse execute(DataBaseManager manager, List<String> userDataAndOtherArgs) {
        if (people.size() == 0) {
            return addPersonToDBAndGetServerResponse(manager, userDataAndOtherArgs);
        } else {
            if (toDeletePerson.toString().length() < people.getLast().toString().length()) {
                return ServerResponse.builder().error("add error").command(getName()).build();
            } else {
                return addPersonToDBAndGetServerResponse(manager, userDataAndOtherArgs);
            }
        }
    }

    @Override
    public String getName() {
        return "add_if_max";
    }

    public String getDescription(ResourceBundle bundle) {
        return bundle.getString("add_if_max description");
    }
}
