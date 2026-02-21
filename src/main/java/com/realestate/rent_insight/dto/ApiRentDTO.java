package com.realestate.rent_insight.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 공공데이터 API로부터 받은 XML 응답의 <item> 태그의 내용을
 * 임시로 담아두기 위한 데이터 운반용 객체
 * API 응답 구조와 거의 1:1로 매칭되는 역할
 *
 * 1. API 응답을 Java가 이해할 수 있는 객체 형태로 쉽게 변환하기 위해 사용
 * 2. 이 DTO를 거쳐서, 최종적으로 DB에 저장될 RentComplete 엔티티로 데이터를 가공하고 변환
 */
@Data // Lombok: Getter, Setter, ToString 등의 메서드를 자동으로 생성해줌.
@JsonIgnoreProperties(ignoreUnknown = true) // API 응답에는 있지만, 이 클래스에는 정의되지 않은 필드(예: preDeposit)는 조용히 무시하도록 설정합니다.
public class ApiRentDTO {

    /**
     * @JsonProperty 어노테이션
     * XML/JSON의 필드 이름(key)과, 이 클래스의 필드 이름을 서로 매핑(연결)시켜주는 역할
     * API 응답의 <buildYear> 태그 값이 이 클래스의 buildYear 필드에 자동으로 들어옴
     * 만약 필드 이름과 태그 이름이 같다면 생략할 수도 있다함
     */


    //  가끔 int 타입 변수에 값이 비어있는게 있어서 null 허용하려고 Integer 사용
    // <buildYear></buildYear> 요따위로 들어옴

    @JsonProperty("buildYear")
    private Integer buildYear; // 건축년도

    @JsonProperty("contractTerm")
    private String contractTerm; // 계약기간

    @JsonProperty("contractType")
    private String contractType; // 계약구분

    @JsonProperty("dealDay")
    private Integer dealDay; // 계약일

    @JsonProperty("dealMonth")
    private Integer dealMonth; // 계약월

    @JsonProperty("dealYear")
    private Integer dealYear; // 계약년

    // API 응답에서 18,000 처럼 ,가 포함된 문자열로 오기 때문에
    // 일단 String으로  받은 뒤에 나중에 숫자로 변환할 예정
    @JsonProperty("deposit")
    private String deposit; // 보증금

    // XML 태그 이름은 'excluUseAr'인데 뭔뜻인지 몰라서 걍 area 로 변경함
    @JsonProperty("excluUseAr")
    private double area; // 전용면적

    @JsonProperty("floor")
    private Integer floor; // 층

    @JsonProperty("jibun")
    private String jibun; // 지번

    @JsonProperty("monthlyRent")
    private String monthlyRent; // 월세

    // XML 태그 이름은 'offiNm'이지만 name으로 변경
    @JsonProperty("offiNm")
    private String name; // 오피스텔 이름

    @JsonProperty("sggCd")
    private String sggCd; // 시군구코드

    @JsonProperty("sggNm")
    private String sggNm; // 시군구명

    @JsonProperty("umdNm")
    private String umdNm; // 법정동명

     @JsonProperty("preDeposit")
     private String preDeposit; // 종전계약보증금

     @JsonProperty("useRRRight")
     private String useRRRight; // 갱신요구권사용

}
