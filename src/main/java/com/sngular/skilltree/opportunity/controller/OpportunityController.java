package com.sngular.skilltree.opportunity.controller;

import com.sngular.skilltree.api.OpportunityApi;
import com.sngular.skilltree.api.model.OpportunityDTO;
import com.sngular.skilltree.api.model.PatchedOpportunityDTO;
import com.sngular.skilltree.opportunity.model.Opportunity;
import com.sngular.skilltree.opportunity.repository.neo4j.repository.OpportunityRepository;
import com.sngular.skilltree.opportunity.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OpportunityController implements OpportunityApi {

    private final OpportunityService opportunityService;
    private final OpportunityRepository opportunityRepository;

    @Override
    public ResponseEntity<List<OpportunityDTO>> getOpportunities() {
        Flux<Opportunity> list = (Flux<Opportunity>) opportunityRepository.findAll();
        return OpportunityApi.super.getOpportunities();
    }

    @Override
    public ResponseEntity<OpportunityDTO> addOpportunity(OpportunityDTO opportunityDTO) {
        return OpportunityApi.super.addOpportunity(opportunityDTO);
    }

    @Override
    public ResponseEntity<OpportunityDTO> getOpportunityByCode(String opportunitycode) {
        return OpportunityApi.super.getOpportunityByCode(opportunitycode);
    }

    @Override
    public ResponseEntity<Void> deleteOpportunity(String opportunitycode) {
        return OpportunityApi.super.deleteOpportunity(opportunitycode);
    }

    @Override
    public ResponseEntity<OpportunityDTO> updateOpportunity(String opportunitycode, OpportunityDTO opportunityDTO) {
        return OpportunityApi.super.updateOpportunity(opportunitycode, opportunityDTO);
    }

    @Override
    public ResponseEntity<OpportunityDTO> patchOpportunity(String opportunitycode, PatchedOpportunityDTO patchedOpportunityDTO) {
        return OpportunityApi.super.patchOpportunity(opportunitycode, patchedOpportunityDTO);
    }
}
