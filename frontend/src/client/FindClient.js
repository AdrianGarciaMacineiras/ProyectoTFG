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

function FindClient() {

  const [form, setForm] = useState({
    clientCode: ''
  });

  const graphTemp = {
    nodes: [],
    edges: []
  };

  const [graph, setGraph] = useState({
    nodes: [],
    edges: []
  });

  const [aux, setAux] = useState([]);

  const options = {
    layout: {
      improvedLayout: true
    },
    nodes: {
      shape: "dot",
      scaling: { min: 10, label: false }
    },
    edges: {
      color: "#000000",
      smooth: {
        enabled: true,
        type: "discrete",
        roundness: 0.5
      }
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
    select: function (event) {
      let { nodes, edges } = event;
    }
  };

  const findClient = (clientCode) =>
      fetch(`http://${window.location.hostname}:9080/client/${clientCode}`,
      {
        method: "GET", headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*"
        }
      })
      .then(response => { return response.json() })
      .then(response => {
        setAux(response);
        let i = 1;
        graphTemp.nodes.push({ id: i, label: response.name, title: JSON.stringify(response, '', 2) });
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
      clientCode: ''
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
                <MDTypography variant="h6" color="white">
                  Find Clients
                </MDTypography>
              </MDBox>
              <MDBox pt={3}>
                <form onSubmit={handleSubmit}>
                  <MDBox>
                    <MDTypography variant="h6" fontWeight="medium">Client code</MDTypography>
                    <MDInput
                      id="clientCode"
                      type="text"
                      value={form.clientCode}
                      onChange={handleClientCode} />
                  </MDBox>
                  <MDButton variant="gradient" color="dark" onClick={handleSubmit}>Submit</MDButton>
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
                <MDTypography variant="h6" color="white">
                  Clients Graph
                </MDTypography>
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

export default FindClient;
