package com.monitor.monitorapi.service;

import com.monitor.monitorapi.model.CPUUsage;
import com.monitor.monitorapi.repository.CPUUsageRepository;
import com.monitor.monitorapi.util.MonitorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CPUUsageService {
    @Autowired
    CPUUsageRepository cpuUsageRepository;
    @Autowired
    SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    MemoryUsageService memoryUsageService;

    public void saveCPUUsage(long timestamp, double percent) {
        cpuUsageRepository.save(new CPUUsage(sequenceGeneratorService.generateSequence(CPUUsage.SEQUENCE_NAME), timestamp, percent));
    }

    public void deleteOldRecords() {
        cpuUsageRepository.deleteAll();
    }

    public void startMonitoring() {
        if(!MonitorUtil.getInstance().checkMonitorStatus()) {
            MonitorUtil.getInstance().startMonitor();
            new CPUMonitor().start();
            new MemoryUsage().start();
        }
    }

    public void saveMemoryUsage(long timestamp, double percent) {
        memoryUsageService.saveMemoryUsage(timestamp, percent);
    }

    public void deleteOldMemoryUsageRecords(){
        memoryUsageService.deleteOldRecords();
    }

    public class CPUMonitor extends Thread {

        public void run() {

            System.out.println("Starting CPU Monitor...");
            deleteOldRecords();

            Map<Long, Long> threadMap = new HashMap<>();
            List<Long> threadIds = new ArrayList<>();

            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            for(long threadId : threadMXBean.getAllThreadIds()) {
                threadMap.put(threadId, threadMXBean.getThreadCpuTime(threadId));
                threadIds.add(threadId);
            }

            while (MonitorUtil.getInstance().checkMonitorStatus()) {
                try{
                    // sleep for 1 sec
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                long diffSum = 0;

                for(long threadId : threadMXBean.getAllThreadIds()) {
                    long currentCPUTime = threadMXBean.getThreadCpuTime(threadId);
                    if(threadMap.containsKey(threadId)) {
                        diffSum += (currentCPUTime - threadMap.get(threadId));
                        threadMap.put(threadId, currentCPUTime);
                        threadIds.remove(threadId);
                    } else {
                        threadMap.put(threadId, currentCPUTime);
                    }
                }

                double cpuUsage = (diffSum / Math.pow(10, 7));
                long timestamp = Instant.now().toEpochMilli();

                saveCPUUsage(timestamp, cpuUsage);

                // Remove all threadIds that are no longer there
                for(long threadId : threadIds) {
                    threadMap.remove(threadId);
                }

                // Add current threadId in the list
                threadIds = new ArrayList<>(threadMap.keySet());
            }

            System.out.println("CPU Monitor done");
        }
    }

    public class MemoryUsage extends Thread {

        public void run() {
            System.out.println("Starting Memory Monitor...");
            memoryUsageService.deleteOldRecords();

            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

            while (MonitorUtil.getInstance().checkMonitorStatus()) {
                try {
                    // sleep for 1 sec
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                double used = (double)memoryMXBean.getHeapMemoryUsage().getUsed();
                double max = (double)memoryMXBean.getHeapMemoryUsage().getMax();
                saveMemoryUsage(Instant.now().toEpochMilli(), (used / max) * 100);

            }

            System.out.println("Memory Monitor stopped");
        }
    }

}
