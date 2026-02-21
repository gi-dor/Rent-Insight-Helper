package com.realestate.rent_insight.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * CallApiService의 통합 테스트 클래스입니다.
 * @SpringBootTest 어노테이션을 사용하여, 실제 Spring 컨테이너를 실행하고
 * 모든 Bean들을 주입받아 테스트를 진행합니다.
 */
@SpringBootTest
class CallApiServiceTest {

    // 테스트할 대상인 CallApiService를 Spring 컨테이너로부터 주입받습니다.
    @Autowired
    private CallApiService callApiService;

    @Test
    @DisplayName("서울시 전체 데이터 수집 및 파싱 테스트")
    void callDataSeoulTest() {
        // [실행]
        // callApiService의 callDataSeoul 메서드를 직접 호출합니다.
        callApiService.callDataSeoul();

        // [검증]
        // 이 테스트의 성공 여부는, 실행 후 콘솔 창에 출력되는 로그를 보고 판단합니다.
        // "데이터 파싱 성공: X건" 과 "첫 번째 데이터 샘플: ..." 로그가
        // 서울시 25개 구에 대해 정상적으로 출력되는지 확인합니다.
        // 에러 로그가 발생하지 않으면 성공입니다.
    }
}
