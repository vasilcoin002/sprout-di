package org.sproutdi.core.config;

public class CacheConfig {
    private boolean enabled = true;
    private long defaultTtlMillis = 5000L;

    public CacheConfig() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getDefaultTtlMillis() {
        return defaultTtlMillis;
    }

    public void setDefaultTtlMillis(long defaultTtlMillis) {
        this.defaultTtlMillis = defaultTtlMillis;
    }
}
