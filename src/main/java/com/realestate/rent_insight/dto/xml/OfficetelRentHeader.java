package com.realestate.rent_insight.dto.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * API 응답 XML의 <header> 태그에 해당하는 데이터를 담는 DTO
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfficetelRentHeader {

    @JsonProperty("resultCode")
    private String resultCode;

    @JsonProperty("resultMsg")
    private String resultMsg;
}
