package pro.dreeki.persistence.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "words")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordEntity {
	@Id
	@Column(name = "word", updatable = false, nullable = false)
	private String word;
}
