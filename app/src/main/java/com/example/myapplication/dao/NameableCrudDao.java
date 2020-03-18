package com.example.myapplication.dao;

import java.util.List;

public interface NameableCrudDao<Type> extends CrudDao<Type> {

    List<Type> findAllOrderAlphabetic();

}
