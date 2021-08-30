package com.example.doer.doer2.repositories;

import com.example.doer.doer2.model.Doer;
import com.example.doer.doer2.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public interface DoerRepository extends CrudRepository<Doer, Long> {
    List<Doer> getDoerByName(String name);
    @Query("from Doer d where upper(d.name) = upper(:name)")
    List<Doer> getDoerByName2(String name);
    Doer getDoerById(Long id);
    List<Doer> getDoerByNameContains(String s);
    List<Doer> getDoerBySurnameContains(String s);
    @Query("from  Doer d where (d.name like %:s%) or (d.surname like %:s%)")
    List<Doer> getDoerByNameContainsOrSurnameContains(String s);


    @Query("select d.name, d.surname from Doer d where (d.name like %:s%) or (d.surname like %:s%)")
    List<Object> getNameAndSurnameDoer(@Param("s") String s);

    @Query("select d.name from Doer d where d.name like %:s%")
    List<String> getName(@Param("s") String s);

    @Override
    void deleteById(Long id);

    @Query("from  Doer d where d.surname = :surname and d.name = :name")
    List<Doer> getDoerByNameAndSurnameFullCompliance(String surname,String name);

}
