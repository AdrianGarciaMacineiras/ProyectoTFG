package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.Position;

public interface PositionUpdater {

    Position update(final String positioncode, final Position newPosition);

    Position patch(final String positioncode, final Position patchedPosition);
}
