import React, {useState} from 'react';
import "../network.css";
import VisGraph from 'react-vis-graph-wrapper';

function FindSkill() {

    const [form, setForm] = useState({
        skillCode: ''
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

    const FindSkill = (skillCode) =>
	    fetch(`http://localhost:9080/skills/${skillCode}`, {method: "GET", headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*"
          }})
        .then(response => {return response.json()})
        .then(response => {
          setAux(response);
          var i = 1
          var temp = {Name: response.name, Code: response.code}
          graphTemp.nodes.push({id:i, label: response.name, title: JSON.stringify(temp,'',2), group:"mainSkill"});
          response.subSkills.forEach(element => {
            i++;
            var idHijo = i;
            var temp = {Name: element.name, Code: element.code}
            graphTemp.nodes.push({id:idHijo, label: element.name, title: JSON.stringify(temp,'',2)});
            graphTemp.edges.push({from:1, to: i, label: "REQUIRE"})
            element.subSkills.forEach(subskill => {
              i++;
              var temp = {Name: subskill.name, Code: subskill.code}
              graphTemp.nodes.push({id:i, label: subskill.name, title: JSON.stringify(temp,'',2)});
              graphTemp.edges.push({from:idHijo, to: i, label: "REQUIRE"})
            })
          });
          console.log(graphTemp);
          setGraph(prev => graphTemp);
        });

    const handleSkillCode = (event) => {
        setForm({
            ...form,
            [event.target.id]: event.target.value,
        });
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        
        FindSkill(form.skillCode);

        setForm({
            skillCode:''
        })        
    }

  return (
    <div>
        <form onSubmit = {handleSubmit}>
            <div>
                <label htmlFor="skillCode">Skill code</label>
                <input
                    id="skillCode"
                    type="text"
                    value = {form.skillCode}
                    onChange = {handleSkillCode}/>
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

export default FindSkill;
