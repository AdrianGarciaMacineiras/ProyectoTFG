import './App.css';
import React, {useState} from 'react';
import "./network.css";
import VisGraph from 'react-vis-graph-wrapper';

function App() {

    const [form, setForm] = useState({
        clientCode: ''
    });

    const graphTemp = {
      nodes:[],
      edges:[]
    };

    /*const graph = {
      nodes: [
        { id: 1, label: "Node 1", title: "node 1 tootip text" },
        { id: 2, label: "Node 2", title: "node 2 tootip text" },
        { id: 3, label: "Node 3", title: "node 3 tootip text" },
        { id: 4, label: "Node 4", title: "node 4 tootip text" },
        { id: 5, label: "Node 5", title: "node 5 tootip text" }
      ],
      edges: [
        { from: 1, to: 2 },
        { from: 1, to: 3 },
        { from: 2, to: 4 },
        { from: 2, to: 5 }
      ]
    };*/

    const [graph, setGraph] = useState({
      nodes:[],
      edges:[]
    });



    const [aux, setAux] = useState([]);

    const [isToggled, setIsToggled] = useState(false);

  
    const options = {
      layout: {
          improvedLayout: true
      },
      edges: {
        color: "#000000"
      },
      height: "500px",
      physics: {
        enabled: true
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
          var i = 1;
          var temp ={Name:response.name, Surname:response.surname, Email:response.email, EmployeeId:response.employeeId,
            FriendlyName:response.friendlyName, Title:response.title}
          graphTemp.nodes.push({id:i, label: response.name + ' ' + response.surname, title: JSON.stringify(temp,'',2)});
          response.knows.forEach(element => {
            i++;
            var temp = {Primary:element.primary, Experience: element.experience, Level: element.level}
            graphTemp.nodes.push({id:i, label: element.code, title:  JSON.stringify(temp,'',2)});
            graphTemp.edges.push({from:1, to:i})
          });
          response.interest.forEach(element => {
            i++;
            graphTemp.edges.push({from:1, to:i})
            graphTemp.nodes.push({id:i, label: element, title: element});
          });
          console.log(graphTemp);
          setGraph(prev => graphTemp);
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
          
        <pre>{JSON.stringify(graph,'',2)}</pre>

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
