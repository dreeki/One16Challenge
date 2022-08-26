package pro.dreeki.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class WordPrefixTreeTest {

	private static final String FOOBAR = "foobar";

	private static final String FOO = "foo";
	private static final String BAR = "bar";

	private static final String BA = "ba";

	private static final String FO = "fo";
	private static final String OB = "ob";
	private static final String AR = "ar";

	private static final String A = "a";

	@Test
	void findSolutions_NothingFound() {
		WordPrefixTree tree = WordPrefixTree.builder()
				.wordToFind(FOOBAR)
				.possibleParts(List.of(FOO, AR))
				.build();

		assertThat(tree.findSolutions()).isEmpty();
	}

	@Test
	void findSolutions_OnePath_OneSolution() {
		WordPrefixTree tree = WordPrefixTree.builder()
				.wordToFind(FOOBAR)
				.possibleParts(List.of(FOO, BAR))
				.build();

		assertThat(tree.findSolutions()).containsExactlyInAnyOrder(
				new Snake().addToFront(BAR).addToFront(FOO)
		);
	}

	@Test
	void findSolutions_TwoPaths_OneSolution() {
		WordPrefixTree tree = WordPrefixTree.builder()
				.wordToFind(FOOBAR)
				.possibleParts(List.of(FOO, BAR, BA))
				.build();

		assertThat(tree.findSolutions()).containsExactlyInAnyOrder(
				new Snake().addToFront(BAR).addToFront(FOO)
		);
	}

	@Test
	void findSolutions_TreePaths_TwoSolutions() {
		WordPrefixTree tree = WordPrefixTree.builder()
				.wordToFind(FOOBAR)
				.possibleParts(List.of(FOO, BAR, BA, FO, OB, AR))
				.build();

		assertThat(tree.findSolutions()).containsExactlyInAnyOrder(
				new Snake().addToFront(BAR).addToFront(FOO),
				new Snake().addToFront(AR).addToFront(OB).addToFront(FO)
		);
	}
}