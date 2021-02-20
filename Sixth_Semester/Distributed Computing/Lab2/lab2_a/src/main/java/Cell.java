import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicBoolean;

@Data
@AllArgsConstructor
public class Cell {
    private CellStatus cellStatus;
    private AtomicBoolean inProgress;
}
