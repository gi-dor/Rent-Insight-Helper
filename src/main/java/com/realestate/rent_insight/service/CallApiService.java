package com.realestate.rent_insight.service;

import com.realestate.rent_insight.domain.entity.DataUpdateLog;
import com.realestate.rent_insight.domain.entity.RentComplete;
import com.realestate.rent_insight.domain.repository.DataUpdateLogRepository;
import com.realestate.rent_insight.domain.repository.RentCompleteRepository;
import com.realestate.rent_insight.dto.ApiCountDTO;
import com.realestate.rent_insight.dto.ApiProperties;
import com.realestate.rent_insight.dto.ApiRentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class CallApiService {

    /**
     * 공공데이터 API 호출 하려면 이렇게 필요함
     * LAWD_CD : 코드 10자리중 5자리만 필요함;
     * DEAL_YMD : 202512  이렇게 6자리 필요
     * serviceKey : 나중에 application.yml에서 API 키와 URL을 주입받을 예정
     */

    private final RegionService regionService;
    private final RentCompleteRepository rentCompleteRepository;
    private final DataUpdateLogRepository dataUpdateLogRepository;
    private final ApiProperties apiProperties;  // apiProperties 클래스에 2개의 API호출 방법
    private final CallApiDBLogicService callApiDBLogicService;

    // 두개의 API로 인해 ApiProperties 만듬 이로 인해 하단 @Value 는 실직함
//    @Value("${api.serviceKey}")
//    private String serviceKey;
//
//    @Value("${api.baseUrl}")
//    private String baseUrl;

    public void apiKey() {
        String serviceKey = apiProperties.getServiceKey();
        String officetelUrl = apiProperties.getBaseUrl().getOfficetel();
        String multiHouseUrl = apiProperties.getBaseUrl().getMultiHouse();
    }



    @Scheduled(cron = "0 00 19 * * MON-SAT")
    public void scheduleCallDataSeoul(){
        String dealYmd = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        log.info("스케줄러가 실행됩니다. 대상 월: {}", dealYmd);
        startDataSuccessFailProcess(dealYmd);
    }






    public void startDataSuccessFailProcess(String dealYmd ) {
        log.info("실거래가 데이터 수집을 시작합니다. 대상 월: {}", dealYmd);
        try {
            // 실제 데이터 다루는 로직 호출
            ApiCountDTO apiCountDTOResult = callApiDBLogicService.callDataProcess(dealYmd);

            log.info("======  데이터 수집 완료. 진행날짜 : {}  ========", LocalDate.now());
            log.info("총 삭제된 데이터: {}건", apiCountDTOResult.getTotalDeletedCount());
            log.info("총 저장된 데이터: {}건", apiCountDTOResult.getTotalInsertedCount());


            DataUpdateLog successLog = DataUpdateLog.builder()
                    .completionTime(LocalDateTime.now())
                    .status("SUCCESS")
                    .totalDeletedCount( apiCountDTOResult.getTotalDeletedCount())
                    .totalInsertedCount(apiCountDTOResult.getTotalInsertedCount())
                    .build();
            dataUpdateLogRepository.save(successLog);
        } catch (Exception e ) {
            log.error(">>>> 데이터 수집 배치 작업 실패. 전체 작업이 롤백되었습니다. 원인: {}", e.getMessage());
            DataUpdateLog errorLog = DataUpdateLog.builder()
                    .completionTime(LocalDateTime.now())
                    .status("FAIL")
                    .build();
            dataUpdateLogRepository.save(errorLog);
        }
    }



}
