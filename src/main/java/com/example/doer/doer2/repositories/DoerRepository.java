package com.example.doer.doer2.repositories;

import com.example.doer.doer2.model.Doer;
import com.example.doer.doer2.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public interface DoerRepository extends CrudRepository<Doer, Long> {


    List<Doer> getDoerByName(String name);

    @Query("from Doer d where upper(d.name) = upper(:name)")
    List<Doer> getDoerByName2(String name);

    @Override
    void deleteById(Long id);

    List<Doer> getDoerById(Long id);

    List<Doer> getDoerByNameContains(String s);
    List<Doer> getDoerBySurnameContains(String s);

   // ("INTO Bike (id, name) VALUES (:name , :lastName, :textQuote);")
    //void insertDoerAndQuote(String name, String lastName, String textQuote);

}
