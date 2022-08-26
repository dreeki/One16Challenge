package pro.dreeki.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.jupiter.api.Test;

class NodeTest {

	private static final String FIRST_PART = "foo";
	private static final String SECOND_PART = "bar";

	private static final Node ROOT_NODE = Node.createRootNode(FIRST_PART + SECOND_PART);
	private static final Node CHILD_LEVEL_1 = Node.builder().parent(ROOT_NODE).currentPrefix(FIRST_PART).build();
	private static final Node CHILD_LEVEL_2_SOLUTION = Node.builder().parent(CHILD_LEVEL_1).currentPrefix(SECOND_PART).build();
	private static final Node CHILD_LEVEL_2_NO_SOLUTION = Node.builder().parent(CHILD_LEVEL_1).currentPrefix(SECOND_PART.substring(0, SECOND_PART.length() - 1)).build();

	@Test
	void construction() {
		assertThatNullPointerException()
				.isThrownBy(() -> Node.builder().currentPrefix(FIRST_PART).build())
				.withMessage("Use static factory method for the root or include the parent");
		assertThatNullPointerException()
				.isThrownBy(() -> Node.builder().parent(ROOT_NODE).build())
				.withMessage("Do not use null strings as part of the solution");

		assertThatIllegalArgumentException()
				.isThrownBy(() -> Node.builder().parent(ROOT_NODE).currentPrefix("").build())
				.withMessage("Do not use empty strings as part of the solution");
	}

	@Test
	void isRoot() {
		assertThat(ROOT_NODE.isRoot()).isTrue();

		assertThat(CHILD_LEVEL_1.isRoot()).isFalse();
		assertThat(CHILD_LEVEL_2_SOLUTION.isRoot()).isFalse();
		assertThat(CHILD_LEVEL_2_NO_SOLUTION.isRoot()).isFalse();
	}

	@Test
	void isSolution() {
		assertThat(ROOT_NODE.isSolution()).isFalse();
		assertThat(CHILD_LEVEL_1.isSolution()).isFalse();

		assertThat(CHILD_LEVEL_2_SOLUTION.isSolution()).isTrue();

		assertThat(CHILD_LEVEL_2_NO_SOLUTION.isSolution()).isFalse();
	}

	@Test
	void findSolutionNodes() {
		assertThat(ROOT_NODE.findSolutionNodes()).containsExactly(CHILD_LEVEL_2_SOLUTION);
		assertThat(CHILD_LEVEL_1.findSolutionNodes()).containsExactly(CHILD_LEVEL_2_SOLUTION);
		assertThat(CHILD_LEVEL_2_SOLUTION.findSolutionNodes()).containsExactly(CHILD_LEVEL_2_SOLUTION);

		assertThat(CHILD_LEVEL_2_NO_SOLUTION.findSolutionNodes()).isEmpty();
	}

}