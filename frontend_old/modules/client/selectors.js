const getModuleState = state => state.client;

export const getClient = state =>
    getModuleState(state).client;