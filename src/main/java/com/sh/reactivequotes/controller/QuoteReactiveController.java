package com.sh.reactivequotes.controller;

import java.time.Duration;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sh.reactivequotes.domain.Quote;
import com.sh.reactivequotes.repository.QuoteMongoReactiveRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@RestController
public class QuoteReactiveController {

	private static final int DELAY_PER_ITEM_MS = 100;

	private QuoteMongoReactiveRepository quoteMongoReactiveRepository;

	@GetMapping("/quotes-reactive")
	public Flux<Quote> getQuoteFlux() {
		return quoteMongoReactiveRepository.findAll().delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
	}

	@GetMapping("/quotes-reactive-paged")
	public Flux<Quote> getQuoteFlux(final @RequestParam(name = "page") int page,
								   final @RequestParam(name = "size") int size) {
		return quoteMongoReactiveRepository.retrieveAllQuotesPaged(PageRequest.of(page, size))
				.delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
	}

}
