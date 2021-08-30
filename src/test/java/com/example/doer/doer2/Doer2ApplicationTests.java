package com.example.doer.doer2;

import com.example.doer.doer2.model.*;
import com.example.doer.doer2.repositories.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class Doer2ApplicationTests {

    @Autowired
    private DoerRepository doerRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TempDoersAndQuotesInAdminTableRepository tempDoersAndQuotesInAdminTableRepository;

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
    public void testDelete() {
        quoteRepository.deleteAllByDoerId(2L);
        doerRepository.deleteById(2L);
        Doer DoeroptionalDoer = doerRepository.getDoerById(260L);
        Set<Quote> quotes = DoeroptionalDoer.getQuotes();
    }

    @Transactional
    @Test
    @Rollback(value = false)
    public void TestSave() {
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

    @Transactional
    @Test
    public void testContains() {
        List<Doer> li = doerRepository.getDoerByNameContains("а");
        List<Doer> li2 = doerRepository.getDoerBySurnameContains("а");
        List<Doer> li3 = doerRepository.getDoerByNameContainsOrSurnameContains("а");
        List<Quote> liQ = quoteRepository.getQuoteByTextContains("в");
    }

    @Transactional
    @Test
    public void testInsertQuoteBySurnameAndContains() {
        List<Doer> doers = doerRepository.getDoerBySurnameContains("Медведев");
        List<Quote> quotes = new ArrayList<>();
        for (Doer doer : doers) {
            Set<Quote> quotesAll = doer.getQuotes();
            for (Quote quote : quotesAll) {
                if (quote.getText().contains("а")) {
                    quotes.add(quote);
                }
            }
        }
        Assert.assertNotNull(quotes);
    }

    @Transactional
    @Test
    public void testInsertDoerBySurnameContains() {
        List<Doer> findedDoers = doerRepository.getDoerBySurnameContains("е");
        Assert.assertNotNull(findedDoers);
    }

    @Test
    public void testFullCompilarBySurnameAndName() {
        List<Doer> doers = doerRepository.getDoerByNameAndSurnameFullCompliance("Чаплин", "Чарли");
        Assert.assertNotNull(doers);
    }

    @ResponseBody
    @Test
    // @Rollback(value = false)
    public void testAddDoerOrQuote() {
        String doerName = "Чарли";
        String doerSurname = "Чаплин";
        String charsQuote = "новая цитата";
        List<Doer> doersContainsSurnameAndName = doerRepository.getDoerByNameAndSurnameFullCompliance("Чаплин", "Чарли");

        if (doersContainsSurnameAndName.isEmpty()) {
            //добавление нового писателя с цитатой
            Doer newDoer = new Doer();
            newDoer.setName(doerName);
            newDoer.setSurname(doerSurname);
            newDoer.setLikes(0L);

            Quote newQuote = new Quote();
            newQuote.setDoer(newDoer);
            newQuote.setText(charsQuote);
            newQuote.setLikes(0L);

            newDoer.getQuotes().add(newQuote);

            doerRepository.save(newDoer);
            quoteRepository.save(newQuote);
        } else if (!doersContainsSurnameAndName.isEmpty()) {
            //добавление только цитаты
            Doer doer = doersContainsSurnameAndName.get(0);

            Quote newQuote = new Quote();
            newQuote.setDoer(doer);
            newQuote.setText(charsQuote);
            newQuote.setLikes(0L);

            doer.getQuotes().add(newQuote);

            doerRepository.save(doer);
            quoteRepository.save(newQuote);
        }
    }

    @Test
    public void addTempDoersAndQuotes(){
        TempDoersAndQuotesTable newRecord = new TempDoersAndQuotesTable();
        newRecord.setDoerName("slava");
        newRecord.setDoerSurname("rylkov");
        newRecord.setQuote("temp quote");
        tempDoersAndQuotesInAdminTableRepository.save(newRecord);
    }

    @Test
    public void getAllTempDoersQuotes(){
        List<TempDoersAndQuotesTable> all = tempDoersAndQuotesInAdminTableRepository.getAllTempDoersAndQuotes();
        Assert.assertNotNull(all);
    }

    @Test
    @Rollback(value = false)
    public  void testDeleteTempDoersQuotes(){
        tempDoersAndQuotesInAdminTableRepository.deleteById(26L);
    }

    @Test
    public void addadministator() {
        Admin newAdmin = new Admin();
        newAdmin.setLogin("slavaLogin");
        newAdmin.setPassword("slavaPassword");
        adminRepository.save(newAdmin);
    }

    @Test
    public void testGetAdministratorByLoginAndPassword() {
        List<Admin> administrators = adminRepository.getAdminByLoginAndPassword(" ", " ");
        Assert.assertNotNull(administrators);
    }


    @Test
    public void getAllQuotes(){
     //   List<Quote> quotes = quoteRepository.getAllQuotes();
        List<Quote> quotes = new ArrayList<>();
        Doer d1 = new Doer();
        Quote q1 = new Quote();
        q1.setDoer(d1);
        q1.setText("t1");
        q1.setLikes(2L);
        Quote q2 = new Quote();
        q2.setDoer(d1);
        q2.setText("t1");
        q2.setLikes(4L);
        Quote q3 = new Quote();
        q3.setDoer(d1);
        q3.setText("t1");
        q3.setLikes(1L);
        Quote q4 = new Quote();
        q4.setDoer(d1);
        q4.setText("t1");
        q4.setLikes(5L);
        Quote q5 = new Quote();
        q5.setDoer(d1);
        q5.setText("t1");
        q5.setLikes(3L);
        quotes.add(q1);
        quotes.add(q2);
        quotes.add(q3);
        quotes.add(q4);
        quotes.add(q5);


        List<Quote> sotedQuotes = quotes.stream().sorted((o1, o2) -> {
            Long i = o1.getLikes() - o2.getLikes();
            String s = i.toString();
            Integer g = Integer.parseInt(s);
            return g;
        }).limit(10).collect(Collectors.toList());

        //Assert.assertNotNull(quotes);
        Assert.assertNotNull(sotedQuotes);
    }

    @Test
    void fullNameFind() {
        String name = "Дмитрий Медведев";
        String[] s = name.split(" ", 2);
        List<Doer> doerByNameAndSurnameFullCompliance = doerRepository.getDoerByNameAndSurnameFullCompliance(s[0], s[1]);
        Doer d = doerByNameAndSurnameFullCompliance.get(0);
        Assert.assertNotNull(doerByNameAndSurnameFullCompliance);
    }

}
