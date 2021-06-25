package other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Objects;

/**
 * Персона, обладающая некоторыми характеристиками.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Comparable<Person>, Serializable {

    private static final long serialVersionUID = 6L;

    /**
     * Поле - id
     * Поле не может быть null, Значение поля должно быть больше 0,
     * Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически
     */
    private Long id;
    /**
     * Поле - имя
     * Поле не может быть null, Строка не может быть пустой
     */
    private String name;

    /**
     * Поле - рост; Значение поля должно быть больше 0
     */
    private long height;
    /**
     * Поле - вес; Значение поля должно быть больше 0
     */
    private long weight;
    /**
     * Поле - id паспорта; Строка не может быть пустой, Длина строки должна быть не меньше 10,
     * Длина строки не должна быть больше 27, Поле не может быть null
     */
    private String passportID;
    /**
     * Поле - цыет волос; Поле не может быть null
     */
    private Color hairColor;
    /**
     * Поле - местоположение; Поле может быть null
     */
    private Location location;
    /**
     * Поле - координаты; Поле не может быть null
     */
    private Coordinates coordinates;


    private LocalDate creationDate;

    private String creator;

    public String getCreationDateString(Locale locale) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(locale);
        return creationDate.format(formatter);
    }

    public Long getPassportAsLong() {
        return Long.parseLong(passportID);
    }

    public float getCoordinateX() {
        return coordinates.getX();
    }

    public double getCoordinateY() {
        return coordinates.getY();
    }

    public String getLocationName() {
        return location.getName();
    }

    public int getLocationX() {
        return location.getX();
    }

    public float getLocationY() {
        return location.getY();
    }

    public double getLocationZ() {
        return location.getZ();
    }

    /**
     * Метод - сравнение по умоланию по длине id
     *
     * @param p - объект
     * @return -1/1/0
     */
    @Override
    public int compareTo(Person p) {
        return this.name.compareTo(p.name);
    }

    /**
     * Метод - сравнение двух объектов
     *
     * @param o - объект сравнения
     * @return true если объекты равны, false иначе
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return height == person.height &&
                weight == person.weight &&
                id.equals(person.id) &&
                name.equals(person.name) &&
                passportID.equals(person.passportID) &&
                hairColor == person.hairColor &&
                creator.equals(person.getCreator()) &&
                creationDate == person.getCreationDate() &&
                location.equals(person.location) &&
                coordinates.equals(person.coordinates);
    }

    /**
     * Метод - создает хэшкод объекта
     *
     * @return int хэшкож
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, height, weight, passportID, hairColor, location, coordinates, creationDate, creator);
    }

    /**
     * Метод - представляет объект в строковом виде
     *
     * @return String строка с информацией об объекте
     */
    @Override
    public String toString() {
        return "Person :" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", passportID='" + passportID + '\'' +
                ", hairColor=" + hairColor +
                ", location=" + location +
                ", coordinates=" + coordinates;
    }
}
