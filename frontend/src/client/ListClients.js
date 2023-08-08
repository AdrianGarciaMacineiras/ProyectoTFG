import Add from '@mui/icons-material/Add';
import Clear from '@mui/icons-material/Clear';
import Update from '@mui/icons-material/Update';
import {
    IconButton,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TablePagination,
    TableRow,
    Tooltip
} from '@mui/material';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';

import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';


const UpdateClient =
    () => {
      const [clientList, setClientList] = useState([]);

      const [page, setPage] = useState(0);
      const [rowsPerPage, setRowsPerPage] = useState(10);

      const navigate = useNavigate();


      useEffect(() => {
          fetch(`http://${window.location.hostname}:9080/api/client`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
          },
        })
            .then((response) => response.json())
            .then((data) => setClientList(data));
      }, []);

      const handleAdd = () => {
        navigate(`/createClient`);
      };

      const handleUpdate = (event, name) => {
        event.preventDefault();
        navigate(`/updateClientForm/${name}`);
      };

      const handleDelete = (event, name) => {
        event.preventDefault();
          fetch(`http://${window.location.hostname}:9080/api/client/${name}`, {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
          },
        }).then(() => {
          window.location.reload();
        });
      };

      const handleChangePage = (event, newPage) => {
        setPage(newPage);
      };

      const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
      };


      const paginatedData = clientList.slice(
          page * rowsPerPage, page * rowsPerPage + rowsPerPage);

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
  coloredShadow = 'info'
  display = 'flex'
  justifyContent = 'space-between'
                alignItems='center'
              >
                <MDTypography variant='h6' color='white'>List Clients</MDTypography>
                <MDBox display="flex" justifyContent="flex-end">
                  <Tooltip title="Add item">
                    <IconButton aria-label="add row" size="small" onClick={handleAdd}>
                      <Add />
                    </IconButton>
                  </Tooltip>
                </MDBox>
              </MDBox>
              <TableContainer component={Paper}>
                <Table sx={
    { minWidth: 650 }} aria-label='simple table'>
                  <TableHead sx={{
    display: 'table-header-group' }}>
                    <TableRow>
                      <TableCell align='left'>Code</TableCell>
                      <TableCell align="left">Name</TableCell>
                      <TableCell align='left'>Industry</TableCell>
                      <TableCell align="left">Country</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {paginatedData.map((client) => (
                      <TableRow key={client.code}>
                        <TableCell align='left'>{client.code}</TableCell>
                        <TableCell align="left">{client.name}</TableCell>
                        <TableCell align='left'>{client.industry}</TableCell>
                        <TableCell align="left">{client.country}</TableCell>
                        <TableCell>
                          <Tooltip title='Update element'>
                            <IconButton
                              aria-label = 'update row'
                              size = 'small'
                              onClick={(event) => handleUpdate(event, client.name)}
                            >
                              {<Update />}
                            </IconButton>
                          </Tooltip>
                        </TableCell>
                        <TableCell>
                          <Tooltip title="Delete item">
                            <IconButton
                              aria-label="clear row"
                              size="small"
                              onClick={(event) => handleDelete(event, client.name)}
                            >
                              {<Clear />}
                            </IconButton>
                          </Tooltip>
                        </TableCell>
                      </TableRow>
                    ))
    }</TableBody>
                </Table>
    </TableContainer>
              <TablePagination
                rowsPerPageOptions={[5, 10, 25, 50]}
                component="div"
                count={clientList.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
              />
    </Card>
          </Grid></Grid>
      </MDBox></DashboardLayout>
  );
};

export default UpdateClient;
