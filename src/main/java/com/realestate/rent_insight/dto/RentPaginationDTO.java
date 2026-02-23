package com.realestate.rent_insight.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentPaginationDTO {

    //  페이징 계산 담당일찐


    // 입력 받는 값
    private int totalRows;    // DB내의 전체 데이터        -- 쿼리타면 여기에 갯수 들어감
    private int currentPage;  // 사용자가 요청한 페이지 번호 --  URL의 ?page=1


    // 기본 설정 값
    private int rows = 30;    // 페이지당  계약건수 갯수
    private int pages = 5;    // 페이지네이션 바에 보여줄 페이지 갯수 (1 2 3 4 5)


    // 계산되어야하는 값
    private int totalPages;
    private int totalBlocks;
    private int currentBlock;   // 현재 페이지가 속한 블록 번호
    private int beginPage;      // 현재 블록 시작 페이지
    private int endPage;        // 현재 블록 끝 페이지

    private int offset;         // SQL 쿼리에 사용할 offset 값

    // 현재  첫번째 페이지 ,마지막 페이지 구분
    private boolean isFirst;
    private boolean isLast;

    public RentPaginationDTO() {
    }

    public RentPaginationDTO(int currentPage, int totalRows) {
        this.currentPage = currentPage;
        this.totalRows = totalRows;
        // 객체가 생성되면서 모든 페이징 값 계산 해버리기
        init();
    }

    private void init() {

        // 글이 존재하는지
        if (totalRows > 0) {
            // 전체 페이지 갯수 계산하기
            totalPages = (int) Math.ceil((double) totalRows / rows);    // 3000개 데이터 /  30행 = totalPages= 100개

            // (현재페이지 번호 -1) * 한 페이지당 보여줄 데이터
            offset = (currentPage - 1) * rows;

            // 페이징 바 계산기
            totalBlocks = (int)Math.ceil((double) totalPages / pages);
            currentBlock = (int) Math.ceil((double) currentPage / pages);
            beginPage = (currentBlock - 1) * pages + 1;
            endPage = currentBlock * pages;

            // 마지막 블럭의 끝 페이지 번호 - 총 12페이지일 때 3번째 블록(11, 12)의 끝 페이지는 15가 아니라 12가 되어야 함
            if (currentBlock == totalBlocks) {
                endPage = totalPages;
            }
            // 혹시 모를 상황 대비: endPage가 totalPages를 넘지 않도록
            if (endPage > totalPages) {
                endPage = totalPages;
            }
            isFirst = (currentPage == 1);
            isLast = (currentPage == totalPages);
        }
    }


}
