package com.realestate.rent_insight.dto.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 * API 응답 XML의 최상위 <response> 태그에 해당하는 데이터를 담는 DTO
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "response") // 이 클래스가 XML의 'response' 루트 요소라는것을 나타냄
public class OfficetelRentResponse {

    @JacksonXmlProperty(localName = "header")
    private OfficetelRentHeader header;

    @JacksonXmlProperty(localName = "body")
    private OfficetelRentBody body;
}
