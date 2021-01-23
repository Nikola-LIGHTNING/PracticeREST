package com.tsvetkov.practice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PracticeApplicationTests {


	@Test
	@DisplayName("JUnit 5 Test")
	void contextLoads() {
		Assertions.assertThrows(Exception.class, () -> {
			throw new Exception("Wada");
		});
	}
}
