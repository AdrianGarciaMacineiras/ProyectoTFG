package com.sngular.skilltree.contract;

import java.util.List;

import com.sngular.skilltree.api.OfficeApi;
import com.sngular.skilltree.api.model.OfficeDTO;
import com.sngular.skilltree.application.OfficeService;
import com.sngular.skilltree.contract.mapper.OfficeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class OfficeController extends AbstractController implements OfficeApi {

  private final OfficeService officeService;

  private final OfficeMapper officeMapper;

  @Override
  public ResponseEntity<List<OfficeDTO>> getOffices() {
    var offices = officeService.findAll();
    return ResponseEntity.ok(officeMapper.toOfficesDTO(offices));
  }
}
