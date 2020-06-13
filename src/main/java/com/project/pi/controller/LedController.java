package com.project.pi.controller;

import com.pi4j.io.gpio.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LedController {
    private GpioPinDigitalOutput pin;

    @RequestMapping("/")
    public String greeting(){
        return "hello world";
    }

    public String checkState(){
        return (getPin().isHigh()?"light is on":"light is off");
    }

    @RequestMapping("/toggle")
    public String light(){
        getPin().toggle();
        return checkState();
    }

    @RequestMapping("/blink")
    public String blink(){
        getPin().blink(200l,5000l);
        return "blinking...";
    }
    public GpioPinDigitalOutput getPin(){
        if(pin == null){
            GpioController gpio = GpioFactory.getInstance();
            pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04,"myLed", PinState.LOW);
        }
        return pin;
    }
}
