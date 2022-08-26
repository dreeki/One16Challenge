package pro.dreeki.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class WordPrefixTree {
	private final String wordToFind;
	private final Node root;

	private WordPrefixTree(WordPrefixTreeBuilder builder) {
		wordToFind = builder.wordToFind;
		root = Node.createRootNode(wordToFind);
		buildTree(builder.possibleParts);
	}

	private void buildTree(List<String> possibleParts) {
		Queue<Node> queue = new LinkedList<>();
		queue.add(root);

		while (!queue.isEmpty()) {
			Node current = queue.remove();
			List<String> prefixes = possibleParts.stream()
					.filter(part -> current.getPartToFind().startsWith(part))
					.toList();

			prefixes.stream()
					.map(prefix -> Node.builder().parent(current).currentPrefix(prefix).build())
					.forEach(queue::add);
		}
	}

	List<Snake> findSolutions() {
		List<Node> solutionNodes = depthFirstSearchSolutions();
		return solutionNodes.stream()
				.map(this::toSnake)
				.toList();
	}

	private Snake toSnake(Node node) {
		Snake snake = new Snake();
		Node current = node;
		while (!current.isRoot()) {
			snake.addToFront(current.getCurrentPrefix());
			current = current.getParent();
		}
		return snake;
	}

	private List<Node> depthFirstSearchSolutions() {
		List<Node> result = new ArrayList<>();
		Queue<Node> queue = new LinkedList<>();
		queue.add(root);

		while (!queue.isEmpty()) {
			Node current = queue.remove();
			if (current.isSolution()) {
				result.add(current);
			} else {
				queue.addAll(current.getChildren());
			}
		}

		return result;
	}

	String getWordToFind() {
		return wordToFind;
	}

	static WordPrefixTreeBuilder builder() {
		return new WordPrefixTreeBuilder();
	}

	static class WordPrefixTreeBuilder {
		private String wordToFind;
		private List<String> possibleParts;

		private WordPrefixTreeBuilder() {
		}

		WordPrefixTreeBuilder wordToFind(String wordToFind) {
			this.wordToFind = wordToFind;
			return this;
		}

		WordPrefixTreeBuilder possibleParts(List<String> possibleParts) {
			this.possibleParts = possibleParts;
			return this;
		}

		WordPrefixTree build() {
			return new WordPrefixTree(this);
		}
	}
}
