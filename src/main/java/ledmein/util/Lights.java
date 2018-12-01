package ledmein.util;

import ledmein.model.RGB;

import java.util.Random;

public interface Lights {

    int[] RED = {255,0,0};
    int[] GREEN = {0,255,0};
    int[] BLUE = {0,0,255};


    Random random = new Random();
    static RGB getRandomLight() {
        return new RGB(new int[]{
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
        });
    }

}
