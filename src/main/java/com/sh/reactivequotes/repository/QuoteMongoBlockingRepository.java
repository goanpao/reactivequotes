package com.sh.reactivequotes.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sh.reactivequotes.domain.Quote;

public interface QuoteMongoBlockingRepository extends CrudRepository<Quote, String> {
	@Query("{ id: { $exists: true }}")
	List<Quote> retrieveAllQuotesPaged(final Pageable page);
}
