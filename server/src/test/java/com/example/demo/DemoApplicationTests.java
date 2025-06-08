package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:h2:mem:testdb;MODE=PostgreSQL",
		"spring.datasource.driver-class-name=org.h2.Driver"
})
class DemoApplicationTests {
	@Test
	void contextLoads() {
	}
}
