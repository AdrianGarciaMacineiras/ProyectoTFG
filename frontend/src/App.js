import './App.css';
import React, {useState} from 'react';
import "./network.css";
import VisGraph from 'react-vis-graph-wrapper';

function App() {

    const [form, setForm] = useState({
        clientCode: ''
    });

    const [data, setData] = useState([{
      Name:'',
      Surname:'',
      Email:'',
      EmployeeId:'',
      FriendlyName:'',
      Title:'',
    }]);

    const [interest, setInterest] = useState([]);

    const [aux, setAux] = useState([]);

    const[knows, setKnows] = useState([]);

    const [isToggled, setIsToggled] = useState(false);

    const [nodes, setNodes] = useState([]);

    const graph = {
      nodes:[],
      edges:[]
    }

    const options = {
      layout: {
          improvedLayout: true
      },
      edges: {
        color: "#000000"
      },
      height: "500px",
      physics: {
        enabled: false
      }
    };
    
    const events = {
      select: function(event) {
        var { nodes, edges } = event;
      }
    };

    const findClient = (clientCode) =>
	    fetch(`http://localhost:9080/person/${clientCode}`, {method: "GET", headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*"
          }})
        .then(response => {return response.json()})
        .then(response => {
          setAux(response);
          setData({Name:response.name, Surname:response.surname, Email:response.email, EmployeeId:response.employeeId,
          FriendlyName:response.friendlyName, Title:response.title});
          setKnows(response.knows);
          setInterest(response.interest);
        });

    const handleClientCode = (event) => {
        setForm({
            ...form,
            [event.target.id]: event.target.value,
        });
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        
        findClient(form.clientCode);

        setForm({
            clientCode:''
        })
        
        setIsToggled(!isToggled);

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
          
          <pre>{JSON.stringify(aux,'',2)}</pre>

        {isToggled &&
          <VisGraph
            graph={graph}
            options={options}
            events={events}
            getNetwork={network => {
              //  if you want access to vis.js network api you can set the state in a parent component using this property
            }}
          /> 
        }
    </div>
  );
}

export default App;
