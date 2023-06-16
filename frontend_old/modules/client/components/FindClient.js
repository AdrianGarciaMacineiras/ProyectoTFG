import React, {useState, useEffect} from 'react';
import {useSelector, useDispatch} from 'react-redux';

import * as selectors from '../selectors';
import * as actions from '../actions';
import {Pager} from '../../common';
import {useParams} from 'react-router-dom';

const FindClient = () => {

    const client = useSelector(selectors.getClient);
    const [clientCode, setClientCode] = useState('');
    const dispatch = useDispatch();

    let form;

    const handleSubmit = event => {

            event.preventDefault();

            if(form.checkValidity()){
                dispatch(actions.findClient(clientCode));
            } else {
                setBackendErrors(null);
                form.classList.add('was-validated');
            }
        }

    return(
        <div className="card-body">
            <form ref={node => form = node}
                className="needs-validation" noValidate
                onSubmit={e => handleSubmit(e)}>
                <div className="form-group row">
                    <label id="clientName" htmlFor="clientCode" className="col-md-3 col-form-label"/>
                        <div className="col-md-4">
                            <input type="text" id="clientCode" className="form-control"
                                value={clientCode}
                                onSubmit={e => setClientCode(e.target.value)}
                                autoFocus
                                required/>
                            <div id="required" className="invalid-feedback"/>
                        </div>
                </div>
            </form>
        </div>
    )
}
