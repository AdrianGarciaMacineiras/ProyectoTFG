package com.tfg.skilltree.application.updater;

import com.tfg.skilltree.model.Position;

public interface PositionUpdater {

    Position update(final String positionCode, final Position newPosition);

    Position patch(final String positionCode, final Position patchedPosition);
}
