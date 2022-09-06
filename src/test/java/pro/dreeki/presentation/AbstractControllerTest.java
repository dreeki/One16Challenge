package pro.dreeki.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import pro.dreeki.AbstractBaseTest;

@Tag("controller")
@AutoConfigureMockMvc
abstract class AbstractControllerTest extends AbstractBaseTest {

	@Autowired
	protected ObjectMapper objectMapper;
	@Autowired
	protected MockMvc mvc;

	protected String write(Object payload) {
		try {
			return objectMapper.writeValueAsString(payload);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}
}
