package com.realestate.rent_insight.domain.mapper;

import com.realestate.rent_insight.domain.entity.RentComplete;
import com.realestate.rent_insight.dto.RentCompleteSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * resources/mappers/RentCompleteMapper.xml 파일에 정의된 SQL 쿼리 연결
 */
@Mapper
public interface RentCompleteMapper {

    /**
     * @param searchDto 사용자가 입력한 검색 조건들이 담긴 DTO
     * @return 조건에 맞는 여러 계약 목록
     */
    List<RentComplete> findByComplexConditions(RentCompleteSearchDTO searchDto);
}
