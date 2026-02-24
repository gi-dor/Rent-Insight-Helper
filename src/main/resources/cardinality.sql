SELECT
    -- 기본 키 (Primary Key)
    COUNT(DISTINCT id) AS id_cardinality,

    -- 날짜 및 지역 정보
    COUNT(DISTINCT contract_date) AS contract_date_cardinality,
    COUNT(DISTINCT sgg_cd) AS sgg_cd_cardinality,
    COUNT(DISTINCT sgg_nm) AS sgg_nm_cardinality,
    COUNT(DISTINCT umd_nm) AS umd_nm_cardinality,
    COUNT(DISTINCT jibun) AS jibun_cardinality,

    -- 건물 및 계약 정보
    COUNT(DISTINCT name) AS name_cardinality,
    COUNT(DISTINCT deposit) AS deposit_cardinality,
    COUNT(DISTINCT monthly_rent) AS monthly_rent_cardinality,
    COUNT(DISTINCT pre_deposit) AS pre_deposit_cardinality,
    COUNT(DISTINCT area) AS area_cardinality,
    COUNT(DISTINCT build_year) AS build_year_cardinality,
    COUNT(DISTINCT floor) AS floor_cardinality,
    COUNT(DISTINCT contract_term) AS contract_term_cardinality,
    COUNT(DISTINCT contract_type) AS contract_type_cardinality,
    COUNT(DISTINCT userrright) AS use_rrright_cardinality
FROM
    rent_complete;


SELECT
    -- 1. 고유 식별자
    COUNT(DISTINCT id) AS id_cardinality, -- 거의 전체 행 개수와 동일하게 나옴 (매우 높음)

    -- 2. 검색 조건으로 사용될 후보들
    COUNT(DISTINCT sgg_cd) AS sgg_cd_cardinality,          -- 시군구 코드 (중간 정도)
    COUNT(DISTINCT umd_nm) AS umd_nm_cardinality,          -- 법정동 이름 (비교적 높음)
    COUNT(DISTINCT name) AS name_cardinality,            -- 건물 이름 (매우 높음)
    COUNT(DISTINCT area) AS area_cardinality,            -- 전용 면적 (매우 높음)
    COUNT(DISTINCT build_year) AS build_year_cardinality,  -- 건축 년도 (낮음)

    -- 3. 범위 검색에 사용될 값들
    COUNT(DISTINCT deposit) AS deposit_cardinality,      -- 보증금 (매우 높음)
    COUNT(DISTINCT monthly_rent) AS monthly_rent_cardinality, -- 월세 (높음)

    -- 4. 카테고리성 정보 (카디널리티가 낮을 것으로 예상)
    COUNT(DISTINCT contract_type) AS contract_type_cardinality -- 계약 종류 (매우 낮음, 예: '신규', '갱신')
FROM
    rent_complete;