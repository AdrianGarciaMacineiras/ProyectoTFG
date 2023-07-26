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
  Button,
} from '@mui/material';

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

  const handleSubmit = () => {
    fetch("http://localhost:9080/client", {
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
    <div>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Code</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Industry</TableCell>
              <TableCell>Country</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {clientList.map((client) => (
              <TableRow key={client.id} onClick={() => handleRowClick(client)}>
                <TableCell>{client.code}</TableCell>
                <TableCell>{client.name}</TableCell>
                <TableCell>{client.industry}</TableCell>
                <TableCell>{client.country}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      {updatedClientData && (
        <form onSubmit={handleSubmit}>
          <TextField
            name="code"
            label="Code"
            value={updatedClientData.code}
            onChange={handleChange}
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
          <Button type="submit" variant="contained" color="primary">
            Update
          </Button>
        </form>
      )}
    </div>
  );
};

export default UpdateClient;
