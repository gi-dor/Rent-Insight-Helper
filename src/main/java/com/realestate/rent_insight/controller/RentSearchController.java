package com.realestate.rent_insight.controller;

import com.realestate.rent_insight.domain.entity.Region;
import com.realestate.rent_insight.domain.entity.RentContract;
import com.realestate.rent_insight.dto.RentContractSearchDTO;
import com.realestate.rent_insight.service.RegionService;
//import com.realestate.rent_insight.service.RentSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rent")
public class RentSearchController {

//    private final RentSearchService rentSearchService;
    private final RegionService regionService;

    @GetMapping("/search")
    public String searchForm(@ModelAttribute("searchDto") RentContractSearchDTO searchDto, Model model) {
        // 1. RegionService를 사용하여 시군구 목록(코드, 이름 포함)을 조회
        List<Region> sigunguList = regionService.getSigunguList();
        model.addAttribute("sigunguList", sigunguList);

        // 2. 서비스 계층을 호출하여 검색 조건에 맞는 데이터를 조회
//        List<RentContract> searchResult = rentSearchService.searchRentContracts(searchDto);
//        model.addAttribute("contracts", searchResult);

        // 3. 뷰
        return "rent/search";
    }

}
