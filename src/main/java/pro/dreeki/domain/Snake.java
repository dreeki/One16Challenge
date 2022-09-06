package pro.dreeki.domain;

import java.util.LinkedList;
import java.util.Objects;

public class Snake {
	private final LinkedList<String> solution;

	public Snake() {
		solution = new LinkedList<>();
	}

	Snake addToFront(String word) {
		solution.addFirst(word);
		return this;
	}

	public String getSolution() {
		return String.join("+", solution) + "=" + String.join("", solution);
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
