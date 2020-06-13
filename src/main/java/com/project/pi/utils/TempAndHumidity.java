package com.project.pi.utils;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioUtil;
import org.springframework.stereotype.Component;

@Component
public class TempAndHumidity {
    private static final int MAXTIMINGS  = 85;
    private final int[] dht11_dat   = { 0, 0, 0, 0, 0 };
    private float temperature;
    private float humidity;

//    public TempAndHumidity() {
//
//        // setup wiringPi
//        if (Gpio.wiringPiSetup() == -1) {
//            System.out.println(" ==>> GPIO SETUP FAILED");
//            return;
//        }
//
//        GpioUtil.export(3, GpioUtil.DIRECTION_OUT);
//    }


    public void getTemperatureFromPi(final int pin) {
        int laststate = Gpio.HIGH;
        int j = 0;
        dht11_dat[0] = dht11_dat[1] = dht11_dat[2] = dht11_dat[3] = dht11_dat[4] = 0;

        Gpio.pinMode(pin, Gpio.OUTPUT);
        Gpio.digitalWrite(pin, Gpio.LOW);
        Gpio.delay(18);

        Gpio.digitalWrite(pin, Gpio.HIGH);
        Gpio.pinMode(pin, Gpio.INPUT);

        for (int i = 0; i < MAXTIMINGS; i++) {
            int counter = 0;
            while (Gpio.digitalRead(pin) == laststate) {
                counter++;
                Gpio.delayMicroseconds(1);
                if (counter == 255) {
                    break;
                }
            }

            laststate = Gpio.digitalRead(pin);

            if (counter == 255) {
                break;
            }

            /* ignore first 3 transitions */
            if (i >= 4 && i % 2 == 0) {
                /* shove each bit into the storage bytes */
                dht11_dat[j / 8] <<= 1;
                if (counter > 16) {
                    dht11_dat[j / 8] |= 1;
                }
                j++;
            }
        }
        // check we read 40 bits (8bit x 5 ) + verify checksum in the last
        // byte
        if (j >= 40 && checkParity()) {
            float h = (float) ((dht11_dat[0] << 8) + dht11_dat[1]) / 10;
            if (h > 100) {
                h = dht11_dat[0]; // for DHT11
            }
            float c = (float) (((dht11_dat[2] & 0x7F) << 8) + dht11_dat[3]) / 10;
            if (c > 125) {
                c = dht11_dat[2]; // for DHT11
            }
            if ((dht11_dat[2] & 0x80) != 0) {
                c = -c;
            }
            final float f = c * 1.8f + 32;
//            System.out.println("Humidity = " + h + " Temperature = " + c + "(" + f + "f)");
            this.humidity = h;
            this.temperature = c;
            System.out.println("new Humidity = " + this.humidity + " new Temperature = " + this.temperature + "(" + f + "f)");

        } else {
            System.out.println("Data not good, skip");
        }
    }

    private boolean checkParity() {
        return dht11_dat[4] == (dht11_dat[0] + dht11_dat[1] + dht11_dat[2] + dht11_dat[3] & 0xFF);
    }

//    public static void main(final String ars[]) throws Exception {
//
//        final TempAndHumidity dht = new TempAndHumidity();
//
//        for (int i = 0; i < 10; i++) {
//            Thread.sleep(2000);
//            dht.getTemperatureFromPi(2);
//        }
//        System.out.println("Done!!");
//    }
    public String getData()  {
        final TempAndHumidity dht = new TempAndHumidity();
        String data = "";
        try{
            for (int i = 0; i < 10; i++) {
                Thread.sleep(2000);
                dht.getTemperatureFromPi(2);
            }
//        System.out.println("Done!!");
            data = this.humidity + " " + this.temperature;
        }catch (InterruptedException e){
            System.out.println("something went wrong" + e.getCause());
        }
        return data;
    }
}
