package com.realestate.rent_insight.service;

import com.realestate.rent_insight.domain.entity.RentComplete;
import com.realestate.rent_insight.domain.mapper.RentCompleteMapper;
import com.realestate.rent_insight.dto.RentCompleteSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 전월세 계약 정보를 검색기능
 * MyBatis 매퍼를 사용하여 복합적인 조건의 검색
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 기본적으로 읽기 전용 트랜잭션으로 실행
public class RentCompleteSearchService {

    private final RentCompleteMapper rentCompleteMapper;

    /**
     * 사용자가 입력한 여러 검색 조건에 따라 전월세 계약 목록조회
     *
     * @param searchDto 사용자가 검색 폼에서 입력한 조건들을 담고 있는 DTO 객체 - 조건 많음
     * @return 검색 조건에 맞는 RentContract 엔티티들의 리스트
     */
    public List<RentComplete> searchRent(RentCompleteSearchDTO searchDto) {
        // RentContractMapper의 findByComplexConditions
        // searchDto에 담긴 조건들로 데이터베이스에서 계약 목록 조회
        //  RentCompleteMapper.xml 파일에 조건 여러개 검색
        return rentCompleteMapper.findByComplexConditions(searchDto);
    }
}
