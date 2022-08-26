package pro.dreeki.persistence;

import org.springframework.stereotype.Service;
import pro.dreeki.domain.WordRepository;
import pro.dreeki.persistence.dao.WordDAO;
import pro.dreeki.persistence.entity.WordEntity;

import java.util.List;

@Service
class WordRepositoryImpl implements WordRepository {

	private final WordDAO wordDAO;

	WordRepositoryImpl(WordDAO wordDAO) {
		this.wordDAO= wordDAO;
	}

	@Override
	public void addWords(List<String> words) {
		List<WordEntity> wordEntities = words.stream()
				.map(WordEntity::new)
				.toList();
		wordDAO.saveAll(wordEntities);
	}

	@Override
	public List<String> findAllWordsWithLengthLowerOrEqualTo(int length) {
		return wordDAO.findAllByWordLengthIsSmallerOrEqualThan(length).stream()
				.map(WordEntity::getWord)
				.toList();
	}
}
