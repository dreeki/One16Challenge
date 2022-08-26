package pro.dreeki.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SnakeTest {

	private static final String TOP_LEVEL = "fo";
	private static final String LEVEL_1 = "ob";
	private static final String LEVEL_2 = "ar";

	@Test
	void addToFront() {
		Snake snake = new Snake();
		snake.addToFront(LEVEL_2)
				.addToFront(LEVEL_1)
				.addToFront(TOP_LEVEL);

		assertThat(snake.getSolution()).containsExactly(TOP_LEVEL, LEVEL_1, LEVEL_2);
	}
}