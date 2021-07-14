package commands;

import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;

public class SumOfWeight extends Command {

    private long sum = 0;

    public SumOfWeight(CollectionsKeeper dc) {
        super(dc);
    }

    @Override
    public ServerResponse execute(List<String> args) {
        if (args.size() == 2) {
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
    public String getDescription() {
        return "sum_of_weight : вывести сумму значений поля weight для всех элементов коллекции";
    }
}
