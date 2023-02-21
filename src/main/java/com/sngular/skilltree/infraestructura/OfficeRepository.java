package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.model.Office;

public interface OfficeRepository {

    Office findByCode(String officecode);
}
