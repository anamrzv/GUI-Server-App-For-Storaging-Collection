package other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerResponse implements Serializable {
    private static final long serialVersionUID = 3L;

    private String command;
    private String error;
    private String message;
    private List<Person> personList;
}
