package other;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Местоположение персоны в формате (x;y;z)
 */
@Getter
@Setter
@Data
@NoArgsConstructor
public class Location implements Serializable {

    private static final long serialVersionUID = 5L;

    /**
     * Поле - координата х
     */
    private Integer x; //Поле не может быть null

    /**
     * Поле - координата y
     */
    private float y;

    /**
     * Поле - координата z
     */
    private Double z; //Поле не может быть null

    /**
     * Поле - название местоположения
     */
    private String name = "Безымянное положение";

    /**
     * Метод - возвращает строку с координатами
     *
     * @return String - информация о координатах
     */
    public String getLocation() {
        return " Координаты x: " + x + " y: " + y + " z: " + z;
    }

    /**
     * Метод - инициализация полей объекта
     *
     * @param x    - координата х
     * @param y    - координата у
     * @param z    - координата z
     * @param name - название
     */
    public void setLocation(int x, float y, double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        if (!name.equals("")) this.name = name;
    }

    /**
     * Метод - представляет объект в строковом виде
     *
     * @return String строка с информацией об объекте
     */
    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", name='" + name + '\'' +
                '}';
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
        Location location = (Location) o;
        return Float.compare(location.y, y) == 0 &&
                x.equals(location.x) &&
                z.equals(location.z) &&
                Objects.equals(name, location.name);
    }

    /**
     * Метод - создает хэшкод объекта
     *
     * @return int хэшкож
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }
}