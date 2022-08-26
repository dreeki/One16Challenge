package pro.dreeki.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

class Node {
	private final Node parent;

	private final String currentPrefix;
	private final String partToFind;

	private final List<Node> children;

	private Node(String wordToFind) {
		parent = null;
		currentPrefix = "";
		partToFind = wordToFind;
		children = new ArrayList<>();
	}

	private Node(NodeBuilder builder) {
		requireNonNull(builder.parent, "Use static factory method for the root or include the parent");
		requireNonNull(builder.currentPrefix, "Do not use null strings as part of the solution");
		if (builder.currentPrefix.isBlank()) {
			throw new IllegalArgumentException("Do not use empty strings as part of the solution");
		}

		parent = builder.parent;
		currentPrefix = builder.currentPrefix;
		partToFind = parent.partToFind.substring(currentPrefix.length());
		children = new ArrayList<>();
		parent.children.add(this);
	}

	boolean isSolution() {
		return partToFind.isBlank();
	}

	boolean isRoot() {
		return isNull(parent);
	}

	List<Node> findSolutionNodes() {
		if (isSolution()) {
			return List.of(this);
		}
		return children.stream()
				.map(Node::findSolutionNodes)
				.flatMap(Collection::stream)
				.toList();
	}

	String getPartToFind() {
		return partToFind;
	}

	String getCurrentPrefix() {
		return currentPrefix;
	}

	List<Node> getChildren() {
		return children;
	}

	Node getParent() {
		return parent;
	}

	static NodeBuilder builder() {
		return new NodeBuilder();
	}

	static Node createRootNode(String wordToFind) {
		return new Node(wordToFind);
	}

	static class NodeBuilder {
		private Node parent;
		private String currentPrefix;

		private NodeBuilder() {
		}

		NodeBuilder parent(Node parent) {
			this.parent = parent;
			return this;
		}

		NodeBuilder currentPrefix(String currentPrefix) {
			this.currentPrefix = currentPrefix;
			return this;
		}

		Node build() {
			return new Node(this);
		}
	}
}
