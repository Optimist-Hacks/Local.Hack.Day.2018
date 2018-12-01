package ledmein.service;

import arduino.Arduino;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class ArduinoService {

    private static Logger logger = LoggerFactory.getLogger(ArduinoService.class);

    private Arduino arduino;

    public void writeColor(Color color) {
        initArduino();

        logger.info("Start write color " + color);
        long timeStart = System.currentTimeMillis();

        String input = String.valueOf((char) 3) +
                (char) color.getRed() +
                (char) color.getGreen() +
                (char) color.getBlue();
        arduino.serialWrite(input);

        long timeEnd = System.currentTimeMillis();
        logger.info("End write color. Takes " + (timeStart - timeEnd) + "ms");
    }

    private void initArduino() {
        if (arduino == null) {
            arduino = new Arduino("/dev/ttyUSB0", 115200);
            boolean connected = arduino.openConnection();
            System.out.println("Arduino connected: " + connected);
        }
    }

}
