package com.realestate.rent_insight.domain.repository;

import com.realestate.rent_insight.domain.entity.DataUpdateLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DataUpdateLogRepository extends JpaRepository<DataUpdateLog, Long> {

    // SELECT * FROM data_update_log
    // WHERE status = ?
    // ORDER BY id DESC
    // LIMIT 1;
    Optional<DataUpdateLog> findFirstByStatusOrderByIdDesc(String status);
}
