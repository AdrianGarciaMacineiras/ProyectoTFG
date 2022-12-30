package com.sngular.skilltree.contract;

import com.sngular.skilltree.api.CandidateApi;
import com.sngular.skilltree.api.model.CandidateDTO;
import com.sngular.skilltree.api.model.PatchedCandidateDTO;
import com.sngular.skilltree.application.CandidateService;
import com.sngular.skilltree.contract.mapper.CandidateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CandidateController implements CandidateApi {

    private final CandidateService candidateService;

    private final CandidateMapper candidateMapper;

    @Override
    public ResponseEntity<List<CandidateDTO>> getCandidates() {
        var candidateList = candidateService.getAll();
        return ResponseEntity.ok(candidateMapper.toCandidatesDTO(candidateList));
    }

    @Override
    public ResponseEntity<CandidateDTO> addCandidate(CandidateDTO candidateDTO) {
        return ResponseEntity.ok(candidateMapper
                .toCandidateDTO(candidateService
                        .create(candidateMapper
                                .toCandidate(candidateDTO))));
    }

    @Override
    public ResponseEntity<CandidateDTO> getCandidateByCode(String candidatecode) {
        return ResponseEntity.ok(candidateMapper
                .toCandidateDTO(candidateService
                        .findByCode(candidatecode)));
    }

    @Override
    public ResponseEntity<Void> deleteCandidate(String candidatecode) {
        var result = candidateService.deleteBeCode(candidatecode);
        return ResponseEntity.status(result? HttpStatus.OK: HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<CandidateDTO> updateCandidate(String candidatecode, CandidateDTO candidateDTO) {
        return ResponseEntity.ok(candidateMapper
                .toCandidateDTO(candidateService
                        .update(candidatecode, candidateMapper
                                .toCandidate(candidateDTO))));
    }

    @Override
    public ResponseEntity<CandidateDTO> patchCandidate(String candidatecode, PatchedCandidateDTO patchedCandidateDTO) {
        return ResponseEntity.ok(candidateMapper
                .toCandidateDTO(candidateService
                        .patch(candidatecode, candidateMapper
                                .toCandidate(patchedCandidateDTO))));
    }
}
