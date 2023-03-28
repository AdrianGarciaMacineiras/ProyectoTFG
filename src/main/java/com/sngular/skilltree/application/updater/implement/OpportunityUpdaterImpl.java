package com.sngular.skilltree.application.updater.implement;

import com.sngular.skilltree.application.updater.OpportunityUpdater;
import com.sngular.skilltree.contract.mapper.PuestoMapper;
import com.sngular.skilltree.infraestructura.PuestoRepository;
import com.sngular.skilltree.model.Puesto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpportunityUpdaterImpl implements OpportunityUpdater {

    private final PuestoRepository puestoRepository;

    private final PuestoMapper mapper;

    @Override
    public Puesto update(final String opportunitycode, final Puesto newPuesto) {
        var oldOpportunity = puestoRepository.findByCode(opportunitycode);
        //mapper.update(oldOpportunity, newOpportunity);
        return puestoRepository.save(newPuesto);
    }

    @Override
    public Puesto patch(final String opportunitycode, final Puesto patchedPuesto) {
        var oldOpportunity = puestoRepository.findByCode(opportunitycode);
        var opportunity = mapper.update(patchedPuesto, oldOpportunity);
        return puestoRepository.save(opportunity);
    }

}
