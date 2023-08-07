package com.sngular.skilltree.application;

import java.util.List;

import com.sngular.skilltree.model.Office;

public interface OfficeService {

  Office findByCode(String officeCode);

  List<Office> findAll();
}
