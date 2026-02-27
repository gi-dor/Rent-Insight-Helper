package com.realestate.rent_insight.domain.mapper;

import com.realestate.rent_insight.domain.entity.RentComplete;
import com.realestate.rent_insight.dto.RentCompleteSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * resources/mappers/RentCompleteMapper.xml 파일에 정의된 SQL 쿼리 연결
 */
@Mapper
public interface RentCompleteMapper {

    List<RentComplete> findByComplexConditions(
            @Param("searchDto") RentCompleteSearchDTO searchDto,
            @Param("limit") int limit,
            @Param("offset") int offset);

    // 페이징 없이 조건으루 싹 검색
    List<RentComplete> findAllByComplexConditions(@Param("searchDto") RentCompleteSearchDTO searchDto);

    int countByComplexConditions(@Param("searchDto") RentCompleteSearchDTO searchDto);
}

