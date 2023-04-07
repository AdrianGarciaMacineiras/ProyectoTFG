package com.sngular.skilltree.contract;

import com.sngular.skilltree.api.PositionApi;
import com.sngular.skilltree.api.model.PatchedPositionDTO;
import com.sngular.skilltree.api.model.PositionDTO;
import com.sngular.skilltree.api.model.PositionSkillDTO;
import com.sngular.skilltree.application.PositionService;
import com.sngular.skilltree.application.updater.PositionUpdater;
import com.sngular.skilltree.contract.mapper.PositionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PositionController implements PositionApi {

    private final PositionService positionService;

    private final PositionUpdater positionUpdater;

    private final PositionMapper positionMapper;


    @Override
    public ResponseEntity<List<PositionDTO>> getPositions() {
        var puestoList = positionService.getAll();
        return ResponseEntity.ok(positionMapper.toPositionsDTO(puestoList));
    }

    @Override
    public ResponseEntity<PositionDTO> addPosition(PositionDTO positionDTO) {
        return ResponseEntity.ok(positionMapper
                .toPositionDTO(positionService
                        .create(positionMapper
                                .toPosition(positionDTO))));
    }

    @Override
    public ResponseEntity<PositionDTO> getPositionByCode(String positioncode) {
        return ResponseEntity.ok(positionMapper
                .toPositionDTO(positionService
                        .findByCode(positioncode)));
    }

    @Override
    public ResponseEntity<Void> deletePosition(String positioncode) {
        var result = positionService.deleteByCode(positioncode);
        return ResponseEntity.status(result? HttpStatus.OK: HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<PositionDTO> updatePosition(String positioncode, PositionDTO positionDTO) {
        return ResponseEntity.ok(positionMapper
                .toPositionDTO(positionUpdater
                        .update(positioncode, positionMapper
                                .toPosition(positionDTO))));
    }

    @Override
    public ResponseEntity<PositionDTO> patchPosition(String positioncode, PatchedPositionDTO patchedPositionDTO) {
        return ResponseEntity.ok(positionMapper
                .toPositionDTO(positionUpdater
                        .patch(positioncode, positionMapper
                                .toPosition(patchedPositionDTO))));
    }
}
