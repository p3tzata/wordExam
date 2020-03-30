package com.example.myapplication.repository;

import java.util.List;

public interface NameableCrudRepository<T>  {

    List<T> findAllOrderAlphabetic(Long parentID,String contains);
}
