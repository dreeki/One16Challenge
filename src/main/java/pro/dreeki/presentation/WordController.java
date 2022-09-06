package pro.dreeki.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.dreeki.domain.Snake;
import pro.dreeki.domain.WordService;
import pro.dreeki.presentation.dto.request.CreateWordsDTO;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/words")
class WordController {

	private final WordService wordService;

	public WordController(WordService wordService) {
		this.wordService = wordService;
	}

	@PostMapping
	public ResponseEntity<?> createWords(@RequestBody CreateWordsDTO createWordsDTO) {
		wordService.addWords(createWordsDTO.words());

		return new ResponseEntity<>(NO_CONTENT);
	}

	@GetMapping("/length/{length}")
	public ResponseEntity<?> findAllSnakesForWordsWithLength(@PathVariable Integer length) {
		Map<String, List<Snake>> allSnakesForWordsWithGivenLength = wordService.findAllSnakesForWordsWithLength(length);

		Map<String, List<String>> desiredOutputForChallenge = allSnakesForWordsWithGivenLength.entrySet().stream()
				.collect(toMap(Map.Entry::getKey, e -> e.getValue().stream().map(Snake::getSolution).toList()));

		return new ResponseEntity<>(desiredOutputForChallenge, OK);
	}
}
