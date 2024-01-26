package com.tfg.skilltree.infraestructura;

import java.util.List;

import com.tfg.skilltree.model.Office;

public interface OfficeRepository {

  Office findByCode(String officecode);

  List<Office> findAll();
}
