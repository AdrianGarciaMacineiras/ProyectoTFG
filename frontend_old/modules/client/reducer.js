import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    client: null
};

const client = (state = initialState.client, action) => {
    switch (action.type) {
		case actionTypes.FIND_CLIENT_COMPLETED:
			return action.client;

		case actionTypes.CLEAR_CLIENT_SEARCH:
			return initialState.client;
        default:
            return state;
    }
}

const reducer = combineReducers({
    client
});

export default reducer;