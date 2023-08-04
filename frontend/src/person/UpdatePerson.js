import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow, TextField} from '@mui/material';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import React, {useEffect, useState} from 'react';

import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDButton from '../components/MDButton';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';

const UpdateClient = () => {
  const [peopleList, setPeopleList] = useState([]);
  const [updatedPeopleData, setUpdatedPeopleData] = useState(null);

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);

  useEffect(() => {
    fetch(`//${window.location.hostname}/people`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      },
    })
      .then((response) => response.json())
      .then((data) => setPeopleList(data));
  }, []);

  const handleRowClick = (people) => {
    setUpdatedPeopleData(people);
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setUpdatedPeopleData((prevClientData) => ({
      ...prevClientData,
      [name]: value,
    }));
  };

  const handleSubmit = (event) => {

    event.preventDefault();

    fetch(`//${window.location.hostname}/people/${updatedPeopleData.code}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      },
      body: JSON.stringify(updatedPeopleData),
    })
      .then((response) => response.json())
      .then((updatedPeople) => {
        setPeopleList((prevpeopleList) =>
          prevpeopleList.map((people) =>
            people.code === updatedPeople.code ? updatedPeople : people
          )
        );

        setUpdatedPeopleData(null);
      })
      .catch((error) => {
        console.error('Error updating people:', error);
      });
  };


  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };


  const paginatedData = peopleList.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage);

  return (
    <DashboardLayout>
      <DashboardNavbar />
      <MDBox pt={6} pb={3}>
        <Grid container spacing={6}>
          <Grid item xs={12}>
            <Card>
              <MDBox
                mx={2} mt={-3} py={3} px={2} variant='gradient'
                bgColor='info'
                borderRadius='lg'
                coloredShadow=
                'info' >
                <MDTypography variant='h6' color='white'>Update Client</MDTypography>
              </MDBox>
              {updatedPeopleData && (
                <form onSubmit={handleSubmit}>
                  <TextField
                    name='code'
                    label='Code'
                    value={updatedPeopleData.code}
                    onChange={handleChange}
                    disabled
                  />
                  <TextField
                    name='name'
                    label='Name'
                    value={updatedPeopleData.name}
                    onChange={handleChange}
                  />
                  <TextField
                    name="industry"
                    label="Industry"
                    value={updatedPeopleData.industry}
                    onChange={handleChange}
                  />
                  <TextField
                    name='country'
                    label='Country'
                    value={updatedPeopleData.country}
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
                      <TableCell align='left'>Name</TableCell>
                      <TableCell align="left">Industry</TableCell>
                      <TableCell align='left'>Country</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {paginatedData.map((people) => (
                      <TableRow key={people.code} onClick={() => handleRowClick(people)}>
                        <TableCell align="left">{people.code}</TableCell>
                        <TableCell align='left'>{people.name}</TableCell>
                        <TableCell align="left">{people.industry}</TableCell>
                        <TableCell align='left'>{people.country}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
              <TablePagination
                rowsPerPageOptions={[5, 10, 25, 50]}
                component="div"
                count={peopleList.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
              />
            </Card>
          </Grid>
        </Grid>
      </MDBox>
    </DashboardLayout>
  );
};

export default UpdateClient;
