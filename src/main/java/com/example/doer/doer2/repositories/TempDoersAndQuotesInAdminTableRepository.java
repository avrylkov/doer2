package com.example.doer.doer2.repositories;

import com.example.doer.doer2.model.Quote;
import com.example.doer.doer2.model.TempDoersAndQuotesTable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TempDoersAndQuotesInAdminTableRepository extends CrudRepository <TempDoersAndQuotesTable, Long> {
    @Query("from  TempDoersAndQuotesTable d")
    List<TempDoersAndQuotesTable> getAllTempDoersAndQuotes();

    void deleteById(Long id);
}
