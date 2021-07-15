package commands;

import database.DataBaseManager;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class Update extends Command {

    private final LinkedList<Person> people = collectionsKeeper.getPeople();

    public Update(CollectionsKeeper dc) {
        super(dc);
    }

    public ServerResponse execute(DataBaseManager manager, List<String> userDataAndOtherArgs) {
        String result = manager.addPersonToDB(toDeletePerson, "update", userDataAndOtherArgs);
        if (result.equals("success update")) {
            LinkedList<Person> newCollection = manager.loadCollectionFromDB().getPeople();
            people.clear();
            people.addAll(newCollection);
            return ServerResponse.builder().message(result).command("update").build();
        } else
            return ServerResponse.builder().error(result).command("update").build();
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription(ResourceBundle bundle) {
        return bundle.getString("update description");
    }
}
