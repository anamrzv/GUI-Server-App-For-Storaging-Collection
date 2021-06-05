package other;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Координаты персоны в формате (x;y)
 */
@Data
public class Coordinates implements Serializable {

    private static final long serialVersionUID = 4L;

    /**
     * Поле - координата х
     */
    private float x;
    /**
     * Поле- коорината y
     */
    private Double y; //Поле не может быть null

    /**
     * Конструктор - создание нового объекта
     *
     * @param x - координата х
     * @param y - коорината y
     */
    public void setCoordinatesFirst(float x, double y) {
        this.x = x;
        this.y = y;
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
        Coordinates that = (Coordinates) o;
        return Float.compare(that.x, x) == 0 &&
                y.equals(that.y);
    }

    /**
     * Метод - создает хэшкод объекта
     *
     * @return int хэшкож
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
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
                '}';
    }
}
