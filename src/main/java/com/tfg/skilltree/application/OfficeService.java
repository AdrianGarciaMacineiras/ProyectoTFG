package com.tfg.skilltree.application;

import java.util.List;

import com.tfg.skilltree.model.Office;

public interface OfficeService {

  Office findByCode(String officeCode);

  List<Office> findAll();
}
