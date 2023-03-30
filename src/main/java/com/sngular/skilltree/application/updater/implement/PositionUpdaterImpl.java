package com.sngular.skilltree.application.updater.implement;

import com.sngular.skilltree.application.updater.PositionUpdater;
import com.sngular.skilltree.contract.mapper.PositionMapper;
import com.sngular.skilltree.infraestructura.PositionRepository;
import com.sngular.skilltree.model.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionUpdaterImpl implements PositionUpdater {

    private final PositionRepository positionRepository;

    private final PositionMapper mapper;

    @Override
    public Position update(final String positioncode, final Position newPosition) {
        var oldPosition = positionRepository.findByCode(positioncode);
        //mapper.update(oldOpportunity, newOpportunity);
        return positionRepository.save(newPosition);
    }

    @Override
    public Position patch(final String positioncode, final Position patchedPosition) {
        var oldPosition = positionRepository.findByCode(positioncode);
        var position = mapper.update(patchedPosition, oldPosition);
        return positionRepository.save(position);
    }

}
