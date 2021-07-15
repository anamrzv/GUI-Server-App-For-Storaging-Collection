package commands;

import database.DataBaseManager;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class Clear extends Command {

    public Clear(CollectionsKeeper dc) {
        super(dc);
    }

    @Override
    public ServerResponse execute(DataBaseManager manager, List<String> userDataAndOtherArgs) {
        LinkedList<Person> people = collectionsKeeper.getPeople();
        if (userDataAndOtherArgs.size() == LENGTH_WITH_ONLY_USER_DATA) {
            List<Person> toBeDeletedPeople = manager.deleteByLoginFromDB(userDataAndOtherArgs);
            if (toBeDeletedPeople != null) {
                people.removeAll(toBeDeletedPeople);
                return ServerResponse.builder().message("clear success").command(getName()).build();
            } else return ServerResponse.builder().error("clear error").build();
        } else {
            return ServerResponse.builder().error("error").build();
        }
    }

    @Override
    public String getName() {
        return "clear";
    }

    public String getDescription(ResourceBundle bundle) {
        return bundle.getString("clear description");
    }
}
