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
//
//    }
    public void initWiringPi(){
        // setup wiringPi
        if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
            return;
        }

        GpioUtil.export(3, GpioUtil.DIRECTION_OUT);
    }


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
            this.humidity = (float) ((dht11_dat[0] << 8) + dht11_dat[1]) / 10;
            if (this.humidity > 100) {
                this.humidity = dht11_dat[0]; // for DHT11
            }
            this.temperature = (float) (((dht11_dat[2] & 0x7F) << 8) + dht11_dat[3]) / 10;
            if (this.temperature > 125) {
                this.temperature = dht11_dat[2]; // for DHT11
            }
            if ((dht11_dat[2] & 0x80) != 0) {
                this.temperature = -this.temperature;
            }
            final float f = this.temperature * 1.8f + 32;
//            System.out.println("Humidity = " + h + " Temperature = " + c + "(" + f + "f)");
//            this.humidity = h;
//            this.temperature = c;
            System.out.println("new new Humidity = " + this.humidity + " new Temperature = " + this.temperature + "(" + f + "f)");

        } else {
            System.out.println("Data not good, skip");
        }
    }

    private boolean checkParity() {
        return dht11_dat[4] == (dht11_dat[0] + dht11_dat[1] + dht11_dat[2] + dht11_dat[3] & 0xFF);
    }

    public String getData()  {
        final TempAndHumidity dht = new TempAndHumidity();
        dht.initWiringPi();
        String result = "";
        String data = "";
        try{
            for (int i = 0; i < 10; i++) {
                Thread.sleep(2000);
                dht.getTemperatureFromPi(2);
                result = dht.humidity +" " + dht.temperature;
                if(dht.humidity>1){
                    break;
                }
            }
            data = result;
        }catch (InterruptedException e){
            System.out.println("something went wrong" + e.getCause());
        }
        return data;
    }


}
