package commands;

import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class CountLessPass extends Command {

    private Long passportId;

    public CountLessPass(CollectionsKeeper dc) {
        super(dc);
    }

    @Override
    public ServerResponse execute(List<String> userDataAndOtherArgs) {
        if (userDataAndOtherArgs.size() != LENGTH_WITH_ONLY_USER_DATA + 1) {
            return ServerResponse.builder().error("passport error arguments").command(getName()).build();
        }
        try {
            String idAsString = userDataAndOtherArgs.get(2);
            passportId = Long.parseLong(idAsString);
            if (passportId < 0) {
                return ServerResponse.builder().error("passport error minus").command(getName()).build();
            }
        } catch (Exception e) {
            return ServerResponse.builder().error("passport error validate").command(getName()).build();
        }
        return doCount();
    }

    private ServerResponse doCount(){
        LinkedList<Person> people = collectionsKeeper.getPeople();
        int res = (int) people.stream()
                .filter(x -> x.getPassportAsLong() < passportId)
                .count();
        return ServerResponse.builder().message(Integer.toString(res)).command(getName()).build();
    }

    @Override
    public String getName() {
        return "count_less_than_passport_id";
    }

    public String getDescription(ResourceBundle bundle) {
        return bundle.getString("count_less_than_passport_id description");
    }
}
