package com.universityProject.workLoad;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
class WorkLoadApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void simpleTest() {
		Assertions.assertEquals(0, 0);
	}

}
