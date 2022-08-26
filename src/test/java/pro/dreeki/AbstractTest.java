package pro.dreeki;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import pro.dreeki.persistence.dao.WordDAO;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("unit")
@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(initializers = AbstractTest.DataSourceInitializer.class)
public abstract class AbstractTest {

	@Autowired
	private WordDAO wordDAO;

	@BeforeEach
	void resetDatabase() {
		wordDAO.deleteAll();
	}

	private static final PostgreSQLContainer<?> DATABASE;

	static {
		DATABASE = new PostgreSQLContainer<>("postgres:12.6-alpine");
		DATABASE.start();
	}

	@Test
	void containerIsRunning() {
		assertThat(DATABASE.isCreated()).isTrue();
		assertThat(DATABASE.isRunning()).isTrue();
	}

	public static class DataSourceInitializer
			implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
					applicationContext,
					"spring.datasource.url=" + DATABASE.getJdbcUrl(),
					"spring.datasource.username=" + DATABASE.getUsername(),
					"spring.datasource.password=" + DATABASE.getPassword()
			);
		}
	}
}
