package com.realestate.rent_insight.domain.mapper;

import com.realestate.rent_insight.domain.entity.RentContract;
import com.realestate.rent_insight.dto.RentContractSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * resources/mappers/RentContractMapper.xml 파일에 정의된 SQL 쿼리 연결
 */
@Mapper
public interface RentContractMapper {

    /**
     * @param searchDto 사용자가 입력한 검색 조건들이 담긴 DTO
     * @return 조건에 맞는 여러 계약 목록
     */
    List<RentContract> findByComplexConditions(RentContractSearchDTO searchDto);
}
