package ledmein.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RGB {
    int[] values = new int[3];
}
