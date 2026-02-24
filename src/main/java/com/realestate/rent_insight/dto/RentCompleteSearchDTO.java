package com.realestate.rent_insight.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RentCompleteSearchDTO {
    // 검색 조건 담당 DTO

    // 시군구 이름 대신 코드로 검색하기 위해  변경
    private String sggCd;     // 시군구 코드 (예: "1111000000")

    // 법정동 이름
    private String umdNm;     // 법정동명 (예: "청운동")

    // 단지명
    private String name;        // 오피스텔 단지명 (부분 검색용)

    // 보증금
    private Integer minDeposit; // 최소 보증금 (단위: 만원)
    private Integer maxDeposit; // 최대 보증금 (단위: 만원)

    // 월세
    private Integer minRent = 0;    // 최소 월세 (단위: 만원)
    private Integer maxRent = 0;    // 최대 월세 (단위: 만원)

    private String areaRange;    // 평형 미터제곱

}
