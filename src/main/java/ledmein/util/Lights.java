package ledmein.util;

import com.sun.xml.internal.bind.v2.runtime.Coordinator;

import java.awt.*;
import java.util.Random;

public interface Lights {


    Color DEFAULT_COLOR = Color.white;
    Color PULL_COLOR = Color.red;
    Color HISTORY_END_COLOR = Color.blue;
    Color BUILD_SUCCESS_COLOR = Color.green;
    Color BUILD_FAILED_COLOR = Color.red;

    Random random = new Random();
    static Color getRandomLight() {
        return new Color(
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
        );
    }

}
