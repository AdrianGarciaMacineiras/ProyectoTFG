import React, { useState } from 'react';
import "../network.css";
import VisGraph from 'react-vis-graph-wrapper';
import MDBox from "../components/MDBox";
import MDTypography from "../components/MDTypography";
import Footer from "../components/Footer";
import MDButton from "../components/MDButton";
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import MDInput from '../components/MDInput';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';

const ClientForm = () => {
  const [clientData, setClientData] = useState({
    code: '',
    name: '',
    industry: '',
    country: ''
  });
  const [graph, setGraph] = useState(null);

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
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setClientData((prevState) => ({
      ...prevState,
      [name]: value
    }));
  };

  const createClient = () => {
    const form = {
      code: clientData.code,
      name: clientData.name,
      industry: clientData.industry,
      country: clientData.country
    };

    const requestBody = JSON.stringify(form);

    fetch(`//${window.location.hostname}/client`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*"
      },
      body: requestBody,
    })
      .then(response => response.json())
      .then(response => {
        setGraph({
          nodes: [{ id: 1, label: response.name, title: JSON.stringify(response, '', 2) }],
          edges: []
        });
      });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    
    createClient();

    setClientData({
      code: '',
      name: '',
      industry: '',
      country: ''
    });
  };

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
                          'info' > <MDTypography variant = 'h6' color = 'white'>Create Client</MDTypography>
                  </MDBox>
      <form onSubmit={handleSubmit}>
      <MDBox pt = {3}>
        <MDTypography variant = 'h6' fontWeight = 'medium'>Code:</MDTypography>
          <MDInput
            type="text"
            id="code"
            name="code"
            value={clientData.code}
            onChange={handleChange}
            required
          />
        </MDBox>
        <MDBox>
          <MDTypography htmlFor="name">Name:</MDTypography>
          <MDInput
            type="text"
            id="name"
            name="name"
            value={clientData.name}
            onChange={handleChange}
            required
          />
        </MDBox>
        <MDBox>
          <MDTypography htmlFor="industry">Industry:</MDTypography>
          <MDInput
            type="text"
            id="industry"
            name="industry"
            value={clientData.industry}
            onChange={handleChange}
            required
          />
        </MDBox>
        <MDBox>
          <MDTypography htmlFor="country">Country:</MDTypography>
          <MDInput
            type="text"
            id="country"
            name="country"
            value={clientData.country}
            onChange={handleChange}
            required
          />
        </MDBox>
        <MDButton variant="gradient" color="dark" onClick={handleSubmit}>Submit</MDButton>
      </form>
      </Card>
    </Grid>
    <Grid item xs = {12}>
      <Card>
        < MDBox
          mx = {2} mt = {-3} py = {3} px = {2} variant = 'gradient'
          bgColor = 'info'
          borderRadius = 'lg'
          coloredShadow = 'info' >
        <MDTypography variant = 'h6' color = 'white'>Client Graph</MDTypography>
                </MDBox>
                <MDBox pt = {3}>    
                  {graph && (
                    <VisGraph
                      graph={graph}
                      options={options}
                      events={events}
                      getNetwork={network => {
                        // if you want access to vis.js network api you can set the state in a parent component using this property
                      }}
                    />
                  )}
                  </MDBox>
              </Card>
            </Grid>
          </Grid>
        </MDBox>
        <Footer />
      </DashboardLayout>
  );
};

export default ClientForm;
