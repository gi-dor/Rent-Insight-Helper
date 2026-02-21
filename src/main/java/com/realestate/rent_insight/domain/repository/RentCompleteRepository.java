package com.realestate.rent_insight.domain.repository;

import com.realestate.rent_insight.domain.entity.RentComplete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentCompleteRepository extends JpaRepository<RentComplete, Long> {


    List<RentComplete> findBySggNmAndUmdNm(String sggNm, String umdNm);


    List<RentComplete> findByNameContaining(String name);

    List<RentComplete> findByDepositBetween(Integer minDeposit, Integer maxDeposit);

    List<RentComplete> findByMonthlyRentBetween(Integer minRent, Integer maxRent);


    @Modifying  // INSERT , UPDATE , DELETE 에서 사용하는 어노테이션이라고함
    @Query("DELETE FROM RentComplete rc WHERE FUNCTION('DATE_FORMAT', rc.contractDate, '%Y%m') = :dealYmd")
    void deleteAllByDealYmd(@Param("dealYmd") String dealYmd);
}
