package commands;

import database.DataBaseManager;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class SimpleAdd extends Command {
    private final LinkedList<Person> people = collectionsKeeper.getPeople();

    public SimpleAdd(CollectionsKeeper dc) {
        super(dc);
    }

    public ServerResponse execute(DataBaseManager manager, List<String> userDataAndOtherArgs) {
        String result = manager.addPersonToDB(person, "add", userDataAndOtherArgs);
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

    protected ServerResponse addPersonToDBAndGetServerResponse(DataBaseManager manager, List<String> args){
        String result = manager.addPersonToDB(person, "add", args);
        if (result.equals("success add")) {
            updatePeopleList(manager, people);
            return ServerResponse.builder().message(result).command(getName()).build();
        } else return ServerResponse.builder().error(result).command(getName()).build();
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription(ResourceBundle bundle) {
        return bundle.getString("add description");
    }
}
