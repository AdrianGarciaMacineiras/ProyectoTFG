package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.contract.mapper.OpportunityMapper;
import com.sngular.skilltree.infraestructura.OpportunityRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.OpportunityCrudRepository;
import com.sngular.skilltree.model.Opportunity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OpportunityUpdaterImpl implements OpportunityUpdater{

    private final OpportunityRepository opportunityRepository;

    private final OpportunityCrudRepository crud;

    private final OpportunityMapper mapper;

    @Override
    public Opportunity update(final String opportunitycode, final Opportunity newOpportunity) {
        validate(opportunitycode);
        crud.detachDelete(opportunitycode);
        return opportunityRepository.save(newOpportunity);
    }

    @Override
    public Opportunity patch(final String opportunitycode, final Opportunity patchedOpportunity) {
        validate(opportunitycode);
        var oldOpportunity = opportunityRepository.findByCode(opportunitycode);
        var opportunity = mapper.update(patchedOpportunity, oldOpportunity);
        crud.detachDelete(opportunitycode);
        return opportunityRepository.save(opportunity);
    }

    private void validate(String code) {
        var oldOpportunity = opportunityRepository.findByCode(code);
        if (Objects.isNull(oldOpportunity) || oldOpportunity.deleted()) {
            throw new EntityNotFoundException("Opportunity", code);
        }
    }

}
