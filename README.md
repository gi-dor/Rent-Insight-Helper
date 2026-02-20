# 🏠 Rent Insight - 전월세 실거래 기반 시세 조회 및 분석 서비스

## 기획 배경
서울 전세 매물을 구하는 과정에서, 대다수의 부동산 플랫폼에  `'허위 매물'` 과 `부정확한 시세 정보` 로 인해 매번 부동산에 연락했습니다 <br>
실제로 거래된 데이터를 기준으로 `해당 지역의 평당 전월세 가격이 얼마인지 정확히 알 수있는 방법이 없나 ?"` 라는 개인적인 필요에 의해 이 프로젝트가 출발하게 되었습니다.

## 📌 프로젝트 개요

Rent Insight는 국토교통부의 공공데이터를 기반으로 전월세 실거래 완료 데이터를 자동으로 수집·저장하고, 조건별 시세 조회 및 분석 기능을 제공하는 백엔드 중심의 웹 애플리케이션입니다.<br>
단순한 게시판 형태의 CRUD를 넘어서, 외부 API 연동을 통한 데이터 수집 자동화, 사용자 편의를 위한 대량 데이터 조회 최적화를 목표로 개발되었습니다.


## 🎯 프로젝트 목적
[비즈니스/서비스 측면]
- 100% 신뢰 가능한 데이터 : 허위 매물이 아닌, 검증된 공공 실거래 데이터만을 제공 
- 직관적인 시세 파악 : 평형, 보증금, 월세 등 세부 조건에 따른 지역별 실제 체감 물가 확인

[기술적 측면]
- 외부 데이터 연동: 실무에서 자주 사용되는 공공데이터 수집 및 처리 구조 경험
- 자동화 설계 : Spring Scheduler 기반 정기적인 배치성 수집 작업(Daily Batch) 설계
- 효율적인 ORM & SQL : 단순 CRUD는 JPA로, 복잡한 동적 검색 조건은 MyBatis로 병행 사용
- 조회 성능 최적화 : 

## 🛠 기술 스택

### Backend
- **Language**: `Java 17`
- **Framework**: `Spring Boot 3.x`

### Frontend
- **JavaScript Library**: `jQuery` (동적 UI 구현)

### Database
- **RDBMS**: `MySQL`
- **ORM**: `JPA (Spring Data JPA)` - 단일 엔티티 CRUD용
- **SQL Mapper**: `MyBatis` - 복합 조건 동적 쿼리 및 통계용

### Infrastructure
- **Scheduler**: `Spring Scheduler` (데이터 수집 자동화)

## 🧩 주요 기능

### 1. 회원 관리
- **회원 가입**: 이메일, 비밀번호, 이름, 닉네임 입력
- **유효성 검사**: 이름, 닉네임, 이메일, 비밀번호 형식 및 중복 체크
- **권한 관리**: 일반 사용자(USER) 및 관리자(ADMIN) 역할 구분

### 2. 전월세 실거래 데이터 수집
- **공공데이터 API 연동**: 국토교통부 오피스텔 전월세 실거래가 API 호출
- **XML 데이터 처리**: `RestTemplate`과 `XmlMapper`를 이용해 XML 응답을 Java 객체로 변환
- **데이터 정제 및 저장**: 수집된 데이터를 `RentComplete` 엔티티로 변환하여 DB에 저장
- **초기 데이터 적재**: `data.sql`을 이용해 서울시 행정구역(시군구, 법정동) 기준 정보 DB 적재

### 3. 전월세 시세 조회
- **복합 조건 검색**: MyBatis 동적 쿼리를 활용한 다중 조건 검색 기능
    - **검색 조건**: 지역(시군구, 법정동), 건물명, 보증금 범위, 월세 범위
    - **정렬**: 최신 계약일 순으로 자동 정렬
- **연동 드롭다운 UI**: jQuery와 비동기 API 통신(`Ajax`)을 이용한 시군구-법정동 연동 드롭다운 구현
- **페이징 처리 (구현 예정)**: 대량의 검색 결과를 페이지 단위로 나누어 보여주는 기능

## 📂 프로젝트 구조
```
src/main/java/com/realestate/rent_insight
├── application
│   └── service         # 비즈니스 로직 (트랜잭션 관리)
├── controller          # 웹 요청 처리
│   └── dto             # 데이터 전송 객체
│       └── xml         # XML 파싱용 DTO
├── domain
│   ├── constant        # 상수 및 Enum (MemberRole 등)
│   ├── entity          # JPA 엔티티
│   ├── mapper          # MyBatis 매퍼 인터페이스
│   └── repository      # 데이터 접근 계층 (JPA Repository)
├── infrastructure      # 외부 시스템 연동 (Scheduler 등)
└── service             # 서비스 계층
```

## 겪었던 문제 및 해결 과정 (Troubleshooting)

### 1. 대량의 초기 데이터(행정구역) 적재 문제
- **문제 현상**: 프로젝트 기능 구현에 앞서, 서울시의 모든 시군구 및 법정동 데이터를 `region` 테이블에 미리 넣어두어야 했습니다. 하지만 수백 개가 넘는 데이터를 손으로 `INSERT` 구문을 작성하는 것은 비효율적이고 오류 발생 가능성이 높았습니다.
- **원인 분석**: 단순 반복적인 데이터 입력 작업을 자동화하여, 개발 생산성을 높이고 데이터의 정확성을 보장할 필요가 있었습니다.
- **해결 과정**:
    1. 행정안전부에서 제공하는 '법정동 코드 전체자료' 원본 파일(CSV)을 다운로드했습니다.
    2. 일회성 유틸리티 클래스인 `DataSqlGenerator.java`를 작성했습니다.
    3. 이 프로그램은 CSV 파일을 한 줄씩 읽어, 필요한 '시군구' 및 '법정동' 데이터만 추출한 후, `INSERT INTO region ...` 형식의 SQL 구문으로 변환하여 콘솔에 출력하도록 구현했습니다.
    4. 콘솔에 출력된 수백 개의 SQL 구문 전체를 복사하여 `data.sql` 파일에 저장했습니다.
    5. 이를 통해, Spring Boot가 실행될 때마다 정확한 행정구역 데이터가 자동으로 DB에 적재되는 프로세스를 구축했습니다.

### 2. `data.sql` 자동 실행 문제
- **문제 현상**: `ddl-auto: update` 설정에서, 애플리케이션 재시작 시 `region` 테이블의 초기 데이터가 `INSERT`되지 않거나, `Duplicate entry` 에러 발생.
- **원인 분석**:
    1. Spring Boot는 MySQL과 같은 운영 DB에서는 데이터 안정성을 위해 `data.sql`을 자동으로 실행하지 않는 것이 기본 정책.
    2. `ddl-auto: update`와 `data.sql`의 실행 순서가 꼬여, 데이터가 있거나 없는 비일관적인 상태가 발생.
- **해결 과정**:
    1. `application.yaml`에 `spring.sql.init.mode: always`를 추가하여, DB 종류와 상관없이 항상 `data.sql`이 실행되도록 강제.
    2. `spring.jpa.defer-datasource-initialization: true`를 추가하여, JPA의 테이블 생성 작업이 완료된 후에 `data.sql`이 실행되도록 순서를 보장.
    3. 개발 환경의 일관성을 위해 `ddl-auto: create`로 변경하여, 매 실행 시 DB를 깨끗하게 초기화하고 `data.sql`로 데이터를 다시 채우는 전략 채택.

### 3. API 호출 관련 문제
- **문제 현상**:
    1. `UriComponentsBuilder.fromHttpUrl(...)` 사용 시 '메서드를 찾을 수 없다'는 컴파일 에러 발생.
    2. API 호출 시 401 (Unauthorized) 에러 응답.
- **원인 분석**:
    1. `fromHttpUrl`은 특정 버전 이상의 Spring에서만 제공되는 메서드로, 프로젝트의 Spring 버전과 호환되지 않았음.
    2. API 서비스 키에 포함된 특수문자(`=`, `+` 등)가 URL 파라미터로 사용될 때, 인코딩 문제로 인해 서버에서 인증 실패.
- **해결 과정**:
    1. `fromHttpUrl` 대신, 하위 버전과 호환성이 높은 `fromUriString` 메서드로 교체.
    2. `UriComponentsBuilder`의 빌드 방식을 `.build(true)`에서 `.build().encode()`로 변경하여, URL의 모든 구성요소가 안전하게 인코딩되도록 수정.

### 4. API 응답 파싱(Parsing) 에러
- **문제 현상**: `JsonParseException`, `NullPointerException`, `DateTimeException` 등 다양한 파싱 관련 에러 발생.
- **원인 분석**:
    1. **`JsonParseException`**: API 서버가 기본적으로 JSON으로 응답하는데, 클라이언트(`XmlMapper`)는 XML을 기대하여 형식 불일치 발생.
    2. **`NullPointerException`**: API가 보내준 데이터 중 일부 아이템의 특정 필드(예: `<보증금>`)가 비어있어, `null` 값에 `.replace()`를 시도하며 에러 발생.
    3. **`DateTimeException`**: 일부 아이템의 날짜 필드(예: `<월>`)가 비어있어, `int`의 기본값인 `0`으로 파싱된 후 `LocalDate.of()`에서 유효하지 않은 값으로 처리되어 에러 발생.
    4. **`@JsonProperty` 매핑 오류**: DTO의 `@JsonProperty`에 실제 XML 태그 이름(영어)이 아닌, 한글 이름을 잘못 명시하여 데이터가 필드에 제대로 매핑되지 않음.
- **해결 과정**:
    1. API 호출 URL에 `_type=xml` 파라미터를 추가하여, 응답 형식을 XML로 명시.
    2. 데이터 변환 로직(`mapItemToContract`)에 `null` 체크 등 방어 코드를 추가하여, 불완전한 데이터가 들어와도 프로그램이 멈추지 않고 해당 아이템을 건너뛰도록 수정.
    3. DTO의 모든 `@JsonProperty` 어노테이션을 제거하여, Jackson이 XML 태그 이름과 DTO 필드 이름을 기준으로 자동으로 매핑하도록 수정.
