package com.realestate.rent_insight.service;

import com.realestate.rent_insight.domain.entity.Region;
import com.realestate.rent_insight.domain.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 지역 정보(시군구, 법정동)를 조회
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionService {

    private final RegionRepository regionRepository;

    // 서울특별시의 법정동 코드 (상수)
    private static final String SEOUL_CODE = "1100000000";

    /**
     * 서울특별시의 모든 ㅇㅇ구 목록 조회
     * @return 시군구 Region 객체 리스트
     */
    public List<Region> getSigunguList() {
        // RegionRepository를 사용하여 parentCode가 서울시인 모든 목록 조회 때려버림
        return regionRepository.findByParentCode(SEOUL_CODE);
    }

    /**
     * 특정 시군구에 속한 모든 ㅇㅇ동 목록을 조회
     * @param sigunguCode 조회할 ㅇㅇ구의 ㅇㅇ동 코드 (예: "1111000000" - 종로구)
     * @return 해당 시군구에 속한 법정동 Region
     */
    public List<Region> getDongList(String sigunguCode) {
        // findByParentCode 메서드를 재사용하여, 이번에는 parentCode로 시군구 코드를 넘겨줌
        return regionRepository.findByParentCode(sigunguCode);
    }
}
