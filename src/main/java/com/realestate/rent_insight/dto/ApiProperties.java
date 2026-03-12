package com.realestate.rent_insight.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api")
@Getter
@Setter
public class ApiProperties {

    private String serviceKey;
    private BaseUrl baseUrl = new BaseUrl();

    @Getter
    @Setter
    public static class BaseUrl {
        private String officetel;
        private String multiHouse;
    }
}
