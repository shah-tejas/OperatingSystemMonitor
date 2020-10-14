package com.monitor.monitorapi.repository;

import com.monitor.monitorapi.model.MemoryUsage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoryUsageRepository extends PagingAndSortingRepository<MemoryUsage, Long> {
}
