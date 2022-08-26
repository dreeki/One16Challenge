package pro.dreeki.domain;

import java.util.List;
import java.util.Map;

public interface WordService {
	void addWords(List<String> words);

	Map<String, List<Snake>> findAllSnakesForWordsWithLength(int length);
}
