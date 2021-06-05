package other;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Comparator;

public class PersonSizeComparator implements Comparator<Person> {
    public int compare(Person first, Person second) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        int firstString = gson.toJson(first).length();
        int secondString = gson.toJson(second).length();
        if (firstString >= secondString) return 1;
        else return -1;
    }
}
