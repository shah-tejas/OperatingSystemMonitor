package com.monitor.monitorapi.util;

public class MonitorUtil {
    private boolean runMonitor;
    private static MonitorUtil monitorUtil = null;

    private MonitorUtil() {
    }

    public static MonitorUtil getInstance() {
        if(monitorUtil == null) {
            monitorUtil = new MonitorUtil();
        }
        return monitorUtil;
    }

    public void startMonitor() {
        runMonitor = true;
    }

    public void stopMonitor() {
        runMonitor = false;
    }

    public boolean checkMonitorStatus() {
        return runMonitor;
    }
}
