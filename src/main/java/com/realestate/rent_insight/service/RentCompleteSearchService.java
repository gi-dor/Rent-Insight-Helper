package com.realestate.rent_insight.service;

import com.realestate.rent_insight.domain.entity.RentComplete;
import com.realestate.rent_insight.domain.mapper.RentCompleteMapper;
import com.realestate.rent_insight.dto.RentCompleteSearchDTO;
import com.realestate.rent_insight.dto.RentPaginationDTO;
import com.realestate.rent_insight.dto.RentPaginationResultDTO;
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

    public RentPaginationResultDTO<RentComplete> searchRent(RentCompleteSearchDTO rentCompleteSearchDTO, int page) {
        int totalRows = rentCompleteMapper.countByComplexConditions(rentCompleteSearchDTO);
        RentPaginationDTO rentPaginationDTO = new RentPaginationDTO(page,totalRows);

        List<RentComplete> list = rentCompleteMapper.findByComplexConditions(
                rentCompleteSearchDTO,
                rentPaginationDTO.getRows(),
                rentPaginationDTO.getOffset()
        );
        return new RentPaginationResultDTO<>(list, rentPaginationDTO);
    }


}
