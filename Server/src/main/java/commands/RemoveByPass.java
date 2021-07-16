package commands;

import database.DataBaseManager;
import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class RemoveByPass extends Command {

    private DataBaseManager manager;
    private List<String> userDataAndOtherArgs;

    private int count = 0;
    private int unsuccessful = 0;
    private int noAccess = 0;
    private long id;
    private LinkedList<Person> people = collectionsKeeper.getPeople();

    public RemoveByPass(CollectionsKeeper collectionsKeeper) {
        super(collectionsKeeper);
    }

    @Override
    public ServerResponse execute(DataBaseManager manager, List<String> userDataAndOtherArgs) {
        this.manager = manager;
        this.userDataAndOtherArgs = userDataAndOtherArgs;

        if (userDataAndOtherArgs.size() != LENGTH_WITH_ONLY_USER_DATA + 1) {
            return ServerResponse.builder().error("passport error arguments").command("remove_by_passport_id").build();
        }

        ServerResponse responseWithIdError = checkId();

        if (responseWithIdError == null) {
            findAndDeletePeopleWithId();
            SimpleAdd.updatePeopleList(manager, people);
            return getServerResponse();
        } else return responseWithIdError;
    }

    private ServerResponse checkId(){
        try {
            id = Long.parseLong(userDataAndOtherArgs.get(2));
            if (id < 0)
                return ServerResponse.builder().error("passport error minus").command("remove_by_passport_id").build();
            return null;
        } catch (Exception e) {
            return ServerResponse.builder().error("passport error validate").command("remove_by_passport_id").build();
        }
    }

    private void findAndDeletePeopleWithId(){
        do {
            Person person = people.stream()
                    .filter(x -> x.getPassportAsLong().equals(id))
                    .findFirst()
                    .orElse(null);
            if (person != null) {
                int result = manager.deleteByIdFromBD(person, userDataAndOtherArgs);
                if (result == 1) {
                    people.remove(person);
                    count++;
                } else if (result == -1) {
                    people.remove(person);
                    unsuccessful++;
                } else {
                    people.remove(person);
                    noAccess++;
                }
            } else break;
        } while (true);
    }

    private ServerResponse getServerResponse(){
        if (count != 0 && unsuccessful == 0 && noAccess == 0)
            return ServerResponse.builder().message("passport remove success").command(getName()).build();
        else if (count == 0 && unsuccessful == 0 && noAccess == 0)
            return ServerResponse.builder().message("passport no id").command(getName()).build();
        else if (count != 0 && unsuccessful != 0)
            return ServerResponse.builder().message("passport not remove from db").command(getName()).build();
        else
            return ServerResponse.builder().error("passport remove error").command(getName()).build();
    }

    @Override
    public String getName() {
        return "remove_all_by_passport_id";
    }

    public String getDescription(ResourceBundle bundle) {
        return bundle.getString("remove_all_by_passport_id description");
    }
}
