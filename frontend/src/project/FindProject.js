import '../network.css';

import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import React, {useState} from 'react';
import VisGraph from 'react-vis-graph-wrapper';

import Footer from '../components/Footer';
// Material Dashboard 2 React example components
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
// Material Dashboard 2 React components
import MDBox from '../components/MDBox';
import MDButton from '../components/MDButton';
import MDInput from '../components/MDInput';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';

function FindProject() {
  const [form, setForm] = useState({projectCode: ''});

  const graphTemp = {nodes: [], edges: []};

  const [graph, setGraph] = useState({nodes: [], edges: []});

  const [aux, setAux] = useState([]);

  const options = {
    layout: {improvedLayout: true},
    nodes: {shape: 'dot', scaling: {min: 10, label: false}},
    edges: {
      color: '#000000',
      smooth: {enabled: true, type: 'discrete', roundness: 0.5}
    },
    groups: {
      client: {color: {background: 'red'}, borderWidth: 3},
      skills: {color: {background: 'green'}, borderWidth: 3}
    },
    height: '800px',
    physics: {
      barnesHut: {
        gravitationalConstant: -11500,
        centralGravity: 0.5,
        springLength: 270,
        springConstant: 0.135,
        avoidOverlap: 0.02
      },
      minVelocity: 0.75
    }
  };

  const events = {
    select: function(event) {
      var {nodes, edges} = event;
    }
  };

  const findProject = (projectCode) =>
      fetch(`//${window.location.hostname}/project/${projectCode}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*'
        }
      })
          .then(response => {return response.json()})
          .then(response => {
            setAux(response);
            var i = 1;
            var temp = {
              Code: response.code,
              Name: response.name,
              InitDate: response.initDate,
              Descripcion: response.desc,
              Area: response.area,
              Guards: response.guards,
              Duration: response.duration,
              Domain: response.domain,
              Tag: response.tag
            };
            graphTemp.nodes.push({
              id: i,
              label: response.name,
              title: JSON.stringify(temp, '', 2)
            });

            i++
            graphTemp.nodes.push({
              id: i,
              label: response.clientCode,
              title: JSON.stringify(response.clientCode, '', 2),
              groups: 'client'
            });
            graphTemp.edges.push({
              from: 1,
              to: i,
              label: 'FOR_CLIENT',
              title: response.clientCode
            });

            response.skills?.forEach(element => {
              i++
              graphTemp.nodes.push(
                  {id: i, label: element, title: element, groups: 'skills'});
              graphTemp.edges.push({
                from: 1,
                to: i,
                label: 'REQUIRE',
                title: response.clientCode
              });
            });

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

    setForm({projectCode: ''})        
    }

  return (
      <DashboardLayout>
          <DashboardNavbar />
          <MDBox pt={6} pb={3}>
              <Grid container spacing={6}>
                  <Grid item xs={12}>
                      <Card>
                          <MDBox
    mx = {2} mt = {-3} py = {3} px = {2} variant = 'gradient'
    bgColor = 'info'
    borderRadius = 'lg'
    coloredShadow = 'info' > <MDTypography variant = 'h6' color = 'white'>Find
    Project</MDTypography>
                          </MDBox><MDBox pt = {3}>
        <form onSubmit = {handleSubmit}><MDBox>
        <MDTypography variant = 'h6' fontWeight = 'medium'>Project code<
            /MDTypography>
                <MDInput
                    id="projectCode"
                    type="text"
                    value = {form.projectCode}
                    onChange = {handleProjectCode}/>
        </MDBox>
            <MDButton variant="gradient" color="dark"  onClick={handleSubmit}>Submit</MDButton>
        </form>
                          </MDBox>
        </Card>
                  </Grid><Grid item xs = {12}><Card>< MDBox
    mx = {2} mt = {-3} py = {3} px = {2} variant = 'gradient'
    bgColor = 'info'
    borderRadius = 'lg'
    coloredShadow =
        'info' > <MDTypography variant = 'h6' color = 'white'>Project
    Graph</MDTypography>
                          </MDBox><MDBox pt = {3}><
        VisGraph
    graph = {graph} options = {options} events = {events} getNetwork =
    {
      network => {
        //  if you want access to vis.js network api you can set the state in a
        //  parent component using this property
      }
    } />
                          </MDBox > </Card>
                  </Grid>
        </Grid>
          </MDBox><Footer /><
        /DashboardLayout>
  );
}

export default FindProject;
