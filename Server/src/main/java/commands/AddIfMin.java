package commands;

import database.DataBaseManager;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;

public class AddIfMin extends Command {

    private final LinkedList<Person> people = collectionsKeeper.getPeople();

    public AddIfMin(CollectionsKeeper dc) {
        super(dc);
    }

    @Override
    public ServerResponse execute(DataBaseManager manager, List<String> args) {
        String result;
        if (people.size() == 0) {
            result = manager.addPersonToDB(person, "add", args);
            if (result.equals("success add")) {
                SimpleAdd.updatePeopleList(manager, people);
                return ServerResponse.builder().message("success add").command(getName()).build();
            } else return ServerResponse.builder().error(result).command(getName()).build();
        } else {
            if (person.toString().length() > people.getFirst().toString().length()) {
                return ServerResponse.builder().error("add error").command(getName()).build();
            } else {
                result = manager.addPersonToDB(person, "add", args);
                if (result.equals("success add")) {
                    SimpleAdd.updatePeopleList(manager, people);
                    return ServerResponse.builder().message("success add").command(getName()).build();
                } else return ServerResponse.builder().error(result).command(getName()).build();
            }
        }
    }

    @Override
    public String getName() {
        return "add_if_min";
    }

    @Override
    public String getDescription() {
        return "add_if_min : добавить новый элемент в коллекцию, если его длина в формате строки меньше наименьшей или совпадает с ней";
    }
}
