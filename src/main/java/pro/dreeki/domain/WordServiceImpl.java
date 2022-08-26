package pro.dreeki.domain;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Service
class WordServiceImpl implements WordService {

	private final WordRepository wordRepository;

	WordServiceImpl(WordRepository wordRepository) {
		this.wordRepository = wordRepository;
	}

	@Override
	public void addWords(List<String> words) {
		wordRepository.addWords(words);
	}

	@Override
	public Map<String, List<Snake>> findAllSnakesForWordsWithLength(int length) {
		Map<Integer, List<String>> wordsPerLength = wordRepository.findAllWordsWithLengthLowerOrEqualTo(length).stream()
				.collect(groupingBy(String::length));

		return wordsPerLength.get(length).stream()
				.map(word -> createTree(word, wordsPerLength))
				.collect(toMap(WordPrefixTree::getWordToFind, WordPrefixTree::findSolutions));
	}

	private WordPrefixTree createTree(String word, Map<Integer, List<String>> wordsPerLength) {
		List<String> substringsOfWord = wordsPerLength.entrySet().stream()
				.filter(entry -> entry.getKey() < word.length())
				.map(Map.Entry::getValue)
				.flatMap(Collection::stream)
				.filter(word::contains)
				.toList();

		return WordPrefixTree.builder()
				.wordToFind(word)
				.possibleParts(substringsOfWord)
				.build();
	}
}
