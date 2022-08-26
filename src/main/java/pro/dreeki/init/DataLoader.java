package pro.dreeki.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pro.dreeki.domain.WordService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
@ConditionalOnProperty(name = "one16.challenge.loader.enable", havingValue = "true")
class DataLoader implements ApplicationRunner {

	private final WordService wordService;

	DataLoader(WordService wordService) {
		this.wordService = wordService;
	}

	@Override
	public void run(ApplicationArguments args) {
		Path path = getPath();
		try {
			List<String> words = Files.readAllLines(path);
			wordService.addWords(words);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Path getPath() {
		try {
			return Path.of(getClass().getClassLoader().getResource("data/input.txt").toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
