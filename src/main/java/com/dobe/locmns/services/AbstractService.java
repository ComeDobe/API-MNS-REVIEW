package com.dobe.locmns.services;

import java.util.List;

public interface AbstractService <T>{
   Integer save(T dto);
   Integer update(T dto);
   List<T> findAll();
   T findById(Integer id);

   Void delete(Integer id);


}
