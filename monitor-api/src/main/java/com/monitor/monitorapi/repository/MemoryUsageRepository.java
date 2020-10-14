package com.monitor.monitorapi.repository;

import com.monitor.monitorapi.model.MemoryUsage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryUsageRepository extends MongoRepository<MemoryUsage, Long> {
}
