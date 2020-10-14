package com.monitor.monitorapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

public @Data
class DatabaseSequence {
    @Id
    private String id;
    private long seq;
}
