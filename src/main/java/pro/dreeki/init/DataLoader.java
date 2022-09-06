package pro.dreeki.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import pro.dreeki.domain.WordService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
		try (InputStream inputStream = new ClassPathResource(path.toString()).getInputStream();
			 InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
			List<String> words = bufferedReader.lines().toList();
			wordService.addWords(words);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Path getPath() {
		return Path.of("data", "input.txt");
	}
}
