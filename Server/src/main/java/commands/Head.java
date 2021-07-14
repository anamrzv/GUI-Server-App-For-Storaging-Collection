package commands;

import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;

public class Head extends Command {

    LinkedList<Person> people;

    public Head(CollectionsKeeper dc) {
        super(dc);
    }

    @Override
    public ServerResponse execute(List<String> args) {
        if (args.size() == 2) {
            people = collectionsKeeper.getPeople();
            if (people.size() == 0)
                return ServerResponse.builder().message("head empty").command("head").build();
            else {
                return ServerResponse.builder().message(people.get(0).toString()).command("head").build();
            }
        } else {
            return ServerResponse.builder().error("error").command("head").build();
        }
    }

    @Override
    public String getName() {
        return "head";
    }

    @Override
    public String getDescription() {
        return "head : вывести первый элемент коллекции";
    }
}
