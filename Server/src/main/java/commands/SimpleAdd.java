package commands;

import database.DataBaseManager;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;

public class SimpleAdd extends Command {

    private final LinkedList<Person> people = collectionsKeeper.getPeople();

    public SimpleAdd(CollectionsKeeper dc) {
        super(dc);
    }

    public ServerResponse execute(DataBaseManager manager, List<String> args) {
        String result = manager.addPersonToDB(person, "add", args);
        if (result.equals("success add")) {
            updatePeopleList(manager, people);
            return ServerResponse.builder().message("success add").command("add").build();
        } else
            return ServerResponse.builder().error(result).command("add").build();
    }

    public static void updatePeopleList(DataBaseManager manager, LinkedList<Person> people){
        LinkedList<Person> newCollection = manager.loadCollectionFromDB().getPeople();
        people.clear();
        people.addAll(newCollection);
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "add person: добавить новый элемент в коллекцию, ввод вручную\nadd json_element : добавить новый элемент в коллекцию, автоматическая обработка строки json";
    }
}
