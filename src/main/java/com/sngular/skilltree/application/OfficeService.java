package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Office;

import java.util.List;

public interface OfficeService {

  Office findByCode(String officeCode);

  List<Office> findAll();
}
