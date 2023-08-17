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
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';
import ClientForm from './CreateClient';


const UpdateClient =
  () => {
    const [clientList, setClientList] = useState([]);

    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);

    const [orderBy, setOrderBy] = useState('code');
    const [sortDirection, setSortDirection] = useState('asc');

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

    const handleSortChange = (column) => {
      if (orderBy === column) {
        setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc');
      } else {
        setOrderBy(column);
        setSortDirection('asc');
      }
    };

    const DataClient = ({ row }) => {
      return (
          <TableRow key={row.code}>
            <TableCell align='left'>{row.code}</TableCell>
            <TableCell align="left">{row.name}</TableCell>
            <TableCell align='left'>{row.industry}</TableCell>
            <TableCell align="left">{row.country}</TableCell>
            <TableCell>
              <Tooltip title='Update element'>
                <IconButton
                  aria-label='update row'
                  size='small'
                  onClick={(event) => handleUpdate(event, row.name)}
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
                  onClick={(event) => handleDelete(event, row.name)}
                >
                  {<Clear />}
                </IconButton>
              </Tooltip>
            </TableCell>
          </TableRow>
      );
    };

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
                  coloredShadow='info'
                  display='flex'
                  justifyContent='space-between'
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
                      display: 'table-header-group'
                    }}>
                      <TableRow>
                        <TableCell align='left' onClick={() => handleSortChange('code')}>
                          <strong>{
                            orderBy === 'code' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') : null}</strong>
                          Code
                        </TableCell>
                        <TableCell align='left' onClick={() => handleSortChange('name')}>
                          <strong>{
                            orderBy === 'name' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') : null}</strong>
                          Name
                        </TableCell>
                        <TableCell align='left' onClick={() => handleSortChange('industry')}>
                          <strong>{
                            orderBy === 'industry' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') : null}</strong>
                          Industry
                        </TableCell>
                        <TableCell align='left' onClick={() => handleSortChange('country')}>
                          <strong>{
                            orderBy === 'country' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') : null}</strong>
                          Country
                        </TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {clientList?.slice()
                        .sort((a, b) => {
                          const aValue = a[orderBy];
                          const bValue = b[orderBy];

                          if (sortDirection === 'asc') {
                            return aValue < bValue ? -1 : aValue > bValue ? 1 : 0;
                          } else {
                            return aValue > bValue ? -1 : aValue < bValue ? 1 : 0;
                          }
                        })
                        .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                        .map((row, index) => (
                          <DataClient key={index} row={
                            row} />
                        ))}
                    </TableBody>
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
