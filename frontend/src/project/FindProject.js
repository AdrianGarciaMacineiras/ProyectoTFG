import React, {useState} from 'react';
import "../network.css";
import VisGraph from 'react-vis-graph-wrapper';
import Grid from "@mui/material/Grid";
import Card from "@mui/material/Card";

// Material Dashboard 2 React components
import MDBox from "../components/MDBox";
import MDTypography from "../components/MDTypography";
import MDInput from '../components/MDInput';
import MDButton from "../components/MDButton";

// Material Dashboard 2 React example components
import DashboardLayout from "../components/LayoutContainers/DashboardLayout";
import DashboardNavbar from "../components/Navbars/DashboardNavbar";
import Footer from "../components/Footer";

function FindProject() {

    const [form, setForm] = useState({
        projectCode: ''
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

    const findProject = (projectCode) =>
	    fetch(`http://localhost:9080/project/${projectCode}`, {method: "GET", headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*"
          }})
        .then(response => {return response.json()})
        .then(response => {
            setAux(response);
            var i = 1;
            var temp ={Code: response.code, Name:response.name, InitDate:response.initDate, Descripcion:response.desc, Area:response.area,
                Guards:response.guards, Duration:response.duration, Domain: response.domain, Tag: response.tag}
            graphTemp.nodes.push({id:i, label: response.name , title: JSON.stringify(temp,'',2)});

            i++
            graphTemp.nodes.push({id:i, label: response.clientCode , title: JSON.stringify(response.clientCode,'',2)});
            graphTemp.edges.push({from:1, to: i, label:"FOR_CLIENT", title: response.clientCode});
            
            console.log(graphTemp);
            setGraph(prev => graphTemp);
        });

    const handleProjectCode = (event) => {
        setForm({
            ...form,
            [event.target.id]: event.target.value,
        });
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        
        findProject(form.projectCode);

        setForm({
            projectCode:''
        })        
    }

  return (
      <DashboardLayout>
          <DashboardNavbar />
          <MDBox pt={6} pb={3}>
              <Grid container spacing={6}>
                  <Grid item xs={12}>
                      <Card>
                          <MDBox
                              mx={2}
                              mt={-3}
                              py={3}
                              px={2}
                              variant="gradient"
                              bgColor="info"
                              borderRadius="lg"
                              coloredShadow="info"
                          >
                              <MDTypography variant="h6" color="white">Find Project</MDTypography>
                          </MDBox>
                          <MDBox pt={3}>
        <form onSubmit = {handleSubmit}>
            <MDBox>
                <MDTypography variant="h6" fontWeight="medium">Project code</MDTypography>
                <MDInput
                    id="projectCode"
                    type="text"
                    value = {form.projectCode}
                    onChange = {handleProjectCode}/>
            </MDBox>
            <MDButton variant="gradient" color="dark">Submit</MDButton>
         </form>
                          </MDBox>
                      </Card>
                  </Grid>
                  <Grid item xs={12}>
                      <Card>
                          <MDBox
                              mx={2}
                              mt={-3}
                              py={3}
                              px={2}
                              variant="gradient"
                              bgColor="info"
                              borderRadius="lg"
                              coloredShadow="info"
                          >
                              <MDTypography variant="h6" color="white">Project Graph </MDTypography>
                          </MDBox>
                          <MDBox pt={3}>
          <VisGraph
            graph={graph}
            options={options}
            events={events}
            getNetwork={network => {
              //  if you want access to vis.js network api you can set the state in a parent component using this property
            }}
          />
                          </MDBox>
                      </Card>
                  </Grid>
              </Grid>
          </MDBox>
          <Footer />
      </DashboardLayout>
  );
}

export default FindProject;
