package other;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class Coordinates implements Serializable {

    private static final long serialVersionUID = 4L;

    private float x;
    private Double y; //Поле не может быть null

    public void setCoordinatesFirst(float x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Float.compare(that.x, x) == 0 &&
                y.equals(that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
