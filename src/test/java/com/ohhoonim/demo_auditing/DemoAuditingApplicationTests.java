package com.ohhoonim.demo_auditing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class DemoAuditingApplicationTests {

	@Autowired
	private PostgreSQLContainer container;

	@Test
	void contextLoads() {
		assertThat(container.getDatabaseName()).isEqualTo("test");
	}
}
