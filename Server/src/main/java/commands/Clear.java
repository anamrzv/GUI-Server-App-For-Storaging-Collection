package commands;

import database.DataBaseManager;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;

public class Clear extends Command {

    public Clear(CollectionsKeeper dc) {
        super(dc);
    }

    @Override
    public ServerResponse execute(DataBaseManager manager, List<String> args) {
        LinkedList<Person> people = collectionsKeeper.getPeople();
        if (args.size() == 2) {
            List<Person> toBeDeletedPeople = manager.deleteByLoginFromDB(args);
            if (toBeDeletedPeople != null) {
                people.removeAll(toBeDeletedPeople);
                return ServerResponse.builder().message("clear success").command("clear").build();
            } else return ServerResponse.builder().error("clear error").build();
        } else {
            return ServerResponse.builder().error("error").build();
        }
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }
}
