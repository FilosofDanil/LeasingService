package com.example.wohnungsuchen.repositories;

import com.example.wohnungsuchen.entities.Offers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface OffersRepository extends CrudRepository<Offers, Long> {
    Page<Offers> findAll(Pageable pageable);
//
//    @Query(value = "do\n" +
//            "$$\n" +
//            "    declare\n" +
//            "        selected_offer offers%rowtype;\n" +
//            "        id int = :id ;\n" +
//            "    begin\n" +
//            "        if not id IS NULL then\n" +
//            "            select *\n" +
//            "            from offers\n" +
//            "            where id != :id \n" +
//            "            into selected_offer;\n" +
//            "        end if;\n" +
//            "    end\n" +
//            "$$", nativeQuery = true)
//    List<Offers> findAll(@Param("id") int id);
}
