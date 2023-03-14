package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Office;

public interface OfficeService {

    Office findByCode(String officeCode);
}
