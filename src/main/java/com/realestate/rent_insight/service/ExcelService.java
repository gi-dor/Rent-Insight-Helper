package com.realestate.rent_insight.service;

import com.realestate.rent_insight.domain.entity.RentComplete;
import com.realestate.rent_insight.domain.mapper.RentCompleteMapper;
import com.realestate.rent_insight.dto.RentCompleteSearchDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelService {

    private final RentCompleteMapper rentCompleteMapper;

    public void createAndStreamExcel(RentCompleteSearchDTO searchDTO, HttpServletResponse response) throws IOException {
        List<RentComplete> dataList = rentCompleteMapper.findAllByComplexConditions(searchDTO);

        try(SXSSFWorkbook workbook = new SXSSFWorkbook(1000)) {
            Sheet sheet = workbook.createSheet("전월세 실거래 목록");

            String[] headers = {"시군구", "법정동", "건물명", "전용면적(㎡)", "층", "보증금(만원)", "월세(만원)", "건축년도"};
            Row headerRow = sheet.createRow(0);

            for(int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            // 데이터 행 생성
            for (int i = 0; i <  dataList.size(); i++) {
                RentComplete data = dataList.get(i);
                Row row = sheet.createRow(i + 1);
//                row.createCell(0).setCellValue(data.getSggNm() != null ? data.getSggNm() : "");
//                row.createCell(1).setCellValue(data.getUmdNm() != null ? data.getUmdNm() : "");
//                row.createCell(2).setCellValue(data.getName() != null ? data.getName() : "" );
//                row.createCell(3).setCellValue(data.getArea() != null ? data.getArea() : 0.0);
//                row.createCell(4).setCellValue(data.getFloor() != null ? data.getFloor() : 0);
//                row.createCell(5).setCellValue(data.getDeposit() != null ? data.getDeposit() : 0);
//                row.createCell(6).setCellValue(data.getMonthlyRent() != null ? data.getMonthlyRent() : 0);
//                row.createCell(7).setCellValue(data.getBuildYear() != null ? data.getBuildYear() : 0);
                createCell(row, 0, data.getSggNm());
                createCell(row, 1, data.getUmdNm());
                createCell(row, 2, data.getName());
                createCell(row, 3, data.getArea());
                createCell(row, 4, data.getFloor());
                createCell(row, 5, data.getDeposit());
                createCell(row, 6, data.getMonthlyRent());
                createCell(row, 7, data.getBuildYear());
            }

            // 브라우저에서 다운로드
            // 날짜계산
            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = now.format(formatter);


            String fileName = "rent_insight_data_" + formattedDate + ".xlsx";

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            workbook.write(response.getOutputStream());
            workbook.dispose(); // 임시 파일 삭제
        } catch (IOException e) {
            throw new IOException("엑셀 파일 생성 중 오류가 발생했습니다.", e);
        }
    }

    private void createCell(Row row , int columnIndex , Object value) {
        Cell cell = row.createCell(columnIndex);
        if (value != null) {
            // value의 타입에 따라 setCellValue를 다르게 호출
            if (value instanceof String) {
                cell.setCellValue((String) value);
            } else if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            } else if (value instanceof Double) {
                cell.setCellValue((Double) value);
            }
            // 다른 타입이 필요하면 여기에 추가...
        } else {
            cell.setCellValue("");
        }
    }
}
