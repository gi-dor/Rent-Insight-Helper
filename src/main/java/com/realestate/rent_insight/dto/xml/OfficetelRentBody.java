package com.realestate.rent_insight.dto.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * API 응답 XML의 <body> 태그에 해당하는 데이터를 담는 DTO
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfficetelRentBody {

    /**
     * <items> 태그 안에 <item> 태그들이 여러 개 있는 리스트 구조 형태
     * @JacksonXmlElementWrapper(useWrapping = false)를 쓰면 <items>를 무시할 수 있지만,
     * 명시적으로 구조를 표현하기 위해 이 방법을 사용합니다.
     */
    @JacksonXmlElementWrapper(localName = "items")
    @JacksonXmlProperty(localName = "item")
    private List<OfficetelRentItem> items;

    @JacksonXmlProperty(localName = "numOfRows")
    private int numOfRows;

    @JacksonXmlProperty(localName = "pageNo")
    private int pageNo;

    @JacksonXmlProperty(localName = "totalCount")
    private int totalCount;
}
