package com.realestate.rent_insight.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RentPaginationResultDTO<T> {

    private List<T> list;
    private RentPaginationDTO rentPaginationDTO;        // init() 계산끝난 페이징 정보객체

    public RentPaginationResultDTO(List<T> list, RentPaginationDTO rentPaginationDTO) {
        this.list = list;
        this.rentPaginationDTO = rentPaginationDTO;
    }
}
