
import {combineReducers} from 'redux';
import app from '../modules/app';
import users from '../modules/client';


const rootReducer = combineReducers({
    app: app.reducer,
    client: client.reducer
});

export default rootReducer;