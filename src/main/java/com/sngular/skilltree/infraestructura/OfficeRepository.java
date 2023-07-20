package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.model.Office;

import java.util.List;

public interface OfficeRepository {

    Office findByCode(String officecode);

    List<Office> findAll();
}
