package com.sngular.skilltree.application.implement;

import java.util.List;

import com.sngular.skilltree.application.OfficeService;
import com.sngular.skilltree.infraestructura.OfficeRepository;
import com.sngular.skilltree.model.Office;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;

    @Override
    public Office findByCode(final String officeCode) {
        return officeRepository.findByCode(officeCode);
    }

    @Override
    public List<Office> findAll() {
        return officeRepository.findAll();
    }
}
