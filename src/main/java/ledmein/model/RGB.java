package ledmein.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RGB {

    private int[] values = new int[3];

    public int[] getValues() {
        return values;
    }

}
