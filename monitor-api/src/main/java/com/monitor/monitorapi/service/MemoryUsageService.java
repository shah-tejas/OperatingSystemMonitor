package com.monitor.monitorapi.service;

import com.monitor.monitorapi.model.CPUUsage;
import com.monitor.monitorapi.model.MemoryUsage;
import com.monitor.monitorapi.repository.MemoryUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemoryUsageService {
    @Autowired
    MemoryUsageRepository memoryUsageRepository;
    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    public void saveMemoryUsage(long timestamp, double percent) {
        memoryUsageRepository.save(new MemoryUsage(sequenceGeneratorService.generateSequence(CPUUsage.SEQUENCE_NAME), timestamp, percent));
    }

    public void deleteOldRecords() {
        memoryUsageRepository.deleteAll();
    }
}
