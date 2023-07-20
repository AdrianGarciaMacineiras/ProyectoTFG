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

function FindPosition() {
  const [form, setForm] = useState({positionCode: ''});

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
      assigned: {color: {background: 'red'}, borderWidth: 3},
      candidates: {color: {background: 'green'}, borderWidth: 3},
      project: {color: {background: 'yellow'}, borderWidth: 3},
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
    },
    configure: {enabled: true, filter: 'physics, layout', showButton: true},
    interaction: {
      hover: true,
      hoverConnectedEdges: true,
      selectable: true,
      selectConnectedEdges: true
    }
  };

  const events = {
    select: function(event) {
      var {nodes, edges} = event;
    }
  };

  const FindPosition = (positionCode) =>
      fetch(`http://localhost:9080/position/${positionCode}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*'
        }
      })
          .then(response => {return response.json()})
          .then(response => {
            setAux(response);
            var i = 1
            var temp = {
              Code: response.code,
              Active: response.active,
              Role: response.role,
              EndDate: response.closingDate,
              InitDate: response.openingDate
            };
            graphTemp.nodes.push({
              id: i,
              label: response.code,
              title: JSON.stringify(temp, '', 2)
            });

            response.assignedPeople.forEach(element => {
              i++;
              var temp = {
                AssignDate: element.assignDate,
                InitDate: element.initDate,
                EndDate: element.endDate,
                Dedication: element.dedication,
                Role: element.role
              };
               graphTemp.nodes.push({
                id: i,
                label: element.assigned,
                title: JSON.stringify(element.assigned, '', 2),
                group: 'assigned'
              });
              graphTemp.edges.push({
                from: i,
                to: 1,
                label: 'COVER',
                title: JSON.stringify(temp, '', 2)
              })
            });

            i++;
            graphTemp.nodes.push({
              id: i,
              label: response.projectCode,
              title: JSON.stringify(response.projectCode, '', 2),
              group: 'project'
            });
            graphTemp.edges.push({
              from: i,
              to: 1,
              label: 'FOR_PROJECT',
              title: JSON.stringify(response.projectCode, '', 2)
            });

            response.candidates.forEach(element => {
              i++;
              graphTemp.nodes.push({
                id: i,
                label: element.candidateCode,
                title: element.candidateCode,
                group: 'candidates'
              });
              var temp = {
                Code: element.code,
                Status: element.status,
                IntroductionDate: element.introductionDate,
                ResolutionDate: element.resolutionDate,
                CreationDate: element.creationDate
              };
              graphTemp.edges.push({
                from: 1,
                to: i,
                label: 'CANDIDATE',
                title: JSON.stringify(temp, '', 2)
              });
            })

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

    setForm({positionCode: ''})
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
    coloredShadow =
        'info' > <MDTypography variant = 'h6' color = 'white'>Find
                     Position</MDTypography>
          </MDBox><MDBox pt = {3}>
        <form onSubmit = {handleSubmit}><MDBox>
        <MDTypography variant = 'h6' fontWeight = 'medium'>Position code</MDTypography>
                <MDInput
                    id="positionCode"
                    type="text"
                    value = {form.positionCode}
                    onChange = {handlePositionCode}/>
        </MDBox>
            <MDButton variant="gradient" color="dark" onClick={handleSubmit}>Submit</MDButton>
        </form>
         </MDBox></Card>
          </Grid><Grid item xs = {12}>
        <Card>< MDBox
    mx = {2} mt = {-3} py = {3} px = {2} variant = 'gradient'
    bgColor = 'info'
    borderRadius = 'lg'
    coloredShadow = 'info' >
        <MDTypography variant = 'h6' color = 'white'>Position
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
         </MDBox>
         </Card>
        </Grid>
        </Grid>
      </MDBox>
        <Footer />
      </DashboardLayout>
  );
}

export default FindPosition;
