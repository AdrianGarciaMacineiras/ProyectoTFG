package com.sngular.skilltree.contract;

import com.sngular.skilltree.api.OfficeApi;
import com.sngular.skilltree.api.model.OfficeDTO;
import com.sngular.skilltree.application.OfficeService;
import com.sngular.skilltree.contract.mapper.OfficeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OfficeController implements OfficeApi {

    private final OfficeService officeService;

    private final OfficeMapper officeMapper;

    @Override
    public ResponseEntity<List<OfficeDTO>> getOffices() {
        var offices = officeService.findAll();
        return ResponseEntity.ok(officeMapper.toOfficesDTO(offices));
    }
}
