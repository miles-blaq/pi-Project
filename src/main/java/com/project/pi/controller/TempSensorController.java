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

    @GetMapping("/temp")
    public ResponseEntity<SensorData> reqSensorDate() {
        SensorData sensorData = sensorService.getSensorData();
        return ResponseEntity.ok(sensorData);
    }

}
