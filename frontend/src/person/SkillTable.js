import { useState, useEffect } from 'react';
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
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
    Checkbox,
    FormControlLabel,
    MenuItem,
} from '@mui/material';
import { Delete as DeleteIcon } from '@mui/icons-material';
import DatePicker from 'react-datepicker';
import MDButton from '../components/MDButton';
import MDInput from '../components/MDInput';



const levels = ['Low', 'Medium', 'Confident', 'High'];

const SkillTable = ({ skill, onReturnRows }) => {
    const [rows, setRows] = useState([]);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);

    useEffect(() => {
        if (skill) {
            const newRow = {
                name: skill.name,
                code: skill.nodeId,
                knows: {
                    level: 'Low',
                    experience: 0,
                    primary: false,
                },
                workwith: false,
                interest: false,
                master: false,
                certificate: {
                    comment: '',
                    date: new Date(),
                },
            };
            setRows(prevRows => [...prevRows, newRow]);
        }
    }, [skill]);

    const handleCheckboxChange = (event, rowIndex, field) => {
        const updatedRows = [...rows];
        updatedRows[rowIndex][field] = event.target.checked;
        setRows(updatedRows);
    };

    const handleSelectChange = (event, rowIndex, field) => {
        const updatedRows = [...rows];
        updatedRows[rowIndex][field] = event.target.value;
        setRows(updatedRows);
    };

    const handleDateChange = (event, field, rowIndex) => {
        const value = event.target.value;
        const updatedRows = [...rows];
        updatedRows[rowIndex][field].date = value;
        setRows(updatedRows);
    };

    const handleDeleteRow = (rowIndex) => {
        const updatedRows = rows.filter((row, index) => index !== rowIndex);
        setRows(updatedRows);
    };

    const handleReturnRows = () => {
        onReturnRows(rows);
    };

    return (
        <DashboardLayout>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label='simple table'>
                    <TableHead sx={{ display: 'table-header-group' }}>
                        <TableRow>
                            <TableCell>Skill</TableCell>
                            <TableCell>Knows</TableCell>
                            <TableCell>Work With</TableCell>
                            <TableCell>Master</TableCell>
                            <TableCell>Interest</TableCell>
                            <TableCell>Certificate</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {rows
                            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                            .map((row, rowIndex) => (
                                <TableRow key={rowIndex}>
                                    <TableCell>{row.name}</TableCell>
                                    <TableCell>
                                        <MDInput
                                            select
                                            label="Level"
                                            value={row.knows.level}
                                            onChange={(event) => handleSelectChange(event, rowIndex, 'knows.level')}
                                        >
                                            {levels.map((level) => (
                                                <MenuItem key={level} value={level}>
                                                    {level}
                                                </MenuItem>
                                            ))}
                                        </MDInput>
                                        <MDInput
                                            type="number"
                                            label="Experience"
                                            value={row.knows.experience}
                                            onChange={(event) => handleSelectChange(event, rowIndex, 'knows.experience')}
                                        />
                                        <FormControlLabel
                                            control={
                                                <Checkbox
                                                    checked={row.knows.primary}
                                                    onChange={(event) => handleCheckboxChange(event, rowIndex, 'knows.primary')}
                                                />
                                            }
                                            label="Primary"
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <Checkbox
                                            checked={row.workwith}
                                            onChange={(event) => handleCheckboxChange(event, rowIndex, 'workwith')}
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <Checkbox
                                            checked={row.master}
                                            onChange={(event) => handleCheckboxChange(event, rowIndex, 'master')}
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <Checkbox
                                            checked={row.interest}
                                            onChange={(event) => handleCheckboxChange(event, rowIndex, 'interest')}
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <MDInput
                                            label="Comment"
                                            value={row.certificate.comment}
                                            onChange={(event) => handleSelectChange(event, rowIndex, 'certificate.comment')}
                                        />
                                        <DatePicker
                                            selected={row.certificate.date}
                                            //onSelect={(date) => setRows(date)}
                                            onChange={(date) => handleDateChange(date, 'certificate', rowIndex)}
                                            dateFormat="dd-MM-yyyy"
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <IconButton onClick={() => handleDeleteRow(rowIndex)}>
                                            <DeleteIcon />
                                        </IconButton>
                                    </TableCell>
                                </TableRow>
                            ))}
                    </TableBody>
                </Table>
                <TablePagination
                    rowsPerPageOptions={[10, 25, 50]}
                    component="div"
                    count={rows.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={(event, newPage) => setPage(newPage)}
                    onRowsPerPageChange={(event) => {
                        setRowsPerPage(parseInt(event.target.value, 10));
                        setPage(0);
                    }}
                />
            </TableContainer>
            <MDButton onClick={handleReturnRows}>Return Rows</MDButton>
        </DashboardLayout>
    );
};

export default SkillTable;