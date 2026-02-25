
EXPLAIN SELECT *
        FROM rent_complete
        WHERE sgg_cd = '11305' AND umd_nm = '미아동'
        AND area BETWEEN 25 AND 36;


CREATE INDEX idx_rent_complete_sgg_umd_area ON rent_complete (sgg_cd, umd_nm, area);


CREATE INDEX idx_rent_complete_contract_date ON rent_complete (contract_date);



show variables like '%profiling%';

set profiling=1;

set profiling_history_size=100;

SELECT *
FROM rent_complete
WHERE sgg_cd = '11215'
  AND umd_nm = '자양동'
  AND area BETWEEN 25 AND 36
order by contract_date DESC;

show profiles;  -- id : 18

-- 해당 쿼리문의 수행 시간을 더 상세한 단위로 확인
show profile for query 344;



drop index idx_rent_complete_contract_date on rent_complete;
drop  index idx_rent_complete_sgg_umd_area on rent_complete;