package com.project.pi.service;

import com.project.pi.model.SensorData;
import com.project.pi.utils.TempAndHumidity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorService {

    @Autowired
    private TempAndHumidity tempAndHumidity;

    public SensorData getSensorData() {
        return new SensorData(tempAndHumidity.getTemperature(),tempAndHumidity.getHumidity());
    }

    public SensorData getTemperatureData(){
        return new SensorData(tempAndHumidity.getTemperature());
    }
    public SensorData getHumidityData(){
        return new SensorData(tempAndHumidity.getHumidity());
    }
}
