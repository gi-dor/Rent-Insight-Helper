package com.realestate.rent_insight;

import com.realestate.rent_insight.dto.ApiProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableConfigurationProperties(ApiProperties.class)
@EnableScheduling
@EnableJpaAuditing  // JPA의 Auditing 기능을 활성화합니다. (생성일/수정일 자동 관리)
@MapperScan("com.realestate.rent_insight.domain.mapper") // MyBatis 매퍼 인터페이스가 있는 패키지 경로를 지정
@SpringBootApplication
public class RentInsightApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentInsightApplication.class, args);
	}
}
