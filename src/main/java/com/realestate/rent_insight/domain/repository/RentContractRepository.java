package com.realestate.rent_insight.domain.repository;

import com.realestate.rent_insight.domain.entity.RentContract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentContractRepository extends JpaRepository<RentContract, Long> {

    /**
     * 시군구명(sggNm)과 법정동명(umdNm)을 '모두' 만족하는 계약 목록을 조회합니다.
     * JPA가 'And' 키워드를 보고 "WHERE sgg_nm = ? AND umd_nm = ?" 와 같은 SQL을 자동으로 생성합니다.
     * 이렇게 하면 다른 구에 있는 동일한 이름의 동이 검색되는 것을 막을 수 있습니다.
     * @param sggNm 조회할 시군구명 (예: "강남구")
     * @param umdNm 조회할 법정동명 (예: "역삼동")
     * @return 해당 지역의 계약 목록
     */
    List<RentContract> findBySggNmAndUmdNm(String sggNm, String umdNm);

    /**
     * [단일 조건] 단지명(name)이 포함된 계약 목록을 조회합니다.
     * 'Containing' 키워드는 SQL의 "LIKE '%...%'" 구문과 동일하게 동작합니다.
     * "SELECT * FROM rent_contract WHERE name LIKE ?" 와 같은 SQL을 생성합니다.
     * @param name 검색할 단지명의 일부 또는 전체
     * @return 해당 단지명이 포함된 계약 목록
     */
    List<RentContract> findByNameContaining(String name);

    /**
     * [단일 조건] 보증금(deposit)이 특정 범위(min ~ max)에 있는 계약 목록을 조회합니다.
     * 'Between' 키워드는 SQL의 "BETWEEN ? AND ?" 구문과 동일하게 동작합니다.
     * "SELECT * FROM rent_contract WHERE deposit BETWEEN ? AND ?" 와 같은 SQL을 생성합니다.
     * @param minDeposit 최소 보증금
     * @param maxDeposit 최대 보증금
     * @return 해당 보증금 범위의 계약 목록
     */
    List<RentContract> findByDepositBetween(Integer minDeposit, Integer maxDeposit);

    /**
     * [단일 조건] 월세(monthlyRent)가 특정 범위(min ~ max)에 있는 계약 목록을 조회합니다.
     * "SELECT * FROM rent_contract WHERE monthly_rent BETWEEN ? AND ?" 와 같은 SQL을 생성합니다.
     * @param minRent 최소 월세
     * @param maxRent 최대 월세
     * @return 해당 월세 범위의 계약 목록
     */
    List<RentContract> findByMonthlyRentBetween(Integer minRent, Integer maxRent);

}
