import Add from '@mui/icons-material/Add';
import Clear from '@mui/icons-material/Clear';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import KeyboardDoubleArrowDownIcon from '@mui/icons-material/KeyboardDoubleArrowDown';
import KeyboardDoubleArrowUpIcon from '@mui/icons-material/KeyboardDoubleArrowUp';
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
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';

import NestedKnows from './NestedKnows';



const ListPeopleKnows =
    () => {
        const [peopleList, setPeopleList] = useState([]);

        const [page, setPage] = useState(0);
        const [rowsPerPage, setRowsPerPage] = useState(50);

        const [open, setOpen] = useState(false);

        const [orderBy, setOrderBy] = useState('code');
        const [sortDirection, setSortDirection] = useState('asc');

        const navigate = useNavigate();

        useEffect(() => {
            fetch(`http://${window.location.hostname}:9080/api/people/shortinfo`, {
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

        const handleUpdate = (event, employeeId) => {
            event.preventDefault();
            navigate(`/updatePersonForm/${employeeId}`);
        };

        const handleDelete = (event, employeeId) => {
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
        };

        function CustomTabPanel(props) {
            const { children, value, index, ...other } = props;

            return (
                <div
                    role='tabpanel'
                    hidden={value !== index}
                    id={`simple-tabpanel-${index}`}
                    aria-labelledby={`simple-tab-${index}`} {...other} > {value === index &&
                        (<MDBox sx={
                            {
                                p: 3
                            }
                        }><MDTypography>{children}</MDTypography>
                        </MDBox>)}</div>
            );
        }

        CustomTabPanel.propTypes = {
            children: PropTypes.node,
            index: PropTypes.number.isRequired,
            value: PropTypes.number.isRequired,
        };


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
                                    {isOpen ? <KeyboardArrowUpIcon /> :
                                        <KeyboardArrowDownIcon />
                                    }
                                </IconButton>
                            </Tooltip>
                        </TableCell>
                        <TableCell align="left">{row.code}</TableCell>
                        <TableCell align='left'>{row.name}</TableCell>
                        <TableCell align="left">{row.surname}</TableCell>
                        <TableCell align='left'>{row.employeeId}</TableCell>
                        <TableCell align="left">{row.title}</TableCell>
                        <TableCell align="left">{row.teamShortName}</TableCell>
                        <TableCell>
                            <Tooltip title='Update element'>< IconButton
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
                                    {<Clear />
                                    }</IconButton>
                            </Tooltip>
                        </TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell style={
                            {
                                paddingBottom: 0, paddingTop: 0
                            }
                        } colSpan={8}>
                            <Collapse in={isOpen} timeout='auto' unmountOnExit><MDBox sx={
                                {
                                    margin: 1
                                }
                            }><MDBox sx={
                                {
                                    borderBottom: 1, borderColor: 'divider'
                                }
                            }>
                                    <TableCell align='center'>Knows</TableCell>
                                </MDBox>
                                <CustomTabPanel value={valor} index={0}>
                                    <NestedKnows data={row.knows} />
                                </CustomTabPanel>
                            </MDBox>
                            </Collapse>
                        </TableCell>
                    </TableRow>
                </React.Fragment >
            );
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
                                        <Table sx=
                                            {
                                                { minWidth: 650 }
                                            } aria-label='simple table'>
                                            <TableHead sx={
                                                {
                                                    display: 'table-header-group'
                                                }
                                            }>
                                                <TableRow>
                                                    <TableCell width='100' component='th' scope='row'><IconButton
                                                        aria-label='expand row'
                                                        size='small'
                                                        onClick=
                                                        {() => setOpen(!open)} >
                                                        {
                                                            open ? <KeyboardDoubleArrowUpIcon /> : <KeyboardDoubleArrowDownIcon />
                                                        }
                                                    </IconButton>
                                                    </TableCell>
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
                                                    <TableCell align='left' onClick={() => handleSortChange('surname')}>
                                                        <strong>{
                                                            orderBy === 'surname' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') :
                                                                null}</strong>
                                                        Surname
                                                    </TableCell>
                                                    <TableCell align=
                                                        'left' onClick={() => handleSortChange('employeeId')}><strong>{
                                                            orderBy === 'employeeId' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') :
                                                                null}</strong>
                                                        EmployeeId
                                                    </TableCell>
                                                    <TableCell align='left' onClick={() => handleSortChange('title')}>
                                                        <strong>{
                                                            orderBy === 'title' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') :
                                                                null}</strong>
                                                        Title
                                                    </TableCell>
                                                    <TableCell align='left' onClick={() => handleSortChange('teamShortName')}>
                                                        <strong>{
                                                            orderBy === 'teamShortName' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') :
                                                                null}</strong>
                                                        Team
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
                                                        <DataPerson key={index} row={
                                                            row} />
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

export default ListPeopleKnows;