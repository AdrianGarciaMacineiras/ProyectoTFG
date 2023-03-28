package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Puesto;

import java.util.List;

public interface PuestoService {

  List<Puesto> getAll();

  Puesto create(final Puesto toPuesto);

  Puesto findByCode(final String opportunitycode);

  boolean deleteByCode(final String opportunitycode);
}
