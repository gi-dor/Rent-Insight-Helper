package com.realestate.rent_insight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * '법정동코드.csv' 파일을 읽기
 * 서울시에 해당하는 지역 데이터의 SQL INSERT 구문을 생성
 */
public class DataSqlGenerator {

    public static void main(String[] args) {
        // ====================================================================================
        // filePath 변수에 다운로드한 '.csv' 파일의 전체 경로를 입력
        String filePath = "C:\\Users\\Admin\\Downloads\\국토교통부_전국 법정동_20250807.csv";
        // 출력되는 모든 INSERT 구문을 복사

        // data.sql 파일에 집어넣으면 Spring Boot 에서 뚝딱 Insert 해줌

        // ====================================================================================

        System.out.println("-- Region 테이블에 서울시 시군구 및 법정동 데이터 초기 적재");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            br.readLine();

            // 파일을 한 줄씩 읽기
            while ((line = br.readLine()) != null) {
                // 각 줄을 쉼표(,)로 나누기
                String[] columns = line.split(",");

                // 컬럼 순서에 맞게 데이터를 변수에 담음
                String code = columns[0];
                String sido = columns[1];
                String sigungu = (columns.length > 2) ? columns[2] : "";
                String eupmyeondong = (columns.length > 3) ? columns[3] : "";

                // 서울특별시 데이터만 골라내기 -- csv 파일은 모든 전국의 시가 있어서 서울시만 걸러야함
                if (sido.equals("서울특별시")) {
                    String parentCode = null;
                    String areaName = null;

                    // ㅇㅇㅇ코드 / 시군구 데이터 /  읍면동 데이터 이기에  판단하여 SQL사용
                    if (!sigungu.isEmpty() && eupmyeondong.isEmpty()) { // 시군구 데이터 (예: "서울특별시", "종로구", "")
                        areaName = sigungu;
                        parentCode = "1100000000"; // 서울특별시 고정 코드
                    } else if (!sigungu.isEmpty() && !eupmyeondong.isEmpty()) { // 읍면동 데이터 (예: "서울특별시", "종로구", "청운동")
                        areaName = eupmyeondong;
                        // 상위 시군구 코드를 법정동코드의 앞 5자리로 생성
                        parentCode = code.substring(0, 5) + "00000";
                    }

                    // 생성된 데이터가 있으면 SQL INSERT 구문을 출력합니다.
                    if (areaName != null) {
                        System.out.printf("INSERT INTO region (code, name, parent_code) VALUES ('%s', '%s', '%s');\n", code, areaName, parentCode);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
