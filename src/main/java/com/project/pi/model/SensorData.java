package com.project.pi.model;

public class SensorData {
    private float temperature;
    private float humidity;

    public SensorData(float temperature) {
        this.temperature = temperature;
    }

    public SensorData(float temperature, float humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public float getTemperature() {
        return temperature;
    }
    public float getHumidity() {
        return humidity;
    }
}
