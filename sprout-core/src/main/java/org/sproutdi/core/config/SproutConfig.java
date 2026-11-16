package org.sproutdi.core.config;

import java.util.ArrayList;
import java.util.List;

public class SproutConfig {
    private List<String> scanPackages = new ArrayList<>();
    private CacheConfig cache = new CacheConfig();
    private ReportConfig report = new ReportConfig();

    public List<String> getScanPackages() {
        return List.copyOf(scanPackages);
    }

    public void setScanPackages(List<String> scanPackages) {
        this.scanPackages = scanPackages == null ? new ArrayList<>() : new ArrayList<>(scanPackages);
    }

    public CacheConfig getCache() {
        return cache;
    }

    public void setCache(CacheConfig cache) {
        if (cache != null) {
            this.cache = cache;
        }
    }

    public ReportConfig getReport() {
        return report;
    }

    public void setReport(ReportConfig report) {
        if (report != null) {
            this.report = report;
        }
    }
}
