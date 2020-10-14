package com.monitor.monitorapi.repository;

import com.monitor.monitorapi.model.CPUUsage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CPUUsageRepository extends PagingAndSortingRepository<CPUUsage, Long> {
}
