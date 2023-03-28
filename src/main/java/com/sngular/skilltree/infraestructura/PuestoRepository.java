package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.model.Puesto;

import java.util.List;

public interface PuestoRepository {

  List<Puesto> findAll();

  Puesto save(Puesto puesto);

  Puesto findByCode(String opportunitycode);

  boolean deleteByCode(String opportunitycode);

  List<Puesto> findByDeletedIsFalse();
}
