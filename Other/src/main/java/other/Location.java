package other;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
public class Location implements Serializable {

    private static final long serialVersionUID = 5L;

    private Integer x; //Поле не может быть null
    private float y;
    private Double z; //Поле не может быть null
    private String name = "Безымянное положение";

    public String getLocation() {
        return " Координаты x: " + x + " y: " + y + " z: " + z;
    }

    public void setLocation(int x, float y, double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        if (!name.equals("")) this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", name='" + name + '\'' +
                '}';
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }
}