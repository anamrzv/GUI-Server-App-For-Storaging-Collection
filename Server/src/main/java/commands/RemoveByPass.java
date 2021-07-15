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

    public RemoveByPass(CollectionsKeeper dc) {
        super(dc);
    }

    @Override
    public ServerResponse execute(DataBaseManager manager, List<String> userDataAndOtherArgs) {
        this.manager = manager;
        if (userDataAndOtherArgs.size() != 2) {
            if (userDataAndOtherArgs.size() != 3) {
                return ServerResponse.builder().error("passport error arguments").command("remove_by_passport_id").build();
            }
            long id;
            try {
                id = Long.parseLong(userDataAndOtherArgs.get(2));
                if (id < 0)
                    return ServerResponse.builder().error("passport error minus").command("remove_by_passport_id").build();
            } catch (Exception e) {
                return ServerResponse.builder().error("passport error validate").command("remove_by_passport_id").build();
            }
            LinkedList<Person> people = collectionsKeeper.getPeople();
            int count = 0;
            int unsuccess = 0;
            int noAcces = 0;
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
                        unsuccess++;
                    } else {
                        people.remove(person);
                        noAcces++;
                    }
                } else break;
            } while (true);
            LinkedList<Person> newCollection = manager.loadCollectionFromDB().getPeople();
            people.clear();
            people.addAll(newCollection);
            if (count != 0 && unsuccess == 0 && noAcces == 0)
                return ServerResponse.builder().message("passport remove success").command("remove_by_passport_id").build();
            else if (count == 0 && unsuccess == 0 && noAcces == 0)
                return ServerResponse.builder().message("passport no id").command("remove_by_passport_id").build();
            else if (count != 0 && unsuccess != 0)
                return ServerResponse.builder().message("passport not remove from db").command("remove_by_passport_id").build();
            else
                return ServerResponse.builder().error("passport remove error").command("remove_by_passport_id").build();
        } else {
            return ServerResponse.builder().error("passport error arguments").command("remove_by_passport_id").build();
        }
    }

    @Override
    public String getName() {
        return "remove_all_by_passport_id";
    }

    public String getDescription(ResourceBundle bundle) {
        return bundle.getString("remove_all_by_passport_id description");
    }
}
