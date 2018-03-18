package com.sh.reactivequotes.configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.LongSupplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sh.reactivequotes.domain.Quote;
import com.sh.reactivequotes.repository.QuoteMongoReactiveRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@Component
public class QuotesDataLoader implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuotesDataLoader.class);
	private QuoteMongoReactiveRepository quoteMongoReactiveRepository;

	@Override
	public void run(String... args) throws Exception {
		if (quoteMongoReactiveRepository.count().block() == 0L) {
			final LongSupplier longSupplier = new LongSupplier() {
				long l = 0L;

				@Override
				public long getAsLong() {
					return l++;
				}
			};
			BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(getClass().getClassLoader().getResourceAsStream("pg2000.txt")));
			
			Flux.fromStream(
                    bufferedReader.lines().filter(l -> !l.trim().isEmpty())
                            .map(l -> quoteMongoReactiveRepository.save(new Quote(String.valueOf(longSupplier.getAsLong()), "El Quijote", l)))
            ).subscribe(m -> LOGGER.info("New quote loaded: {}", m.block()));
			
			LOGGER.info("Repository contains now {} entries.", quoteMongoReactiveRepository.count().block());
        }		
	}

}
