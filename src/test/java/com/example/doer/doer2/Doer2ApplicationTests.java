package com.example.doer.doer2;

import com.example.doer.doer2.model.Doer;
import com.example.doer.doer2.model.Quote;
import com.example.doer.doer2.repositories.DoerRepository;
import com.example.doer.doer2.repositories.QuoteRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
class Doer2ApplicationTests {

    @Autowired
    private DoerRepository doerRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    //@Autowired
    //private EntityManager entityManager;

    //@Autowired
    //private CrudRepository crudRepository;

    @Transactional
    @Test
    public void testFind() {
        Optional<Doer> optionalDoer = doerRepository.findById(2L);
        Doer doer = optionalDoer.get();
        Set<Quote> quotes = doer.getQuotes();
        Assert.assertTrue(quotes.size() > 0);
        //
        List<Doer> doerByName = doerRepository.getDoerByName("Чарли");
        Assert.assertTrue(doerByName.size() > 0);
        //
        List<Doer> doerByName2 = doerRepository.getDoerByName2("альБЕрт");
        Assert.assertTrue(doerByName2.size() > 0);

    }

    @Transactional
    @Test
    @Rollback(value = true)
    public  void testDelete(){
        quoteRepository.deleteAllByDoerId(2L);
        doerRepository.deleteById(2L);

        List<Doer> optionalDoer = doerRepository.getDoerById(260L);
        Doer doer = optionalDoer.get(0);
        Set<Quote> quotes = doer.getQuotes();

        List<Doer> li = doerRepository.getDoerByNameContains("а");
        List<Doer> li2 = doerRepository.getDoerBySurnameContains("а");

    }

    @Transactional
    @Test
    @Rollback(value = false)
    public  void TestSave(){
        Doer doer = new Doer();
        doer.setName("Паша");
        doer.setSurname("Александров");
        doer.setLikes(2L);

        Quote quote = new Quote();
        quote.setText("цитата два");
        doer.getQuotes().add(quote);
        quote.setDoer(doer);

        doerRepository.save(doer);
        quoteRepository.save(quote);
    }

   /* @Transactional
    @Test
    public  void testContains(){
        List<Doer> doers = doerRepository.getDoerByNameOrSurnameContains("а");

    }*/

}
