package com.example.doer.doer2.repositories;

import com.example.doer.doer2.model.Quote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigInteger;
import java.util.List;

public interface QuoteRepository extends CrudRepository<Quote, Long> {
    void deleteAllByDoerId(Long id);

    List<Quote> getQuoteByTextContains(String s);

    Quote getQuoteById(Long id);

    public default void incrementLikes(Long idQuote) {
        Quote quote = getQuoteById(idQuote);
        Long countLikes = quote.getLikes();
        Long newCountLikes = countLikes + 1;
        quote.setLikes(newCountLikes);
        save(quote);
    }

    @Query("select q.id, q.text from Quote q where q.text like %:text%")
    List<Object[]> getQuotes(@Param("text") String s);

    @Query("from  Quote d")
    List<Quote> getAllQuotes();

}
