package com.sngular.skilltree.contract;

import com.sngular.skilltree.api.PuestoApi;
import com.sngular.skilltree.api.model.PatchedPuestoDTO;
import com.sngular.skilltree.api.model.PuestoDTO;
import com.sngular.skilltree.application.PuestoService;
import com.sngular.skilltree.application.updater.OpportunityUpdater;
import com.sngular.skilltree.contract.mapper.PuestoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PuestoController implements PuestoApi {

    private final PuestoService puestoService;

    private final OpportunityUpdater opportunityUpdater;

    private final PuestoMapper puestoMapper;


    @Override
    public ResponseEntity<List<PuestoDTO>> getPuestos() {
        var puestoList = puestoService.getAll();
        return ResponseEntity.ok(puestoMapper.toPuestosDTO(puestoList));
    }

    @Override
    public ResponseEntity<PuestoDTO> addPuesto(PuestoDTO puestoDTO) {
        return ResponseEntity.ok(puestoMapper
                .toPuestoDTO(puestoService
                        .create(puestoMapper
                                .toPuesto(puestoDTO))));
    }

    @Override
    public ResponseEntity<PuestoDTO> getPuestoByCode(String puestocode) {
        return ResponseEntity.ok(puestoMapper
                .toPuestoDTO(puestoService
                        .findByCode(puestocode)));
    }

    @Override
    public ResponseEntity<Void> deletePuesto(String puestocode) {
        var result = puestoService.deleteByCode(puestocode);
        return ResponseEntity.status(result? HttpStatus.OK: HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<PuestoDTO> updatePuesto(String puestocode, PuestoDTO puestoDTO) {
        return ResponseEntity.ok(puestoMapper
                .toPuestoDTO(opportunityUpdater
                        .update(puestocode, puestoMapper
                                .toPuesto(puestoDTO))));
    }

    @Override
    public ResponseEntity<PuestoDTO> patchPuesto(String puestocode, PatchedPuestoDTO patchedPuestoDTO) {
        return ResponseEntity.ok(puestoMapper
                .toPuestoDTO(opportunityUpdater
                        .patch(puestocode, puestoMapper
                                .toPuesto(patchedPuestoDTO))));
    }
}
