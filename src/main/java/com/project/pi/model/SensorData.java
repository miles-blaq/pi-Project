package com.project.pi.model;

public class SensorData {
//    private float temperature;
//    private float humidity;
    private String TempAndH;

//    public SensorData(double temperature) {
//        this.temperature = temperature;
//    }
//
//    public SensorData( int humidity) {
//        this.humidity = humidity;
//    }

    public SensorData(String TempAndH) {
//        this.temperature = temperature;
//        this.humidity = humidity;
        this.TempAndH = TempAndH;
    }

//    public double getTemperature() {
//        return temperature;
//    }

    public String getTempAndH(){
        return this.TempAndH;
    }


}
