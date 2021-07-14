package commands;

import database.DataBaseManager;
import lombok.Setter;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.List;

@Setter
public abstract class Command {

    public CollectionsKeeper collectionsKeeper;
    protected Person person;

    Command(CollectionsKeeper collectionsKeeper) {
        this.collectionsKeeper = collectionsKeeper;
    }

    public ServerResponse execute(List<String> args) {
        return null;
    }

    public ServerResponse execute(DataBaseManager manager, List<String> args) {
        return null;
    }

    public abstract String getName();

    public abstract String getDescription();
}
