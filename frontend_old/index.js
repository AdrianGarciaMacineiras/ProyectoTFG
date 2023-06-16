import React from 'react';
import ReactDOM from 'react-dom';
import 'react-app-polyfill/ie11';
import 'react-app-polyfill/stable';
import {Provider} from 'react-redux';
import {IntlProvider} from 'react-intl';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap';
import '@fortawesome/fontawesome-free/css/fontawesome.css';
import '@fortawesome/fontawesome-free/css/solid.css';
import './index.css';
import * as serviceWorker from './registerServiceWorker';
import configureStore from './store';
import {App} from './modules/app';
import backend from './backend';
import {initReactIntl} from './i18n';

const store = configureStore();

backend.init(error => store.dispatch(app.actions.error(new NetworkError())));

const {locale, messages} = initReactIntl();

ReactDOM.render(
    <React.StrictMode>
        <Provider store={store}>
        	<IntlProvider locale={locale} messages={messages}>
				<App/>
			</IntlProvider>
		</Provider>
    </React.StrictMode>,
    document.getElementById('root'));

serviceWorker.unregister();
