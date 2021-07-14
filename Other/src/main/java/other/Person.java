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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Comparable<Person>, Serializable {

    private static final long serialVersionUID = 6L;

    private Long id; //Поле не может быть null, должно быть больше 0, уникальным
    private String name; //Поле не может быть null, пустой строкой
    private long height; //Больше 0
    private long weight; //Больше 0
    private String passportID; //Поле не может быть null, 10-27 символов
    private Color hairColor; //Поле не может быть null
    private Location location; //Поле не может быть null
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null
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

    @Override
    public int compareTo(Person p) {
        return this.name.compareTo(p.name);
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(name, height, weight, passportID, hairColor, location, coordinates, creationDate, creator);
    }

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
