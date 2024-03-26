package com.dobe.locmns.services;

import java.util.List;

public interface AbstractService <T>{
   Integer save(T dto);
   Integer update(T dto);
   List<T> findAll();
   T findOne(Integer id);
   Void delete(Integer id);
   T getById(Integer id);
   T getByCode(String code);
   T getByEmail(String email);
}
