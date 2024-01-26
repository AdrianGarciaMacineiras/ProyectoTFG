package com.tfg.skilltree.contract;

import java.util.List;

import com.tfg.skilltree.api.OfficeApi;
import com.tfg.skilltree.api.model.OfficeDTO;
import com.tfg.skilltree.application.OfficeService;
import com.tfg.skilltree.contract.mapper.OfficeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
