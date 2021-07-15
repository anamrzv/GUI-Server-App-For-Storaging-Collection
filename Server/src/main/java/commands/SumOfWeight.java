package commands;

import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class SumOfWeight extends Command {

    private long sum = 0;

    public SumOfWeight(CollectionsKeeper dc) {
        super(dc);
    }

    @Override
    public ServerResponse execute(List<String> userDataAndOtherArgs) {
        if (userDataAndOtherArgs.size() == 2) {
            LinkedList<Person> people = collectionsKeeper.getPeople();
            people.forEach(x -> sum += x.getWeight());
            return ServerResponse.builder().message(Long.toString(sum)).command("sum_of_weight").build();
        } else {
            return ServerResponse.builder().error("error").command("sum_of_weight").build();
        }
    }

    @Override
    public String getName() {
        return "sum_of_weight";
    }

    @Override
    public String getDescription(ResourceBundle bundle) {
        return bundle.getString("sum_of_weight description");
    }
}
