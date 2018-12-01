package ledmein.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChristmasTreeData {
    List<RGB> colors;
    int rawsNum;
}
