import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Monk {
    private Integer energy;
    private Monastery monastery;
}
