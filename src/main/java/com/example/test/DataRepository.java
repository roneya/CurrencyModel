package com.example.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<Data, Integer> {


    @Query(value = "select distinct(local_date) from cash", nativeQuery = true)
    List<String> getDates();
    @Query(value = "select price from cash where currency=:curr", nativeQuery = true)
    List<Double> getValues(String curr);
}
