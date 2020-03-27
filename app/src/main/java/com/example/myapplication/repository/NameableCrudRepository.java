package com.example.myapplication.repository;

import java.util.List;

public interface NameableCrudRepository<T>  {

    public List<T> findAllOrderAlphabetic(Object... objs);
}
