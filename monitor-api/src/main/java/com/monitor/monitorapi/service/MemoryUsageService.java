package com.monitor.monitorapi.service;

import com.monitor.monitorapi.model.CPUUsage;
import com.monitor.monitorapi.model.MemoryUsage;
import com.monitor.monitorapi.repository.MemoryUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoryUsageService {
    @Autowired
    MemoryUsageRepository memoryUsageRepository;
    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    public void saveMemoryUsage(long timestamp, double percent) {
        memoryUsageRepository.save(new MemoryUsage(sequenceGeneratorService.generateSequence(MemoryUsage.SEQUENCE_NAME), timestamp, percent));
    }

    public void deleteOldRecords() {
        memoryUsageRepository.deleteAll();
    }

    public List<MemoryUsage> getLatestRecords() {
        Pageable sortedByTimestamp = PageRequest.of(0, 60, Sort.by("timestamp").descending());
        return memoryUsageRepository.findAll(sortedByTimestamp).getContent();
    }
}
