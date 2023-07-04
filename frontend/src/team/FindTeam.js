import React, {useState} from 'react';
import "../network.css";
import VisGraph from 'react-vis-graph-wrapper';

function FindTeam() {

    const [form, setForm] = useState({
        teamCode: ''
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
        team: {color:{background:'red'}, borderWidth:3},
        members: {color:{background:'blue'}, borderWidth:3},
        skills: {color:{background:'green'}, borderWidth:3},
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

    const findTeam = (teamCode) =>
	    fetch(`http://localhost:9080/team/${teamCode}`, {method: "GET", headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*"
          }})
        .then(response => {return response.json()})
        .then(response => {
          setAux(response);
          var i = 1;
          var temp = {Code: response.code, Name: response.name, Description: response.description, Tags: response.tags}      
          graphTemp.nodes.push({id: i, label: response.name, title: JSON.stringify(temp, '', 2), group: "team"})  
          
          response.members.forEach(element=>{
            i++
            var temp ={Code: element.people.code, Name:element.people.name, Surname:element.people.surname, Email:element.people.email, EmployeeId:element.people.employeeId,
                FriendlyName:element.people.friendlyName, Title:element.people.title, BirthDate: element.people.birthDate}
              graphTemp.nodes.push({id:i, label: element.people.name + ' ' + element.people.surname, title: JSON.stringify(temp,'',2), group: "members"});
              console.log(element.charge)
              graphTemp.edges.push({from:i, to: 1, label: "MEMBER_OF", title: element.charge});
          });
          
          response.strategics.forEach(element=>{
            i++
            var temp = {Name: element.name, Code: element.code}
            graphTemp.nodes.push({id:i, label: element.name, title: JSON.stringify(temp,'',2), group:"skills"});
            graphTemp.edges.push({from:1, to: i, label: "STRATEGIC"});
          });

          console.log(graphTemp);
          setGraph(prev => graphTemp);
        });

    const handleTeamCode = (event) => {
        setForm({
            ...form,
            [event.target.id]: event.target.value,
        });
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        
        findTeam(form.teamCode);

        setForm({
            teamCode:''
        })        
    }

  return (
    <div>
        <form onSubmit = {handleSubmit}>
            <div>
                <label htmlFor="teamCode">Team code</label>
                <input
                    id="teamCode"
                    type="text"
                    value = {form.teamCode}
                    onChange = {handleTeamCode}/>
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

export default FindTeam;
