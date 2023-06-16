import './App.css';
import React, {useState} from 'react';

function App() {

    const [form, setForm] = useState({
        clientCode: ''
    });

    const [data, setData] = useState();

    const findClient = (clientCode) =>
	    fetch(`http://localhost:8080/person/${clientCode}`, {method: "GET", headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*"
          }})
        .then(response => {return response.json()});
        
    const handleClientCode = (event) => {
        setForm({
            ...form,
            [event.target.id]: event.target.value,
        });
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        
        findClient(form.clientCode).then(response => setData(response));

        setForm({
            clientCode:''
        })
    }

  return (
    <div>
        <form onSubmit = {handleSubmit}>
            <div>
                <label htmlFor="clientCode">Client code</label>
                <input
                    id="clientCode"
                    type="text"
                    value = {form.clientCode}
                    onChange = {handleClientCode}/>
            </div>
            <button type="submit">Submit</button>
         </form>

         <pre>{JSON.stringify(data, ' ', 2)}</pre>
    </div>
  );
}

export default App;
