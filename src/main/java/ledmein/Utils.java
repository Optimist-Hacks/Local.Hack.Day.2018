package ledmein;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static int randomColor() {
        return ThreadLocalRandom.current().nextInt(0, 256);
    }

    public static int[][] randomRgb(int size) {
        int[][] rgb = new int[size][3];
        for (int i = 0; i < size; i++) {
            rgb[i][0] = randomColor();
            rgb[i][1] = randomColor();
            rgb[i][2] = randomColor();
        }
        return rgb;
    }

}
