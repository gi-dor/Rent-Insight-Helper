package com.realestate.rent_insight.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 오피스텔 전월세 실거래 정보를 담음
 * 데이터베이스의 'rent_contract' 테이블과 매핑
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "rent_complete")
public class RentComplete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 시스템 내부 고유 식별자

    // [조회 조건] 계약일
    @Column(nullable = false)
    private LocalDate contractDate; // 계약일 (dealYear, dealMonth, dealDay 조합)

    // [조회 조건] 지역코드
    @Column(nullable = false)
    private String sggCd; // 시군구 코드 (예: 11110)

    // [조회 조건] 시군구명
    @Column(nullable = false)
    private String sggNm; // 시군구명 (예: 종로구)

    // [조회 조건] 법정동명
    @Column(nullable = false)
    private String umdNm; // 법정동명 (예: 숭인동)

    // [조회 조건] 지번
    private String jibun; // 지번 (예: 1425)

    // [조회 조건] 단지명
    @Column(nullable = false)
    private String name; // 오피스텔 단지명 (offiNm)

    // [조회 조건] 보증금
    @Column(nullable = false)
    private Integer deposit; // 보증금 (단위: 만원)

    // [조회 조건] 월세
    @Column(nullable = true)
    private Integer monthlyRent; // 월세 (단위: 만원)

    // [조회조건 X] 종전 보증금
    @Column(nullable = true)
    private Integer preDeposit; // 종전 보증금 (단위: 만원)

    // [조회 조건] 전용면적
    @Column(nullable = false)
    private Double area; // 전용면적(㎡) (excluUseAr)

    // [조회 조건] 건축년도
    private Integer buildYear; // 건축년도

    // [조회 조건] 층
    private Integer floor; // 층

    // [조회 조건] 계약기간
    private String contractTerm; // 계약기간 (예: 24.09~26.09)

    // [조회 조건] 계약구분
    private String contractType; // 계약구분 (예: 신규, 갱신)

    // 갱신요구 사용 유무
    private String useRRRight; // 사용 or " "


    /**
     * @param contractDate 계약일 (API의 dealYear, dealMonth, dealDay)
     * @param sggCd 시군구 코드 (API의 sggCd)
     * @param sggNm 시군구명 (API의 sggNm)
     * @param umdNm 법정동명 (API의 umdNm)
     * @param jibun 지번 (API의 jibun)
     * @param name 오피스텔 단지명 (API의 offiNm)
     * @param deposit 보증금 (API의 deposit)
     * @param monthlyRent 월세 (API의 monthlyRent)
     * @param area 전용면적 (API의 excluUseAr)
     * @param buildYear 건축년도 (API의 buildYear)
     * @param floor 층 (API의 floor)
     * @param contractTerm 계약기간 (API의 contractTerm)
     * @param contractType 계약구분 (API의 contractType)
     */
    @Builder
    public RentComplete(LocalDate contractDate,
                        String sggCd, String sggNm,
                        String umdNm, String jibun,
                        String name, Integer deposit,
                        Integer monthlyRent, Integer preDeposit,
                        Double area, Integer buildYear, Integer floor,
                        String contractTerm, String contractType,
                        String useRRRight) {
        this.contractDate = contractDate;
        this.sggCd = sggCd;
        this.sggNm = sggNm;
        this.umdNm = umdNm;
        this.jibun = jibun;
        this.name = name;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
        this.preDeposit = preDeposit;
        this.area = area;
        this.buildYear = buildYear;
        this.floor = floor;
        this.contractTerm = contractTerm;
        this.contractType = contractType;
        this.useRRRight = useRRRight;
    }
}
