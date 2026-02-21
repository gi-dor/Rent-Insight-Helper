package com.realestate.rent_insight.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity // JPA가 관리하는 엔티티가됨
@Getter // getter 필드 값 가져오기
@Table(name = "data_update_log") // 테이블 이름을 명시적으로 지정
public class DataUpdateLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 식별자

    @Column(nullable = false)
    LocalDateTime completionTime; // 작업 완료 시각

    @Column(nullable = false , length = 100)
    String status;  // "성공" 또는 "실패"

    Integer totalInsertedCount;     //총 저장 건수
    Integer totalDeletedCount;  // 총 삭제 건수

    @Builder
    public DataUpdateLog(LocalDateTime completionTime, String status, Integer totalInsertedCount, Integer totalDeletedCount) {
        this.completionTime = completionTime;
        this.status = status;
        this.totalInsertedCount = totalInsertedCount;
        this.totalDeletedCount = totalDeletedCount;
    }

}
