package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.contract.mapper.OpportunityMapper;
import com.sngular.skilltree.infraestructura.OpportunityRepository;
import com.sngular.skilltree.model.Opportunity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpportunityUpdaterImpl implements OpportunityUpdater{

    private final OpportunityRepository opportunityRepository;

    private final OpportunityMapper mapper;

    @Override
    public Opportunity update(final String opportunitycode, final Opportunity newOpportunity) {
        var oldOpportunity = opportunityRepository.findByCode(opportunitycode);
        mapper.update(oldOpportunity, newOpportunity);
        return opportunityRepository.save(oldOpportunity);
    }

    @Override
    public Opportunity patch(final String opportunitycode, final Opportunity patchedOpportunity) {
        var oldOpportunity = opportunityRepository.findByCode(opportunitycode);
        mapper.update(oldOpportunity, patchedOpportunity);
        opportunityRepository.save(oldOpportunity);
        return opportunityRepository.save(oldOpportunity);
    }

}
