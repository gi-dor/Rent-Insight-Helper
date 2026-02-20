package com.realestate.rent_insight.domain.repository;

import com.realestate.rent_insight.domain.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Region 엔티티에 대한 데이터베이스 작업처리
 * 검색페이지 검색 바에서 사용된다
 */
public interface RegionRepository extends JpaRepository<Region, String> {

    /**
     * "SELECT * FROM region WHERE parent_code = ?"
     * 서울특별시에 속한 모든 ㅇㅇ구 코드 다 가져오기
     *
     * @param parentCode ("1100000000" - 서울특별시 코드)
     * @return 해당 parentCode를 가진 Region 객체들의 리스트
     */
    List<Region> findByParentCode(String parentCode);


}
