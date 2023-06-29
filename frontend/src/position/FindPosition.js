import React, {useState} from 'react';
import "../network.css";
import VisGraph from 'react-vis-graph-wrapper';

function FindPosition() {

    const [form, setForm] = useState({
        positionCode: ''
    });

    const graphTemp = {
      nodes:[],
      edges:[]
    };

    const [graph, setGraph] = useState({
      nodes:[],
      edges:[]
    });

    const [aux, setAux] = useState([]);
  
    const options = {
      layout: {
          improvedLayout: true
      },
      nodes:{
        shape: "dot",
        scaling: {min:10,label:false}
      },
      edges: {
        color: "#000000",
        smooth: {
          enabled: true,
          type: "discrete",
          roundness: 0.5
        }
      },
      groups: {
        mainSkill: {color:{background:'red'}, borderWidth:3},
      },
      height: "800px",
      physics: {
        barnesHut: {
          gravitationalConstant: -11500,
          centralGravity: 0.5,
          springLength: 270,
          springConstant: 0.135,
          avoidOverlap: 0.02
        },
        minVelocity: 0.75
      },
      configure: {
        enabled: true,
        filter: 'physics, layout',
        showButton: true
     },
      interaction: {
        hover: true,
        hoverConnectedEdges: true,
        selectable: true,
        selectConnectedEdges: true
      }
    };
    
    const events = {
      select: function(event) {
        var { nodes, edges } = event;
      }
    };

    const FindPosition = (positionCode) =>
	    fetch(`http://localhost:9080/position/${positionCode}`, {method: "GET", headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*"
          }})
        .then(response => {return response.json()})
        .then(response => {
          setAux(response);
          var i = 1
          var temp = {Code: response.code, Active: response.active, Role: response.role, EndDate: response.closingDate, InitDate: response.openingDate}
          graphTemp.nodes.push({id:i, label: response.code, title: JSON.stringify(temp,'',2), group:"mainSkill"});

          response.assignedPeople.forEach(element => {
            i++;
            var temp ={AssignDate: element.assignDate, InitDate: element.initDate, EndDate:element.endDate, Dedication: element.dedication, Role: element.role}
            graphTemp.nodes.push({id: i, label: element.assigned, title: JSON.stringify(element.assigned,'',2)});
            graphTemp.edges.push({from: i, to: 1, label: "COVER", title: JSON.stringify(temp,'',2) })
          });

          i++
          graphTemp.nodes.push({id: i, label: response.projectCode, title: JSON.stringify(response.projectCode,'',2)});
          graphTemp.edges.push({from: i, to: 1, label: "FOR_PROJECT", title: JSON.stringify(response.projectCode,'',2)});

          console.log(graphTemp);
          setGraph(prev => graphTemp);
        });

    const handlePositionCode = (event) => {
        setForm({
            ...form,
            [event.target.id]: event.target.value,
        });
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        
        FindPosition(form.positionCode);

        setForm({
            positionCode:''
        })        
    }

  return (
    <div>
        <form onSubmit = {handleSubmit}>
            <div>
                <label htmlFor="positionCode">Position code</label>
                <input
                    id="positionCode"
                    type="text"
                    value = {form.positionCode}
                    onChange = {handlePositionCode}/>
            </div>
            <button type="submit">Submit</button>
         </form>
         
          <VisGraph
            graph={graph}
            options={options}
            events={events}
            getNetwork={network => {
              //  if you want access to vis.js network api you can set the state in a parent component using this property
            }}
          /> 
        

        <pre>{JSON.stringify(graph,'',2)}</pre>
        <pre>{JSON.stringify(aux,'',2)}</pre>

        
    </div>
  );
}

export default FindPosition;
