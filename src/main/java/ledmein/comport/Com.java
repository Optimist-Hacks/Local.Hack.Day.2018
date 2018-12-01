package ledmein.comport;

import arduino.Arduino;

public class Com {

    public static void main(String[] args) throws InterruptedException {

        Arduino arduino = new Arduino("/dev/ttyUSB0", 115200);

        boolean connected = arduino.openConnection();
        System.out.println("Соединение установлено: " + connected);
        Thread.sleep(2000);


        arduino.serialWrite((char) 1);
        System.out.println("Send numb of lines");
        Thread.sleep(1000);

        while (true) {
            for (int i = 0; i <= 255; i += 1) {
                long time1 = System.currentTimeMillis();

                StringBuilder builder = new StringBuilder();
                builder.append((char) i);
                builder.append((char) i);
                builder.append((char) i);
                String res = builder.toString();

                System.out.println(res);
                arduino.serialWrite(res);

                long time2 = System.currentTimeMillis();
                System.out.println("Send rgb: " + i + " " + (time2 - time1));

            }
        }

    }
}
