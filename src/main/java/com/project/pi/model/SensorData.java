package com.project.pi.model;

public class SensorData {
    private float temperature;
    private float humidity;

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public SensorData(float temperature, float humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
