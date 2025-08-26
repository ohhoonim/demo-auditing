package com.ohhoonim.demo_auditing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ohhoonim.demo_auditing.component.auditing.dataBy.entityScan.BusinessEntityScan;

@SpringBootApplication
@BusinessEntityScan(basePackages = {"com.ohhoonim.demo_auditing.para"})
public class DemoAuditingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoAuditingApplication.class, args);
	}

}
