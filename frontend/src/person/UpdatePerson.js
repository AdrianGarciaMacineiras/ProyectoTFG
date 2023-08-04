import React, { useState, useEffect, Fragment } from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  TablePagination,
  IconButton
} from '@mui/material';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import MDBox from '../components/MDBox';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import Collapse from '@mui/material/Collapse';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardDoubleArrowDownIcon from '@mui/icons-material/KeyboardDoubleArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import KeyboardDoubleArrowUpIcon from '@mui/icons-material/KeyboardDoubleArrowUp';
import PropTypes from 'prop-types';
import NestedKnows from './NestedKnows';
import NestedWorkWith from './NestedWorkWith';
import NestedInterest from './NestedInterest';
import NestedMaster from './NestedMaster';
import NestedCertificate from './NestedCertificate';
import { useNavigate } from 'react-router-dom';


const UpdatePerson = () => {

  const [peopleList, setPeopleList] = useState([]);

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(50);

  const [open, setOpen] = useState(false);

  useEffect(() => {
    fetch(`//${window.location.hostname}/people`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      },
    })
      .then((response) => response.json())
      .then((data) => setPeopleList(
        data.map((person) => ({
          ...person,
          open: false,
          tabValue: 0
        }))
      )
      );
  }, []);

  const navigate = useNavigate();

  const handleRowClick = (row) => {
    navigate(`/updatePersonForm/${row.employeeId}`); 
  };


  /*const handleSubmit = (event) => {

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
      })
      .catch((error) => {
        console.error('Error updating people:', error);
      });
  };*/

  function CustomTabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
      <div
        role="tabpanel"
        hidden={value !== index}
        id={`simple-tabpanel-${index}`}
        aria-labelledby={`simple-tab-${index}`}
        {...other}
      >
        {value === index && (
          <MDBox sx={{ p: 3 }}>
            <MDTypography>{children}</MDTypography>
          </MDBox>
        )}
      </div>
    );
  }

  CustomTabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired,
  };

  function a11yProps(index) {
    return {
      id: `simple-tab-${index}`,
      'aria-controls': `simple-tabpanel-${index}`,
    };
  }

  const handleChangeTab = (event, newValue, row) => {
    setPeopleList((prevPeopleList) =>
      prevPeopleList.map((person) =>
        person.code === row.code ? { ...person, tabValue: newValue } : person
      )
    );
  };


  const DataPerson = ({ row }) => {
    const isOpen = row.open;
    const valor = row.tabValue;


    return (
      <Fragment>
        <TableRow sx={{ '& > *': { borderBottom: 'unset' } }} key={row.index} onClick={() => handleRowClick(row)}>
          <TableCell>
            <IconButton
              aria-label="expand row"
              size="small"
              onClick={() => {
                setPeopleList((prevPeopleList) =>
                  prevPeopleList.map((person) =>
                    person.code === row.code ? { ...person, open: !isOpen } : person
                  )
                );
              }}
              style={{ width: "5%" }}
            >
              {isOpen ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
            </IconButton>
          </TableCell>
          <TableCell align="left">{row.code}</TableCell>
          <TableCell align="left">{row.name}</TableCell>
          <TableCell align="left">{row.surname}</TableCell>
          <TableCell align="left">{row.employeeId}</TableCell>
          <TableCell align="left">{row.birthDate}</TableCell>
        </TableRow>
        <TableRow>
          <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={8}>
            <Collapse in={isOpen} timeout="auto" unmountOnExit>
              <MDBox sx={{ margin: 1 }}>
                <MDBox sx={{ borderBottom: 1, borderColor: 'divider' }}>
                  <Tabs
                    value={valor}
                    onChange={(event, newValue) => handleChangeTab(event, newValue, row)}
                    aria-label="basic tabs example"
                  >
                    <Tab label="Knows" {...a11yProps(0)} />
                    <Tab label="WorkWith" {...a11yProps(1)} />
                    <Tab label="Masters" {...a11yProps(2)} />
                    <Tab label="Interests" {...a11yProps(3)} />
                    <Tab label="Certificates" {...a11yProps(4)} />
                  </Tabs>
                </MDBox>
                <CustomTabPanel value={valor} index={0}>
                  <NestedKnows data={row.knows} />
                </CustomTabPanel>
                <CustomTabPanel value={valor} index={1}>
                  <NestedWorkWith data={row.work_with} />
                </CustomTabPanel>
                <CustomTabPanel value={valor} index={2}>
                  <NestedMaster data={row.master} />
                </CustomTabPanel>
                <CustomTabPanel value={valor} index={3}>
                  <NestedInterest data={row.interest} />
                </CustomTabPanel>
                <CustomTabPanel value={valor} index={4}>
                  <NestedCertificate data={row.certificates} />
                </CustomTabPanel>
              </MDBox>
            </Collapse>
          </TableCell>
        </TableRow>
      </Fragment>
    );
  };

  const [orderBy, setOrderBy] = useState('code'); 
  const [sortDirection, setSortDirection] = useState('asc'); 

  const handleSortChange = (column) => {
    if (orderBy === column) {
      setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc');
    } else {
      setOrderBy(column);
      setSortDirection('asc');
    }
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 50));
    setPage(0);
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
                coloredShadow=
                'info' >
                <MDTypography variant='h6' color='white'>Update Person</MDTypography>
              </MDBox>
              <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                  <TableHead sx={{ display: "table-header-group" }}>
                    <TableRow>
                      <TableCell width="100" component="th" scope="row">
                        <IconButton
                          aria-label="expand row"
                          size="small"
                          onClick={() => setOpen(!open)}
                        >
                          {open ? <KeyboardDoubleArrowUpIcon /> : <KeyboardDoubleArrowDownIcon />}
                        </IconButton>
                      </TableCell>
                      <TableCell align="left" onClick={() => handleSortChange('code')}>
                        <strong>{orderBy === 'code' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') : null}</strong>
                        Code
                      </TableCell>
                      <TableCell align="left" onClick={() => handleSortChange('name')}>
                        <strong>{orderBy === 'name' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') : null}</strong>
                        Name
                      </TableCell>
                      <TableCell align="left" onClick={() => handleSortChange('surname')}>
                        <strong>{orderBy === 'surname' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') : null}</strong>
                        Surname
                      </TableCell>
                      <TableCell align="left" onClick={() => handleSortChange('employeeId')}>
                        <strong>{orderBy === 'employeeId' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') : null}</strong>
                        EmployeeId
                      </TableCell>
                      <TableCell align="left" onClick={() => handleSortChange('birthDate')}>
                        <strong>{orderBy === 'birthDate' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') : null}</strong>
                        Birth date
                      </TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {peopleList
                      .slice()
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
                        <DataPerson key={index} row={row} />
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

export default UpdatePerson;
