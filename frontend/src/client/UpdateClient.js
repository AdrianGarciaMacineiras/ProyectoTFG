import React, { useState, useEffect } from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  TextField,
} from '@mui/material';
import MDBox from '../components/MDBox';
import MDButton from '../components/MDButton';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import MDTypography from "../components/MDTypography";

const UpdateClient = () => {
  const [clientList, setClientList] = useState([]);
  const [updatedClientData, setUpdatedClientData] = useState(null);

  useEffect(() => {
    fetch("http://localhost:9080/client", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
    })
      .then((response) => response.json())
      .then((data) => setClientList(data));
  }, []);

  const handleRowClick = (client) => {
    setUpdatedClientData(client);
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setUpdatedClientData((prevClientData) => ({
      ...prevClientData,
      [name]: value,
    }));
  };

  const handleSubmit = (event) => {

    event.preventDefault();

    console.log("Halo");
    
    fetch("http://localhost:9080/client/"+updatedClientData.code, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      body: JSON.stringify(updatedClientData),
    })
      .then((response) => response.json())
      .then((updatedClient) => {
        setClientList((prevClientList) =>
          prevClientList.map((client) =>
            client.id === updatedClient.id ? updatedClient : client
          )
        );
  
        setUpdatedClientData(null);
      })
      .catch((error) => {
        console.error('Error updating client:', error);
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
                          'info' >
                    <MDTypography variant = 'h6' color = 'white'>Update Client</MDTypography>
                  </MDBox>
      {updatedClientData && (
        <form onSubmit={handleSubmit}>
          <TextField
            name="code"
            label="Code"
            value={updatedClientData.code}
            onChange={handleChange}
            disabled
          />
          <TextField
            name="name"
            label="Name"
            value={updatedClientData.name}
            onChange={handleChange}
          />
          <TextField
            name="industry"
            label="Industry"
            value={updatedClientData.industry}
            onChange={handleChange}
          />
          <TextField
            name="country"
            label="Country"
            value={updatedClientData.country}
            onChange={handleChange}
          />
          <MDButton variant="contained" color="primary" onClick={handleSubmit}>Update</MDButton>
        </form>
      )}
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead sx={{ display: "table-header-group" }}>
            <TableRow>
              <TableCell align="left">Code</TableCell>
              <TableCell align="left">Name</TableCell>
              <TableCell align="left">Industry</TableCell>
              <TableCell align="left">Country</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {clientList.map((client) => (
              <TableRow key={client.code} onClick={() => handleRowClick(client)}>
                <TableCell align="left">{client.code}</TableCell>
                <TableCell align="left">{client.name}</TableCell>
                <TableCell align="left">{client.industry}</TableCell>
                <TableCell align="left">{client.country}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
            </Card>
          </Grid>
      </Grid>
    </MDBox>
  </DashboardLayout>
  );
};

export default UpdateClient;
