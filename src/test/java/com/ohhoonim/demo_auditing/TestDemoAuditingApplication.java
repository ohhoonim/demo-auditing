package com.ohhoonim.demo_auditing;

import org.springframework.boot.SpringApplication;

public class TestDemoAuditingApplication {

	public static void main(String[] args) {
		SpringApplication.from(DemoAuditingApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
