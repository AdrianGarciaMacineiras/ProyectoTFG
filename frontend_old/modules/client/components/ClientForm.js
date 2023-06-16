import React, {useState} from 'react';
import {useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import {useHistory} from 'react-router-dom';
import {Errors} from '../../common';
import * as actions from '../actions';

const ClientForm = () => {

    const dispatch = useDispatch();
    const history = useHistory();
    const [clientName, setClientName] = useState('');
    const [industry, setIndustry] = useState('');
    const [country, setCountry] = useState('')
    const [principalOffice, setPrincipalOffice] = useState('');
    const [backendErrors, setBackendErrors] = useState(null);

    let form;

    const handleSubmit = event => {

        event.preventDefault();

        if(form.checkValidity()){
            dispatch(actions.createClient(
                clientName,
                industry,
                country,
                principalOffice,
                () => history.push('/'),
                errors => setBackendErrors(errors),
                () => history.push('/')
            ));
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
                    <label id="clientName" htmlFor="clientName" className="col-md-3 col-form-label"/>
                    <div className="col-md-4">
                        <input type="text" id="clientName" className="form-control"
                            value={clientName}
                            onChange={e => setClientName(e.target.value)}
                            autoFocus
                            required/>
                        <div id="required" className="invalid-feedback"/>
                    </div>
                </div>
                <div className="form-group row">
                    <label id="industry" htmlFor="industry" className="col-md-3 col-form-label"/>
                    <div className="col-md-4">
                        <input type="text" id="industry" className="form-control"
                            value={industry}
                            onChange={e => setIndustry(e.target.value)}
                            required/>
                        <div id="required" className="invalid-feedback"/>
                    </div>
                </div>
                <div className="form-group row">
                    <label id="country" htmlFor="country" className="col-md-3 col-form-label"/>
                    <div className="col-md-4">
                        <input type="text" id="country" className="form-control"
                            value={country}
                            onChange={e => setCountry(e.target.value)}
                            autoFocus
                            required/>
                        <div id="required" className="invalid-feedback"/>
                    </div>
                </div>
           						<div className="form-group row">
                                       <label htmlFor="revenue" className="col-md-3 col-form-label">
           								<FormattedMessage id="project.global.fields.totalStocks"/>
                                       </label>
                                       <div className="col-md-4">
                                           <input type="text" id="revenue" className="form-control"
                                               value={totalStocks}
                                               onChange={e => setTotalStocks(e.target.value)}
                                               autoFocus
                                               required/>
                                           <div className="invalid-feedback">
                                               <FormattedMessage id='project.global.validator.required'/>
                                           </div>
                                       </div>
                                   </div>
           						<div className="form-group row">
                                       <label htmlFor="income" className="col-md-3 col-form-label">
           								<FormattedMessage id="project.global.fields.instantPrice"/>
                                       </label>
                                       <div className="col-md-4">
                                           <input type="text" id="income" className="form-control"
                                               value={instantPrice}
                                               onChange={e => setInstantPrice(e.target.value)}
                                               autoFocus
                                               required/>
                                           <div className="invalid-feedback">
                                               <FormattedMessage id='project.global.validator.required'/>
                                           </div>
                                       </div>
                                   </div>
                                   <div className="form-group row">
                                       <div className="offset-md-3 col-md-1">
                                           <button type="submit" className="btn btn-primary">
           									<FormattedMessage id="project.global.buttons.create"/>
                                           </button>
                                       </div>
                                   </div>
                               </form>
                           </div>)


}