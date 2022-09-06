package pro.dreeki.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pro.dreeki.persistence.dao.WordDAO;
import pro.dreeki.persistence.entity.WordEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class WordServiceImplBaseTest extends AbstractDomainTest {

	private static final String FOOBAR = "foobar";
	private static final String BARFOO = "barfoo";
	private static final String FOO = "foo";
	private static final String BAR = "bar";
	private static final String FO = "fo";
	private static final String OB = "ob";
	private static final String AR = "ar";
	private static final String A = "a";
	private static final String R = "r";

	@Autowired
	private WordService wordService;
	@Autowired
	private WordDAO wordDAO;

	@Test
	void addWords() {
		wordService.addWords(List.of(FOO, BAR, FO, AR));

		List<WordEntity> all = wordDAO.findAll();

		assertThat(all)
				.hasSize(4)
				.containsExactlyInAnyOrder(
						toWordEntity(FOO),
						toWordEntity(BAR),
						toWordEntity(FO),
						toWordEntity(AR)
				);
	}

	@Test
	void addWords_duplicates() {
		wordService.addWords(List.of(FOO, BAR, FO, AR));
		wordService.addWords(List.of(FOO, BAR, FO, AR));

		List<WordEntity> all = wordDAO.findAll();

		assertThat(all)
				.hasSize(4)
				.containsExactlyInAnyOrder(
						toWordEntity(FOO),
						toWordEntity(BAR),
						toWordEntity(FO),
						toWordEntity(AR)
				);
	}

	@Test
	void findAllSnakesForWordsWithLength() {
		wordService.addWords(List.of(FOOBAR, FOO, BAR));

		Map<String, List<Snake>> result = wordService.findAllSnakesForWordsWithLength(6);

		assertThat(result).containsOnlyKeys(FOOBAR);
		assertThat(result.get(FOOBAR)).containsExactlyInAnyOrder(new Snake().addToFront(BAR).addToFront(FOO));
	}

	@Test
	void findAllSnakesForWordsWithLength_MoreSnakes() {
		wordService.addWords(List.of(BARFOO, FOOBAR, FOO, BAR, FO, OB, AR, A, R));

		Map<String, List<Snake>> result = wordService.findAllSnakesForWordsWithLength(6);

		assertThat(result).containsOnlyKeys(FOOBAR, BARFOO);
		assertThat(result.get(FOOBAR)).containsExactlyInAnyOrder(
				new Snake().addToFront(BAR).addToFront(FOO),
				new Snake().addToFront(AR).addToFront(OB).addToFront(FO),
				new Snake().addToFront(R).addToFront(A).addToFront(OB).addToFront(FO)
		);
		assertThat(result.get(BARFOO)).containsExactlyInAnyOrder(new Snake().addToFront(FOO).addToFront(BAR));
	}

	private WordEntity toWordEntity(String word) {
		return new WordEntity(word);
	}
}