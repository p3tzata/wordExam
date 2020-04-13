package com.example.WordCFExam.repository;

import java.util.List;

public interface NameableCrudRepository<T>  {

    List<T> findAllOrderAlphabetic(Long parentID,String contains);
}
