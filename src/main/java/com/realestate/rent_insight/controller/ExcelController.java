package com.realestate.rent_insight.controller;

import com.realestate.rent_insight.dto.RentCompleteSearchDTO;
import com.realestate.rent_insight.service.ExcelService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/excel")
public class ExcelController {

    private final ExcelService excelService;

    @GetMapping("/download")
    public void downloadExcel(@ModelAttribute RentCompleteSearchDTO searchDTO ,
                              HttpServletResponse response) throws IOException {
        excelService.createAndStreamExcel(searchDTO, response);
    }
}
