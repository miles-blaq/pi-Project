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

    @RequestMapping("/lights")
    public String light(){
        if(pin == null){
            GpioController gpio = GpioFactory.getInstance();
            pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04,"myLed", PinState.LOW);
        }
        pin.toggle();
        return "OK";
    }
}
