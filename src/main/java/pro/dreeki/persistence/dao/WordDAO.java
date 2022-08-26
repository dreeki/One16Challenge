package pro.dreeki.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.dreeki.persistence.entity.WordEntity;

import java.util.List;

@Repository
public interface WordDAO extends JpaRepository<WordEntity, String> {

	@Query(value = """
		SELECT w
		FROM WordEntity w
		WHERE length(w.word) <= :length
	""")
	List<WordEntity> findAllByWordLengthIsSmallerOrEqualThan(int length);
}
