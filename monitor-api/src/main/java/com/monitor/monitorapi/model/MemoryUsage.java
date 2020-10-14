package com.monitor.monitorapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("MemoryUsage")
@AllArgsConstructor
public @Data class MemoryUsage {

    @Transient
    public static final String SEQUENCE_NAME = "memory_usage_sequence";

    @Id
    private long id;
    private long timestamp;
    private double percentage;
}
