package com.example.doer.doer2;


import com.example.doer.doer2.model.Admin;
import com.example.doer.doer2.model.Doer;
import com.example.doer.doer2.model.Quote;
import com.example.doer.doer2.model.TempDoersAndQuotesTable;
import com.example.doer.doer2.repositories.AdminRepository;
import com.example.doer.doer2.repositories.TempDoersAndQuotesInAdminTableRepository;
import com.example.doer.doer2.repositories.DoerRepository;
import com.example.doer.doer2.repositories.QuoteRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Controller
public class Controller {
    private final DoerRepository doerRepository;
    private final QuoteRepository quoteRepository;
    private final AdminRepository adminRepository;
    private final TempDoersAndQuotesInAdminTableRepository tempDoersAndQuotesInAdminTableRepository;

    protected Controller(DoerRepository doerRepository,
                         QuoteRepository quoteRepository,
                         AdminRepository adminRepository,
                         TempDoersAndQuotesInAdminTableRepository tempDoersAndQuotesInAdminTableRepository
    ) {
        this.doerRepository = doerRepository;
        this.quoteRepository = quoteRepository;
        this.adminRepository = adminRepository;
        this.tempDoersAndQuotesInAdminTableRepository = tempDoersAndQuotesInAdminTableRepository;
    }

    @GetMapping("/")
    public String mainPage() {
        return "mainPage";
    }

    @GetMapping("/findQuote")
    public String findQuote(Model model) {
        List<Quote> quotes = quoteRepository.getAllQuotes();
        List<Quote> sortedQuotes = quotes.stream().sorted((o1, o2) -> {
            Long i = o2.getLikes() - o1.getLikes();
            String s = i.toString();
            Integer g = Integer.parseInt(s);
            return g;
        }).limit(40).collect(Collectors.toList());
        model.addAttribute("searchResult", sortedQuotes);
        return "findQuotePage";
    }

    @GetMapping("/doerSearchPage")
    public String findDoer() {
        return "doerSearchPage";
    }

    @GetMapping("/insertQuote")
    public String insertQuote() {
        return "insertQuotePage";
    }

    @GetMapping("/autowiredAdmin")
    public String autowiredPage() {
        return "autowiredAdmin";
    }

    @GetMapping("/mainPage")
    public String mainPage2(){
        return "mainPage";
    }


    @RequestMapping(value = "/formSearchQuote", method = RequestMethod.POST)
    public String findQuote(@RequestParam(name = "chars") String chars, Model model) {
        String nameFirstLineInSearchQuote = "??????";
        String quoteFirstLineInSearchQuote = "????????????";
        List<Quote> quotes = quoteRepository.getQuoteByTextContains(chars);


        model.addAttribute("nameFirstLineInSearchQuote", nameFirstLineInSearchQuote);
        model.addAttribute("quoteNameFirstLineInSearchQuote", quoteFirstLineInSearchQuote);
        model.addAttribute("searchResult", quotes);

        return "findQuotePage";
    }

    @ResponseBody
    @RequestMapping(value = "/dataSearchQuote", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> searchQuoteData(@RequestParam(name = "chars") String chars) {
        return doSearchDoersAndQuote(chars);
    }

    @RequestMapping(value = "quotesDoerByFindQuote", method = RequestMethod.GET)
    public String fillingPageDoerByDoerIdByFindQuotePage(@RequestParam(name = "id") Long id, Model model) {
        fillingPageDoer(id, model);
        return "pageDoer";
    }

    @RequestMapping(value = "quotesDoer", method = RequestMethod.GET)
    public String fillingPageDoerByDoerId(@RequestParam(name = "idDoer") Long id, Model model) {
        fillingPageDoer(id, model);
        return "pageDoer";
    }

    private void fillingPageDoer(Long id, Model model) {
        Doer doer = doerRepository.getDoerById(id);
        if (doer == null) {
            return;
        }
        String name = doer.getName();
        String surname = doer.getSurname();
        Set<Quote> quotesDoer = doer.getQuotes();
        List<Quote> sortedQuotes = quotesDoer.stream().sorted((o1, o2) -> {
            Long i = o2.getLikes() - o1.getLikes();
            String s = i.toString();
            Integer g = Integer.parseInt(s);
            return g;
        }).collect(Collectors.toList());

        model.addAttribute("name", name + " " + surname);
        model.addAttribute("quotesDoer", sortedQuotes);
    }

    @RequestMapping(value = "likesQuote", method = RequestMethod.GET)
    public String likesQuote(@RequestParam(name = "idQuote") Long idQuote,
                             @RequestParam(name = "idDoer") Long idDoer,
                             Model model) {

        quoteRepository.incrementLikes(idQuote);
        fillingPageDoer(idDoer, model);
        return "pageDoer";
    }

    @RequestMapping(value = "Autowired", method = RequestMethod.POST)
    public String testAutowiredAdmin(@RequestParam(name = "login") String login,
                                     @RequestParam(name = "password") String password,
                                     Model model) {
        List<Admin> admin = adminRepository.getAdminByLoginAndPassword(login, password);
        if (!admin.isEmpty()) {
            List<TempDoersAndQuotesTable> allRecords = tempDoersAndQuotesInAdminTableRepository.getAllTempDoersAndQuotes();
            model.addAttribute("name","??????");
            model.addAttribute("quote","????????????");
            if(allRecords != null) {
                model.addAttribute("resultBySelectAdminAutowired", allRecords);
            }
            model.addAttribute("completeAutowired", "??????????????");

        } else {
            model.addAttribute("completeAutowired", "????????????");
        }
        return "adminTable";
    }


    private List<String> doSearchDoersAndQuote(String chars) {
        if (StringUtils.isEmpty(chars)) {
            return Collections.EMPTY_LIST;
        }
        List<Quote> doersAndQuote = quoteRepository.getQuoteByTextContains(chars);
        List<String> strings = doersAndQuote.stream()
                .map(q -> q.getText())
                .collect(Collectors.toList());
        return strings;
    }

    private List<Doer> doSearchDoers(String chars) {
        if (org.springframework.util.StringUtils.isEmpty(chars)) {
            return Collections.EMPTY_LIST;
        }
        List<Doer> doers = doerRepository.getDoerByNameContainsOrSurnameContains(chars);
        return doers;
    }


    @RequestMapping(value = "/formSearchDoer", method = RequestMethod.POST)
    public String findDoerSql(@RequestParam(name = "chars") String chars, Model model) {
        String[] s = chars.split(" ");
        if(s.length >= 2){
            List<Doer> doerByNameAndSurnameFullCompliance = doerRepository.getDoerByNameAndSurnameFullCompliance(s[1], s[0]);
            if(!doerByNameAndSurnameFullCompliance.isEmpty()) {
                fillingPageDoer(doerByNameAndSurnameFullCompliance.get(0).getId(), model);
                return "pageDoer";
            }else
            {
                return "doerSearchPage";
            }
        }else {
            List<Doer> doers = doSearchDoers(chars);
            model.addAttribute("doersSearchResult", doers);
            return "doerSearchPage";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/dataSearchDoerByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> substitutionDoerName(@RequestParam(name = "chars") String chars) {
        List<Doer> doers = doerRepository.getDoerBySurnameContains(chars);
        List<String> names = doers.stream().map(d -> d.getName() + " " + d.getSurname()).collect(Collectors.toList());
        return names;
    }

    @ResponseBody
    @RequestMapping(value = "dataSearchByInsertDoer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> doerInsertSearch(@RequestParam(name = "chars") String chars) {
        if (org.springframework.util.StringUtils.isEmpty(chars)) {
            return Collections.EMPTY_LIST;
        } else {
            List<Doer> doers = doerRepository.getDoerBySurnameContains(chars);
            List<String> doersSurnames = doers.stream().map(d -> d.getSurname()).collect(Collectors.toList());
            return doersSurnames;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/quoteInsert", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> quoteInsertSearch(@RequestParam("charsQuote") String charsQuote, @RequestParam("chars") String charsSurName) {
        List<Doer> doers = doerRepository.getDoerBySurnameContains(charsSurName);
        List<String> quotes = new ArrayList<>();
        for (Doer doer : doers) {
            Set<Quote> quotesAll = doer.getQuotes();
            for (Quote quote : quotesAll) {
                if (quote.getText().contains(charsQuote)) {
                    quotes.add(quote.getText());
                }
            }
        }
        return quotes;
    }

    @RequestMapping(value = "addTempDoersAndQuotes", method = RequestMethod.POST)
    public String addTempDoersAndQuotes(@RequestParam(name = "chars") String doerSurname,
                                        @RequestParam(name = "charsName") String doerName,
                                        @RequestParam(name = "charsQuote") String charsQuote,
                                        @RequestParam(name = "linkDoer") String linkDoer,
                                        Model model) {
        TempDoersAndQuotesTable newRecord = new TempDoersAndQuotesTable();
        newRecord.setDoerName(doerName);
        newRecord.setDoerSurname(doerSurname);
        newRecord.setQuote(charsQuote);
        newRecord.setLinkDoer(linkDoer);
        tempDoersAndQuotesInAdminTableRepository.save(newRecord);
        model.addAttribute("doerResult", "???????????????????? ???? ????????????????");
        return "insertQuotePage";
    }

    @RequestMapping(value = "addDoersAndQuotes",method = RequestMethod.POST)
    public String addDoersAndQuotes(@RequestParam(name = "name") String name,
                                    @RequestParam(name = "surName") String surName,
                                    @RequestParam(name = "quote") String quote,
                                    @RequestParam(name = "linkDoer") String link,
                                    @RequestParam(name = "tempId") Long tempId,
                                    Model model){
        List<Doer> doersContainsSurnameAndName = doerRepository.getDoerByNameAndSurnameFullCompliance(name, surName);
        if (doersContainsSurnameAndName.isEmpty()) {
            //???????????????????? ???????????? ???????????????? ?? ??????????????
            Doer newDoer = new Doer();
            newDoer.setName(name);
            newDoer.setSurname(surName);
            newDoer.setLikes(0L);
            newDoer.setLink(link);

            Quote newQuote = new Quote();
            newQuote.setDoer(newDoer);
            newQuote.setText(quote);
            newQuote.setLikes(0L);

            newDoer.getQuotes().add(newQuote);

            doerRepository.save(newDoer);
            quoteRepository.save(newQuote);

            tempDoersAndQuotesInAdminTableRepository.deleteById(tempId);

        } else if (!doersContainsSurnameAndName.isEmpty()) {
            //???????????????????? ???????????? ????????????
            Doer doer = doersContainsSurnameAndName.get(0);

            Quote newQuote = new Quote();
            newQuote.setDoer(doer);
            newQuote.setText(quote);
            newQuote.setLikes(0L);

            doer.getQuotes().add(newQuote);

            doerRepository.save(doer);
            quoteRepository.save(newQuote);

            tempDoersAndQuotesInAdminTableRepository.deleteById(tempId);

        }
        return "adminTable";
    }


    @RequestMapping(value = "/addDoerAndQuote", method = RequestMethod.GET)
    public String addDoerAndQuote(@RequestParam(name = "doerName") String doerSurname,
                                  @RequestParam(name = "doerSurname") String doerName,
                                  @RequestParam(name = "idRecord") Long idRecord,
                                  @RequestParam(name = "textQuote") String charsQuote,
                                  @RequestParam(name = "linkDoer") String linkDoer,
                                  Model model) {
        List<Doer> doersContainsSurnameAndName = doerRepository.getDoerByNameAndSurnameFullCompliance(doerSurname, doerName);
        if (doersContainsSurnameAndName.isEmpty()) {
            //???????????????????? ???????????? ???????????????? ?? ??????????????
            Doer newDoer = new Doer();
            newDoer.setName(doerName);
            newDoer.setSurname(doerSurname);
            newDoer.setLikes(0L);
             newDoer.setLink(linkDoer);

            Quote newQuote = new Quote();
            newQuote.setDoer(newDoer);
            newQuote.setText(charsQuote);
            newQuote.setLikes(0L);

            newDoer.getQuotes().add(newQuote);

            doerRepository.save(newDoer);
            quoteRepository.save(newQuote);

            tempDoersAndQuotesInAdminTableRepository.deleteById(idRecord);

        } else if (!doersContainsSurnameAndName.isEmpty()) {
            //???????????????????? ???????????? ????????????
            Doer doer = doersContainsSurnameAndName.get(0);

            Quote newQuote = new Quote();
            newQuote.setDoer(doer);
            newQuote.setText(charsQuote);
            newQuote.setLikes(0L);

            doer.getQuotes().add(newQuote);

            doerRepository.save(doer);
            quoteRepository.save(newQuote);

            tempDoersAndQuotesInAdminTableRepository.deleteById(idRecord);

        }
        return "adminTable";
    }

    @RequestMapping(value = "/deleteRecordFromTempTable",method = RequestMethod.GET)
    public String deleteRecordFromTempTable(@RequestParam(name = "idRecord") Long id,Model model){
        tempDoersAndQuotesInAdminTableRepository.deleteById(id);
        List<TempDoersAndQuotesTable> allRecords = tempDoersAndQuotesInAdminTableRepository.getAllTempDoersAndQuotes();
        model.addAttribute("name","??????");
        model.addAttribute("quote","????????????");
        if(allRecords != null) {
            model.addAttribute("resultBySelectAdminAutowired", allRecords);
        }
        model.addAttribute("completeAutowired", "??????????????");
        return "adminTable";
    }

}

