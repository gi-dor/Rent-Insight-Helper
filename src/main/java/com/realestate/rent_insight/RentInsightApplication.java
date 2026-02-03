package com.realestate.rent_insight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing  // 이 어노테이션을 추가해야 생성일/수정일 자동 관리가 작동한다고함 @CreatedDate , @LastModifiedDate
@SpringBootApplication
public class RentInsightApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentInsightApplication.class, args);
	}

}
