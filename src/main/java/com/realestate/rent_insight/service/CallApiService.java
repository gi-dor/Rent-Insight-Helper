package com.realestate.rent_insight.service;

import com.realestate.rent_insight.domain.entity.Region;
import com.realestate.rent_insight.domain.entity.RentComplete;
import com.realestate.rent_insight.domain.repository.RentCompleteRepository;
import com.realestate.rent_insight.dto.ApiRentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.dataformat.xml.XmlMapper;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional  // 하다가 뻑 나면 안되니깐
public class CallApiService {

    /**
     * 공공데이터 API 호출 하려면 이렇게 필요함
     * LAWD_CD : 코드 10자리중 5자리만 필요함;
     * DEAL_YMD : 202512  이렇게 6자리 필요
     * serviceKey : 나중에 application.yml에서 API 키와 URL을 주입받을 예정
     */

    private final RegionService regionService;
    private final RentCompleteRepository rentCompleteRepository;

    @Value("${api.serviceKey}")
    private String serviceKey;

    @Value("${api.baseUrl}")
    private String baseUrl;

    public void callDataSeoul() {

        // 데이터 부르기 전에 준비 재료들
        // 1. DEAL_YMD 부터
        String dealYmd = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        log.info("실거래가 데이터 수집을 시작합니다 대상 : 월 {} ",dealYmd);

        // 2. LAWD_CD 코드 5자리 가져오기 - DB에 있는 25개 ㅇㅇ구 Ex_) 종로구 용산구 각자 코드 10자리 가져와서 자르기
        List<Region> regionList = regionService.getSigunguList();
        log.info("{} 개 지역의 데이터를 수집 : " , regionList.size());

        // 3. 가져 오려는 연도의 월의 해당 자료 삭제 - 매일 가져올껀데 그때마다 중복 비교해서 하면 시간 오래걸릴거같음
        // 그래서 걍 삭제 할꺼임 ㅋㅋ  Ex )  2026.02.21 에 데이터 가져왔느데 2026.02.22에 또 가져오는데 중복 비교하기싫음
        log.info("기존 보유한 월{} 데이터 삭제 ",dealYmd);
        rentCompleteRepository.deleteAllByDealYmd(dealYmd);

        // 호출위해서 RestTemplate- 외부와 통신 // 걍 API 호출도구라고 생각하면됨
        // 비동기 방식이 아니라 응답을 받을때 까지 기다리는 동기 방식이라고함
        /** https://velog.io/@soosungp33/%EC%8A%A4%ED%94%84%EB%A7%81-RestTemplate-%EC%A0%95%EB%A6%AC%EC%9A%94%EC%B2%AD-%ED%95%A8
         * RestTemplate
         * Spring 3부터 지원, REST API 호출이후 응답을 받을 때까지 기다리는 동기 방식
         * AsyncRestTemplate
         * Spring 4에 추가된 비동기 RestTemplate이다.
         * WebClient
         * Spring 5에 추가된 논블럭, 리엑티브 웹 클라이언트로 동기, 비동기 방식을 지원한다.
         */
        RestTemplate restTemplate = new RestTemplate();

        // XML 파싱할 번역기 라는데 ? - ㅋㅋ springboot4버전에 맞는 jackson 찾는데 시간 잡아먹었네 아오
        ObjectMapper objectMapper = new XmlMapper();

        // API 호출해서 데이터 가져오기 + 저장
        for(Region sigungu : regionList) {
            try {
                // LAWD_CD 잘라내기 앞 5자리
                String lawdCd = sigungu.getCode().substring(0,5);
                log.info(">>>>> 데이터 수집중...  시군구명 : {} , 시군구 5자리 : {}" ,sigungu.getName() , lawdCd);

                // 호출 _ UriComponentsBuilder 완전한 요청URL 만들기
                URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("LAWD_CD", lawdCd)
                        .queryParam("DEAL_YMD", dealYmd)
                        .queryParam("numOfRows", "1000") // 데이터를 충분히 가져오기 위해 개수 설정
                        .queryParam("_type", "xml")
                        .build(true)
                        .toUri();

                // 호출 해서 Response 문자열로 받기
                String xmlResponse = restTemplate.getForObject(uri, String.class);
                log.info("API Response for {}: {}", lawdCd, xmlResponse);

                // 객체로 변환
                JsonNode parentNode = objectMapper.readTree(xmlResponse);

                JsonNode childNode = parentNode.path("body").path("items").path("item");

                // 최종으로 담을 결과물
                List<ApiRentDTO> dtoList;

                // 데이터 유무 확인 및 DTO 리스트로 변환
                if(childNode.isMissingNode() || childNode.isNull()) {
                    log.info(">> {} ({}) 데이터 없음. 건너뜁니다.", sigungu.getName(), lawdCd);
                    dtoList = Collections.emptyList(); // 빈 리스트로 초기화
                    continue;
                } else if (childNode.isArray()) {
                    // item이 여러 개일 경우 (배열)
                    dtoList = objectMapper.convertValue(childNode, new TypeReference<List<ApiRentDTO>>() {});
                } else {
                    // item이 하나만 있을 경우 (객체) - API가 가끔 이렇게 응답함
                    ApiRentDTO dto = objectMapper.convertValue(childNode, ApiRentDTO.class);
                    dtoList = Collections.singletonList(dto);
                }

                // 변환된 DTO 리스트의 내용 확인
                if (!dtoList.isEmpty()) {
                    log.info(">> 파싱 성공: {}건", dtoList.size());
                    log.info(">> 첫 번째 데이터 샘플: {}", dtoList.get(0)); // Lombok의 @Data가 만들어준 toString()

                    // DTO 리스트들을 엔티티 리스트로 바꿈
                    List<RentComplete> rentCompleteEntity = new ArrayList<>();

                    for(ApiRentDTO dto : dtoList) {
                        RentComplete entity = this.apiRentDtoConversionRentComplete(dto);
                        rentCompleteEntity.add(entity);
                    }

                    rentCompleteRepository.saveAll(rentCompleteEntity);
                    log.info(">> {} 건의 데이터를 DB에 저장했습니다.", rentCompleteEntity.size());
                } else {
                    // 이 경우는 위에서 continue 처리되어 사실상 도달하기 어려움
                    log.info(">> 파싱된 데이터가 없습니다.");
                }



            } catch (Exception e) {
                log.error(">>>> 오류 발생 {} , {}: ", sigungu.getName() , e.getMessage());
                continue;
            }
        }
        log.info("======  데이터 수집 완료. {}  ========", LocalDate.now());
    }


    private RentComplete apiRentDtoConversionRentComplete(ApiRentDTO apiRentDTO) {
        // 보증금 , 월세 , 종전(이전)보증금이 10,000 요따위로 들어와서 쉼표 제거하고 공백없어서 숫자로 바꿈
        int deposit = 0;
        try {
            deposit = Integer.parseInt(apiRentDTO.getDeposit().replace(",","").trim());
        } catch (Exception e) {
            // 숫자 변환 실패 시, 기본값 0을 사용하고 별도의 처리는 하지 않음
        }

        // 월세
        int monthlyRent = 0;
        try {
            monthlyRent = Integer.parseInt(apiRentDTO.getMonthlyRent().replace(",", "").trim());
        } catch (Exception e) {
            // 숫자 변환 실패 시, 기본값 0을 사용하고 별도의 처리는 하지 않음
        }

        // 이전 보증금
        int preDeposit = 0;
        try {
            // preDeposit 필드가 null이 아닐 경우에만 처리
            if (apiRentDTO.getPreDeposit() != null && !apiRentDTO.getPreDeposit().isBlank()) {
                preDeposit = Integer.parseInt(apiRentDTO.getPreDeposit().replace(",", "").trim());
            }
        } catch (Exception e) {
            // 숫자 변환 실패 시, 기본값 0을 사용하고 별도의 처리는 하지 않음
        }

        // dealDay dealMonth dealYear
        LocalDate contractDate = null;
        if (apiRentDTO.getDealYear() != null && apiRentDTO.getDealMonth() != null && apiRentDTO.getDealDay() != null) {
            contractDate = LocalDate.of(apiRentDTO.getDealYear(), apiRentDTO.getDealMonth(), apiRentDTO.getDealDay());
        }

        return RentComplete.builder()
                .contractDate(contractDate)
                .sggCd(apiRentDTO.getSggCd())
                .sggNm(apiRentDTO.getSggNm())
                .umdNm(apiRentDTO.getUmdNm())
                .jibun(apiRentDTO.getJibun())
                .name(apiRentDTO.getName())
                .deposit(deposit)
                .monthlyRent(monthlyRent)
                .area(apiRentDTO.getArea())
                .buildYear(apiRentDTO.getBuildYear())
                .floor(apiRentDTO.getFloor())
                .contractTerm(apiRentDTO.getContractTerm())
                .contractType(apiRentDTO.getContractType())
                .preDeposit(preDeposit) // [추가] 종전 계약 보증금 필드 매핑
                .useRRRight(apiRentDTO.getUseRRRight()) // [추가] 갱신요구권 사용여부 필드 매핑
                .build();
    }
}
