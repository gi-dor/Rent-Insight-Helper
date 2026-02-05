package com.realestate.rent_insight.controller;

import com.realestate.rent_insight.domain.entity.Region;
import com.realestate.rent_insight.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 검색 페이지에 검색조건 드롭다운 리스트 지역 정보(시군구, 법정동)
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;

    /**
     * 서울특별시의 모든 ㅇㅇ구 or ㅇㅇ시 목록을 반환 API
     * GET /api/regions/sigungu
     * @return 시군구 목록 (JSON 형식)
     */
    @GetMapping("/sigungu")
    public ResponseEntity<List<Region>> getSigunguList() {
        List<Region> sigunguList = regionService.getSigunguList();
        return ResponseEntity.ok(sigunguList);
    }

    /**
     * 특정 서울특별시 ㅇㅇ구에 속한 ㅇㅇ동 목록을 반환하는 API
     * GET /api/regions/dong?sigunguCode=1111000000
     * @param sigunguCode 조회할 시군구의 법정동 코드
     * @return 법정동 목록 (JSON 형식)
     */
    @GetMapping("/dong")
    public ResponseEntity<List<Region>> getDongList(@RequestParam("sigunguCode") String sigunguCode) {
        List<Region> dongList = regionService.getDongList(sigunguCode);
        return ResponseEntity.ok(dongList);
    }
}
