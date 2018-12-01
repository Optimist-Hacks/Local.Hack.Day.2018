package ledmein.repository.authorToColorRepository;

import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.HashMap;

public interface AuthorsColorsRepository {

    @NonNull
    HashMap<String, Color> getAuthorsColors();
}
