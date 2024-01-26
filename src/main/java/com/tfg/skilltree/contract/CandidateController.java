package com.tfg.skilltree.contract;

import com.tfg.skilltree.api.CandidateApi;
import com.tfg.skilltree.api.model.CandidateDTO;
import com.tfg.skilltree.api.model.PatchedCandidateDTO;
import com.tfg.skilltree.application.CandidateService;
import com.tfg.skilltree.application.updater.CandidateUpdater;
import com.tfg.skilltree.contract.mapper.CandidateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CandidateController implements CandidateApi {

    private final CandidateService candidateService;

    private final CandidateUpdater candidateUpdater;

    private final CandidateMapper candidateMapper;

    @Override
    public ResponseEntity<CandidateDTO> getCandidateByCode(String candidateCode) {
        return ResponseEntity.ok(candidateMapper
                .toCandidateDTO(candidateService
                        .findByCode(candidateCode)));
    }

    @Override
    public ResponseEntity<Void> deleteCandidate(String candidateCode) {
        var result = candidateService.deleteByCode(candidateCode);
        return ResponseEntity.status(result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<CandidateDTO> updateCandidate(String candidateCode, CandidateDTO candidateDTO) {
        return ResponseEntity.ok(candidateMapper
                .toCandidateDTO(candidateUpdater
                        .update(candidateCode, candidateMapper
                                .toCandidate(candidateDTO))));
    }

    @Override
    public ResponseEntity<CandidateDTO> patchCandidate(String candidateCode, PatchedCandidateDTO patchedCandidateDTO) {
        return ResponseEntity.ok(candidateMapper
                .toCandidateDTO(candidateUpdater
                        .patch(candidateCode, candidateMapper
                                .toCandidate(patchedCandidateDTO))));
    }
}
