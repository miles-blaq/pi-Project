package com.project.pi.controller;


import com.project.pi.model.SensorData;
import com.project.pi.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempSensorController {

    @Autowired
    private SensorService sensorService;

    @GetMapping("/tempAndHumidity")
    public ResponseEntity<SensorData> reqSensorData() {
        SensorData sensorData = sensorService.getSensorData();
        return ResponseEntity.ok(sensorData);
    }

    @GetMapping("/temperature")
    public ResponseEntity<SensorData> reqTemperatureData() {
        SensorData temperatureData = sensorService.getTemperatureData();
        return ResponseEntity.ok(temperatureData );
    }

    @GetMapping("/humidity")
    public ResponseEntity<SensorData> reqHumidityData() {
        SensorData humidityData = sensorService.getHumidityData();
        return ResponseEntity.ok(humidityData );
    }


}
