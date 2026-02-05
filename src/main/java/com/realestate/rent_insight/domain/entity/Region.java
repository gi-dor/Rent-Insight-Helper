package com.realestate.rent_insight.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 행정구역(ㅇㅇ시,ㅇㅇ동) 기준 정보를 담는 엔티티
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "region")
public class Region {

    /**
     * 법정동 코드 (10자리)
     */
    @Id
    private String code;

    /**
     * 지역명 (예: "종로구", "청운동")
     */
    private String name;

    /**
     * 상위 지역의 법정동 코드
     * "청운동"의 parentCode는 "종로구"의 코드가 됨
     * 최상위 지역(시군구)의 경우  null 될수 있음
     */
    private String parentCode;
}
