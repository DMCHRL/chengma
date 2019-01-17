package com.suitong.devplatform.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to JHipster.
 * <p>
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private String apiConfigs;
    private String noCheckUrl;

    public String getApiConfigs() {
        return apiConfigs;
    }

    public void setApiConfigs(String apiConfigs) {
        this.apiConfigs = apiConfigs;
    }

    public String getNoCheckUrl() {
        return noCheckUrl;
    }

    public void setNoCheckUrl(String noCheckUrl) {
        this.noCheckUrl = noCheckUrl;
    }
}
