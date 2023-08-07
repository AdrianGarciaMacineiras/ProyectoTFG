package com.sngular.skilltree.contract;

import java.util.List;

import com.sngular.skilltree.api.PositionApi;
import com.sngular.skilltree.api.model.CandidateDTO;
import com.sngular.skilltree.api.model.PatchedPositionDTO;
import com.sngular.skilltree.api.model.PositionDTO;
import com.sngular.skilltree.application.PositionService;
import com.sngular.skilltree.application.updater.PositionUpdater;
import com.sngular.skilltree.contract.mapper.CandidateMapper;
import com.sngular.skilltree.contract.mapper.PositionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class PositionController extends AbstractController implements PositionApi {

  private final PositionService positionService;

  private final PositionUpdater positionUpdater;

  private final PositionMapper positionMapper;

  private final CandidateMapper candidateMapper;


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
    public ResponseEntity<PositionDTO> getPositionByCode(String positionCode) {
        return ResponseEntity.ok(positionMapper
                .toPositionDTO(positionService
                        .findByCode(positionCode)));
    }

    @Override
    public ResponseEntity<Void> deletePosition(String positionCode) {
        var result = positionService.deleteByCode(positionCode);
        return ResponseEntity.status(result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<PositionDTO> updatePosition(String positionCode, PositionDTO positionDTO) {
        return ResponseEntity.ok(positionMapper
                .toPositionDTO(positionUpdater
                        .update(positionCode, positionMapper
                                .toPosition(positionDTO))));
    }

    @Override
    public ResponseEntity<PositionDTO> patchPosition(String positionCode, PatchedPositionDTO patchedPositionDTO) {
        return ResponseEntity.ok(positionMapper
                .toPositionDTO(positionUpdater
                        .patch(positionCode, positionMapper
                                .toPosition(patchedPositionDTO))));
    }

    @Override
    public ResponseEntity<PositionDTO> generateCandidates(String positionCode){
        return ResponseEntity.ok(positionMapper
                .toPositionDTO(positionService
                        .generateCandidates(positionCode)));
    }

    @Override
    public ResponseEntity<PositionDTO> assignCandidate(String positionCode, String peopleCode) {
        return ResponseEntity.ok(positionMapper
                .toPositionDTO(positionService
                        .assignCandidate(positionCode, peopleCode)));
    }

    @Override
    public ResponseEntity<List<CandidateDTO>> getPositionCandidates(String positionCode){
        return ResponseEntity.ok(candidateMapper
                .toCandidatesDTO(positionService
                        .getCandidates(positionCode)));
    }
}
