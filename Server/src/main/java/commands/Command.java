package commands;

import database.DataBaseManager;
import lombok.Setter;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.List;
import java.util.ResourceBundle;

@Setter
public abstract class Command {

    public CollectionsKeeper collectionsKeeper;
    protected Person person;

    protected final int LENGTH_WITH_ONLY_USER_DATA = 2;

    Command(CollectionsKeeper collectionsKeeper) {
        this.collectionsKeeper = collectionsKeeper;
    }

    public ServerResponse execute(List<String> userDataAndOtherArgs) {
        return null;
    }

    public ServerResponse execute(DataBaseManager manager, List<String> userDataAndOtherArgs) {
        return null;
    }

    public abstract String getName();

    public abstract String getDescription(ResourceBundle bundle);
}
