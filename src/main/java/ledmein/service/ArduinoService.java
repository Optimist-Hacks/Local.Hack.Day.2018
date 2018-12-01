package ledmein.service;

import arduino.Arduino;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class ArduinoService {

    private static Logger logger = LoggerFactory.getLogger(ArduinoService.class);
    private static final int SIZE = 2;

    private Arduino arduino;

    public void writeColor(Color color) {
        initArduino();

        if(arduino == null)
            return;

        logger.info("Start write color " + color);
        long timeStart = System.currentTimeMillis();

        String input = "" +
                (char) color.getRed() +
                (char) color.getGreen() +
                (char) color.getBlue();
        arduino.serialWrite(input);

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
