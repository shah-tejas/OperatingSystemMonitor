package com.monitor.monitorapi.controller;

import com.monitor.monitorapi.dto.MonitorStatus;
import com.monitor.monitorapi.service.CPUUsageService;
import com.monitor.monitorapi.util.MonitorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonitorStatusController {

    @Autowired
    CPUUsageService cpuUsageService;

    @PostMapping("/api/monitorstatus/start")
    public ResponseEntity<MonitorStatus> startMonitor() {
        cpuUsageService.startMonitoring();
        return new ResponseEntity<>(new MonitorStatus(true), HttpStatus.CREATED);
    }

    @PostMapping("/api/monitorstatus/stop")
    public ResponseEntity<MonitorStatus> stopMonitor() {
        MonitorUtil.getInstance().stopMonitor();
        return new ResponseEntity<>(new MonitorStatus(false), HttpStatus.CREATED);
    }

    @GetMapping("/api/monitorstatus")
    public ResponseEntity<MonitorStatus> getMonitorStatus() {
        boolean status = MonitorUtil.getInstance().checkMonitorStatus();
        return new ResponseEntity<>(new MonitorStatus(status), HttpStatus.OK);
    }
}
