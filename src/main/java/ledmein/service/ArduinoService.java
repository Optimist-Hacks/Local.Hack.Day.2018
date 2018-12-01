package ledmein.service;

import arduino.Arduino;
import ledmein.model.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class ArduinoService {

    private static Logger logger = LoggerFactory.getLogger(ArduinoService.class);
    private static final int SIZE = 2;

    private Arduino arduino;

    public void writeEvent(EventType eventType){
        byte res = 1;
        switch (eventType) {
            case COMMIT:
                res = 9;
                break;
            case PULL_REQUEST:
                res = 1;
                break;
            case FORK:
                res = 2;
                break;
            case PUSH:
                res = 3;
                break;
            case ISSUE:
                res = 4;
                break;
            case IGNORE:
                res = 5;
                break;
            case BUILD_STARTED:
                res = 6;
                break;
            case BUILD_SUCCESS:
                res = 7;
                break;
            case BUILD_FAILED:
                res = 8;
                break;
        }
        arduino.serialWrite((char) res);
    }

    public void writeColor(Color color) {
        int red = Math.max(1, color.getRed());
        int green = Math.max(1, color.getGreen());
        int blue = Math.max(1, color.getBlue());

        color = new Color(red, green, blue);
        initArduino();

        if(arduino == null)
            return;

        logger.info("Start write color " + color);
        long timeStart = System.currentTimeMillis();

        byte[] bytes = new byte[] {
                (byte) color.getRed(),
                (byte) color.getGreen(),
                (byte) color.getBlue(),
                (byte) color.getRed(),
                (byte) color.getGreen(),
                (byte) color.getBlue()
        };



//        String input = "" +
//                (char) (color.getRed() << 8 + color.getGreen()) +
//                (char) (color.getBlue() << 8 + color.getRed()) +
//                (char) (color.getGreen() << 8 + color.getBlue());

//        String input = "" + (byte)((color.getRed() *6/256)*36) + (byte)((color.getGreen()*6/256)*36) + (byte)((color.getBlue()*6/256)*36);

        arduino.serialWrite(new String(bytes));

        long timeEnd = System.currentTimeMillis();
        logger.info("End write color. Takes " + (timeStart - timeEnd) + "ms");
    }

    private void initArduino() {
        if (arduino == null) {
            try {
                arduino = new Arduino("/dev/ttyUSB0", 115200);
                boolean connected = arduino.openConnection();
                logger.info("Arduino connected: " + connected + ". Start write size to arduino" + SIZE);
                arduino.serialWrite((char) SIZE);
                logger.info("End write size to arduino");
            } catch (Exception e) {
                logger.info("Fail to connect to Arduino");
                arduino = null;
            }
        }
    }

}
