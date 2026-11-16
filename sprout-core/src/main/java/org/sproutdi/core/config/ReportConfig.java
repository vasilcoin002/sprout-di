package org.sproutdi.core.config;

public class ReportConfig {
    private boolean enabled = true;
    private String outputPath = "sprout-report.txt";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
}
