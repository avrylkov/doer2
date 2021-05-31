package com.example.doer.doer2.repositories;

import com.example.doer.doer2.model.Quote;
import org.springframework.data.repository.CrudRepository;

public interface QuoteRepository  extends CrudRepository<Quote, Long> {


    void deleteAllByDoerId(Long id);
}
