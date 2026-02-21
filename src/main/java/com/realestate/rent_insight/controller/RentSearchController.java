package com.realestate.rent_insight.controller;

import com.realestate.rent_insight.domain.entity.DataUpdateLog;
import com.realestate.rent_insight.domain.entity.Region;
import com.realestate.rent_insight.domain.entity.RentComplete;
import com.realestate.rent_insight.domain.repository.DataUpdateLogRepository;
import com.realestate.rent_insight.dto.RentCompleteSearchDTO;
import com.realestate.rent_insight.service.RegionService;//import com.realestate.rent_insight.service.RentSearchService;

import com.realestate.rent_insight.service.RentCompleteSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rent")
public class RentSearchController {

    private final RentCompleteSearchService rentCompleteSearchService;
    private final RegionService regionService;
    private final DataUpdateLogRepository dataUpdateLogRepository;

    @GetMapping("/search")
    public String searchForm(@ModelAttribute("searchDto") RentCompleteSearchDTO searchDto, Model model) {
        // 1. RegionService를 사용하여 시군구 목록(코드, 이름 포함)을 조회
        List<Region> sigunguList = regionService.getSigunguList();
        model.addAttribute("sigunguList", sigunguList);

        // 2. 서비스 계층을 호출하여 검색 조건에 맞는 데이터를 조회
        List<RentComplete> searchResult = rentCompleteSearchService.searchRent(searchDto);
        model.addAttribute("rentSearchResult", searchResult);

        // 3. SUCCESS 로그 조회
        Optional<DataUpdateLog> lastLog = dataUpdateLogRepository.findFirstByStatusOrderByIdDesc("SUCCESS");
        if(lastLog.isPresent()) {
            DataUpdateLog log = lastLog.get();
            model.addAttribute("lastLog", log.getCompletionTime());
        }

        // 4. 뷰
        return "rent/search";
    }

}
