import React, {useState} from 'react';

const FindClient = () => {

    const [clientCode, setClientCode] = useState('');

    const handleClientCode = (event) => {
        setClientCode(event.target.value)
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        alert(clientCode);

    }

  return (
    <form onSubmit = {handleSubmit}>
      <div>
        <label htmlFor="clientCode">ClientCOde</label>
        <input
            id="clientCode"
            type="text"
            value = clientCode
            onChange = {handleClientCode}/>
      </div>
      <button type="submit">Submit</button>
    </form>
  );
};

export { FindClient };