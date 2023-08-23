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
import { format, parse } from 'date-fns';
import moment from 'moment';

const levels = ['LOW', 'MIDDLE', 'ADVANCED'];

const SkillTable = ({ skill, onReturnRows }) => {
    const [rows, setRows] = useState([]);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);

    useEffect(() => {
        const skillExists = rows.some(row => row.code === skill.nodeId);

        if (!skillExists) {
            const newRow = {
                name: skill.name,
                code: skill.nodeId,
                knows: {
                    level: '',
                    experience: 0,
                    primary: false,
                    add: false,
                },
                workwith: false,
                interest: false,
                master: false,
                certificates: {
                    comment: '',
                    date: moment().format('DD-MM-YYYY'),
                    add: false,
                },
            };
            setRows(prevRows => [...prevRows, newRow]);
        }
    }, [skill, rows]);

    const handleCheckboxChange = (event, rowIndex, field) => {
        const updatedRows = [...rows];
        updatedRows[rowIndex][field] = event.target.checked;
        setRows(updatedRows);
    };

    const handleComplexCheckboxChange = (event, rowIndex, field) => {
        const [mainField, nestedField] = field.split('.');
        const updatedRows = [...rows];
        updatedRows[rowIndex][mainField][nestedField] = event.target.checked;
        setRows(updatedRows);
    };

    const handleSelectChange = (event, rowIndex, field) => {
        const [mainField, nestedField] = field.split('.');
        const updatedRows = [...rows];
        updatedRows[rowIndex][mainField][nestedField] = event.target.value;
        setRows(updatedRows);
    };

    const handleDateChange = (date, field, rowIndex) => {
        const updatedRows = [...rows];
        updatedRows[rowIndex][field].date = moment(date).format('DD-MM-YYYY');
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
                            <TableCell>Certificatescertificates</TableCell>
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
                                            sx={{ width: '100%' }}
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
                                                    onChange={(event) => handleComplexCheckboxChange(event, rowIndex, 'knows.primary')}
                                                />
                                            }
                                            label="Primary"
                                        />
                                        <FormControlLabel
                                            control={
                                                <Checkbox
                                                    checked={row.knows.add}
                                                    onChange={(event) => handleComplexCheckboxChange(event, rowIndex, 'knows.add')}
                                                />
                                            }
                                            label="Add"
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <FormControlLabel
                                            control={
                                                <Checkbox
                                                    checked={row.workwith}
                                                    onChange={(event) => handleCheckboxChange(event, rowIndex, 'workwith')}
                                                />
                                            }
                                            label="Add"
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <FormControlLabel
                                            control={
                                                <Checkbox
                                                    checked={row.master}
                                                    onChange={(event) => handleCheckboxChange(event, rowIndex, 'master')}
                                                />
                                            }
                                            label="Add"
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <FormControlLabel
                                            control={
                                                <Checkbox
                                                    checked={row.interest}
                                                    onChange={(event) => handleCheckboxChange(event, rowIndex, 'interest')}
                                                />
                                            }
                                            label="Add"
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <MDInput
                                            label="Comment"
                                            value={row.certificates.comment}
                                            onChange={(event) => handleSelectChange(event, rowIndex, 'certificates.comment')}
                                        />
                                        <DatePicker
                                            selected={parse(row.certificates.date, 'dd-MM-yyyy', new Date())}
                                            onSelect={(date) => handleDateChange(date, 'certificates', rowIndex)}
                                            onChange={(date) => handleDateChange(date, 'certificates', rowIndex)}
                                            dateFormat='dd-MM-yyyy'
                                        />
                                        <FormControlLabel
                                            control={
                                                <Checkbox
                                                    checked={row.certificates.add}
                                                    onChange={(event) => handleComplexCheckboxChange(event, rowIndex, 'certificates.add')}
                                                />
                                            }
                                            label="Add"
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