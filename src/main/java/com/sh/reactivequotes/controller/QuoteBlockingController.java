package com.sh.reactivequotes.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sh.reactivequotes.domain.Quote;
import com.sh.reactivequotes.repository.QuoteMongoBlockingRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class QuoteBlockingController {

	private QuoteMongoBlockingRepository quoteMongoBlockingRepository;

	@GetMapping("/quotes-blocking")
	public Iterable<Quote> getQuoteFlux() {
		return quoteMongoBlockingRepository.findAll();
	}

	@GetMapping("/quotes-blocking-paged")
	public Iterable<Quote> getQuoteFlux(final @RequestParam(name = "page") int page,
								   final @RequestParam(name = "size") int size) {
		return quoteMongoBlockingRepository.retrieveAllQuotesPaged(PageRequest.of(page, size));
	}

}
