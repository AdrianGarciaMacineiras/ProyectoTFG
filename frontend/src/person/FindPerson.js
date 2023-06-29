import React, {useState} from 'react';
import "../network.css";
import VisGraph from 'react-vis-graph-wrapper';

function FindPerson() {

    const [form, setForm] = useState({
        personCode: ''
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
        knows: {color:{background:'red'}, borderWidth:3},
        interest: {color:{background:'blue'}, borderWidth:3},
        work_with: {color:{background:'green'}, borderWidth:3},
        master: {color:{background:'orange'}, borderWidth:3},
        have_certificate: {color:{background:'yellow'}, borderWidth:3},
        position: {color:{background:'white'}, borderWidth:3},
        candidate: {color:{background:'pink'}, borderWidth:3},
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

    const findPerson = (personCode) =>
	    fetch(`http://localhost:9080/person/${personCode}`, {method: "GET", headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*"
          }})
        .then(response => {return response.json()})
        .then(response => {
          setAux(response);
          var i = 1;
          var temp ={Code: response.code, Name:response.name, Surname:response.surname, Email:response.email, EmployeeId:response.employeeId,
            FriendlyName:response.friendlyName, Title:response.title, BirthDate: response.birthDate}
          graphTemp.nodes.push({id:i, label: response.name + ' ' + response.surname, title: JSON.stringify(temp,'',2)});
          response.knows.forEach(element => {
            i++;
            var temp = {Code:element.code, Primary:element.primary, Experience: element.experience, Level: element.level}
            graphTemp.nodes.push({id:i, label: element.name, title:  JSON.stringify(temp,'',2), group: 'knows', value: element.experience*150});
            graphTemp.edges.push({from:1, to:i, label: "KNOWS"});
          });
          response.interest.forEach(element => {
            i++;
            graphTemp.nodes.push({id:i, label: element, title: element, group: 'interest', value: 10});
            graphTemp.edges.push({from:1, to:i, label: "INTEREST"});
          });
          response.work_with.forEach(element => {
            i++;
            graphTemp.nodes.push({id:i, label: element, title: element, group: 'work_with'});
            graphTemp.edges.push({from:1, to:i, label: "WORK_WITH"});
          });
          response.master.forEach(element => {
            i++;
            graphTemp.nodes.push({id:i, label: element, title: element, group: 'master'});
            graphTemp.edges.push({from:1, to:i, label: "MASTER"});
          });
          response.certificates.forEach(element => {
            i++;
            var temp = {Code:element.code, Name: element.name, Comments: element.comments, Date: element.date}
            graphTemp.nodes.push({id:i, label: element.code, title: JSON.stringify(temp,'',2), group: 'have_certificate'});
            graphTemp.edges.push({from:1, to:i, label: "HAVE_CERTIFICATE"});
          });
          response.assigns.forEach(element => {
            i++;
            graphTemp.nodes.push({id:i, label: element.name, title: element.name, group: 'position'});
            element.assignments.forEach(element => {
              var temp = {AssignDate: element.assignDate, InitDate: element.initDate, EndDate: element.endDate, Dedication:element.dedication, Role: element.role}
              graphTemp.edges.push({from:1, to:i, label: "COVER", title: JSON.stringify(temp,'',2)});
            })
          })
          response.candidacies.forEach(element => {
            var temp = {Code: element.code, IntroductionDate: element.introductionDate, ResolutionDate: element.resolutionDate, CreationDate: element.creationDate, 
            Status: element.status}
            graphTemp.edges.push({from:1, to:i, label: "CANDIDATE", title: JSON.stringify(temp,'',2), group: 'candidate'});
          });
          console.log(graphTemp);
          setGraph(prev => graphTemp);
        });

    const handlePersonCode = (event) => {
        setForm({
            ...form,
            [event.target.id]: event.target.value,
        });
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        
        findPerson(form.personCode);

        setForm({
            personCode:''
        })        
    }

  return (
    <div>
        <form onSubmit = {handleSubmit}>
            <div>
                <label htmlFor="personCode">Person code</label>
                <input
                    id="personCode"
                    type="text"
                    value = {form.personCode}
                    onChange = {handlePersonCode}/>
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

export default FindPerson;
