package commands;

import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Show extends Command {

    public Show(CollectionsKeeper dc) {
        super(dc);
    }

    @Override
    public ServerResponse execute(List<String> args) {
        List<Person> sortedPeople;
        LinkedList<Person> people = collectionsKeeper.getPeople();
        if (people.size() == 0)
            return ServerResponse.builder().personList(null).command("show").build();
        else {
            sortedPeople = people.stream()
                    .sorted(Comparator.comparing(Person::getId))
                    .collect(Collectors.toList());
        }
        return ServerResponse.builder().personList(sortedPeople).command("show").build();
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
