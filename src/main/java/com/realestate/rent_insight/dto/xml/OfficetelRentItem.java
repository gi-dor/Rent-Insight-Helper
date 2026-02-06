package com.realestate.rent_insight.dto.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * API 응답 XML의 <item> 태그에 해당하는 데이터를 담는 DTO
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true) // XML에 있지만 DTO에 정의하지 않은 필드는 무시합니다.
public class OfficetelRentItem {

    // XML 태그와 필드 이름이 동일하므로, 불필요하고 잘못된 @JsonProperty를 모두 제거
    private String buildYear;
    private String contractTerm;
    private String contractType;
    private int dealDay;
    private int dealMonth;
    private int dealYear;
    private String deposit;
    private double excluUseAr;
    private int floor;
    private String jibun;
    private String monthlyRent;
    private String offiNm;
    private String sggCd;
    private String sggNm;
    private String umdNm;
}
