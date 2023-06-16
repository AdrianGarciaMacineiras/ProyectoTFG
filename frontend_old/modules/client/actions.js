import * as actionTypes from './actionTypes';
import backend from '../../backend';
import * as selectors from './selectors';


export const findClient = (criteria, clientCreated) => (dispatch, getState) => {
    const client = selectors.getClient(getState());
    if (!client || clientCreated) {
		dispatch(clearClient());
        backend.clientService.findClient(criteria,
			result => dispatch(findClientCompleted({criteria, result})));
    }
}

export const clearClient = () => ({
	type: actionTypes.CLEAR_CLIENT_SEARCH
});

export const findClientCompleted = client => ({
    type: actionTypes.FIND_CLIENT_COMPLETED,
    client
});