package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.Position;

public interface PositionUpdater {

    Position update(final String positionCode, final Position newPosition);

    Position patch(final String positionCode, final Position patchedPosition);
}
