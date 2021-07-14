package commands;

import database.DataBaseManager;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;

public class Update extends Command {

    private final LinkedList<Person> people = collectionsKeeper.getPeople();

    public Update(CollectionsKeeper dc) {
        super(dc);
    }

    public ServerResponse execute(DataBaseManager manager, List<String> args) {
        String result = manager.addPersonToDB(person, "update", args);
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
    public String getDescription() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }
}
