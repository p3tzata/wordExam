package com.example.WordCFExam.dao.base;


public interface CrudDao<Type> {

    Type findByID(Long ID);
    Long insert(Type entity);
    Integer update(Type entity);
    Integer delete(Type entity);


}
