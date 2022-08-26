package pro.dreeki.domain;

import java.util.List;

public interface WordRepository {
	void addWords(List<String> words);

	List<String> findAllWordsWithLengthLowerOrEqualTo(int length);
}
