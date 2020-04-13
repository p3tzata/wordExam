package com.example.WordCFExam.dao.base;

import java.util.List;

public interface NameableCrudDao<Type> extends CrudDao<Type> {

   List<Type> findAllOrderAlphabetic(Long parentID,String contains);

}
