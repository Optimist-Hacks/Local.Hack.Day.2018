package ledmein.util;

import ledmein.model.RGB;

import java.awt.*;
import java.util.Random;

public interface Lights {


    Color pushColor = Color.red;
    Color commitColor = Color.green;

    Random random = new Random();
    static RGB getRandomLight() {
        return new RGB(new int[]{
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
        });
    }

}
