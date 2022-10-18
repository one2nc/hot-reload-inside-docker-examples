package in.login;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class LoginApplicationTests {

	@Test
	void contextLoads() throws InterruptedException {
		Thread.sleep(2000*60);
	}

}
