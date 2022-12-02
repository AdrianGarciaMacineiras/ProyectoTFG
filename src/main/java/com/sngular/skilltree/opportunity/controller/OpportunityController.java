package com.sngular.skilltree.opportunity.controller;

import com.sngular.skilltree.api.OpportunityApi;
import com.sngular.skilltree.api.model.OpportunityDTO;
import com.sngular.skilltree.api.model.PatchedOpportunityDTO;
import com.sngular.skilltree.opportunity.mapper.OpportunityMapper;
import com.sngular.skilltree.opportunity.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OpportunityController implements OpportunityApi {

    private final OpportunityService opportunityService;

    private final OpportunityMapper opportunityMapper;

    @Override
    public ResponseEntity<List<OpportunityDTO>> getOpportunities() {
        var opportunityList = opportunityService.getAll();
        return ResponseEntity.ok(opportunityMapper.toOpportunitiesDTO(opportunityList));
    }

    @Override
    public ResponseEntity<OpportunityDTO> addOpportunity(OpportunityDTO opportunityDTO) {
        return ResponseEntity.ok(opportunityMapper
                .toOpportunityDTO(opportunityService
                        .create(opportunityMapper
                                .toOpportunity(opportunityDTO))));
    }

    @Override
    public ResponseEntity<OpportunityDTO> getOpportunityByCode(String opportunitycode) {
        return ResponseEntity.ok(opportunityMapper
                .toOpportunityDTO(opportunityService
                        .findByCode(opportunitycode)));
    }

    @Override
    public ResponseEntity<Void> deleteOpportunity(String opportunitycode) {
        var result = opportunityService.deleteBeCode(opportunitycode);
        return ResponseEntity.status(result? HttpStatus.OK: HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<OpportunityDTO> updateOpportunity(String opportunitycode, OpportunityDTO opportunityDTO) {
        return ResponseEntity.ok(opportunityMapper
                .toOpportunityDTO(opportunityService
                        .update(opportunitycode, opportunityMapper
                                .toOpportunity(opportunityDTO))));
    }

    @Override
    public ResponseEntity<OpportunityDTO> patchOpportunity(String opportunitycode, PatchedOpportunityDTO patchedOpportunityDTO) {
        return ResponseEntity.ok(opportunityMapper
                .toOpportunityDTO(opportunityService
                        .patch(opportunitycode, opportunityMapper
                                .toOpportunity(patchedOpportunityDTO))));
    }
}
