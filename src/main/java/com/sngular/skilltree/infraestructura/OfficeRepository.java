package com.sngular.skilltree.infraestructura;

import java.util.List;

import com.sngular.skilltree.model.Office;

public interface OfficeRepository {

  Office findByCode(String officecode);

  List<Office> findAll();
}
