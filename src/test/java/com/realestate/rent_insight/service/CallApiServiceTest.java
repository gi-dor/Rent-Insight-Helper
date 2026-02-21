package com.realestate.rent_insight.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * CallApiService의 통합 테스트 클래스입니다.
 */
@SpringBootTest
class CallApiServiceTest {

    @Autowired
    private CallApiService callApiService;

    /**
     * 스케줄러가 호출하는 '이번 달' 데이터 수집 기능을 테스트합니다.
     */
    @Test
    @DisplayName("이번 달 데이터 수집 테스트")
    void callDataSeoulTest() {
        callApiService.callDataSeoul();
    }

    /**
     * [일회성 데이터 수집 실행기]
     * 2024년, 2025년 전체 과거 데이터를 수집하기 위한 테스트입니다.
     * 데이터 수집에 시간이 오래 걸리므로, 필요할 때만 수동으로 실행해야 합니다.
     * 평소에는 @Disabled 어노테이션으로 비활성화해두는 것이 좋습니다.
     */
    @Test
    @Disabled // 평소에는 실행되지 않도록 비활성화 실행하고 싶을 때 이 어노테이션을 주석 처리
    @DisplayName("2024년, 2025년 전체 데이터 수집")
    void collectAllPastDataTest() {
        // 수집할 년도를 배열로 정의
        int[] years = {2024, 2025};

        for (int year : years) {
            for (int month = 1; month <= 12; month++) {
                // "202401", "202402", ..., "202512" 형식의 문자열 생성
                String dealYmd = String.format("%d%02d", year, month);
                
                // 특정 년월의 데이터 수집 실행
                callApiService.callDataSeoulPast(dealYmd);
            }
        }
    }


    @Test
    @Disabled
    @DisplayName("2024년 1월 데이터 삭제 및 재수집 검증")
    void reCollectSpecificMonthTest() {
        String targetDealYmd = "202401";

        System.out.println("\n\n===== 첫 번째 수집 시작: " + targetDealYmd + " =====\n");
        callApiService.callDataSeoulPast(targetDealYmd);
        System.out.println("\n===== 첫 번째 수집 완료: " + targetDealYmd + " =====\n\n");


    }
}
