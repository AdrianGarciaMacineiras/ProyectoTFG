import Add from '@mui/icons-material/Add';
import Clear from '@mui/icons-material/Clear';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import KeyboardDoubleArrowDownIcon
    from '@mui/icons-material/KeyboardDoubleArrowDown';
import KeyboardDoubleArrowUpIcon
    from '@mui/icons-material/KeyboardDoubleArrowUp';
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
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';

import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';
import CandidateList from './components/CandidateList';

const ListPositions = () => {
    const [positionList, setPositionList] = useState([]);

    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(50);

    const [open, setOpen] = useState(false);

    const [orderBy, setOrderBy] = useState('');
    const [sortDirection, setSortDirection] = useState('asc');

    const navigate = useNavigate();

    useEffect(() => {
        fetch(`http://${window.location.hostname}:9080/api/position/shortinfo`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
            },
        })
            .then((response) => response.json())
            .then(
                (data) => setPositionList(data.map(
                    (position) => ({ ...position, open: false, tabValue: 0 }))));
    }, []);

    const handleAdd = () => {
        navigate(`/createPosition`);
    };

    const handleUpdate = (event, positionCode) => {
        event.preventDefault();
        navigate(`/updatePositionForm/${positionCode}`);
    };

    const handleDelete = (event, positionCode) => {
        event.preventDefault();
        fetch(`http://${window.location.hostname}:9080/api/position/${positionCode}`, {
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
                aria-labelledby={`simple-tab-${index}`} {...other} >
                {value === index &&
                    (<MDBox sx={{ p: 3 }}>
                        <MDTypography>{children}</MDTypography>
                    </MDBox>)}
            </div>
        );
    }

    CustomTabPanel.propTypes = {
        children: PropTypes.node,
        index: PropTypes.number.isRequired,
        value: PropTypes.number.isRequired,
    };

    const initialCandidateState = {
        page: 0,
        rowsPerPage: 10
    };

    const [candidateState, setCandidateState] = useState(initialCandidateState);

    const handleCandidateStateChange = (newState) => {
        setCandidateState(prevState => ({
            ...prevState,
            ...newState
        }));
    };

    const handleCandidatePageChange = (newPage) => {
        handleCandidateStateChange({ page: newPage });
    };

    const handleCandidateRowsPerPageChange = (newRowsPerPage) => {
        handleCandidateStateChange({
            rowsPerPage: newRowsPerPage,
            page: 0
        });
    };

    const DataPosition = ({ row }) => {
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
                                    setPositionList((prevPositionList) =>
                                        prevPositionList.map((position) =>
                                            position.code === row.code ? { ...position, open: !isOpen } : position
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
                    <TableCell align='left'>{row.name}</TableCell>
                    <TableCell align="left">{row.projectCode}</TableCell>
                    <TableCell align='left'>{row.openingDate}</TableCell>
                    <TableCell align="left">{row.priority==="null"? '' : row.priority}</TableCell>
                    <TableCell align="left">{row.mode}</TableCell>
                    <TableCell align="left">{row.role==="null"? '' : row.role}</TableCell>
                    <TableCell align="left">{row.managedBy}</TableCell>
                    <TableCell>
                        <Tooltip title='Update element'>< IconButton
                            aria-label='update row'
                            size='small'
                            onClick={(event) => handleUpdate(event, row.code)} >
                            {<Update />}
                        </IconButton>
                        </Tooltip>
                    </TableCell>
                    <TableCell>
                        <Tooltip title="Delete item">
                            <IconButton
                                aria-label="clear row"
                                size="small"
                                onClick={(event) => handleDelete(event, row.code)}
                            >
                                {<Clear />}
                            </IconButton>
                        </Tooltip>
                    </TableCell>
                </TableRow>
                <TableRow>
                    <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={8}>
                        <Collapse in={isOpen} timeout='auto' unmountOnExit>
                            <MDBox sx={{ margin: 1 }}>
                                <MDBox sx={{ borderBottom: 1, borderColor: 'divider' }}>
                                    <TableCell align='center'>Candidates</TableCell>
                                </MDBox>
                                <CustomTabPanel value={valor} index={0}>
                                    <CandidateList
                                        key={row.code}
                                        data={row.candidates}
                                        state={candidateState[row.code]}
                                        onStateChange={newState => handleCandidateStateChange(row.code, newState)}
                                        onPageChange={newPage => handleCandidatePageChange(row.code, newPage)}
                                        onRowsPerPageChange={newRowsPerPage => handleCandidateRowsPerPageChange(row.code, newRowsPerPage)}
                                    />
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

    function getSortIcon(column) {
        if (orderBy === column) {
            return sortDirection === 'asc' ? <ArrowDropUpIcon /> : <ArrowDropDownIcon />;
        };
        if (column !== 'projectCode'
            && column !== 'openingDate'
            && column !== 'closingDate') {
            return <SortByAlphaIcon />;
        } else {
            return <SortIcon />;
        }
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
                                    <MDTypography variant='h6' color='white'>Position List</MDTypography>
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
                                                        onClick={() => setOpen(!open)} >
                                                        {open ?
                                                            <KeyboardDoubleArrowUpIcon /> :
                                                            <KeyboardDoubleArrowDownIcon />
                                                        }
                                                    </IconButton>
                                                </TableCell>
                                                <TableCell align='left' onClick={() => handleSortChange('name')}>
                                                    {getSortIcon('name')}
                                                    Name
                                                </TableCell>
                                                <TableCell align='left'
                                                           onClick={() => handleSortChange(
                                                               'projectName')}>
                                                    {getSortIcon('projectName')}
                                                    Project Code
                                                </TableCell>
                                                <TableCell align='left' onClick={() => handleSortChange('openingDate')}>
                                                    {getSortIcon('openingDate')}
                                                    Opening Date
                                                </TableCell>
                                                <TableCell align='left' onClick={() => handleSortChange('priority')}>
                                                    {getSortIcon('priority')}
                                                    Priority
                                                </TableCell>
                                                <TableCell align='left' onClick={() => handleSortChange('mode')}>
                                                    {getSortIcon('mode')}
                                                    Mode
                                                </TableCell>
                                                <TableCell align='left' onClick={() => handleSortChange('role')}>
                                                    {getSortIcon('role')}
                                                    Role
                                                </TableCell>
                                                <TableCell align='left' onClick={() => handleSortChange('managedBy')}>
                                                    {getSortIcon('managedBy')}
                                                    Managed By
                                                </TableCell>
                                            </TableRow>
                                        </TableHead>
                                        <TableBody>
                                            {positionList?.slice()
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
                                                    <DataPosition key={index} row={row} />
                                                ))}
                                        </TableBody>
                                    </Table>
                                </TableContainer>
                                <TablePagination
                                    rowsPerPageOptions={[5, 10, 25, 50]} component='div'
                                    count={positionList.length}
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
        </React.Fragment>
    );
};

export default ListPositions;