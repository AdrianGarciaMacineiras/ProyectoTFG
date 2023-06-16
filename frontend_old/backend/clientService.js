import {fetchConfig, appFetch} from './appFetch';

export const findClient = (clientCode, onSuccess) =>
	appFetch(`/client/${clientCode}`, fetchConfig('GET'), onSuccess);