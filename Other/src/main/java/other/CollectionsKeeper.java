package other;

import lombok.Data;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class CollectionsKeeper implements Serializable {

    private static final long serialVersionUID = 2L;

    /**
     * Поле - связный список объектов Person
     */
    private final LinkedList<Person> people = new LinkedList<>();

    /**
     * Поле - отображение объектов Location
     */
    private final Map<Integer, Location> readyLocations = new HashMap<>();

    /**
     * Метод- добавляет локацию в коллекцию
     */
    public void addLocation(Location l) {
        readyLocations.put(readyLocations.size() + 1, l);
    }

    /**
     * Поле - TreeSet объектов String для обработки рекурсии в execute_script
     */
    private final Set<String> scriptNames = new TreeSet<>();

    /**
     * Метод - геттер коллекции людей
     *
     * @return LinkedList<Person> коллекция
     */
    public LinkedList<Person> getPeople() {
        return people;
    }

    /**
     * Метод - валидация на отсутствие цифр и спец символов в строке
     *
     * @return boolean true/false
     */
    public boolean validateName(String name) {
        Pattern pattern = Pattern.compile("^[\\p{L} ]+$");
        Matcher m = pattern.matcher(name);
        return m.matches();
    }

    /**
     * Метод - валидация на отсутствие букв в строке
     *
     * @return boolean true/false
     */
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
