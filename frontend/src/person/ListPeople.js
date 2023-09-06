import Add from '@mui/icons-material/Add';
import Clear from '@mui/icons-material/Clear';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import KeyboardDoubleArrowDownIcon from '@mui/icons-material/KeyboardDoubleArrowDown';
import KeyboardDoubleArrowUpIcon from '@mui/icons-material/KeyboardDoubleArrowUp';
import ArrowDropUpIcon from '@mui/icons-material/ArrowDropUp';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import SortIcon from '@mui/icons-material/Sort';
import SortByAlphaIcon from '@mui/icons-material/SortByAlpha';
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
import Collapse from '@mui/material/Collapse';
import Grid from '@mui/material/Grid';
import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';
import PropTypes from 'prop-types';
import React, { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';

import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';

import NestedCertificate from './components/NestedCertificate';
import NestedKnows from './components/NestedKnows';
import NestedSimpleTable from './components/NestedSimpleTable';


const ListPeople =
  () => {
    const [peopleList, setPeopleList] = useState([]);

    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(50);

    const [open, setOpen] = useState(false);

    const [orderBy, setOrderBy] = useState('');
    const [sortDirection, setSortDirection] = useState('asc');

    const navigate = useNavigate();

    useEffect(() => {
      fetch(`http://${window.location.hostname}:9080/api/people`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*',
        },
      })
        .then((response) => response.json())
        .then(
          (data) => setPeopleList(data.map(
            (person) => ({ ...person, open: false, tabValue: 0 }))));
    }, []);


    const handleAdd = () => {
      navigate(`/createPerson`);
    };

    const handleUpdate = useCallback((event, employeeId) => {
      event.preventDefault();
      navigate(`/updatePersonForm/${employeeId}`);
    }, [navigate]);

    const handleDelete = useCallback((event, employeeId) => {
      event.preventDefault();
      fetch(`http://${window.location.hostname}:9080/api/person/${employeeId}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*',
        },
      }).then(() => {
        window.location.reload();
      });
    }, []);

    function CustomTabPanel(props) {
      const { children, value, index, ...other } = props;

      return (
        <div
          role='tabpanel'
          hidden={value !== index}
          id={`simple-tabpanel-${index}`}
          aria-labelledby={`simple-tab-${index}`} {...other} >
          {value === index &&
            (<MDBox sx={{ p: 3 }}>
              <MDTypography>{children}</MDTypography>
            </MDBox>)
          }
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

    const handleChangeTab = useCallback((event, newValue, row) => {
      setPeopleList((prevPeopleList) =>
        prevPeopleList.map((person) =>
          person.code === row.code ? { ...person, tabValue: newValue } : person
        )
      );
    }, [setPeopleList]);


    const DataPerson = ({ row }) => {
      const isOpen = row.open;
      const valor = row.tabValue;

      return (
        <React.Fragment >
          <TableRow sx={{ '& > *': { borderBottom: 'unset' } }} key={row.index}>
            <TableCell>
              <Tooltip title="Expand row">
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
                  {isOpen ?
                    <KeyboardArrowUpIcon /> :
                    <KeyboardArrowDownIcon />
                  }
                </IconButton>
              </Tooltip>
            </TableCell>
            <TableCell align="left">{row.code}</TableCell>
            <TableCell align='left'>{row.name}</TableCell>
            <TableCell align="left">{row.surname}</TableCell>
            <TableCell align='left'>{row.employeeId}</TableCell>
            <TableCell>
              <Tooltip title='Update element'>
                < IconButton
                  aria-label='update row'
                  size='small'
                  onClick={(event) => handleUpdate(event, row.employeeId)} >
                  {<Update />}
                </IconButton>
              </Tooltip>
            </TableCell>
            <TableCell>
              <Tooltip title="Delete item">
                <IconButton
                  aria-label="clear row"
                  size="small"
                  onClick={(event) => handleDelete(event, row.employeeId)}
                >
                  {<Clear />}
                </IconButton>
              </Tooltip>
            </TableCell>
          </TableRow>
          <TableRow>
            <TableCell
              style={{ paddingBottom: 0, paddingTop: 0 }}
              colSpan={8}>
              <Collapse in={isOpen} timeout='auto' unmountOnExit>
                <MDBox sx={{ margin: 1 }}>
                  <MDBox sx={{ borderBottom: 1, borderColor: 'divider' }}>
                    <Tabs value={valor} onChange={(event, newValue) =>
                      handleChangeTab(event, newValue, row)} aria-label='basic tabs example'>
                      <Tab label='Knows' {...a11yProps(0)} />
                      <Tab label="WorkWith" {...a11yProps(1)} />
                      <Tab label='Masters' {...a11yProps(2)} />
                      <Tab label="Interests" {...a11yProps(3)} />
                      <Tab label='Certificates' {...a11yProps(4)} />
                    </Tabs>
                  </MDBox>
                  <CustomTabPanel value={valor} index={0}>
                    <NestedKnows
                      key={row.code}
                      data={row.knows}
                      state={nestedKnowStates[row.code]}
                      onStateChange={newState => handleNestedKnowStateChange(row.code, newState)}
                      onPageChange={newPage => handleNestedKnowPageChange(row.code, newPage)}
                      onRowsPerPageChange={newRowsPerPage => handleNestedKnowRowsPerPageChange(row.code, newRowsPerPage)}
                    />
                  </CustomTabPanel>
                  <CustomTabPanel value={valor} index={1}>
                    <NestedSimpleTable data={row.work_with} />
                  </CustomTabPanel>
                  <CustomTabPanel value={valor} index={2}>
                    <NestedSimpleTable data={row.master} />
                  </CustomTabPanel>
                  <CustomTabPanel value={valor} index={3}>
                    <NestedSimpleTable data={row.interest} />
                  </CustomTabPanel>
                  <CustomTabPanel value={valor} index={4}>
                    <NestedCertificate
                      key={row.code}
                      data={row.certificates}
                      state={nestedCertificateStates[row.code]}
                      onStateChange={newState => handleNestedCertificateStateChange(row.code, newState)}
                      onPageChange={newPage => handleNestedCertificatePageChange(row.code, newPage)}
                      onRowsPerPageChange={newRowsPerPage => handleNestedCertificateRowsPerPageChange(row.code, newRowsPerPage)} />
                  </CustomTabPanel>
                </MDBox>
              </Collapse>
            </TableCell>
          </TableRow>
        </React.Fragment >
      );
    };

    const [nestedKnowStates, setNestedKnowStates] = useState({});
    const [nestedCertificateStates, setNestedCertificateStates] = useState({});

    const handleNestedKnowStateChange = (personCode, newState) => {
      setNestedKnowStates(prevStates => ({
        ...prevStates,
        [personCode]: newState
      }));
    };

    const handleNestedKnowPageChange = (personCode, newPage) => {
      setNestedKnowStates(prevStates => ({
        ...prevStates,
        [personCode]: {
          ...prevStates[personCode],
          page: newPage
        }
      }));
    };

    const handleNestedKnowRowsPerPageChange = (personCode, newRowsPerPage) => {
      setNestedKnowStates(prevStates => ({
        ...prevStates,
        [personCode]: {
          ...prevStates[personCode],
          rowsPerPage: newRowsPerPage,
          page: 0
        }
      }));
    };

    const handleNestedCertificateStateChange = (personCode, newState) => {
      setNestedCertificateStates(prevStates => ({
        ...prevStates,
        [personCode]: newState
      }));
    };

    const handleNestedCertificatePageChange = (personCode, newPage) => {
      setNestedKnowStates(prevStates => ({
        ...prevStates,
        [personCode]: {
          ...prevStates[personCode],
          page: newPage
        }
      }));
    };

    const handleNestedCertificateRowsPerPageChange = (personCode, newRowsPerPage) => {
      setNestedCertificateStates(prevStates => ({
        ...prevStates,
        [personCode]: {
          ...prevStates[personCode],
          rowsPerPage: newRowsPerPage,
          page: 0
        }
      }));
    };

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

    function getSortIcon(column) {
      if (orderBy === column) {
        return sortDirection === 'asc' ? <ArrowDropUpIcon /> : <ArrowDropDownIcon />;
      };
      if (column !== 'code') {
        return <SortByAlphaIcon />;
      } else {
        return <SortIcon />;
      }
    }

    return (
      <React.Fragment>
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
                    alignItems=
                    'center' >
                    <MDTypography variant='h6' color='white'>People List</MDTypography>
                    <MDBox display="flex" justifyContent="flex-end">
                      <Tooltip title="Add item">
                        <IconButton aria-label="add row" size="small" onClick={handleAdd}>
                          <Add />
                        </IconButton>
                      </Tooltip>
                    </MDBox>
                  </MDBox>
                  <TableContainer component={Paper}>
                    <Table sx={{ minWidth: 650 }} aria-label='simple table'>
                      <TableHead sx={{ display: 'table-header-group' }}>
                        <TableRow>
                          <TableCell width='100' component='th' scope='row'>
                            <IconButton
                              aria-label='expand row'
                              size='small'
                              onClick=
                              {() => setOpen(!open)} >
                              {open ?
                                <KeyboardDoubleArrowUpIcon /> :
                                <KeyboardDoubleArrowDownIcon />
                              }
                            </IconButton>
                          </TableCell>
                          <TableCell align='left' onClick={() => handleSortChange('code')}>
                            {getSortIcon('code')}
                            Code
                          </TableCell>
                          <TableCell align='left' onClick={() => handleSortChange('name')}>
                            {getSortIcon('name')}
                            Name
                          </TableCell>
                          <TableCell align='left' onClick={() => handleSortChange('surname')}>
                            {getSortIcon('surname')}
                            Surname
                          </TableCell>
                          <TableCell align='left' onClick={() => handleSortChange('employeeId')}>
                            {getSortIcon('employeeId')}
                            EmployeeId
                          </TableCell>
                        </TableRow>
                      </TableHead>
                      <TableBody>
                        {peopleList?.slice()
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
                    rowsPerPageOptions={[5, 10, 25, 50]} component='div'
                    count={peopleList.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={
                      handleChangeRowsPerPage}
                  />
                </Card>
              </Grid>
            </Grid>
          </MDBox>
        </DashboardLayout>
      </React.Fragment>
    );
  };

export default ListPeople;
