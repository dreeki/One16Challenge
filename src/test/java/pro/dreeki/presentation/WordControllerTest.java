package pro.dreeki.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pro.dreeki.persistence.dao.WordDAO;
import pro.dreeki.persistence.entity.WordEntity;
import pro.dreeki.presentation.dto.request.CreateWordsDTO;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WordControllerTest extends AbstractControllerTest {

	@Autowired
	private WordDAO wordDAO;

	private static final String ENDPOINT = "/api/words";

	private static final String BAR = "bar";
	private static final String FOO = "foo";
	private static final String FOOBAR = "foobar";

	@Test
	void createWords() throws Exception {
		List<String> words = List.of(FOO, BAR, FOOBAR);

		mvc.perform(post(ENDPOINT)
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON)
						.content(write(new CreateWordsDTO(words))))
				.andExpect(status().isNoContent());

		List<String> result = wordDAO.findAll().stream()
				.map(WordEntity::getWord)
				.toList();

		assertThat(result).containsExactlyInAnyOrderElementsOf(words);
	}

	@Test
	void findAllSnakesForWordsWithLength() throws Exception {
		List<WordEntity> words = Stream.of(FOO, BAR, FOOBAR)
				.map(WordEntity::new)
				.toList();

		wordDAO.saveAll(words);

		mvc.perform(get(ENDPOINT + "/length/6")
						.accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
				.andExpect(jsonPath("$." + FOOBAR).exists())
				.andExpect(jsonPath("$." + FOOBAR).isArray())
				.andExpect(jsonPath("$." + FOOBAR, hasSize(1)))
				.andExpect(jsonPath("$." + FOOBAR, contains(String.format("%s+%s=%s", FOO, BAR, FOOBAR))));
	}
}