package commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import other.CollectionsKeeper;
import other.Person;
import other.PersonSizeComparator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class SpecialSave {

    private final CollectionsKeeper collectionsKeeper;

    /**
     * Конструктор - создание нового объекта
     *
     * @param dc - обработчик команд
     */
    public SpecialSave(CollectionsKeeper dc) {
        this.collectionsKeeper = dc;
    }

    public String execute() {
        String dir = System.getenv("output7");
        if (dir == null) {
            dir = "C:/Users/Ana/Programming/lab-work-7/src/main/resources/output.txt";
        }
        try (PrintWriter pw = new PrintWriter(dir)) {
            LinkedList<Person> people = collectionsKeeper.getPeople();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            PersonSizeComparator comparator = new PersonSizeComparator();
            people.stream()
                    .sorted(comparator)
                    .map(gson::toJson)
                    .forEach(x -> pw.write(x + "\n"));
            return null;
        } catch (FileNotFoundException e) {
            return "Ошибка при сохранении коллекции в файл.";
        }
    }
}