package commands;

import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class Head extends Command {

    private LinkedList<Person> people;

    public Head(CollectionsKeeper dc) {
        super(dc);
    }

    @Override
    public ServerResponse execute(List<String> userDataAndOtherArgs) {
        if (userDataAndOtherArgs.size() == LENGTH_WITH_ONLY_USER_DATA) {
            people = collectionsKeeper.getPeople();
            if (people.size() == 0)
                return ServerResponse.builder().message("head empty").command(getName()).build();
            else {
                return ServerResponse.builder().message(people.get(0).toString()).command(getName()).build();
            }
        } else {
            return ServerResponse.builder().error("error").command(getName()).build();
        }
    }

    @Override
    public String getName() {
        return "head";
    }

    public String getDescription(ResourceBundle bundle) {
        return bundle.getString("head description");
    }
}
