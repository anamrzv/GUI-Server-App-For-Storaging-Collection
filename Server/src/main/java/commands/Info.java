package commands;

import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class Info extends Command {

    public Info(CollectionsKeeper dc) {
        super(dc);
    }

    @Override
    public ServerResponse execute(List<String> userDataAndOtherArgs) {
        if (userDataAndOtherArgs.size() == LENGTH_WITH_ONLY_USER_DATA) {
            LinkedList<Person> people = collectionsKeeper.getPeople();
            return ServerResponse.builder().message(Integer.toString(people.size())).command(getName()).build();
        } else {
            return ServerResponse.builder().error("error").command(getName()).build();
        }
    }

    @Override
    public String getName() {
        return "info";
    }

    public String getDescription(ResourceBundle bundle) {
        return bundle.getString("info description");
    }
}
