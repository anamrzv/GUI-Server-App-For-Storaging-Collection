package commands;

import other.CollectionsKeeper;
import other.Person;
import other.ServerResponse;

import java.util.LinkedList;
import java.util.List;

public class CountLessPass extends Command {

    public CountLessPass(CollectionsKeeper dc) {
        super(dc);
    }

    @Override
    public ServerResponse execute(List<String> args) {
        Long id;
        if (args.size() != 3) {
            return ServerResponse.builder().error("passport error arguments").command("count_less_than_passport_id").build();
        }
        try {
            id = Long.parseLong(args.get(2));
            if (id < 0) {
                return ServerResponse.builder().error("passport error minus").command("count_less_than_passport_id").build();
            }
        } catch (Exception e) {
            return ServerResponse.builder().error("passport error validate").command("count_less_than_passport_id").build();
        }
        LinkedList<Person> people = collectionsKeeper.getPeople();
        int res = (int) people.stream()
                .filter(x -> x.getPassportAsLong() < id)
                .count();
        return ServerResponse.builder().message(Integer.toString(res)).command("count_less_than_passport_id").build();
    }

    @Override
    public String getName() {
        return "count_less_than_passport_id";
    }

    @Override
    public String getDescription() {
        return "count_less_than_passport_id passportID : вывести количество элементов, значение поля passportID которых меньше заданного";
    }
}
