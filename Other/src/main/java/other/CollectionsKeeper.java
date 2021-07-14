package other;

import lombok.Data;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class CollectionsKeeper implements Serializable {

    private static final long serialVersionUID = 2L;

    private final LinkedList<Person> people = new LinkedList<>();
    private final Map<Integer, Location> readyLocations = new HashMap<>();
    private final Set<String> scriptNames = new TreeSet<>();

    public void addLocation(Location l) {
        readyLocations.put(readyLocations.size() + 1, l);
    }

    public LinkedList<Person> getPeople() {
        return people;
    }

    public boolean validateName(String name) {
        Pattern pattern = Pattern.compile("^[\\p{L} ]+$");
        Matcher m = pattern.matcher(name);
        return m.matches();
    }

    public boolean validatePassport(String pass) {
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher m = pattern.matcher(pass);
        return m.matches();
    }

    public Set<String> getScriptNames() {
        return scriptNames;
    }

    public boolean addScriptName(String name) {
        return scriptNames.add(name);
    }

    public void clearScriptNames() {
        scriptNames.clear();
    }
}
