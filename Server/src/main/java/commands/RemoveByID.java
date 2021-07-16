package commands;

import database.DataBaseManager;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class RemoveByID extends Command {

    private long id;
    private final int ONE_DELETED_PERSON = 1;
    private final int NOBODY_DELETED = 0;

    private DataBaseManager manager;
    private List<String> userDataAndOtherArgs;

    public RemoveByID(CollectionsKeeper dc) {
        super(dc);
    }

    @Override
    public ServerResponse execute(DataBaseManager manager, List<String> userDataAndOtherArgs) {
        this.manager = manager;
        this.userDataAndOtherArgs = userDataAndOtherArgs;

        if (userDataAndOtherArgs.size() != LENGTH_WITH_ONLY_USER_DATA + 1)
            return ServerResponse.builder().error("id error arguments").command("remove_by_id").build();

        ServerResponse responseWithIdError = checkId();

        if (responseWithIdError == null) {
            Person toDeletePerson = findPersonWithId();
            if (toDeletePerson == null)
                return ServerResponse.builder().message("id no id").command("remove_by_id").build();
            else return deleteFromDB();
        } else return responseWithIdError;
    }

    private ServerResponse checkId() {
        try {
            id = Long.parseLong(userDataAndOtherArgs.get(2));
            if (id < 0)
                return ServerResponse.builder().error("id error minus").command("remove_by_id").build();
            return null;
        } catch (Exception e) {
            return ServerResponse.builder().error("id error validate").command("remove_by_id").build();
        }
    }

    private Person findPersonWithId() {
        LinkedList<Person> people = collectionsKeeper.getPeople();
        return people.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private ServerResponse deleteFromDB() {
        int result = manager.deleteByIdFromBD(person, userDataAndOtherArgs);
        if (result == ONE_DELETED_PERSON) {
            collectionsKeeper.getPeople().remove(this.person);
            return ServerResponse.builder().message("id remove success").command("remove_by_id").build();
        } else if (result == NOBODY_DELETED) {
            return ServerResponse.builder().error("id creator error").command("remove_by_id").build();
        } else
            return ServerResponse.builder().error("id db error").command("remove_by_id").build();
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription(ResourceBundle bundle) {
        return bundle.getString("remove_by_id description");
    }
}
