package com.monitor.monitorapi.repository;

import com.monitor.monitorapi.model.CPUUsage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CPUUsageRepository extends MongoRepository<CPUUsage, Long> {
}
