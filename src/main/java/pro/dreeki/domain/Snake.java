package pro.dreeki.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Snake {
	private final LinkedList<String> solution;

	public Snake() {
		solution = new LinkedList<>();
	}

	public Snake addToFront(String word) {
		solution.addFirst(word);
		return this;
	}

	public List<String> getSolution() {
		return solution;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Snake snake = (Snake) o;
		return Objects.equals(solution, snake.solution);
	}

	@Override
	public int hashCode() {
		return Objects.hash(solution);
	}

	@Override
	public String toString() {
		return "Snake{" +
				"solution=" + solution +
				'}';
	}
}
