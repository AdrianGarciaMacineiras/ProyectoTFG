import { useEffect, useRef, useState } from 'react';
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import {
    Checkbox,
    FormControlLabel,
    IconButton,
    MenuItem,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TablePagination,
    TableRow,
    Select,
    InputLabel,
} from '@mui/material';
import { Delete as DeleteIcon } from '@mui/icons-material';
import DatePicker from 'react-datepicker';
import MDButton from '../components/MDButton';
import MDInput from '../components/MDInput';
import { parse } from 'date-fns';
import moment from 'moment';

const levels = ['LOW', 'MIDDLE', 'ADVANCED'];

const SkillTable = ({ skill, onReturnRows, listToUpdate }) => {
    const rowsRef = useRef([]);
    const [rows, setRows] = useState([]);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);

    useEffect(() => {
        if (listToUpdate) {
            const newRows = [];

            listToUpdate.knows.forEach(knowsItem => {
                const matchingRow = newRows.find(row => row.code === knowsItem.code);

                if (!matchingRow) {
                    const newRow = {
                        code: knowsItem.code,
                        name: knowsItem.name,
                        knows: { ...knowsItem, add: true },
                        workwith: false,
                        master: false,
                        interest: false,
                        certificates: {
                            comment: '',
                            date: moment().format('DD-MM-YYYY'),
                            add: false,
                        },
                    };
                    newRows.push(newRow);
                }
            });

            listToUpdate.certificates.forEach(certificatesItem => {
                const matchingRow = newRows.find(row => row.code === certificatesItem.code);

                if (matchingRow) {
                    matchingRow.certificates = { ...certificatesItem };
                } else {
                    const newRow = {
                        code: certificatesItem.code,
                        name: certificatesItem.name,
                        knows: {
                            level: '',
                            experience: 0,
                            primary: false,
                            add: false,
                        },
                        workwith: false,
                        master: false,
                        interest: false,
                        certificates: { ...certificatesItem, add: true },
                    };
                    newRows.push(newRow);
                }
            });

            listToUpdate.master.forEach(masterItem => {
                const matchingRow = newRows.find(row => row.code === masterItem.code);

                if (matchingRow) {
                    matchingRow.master = true;
                } else {
                    const newRow = {
                        code: masterItem.code,
                        name: masterItem.name,
                        knows: {
                            level: '',
                            experience: 0,
                            primary: false,
                            add: false,
                        },
                        workwith: false,
                        master: true,
                        interest: false,
                        certificates: {
                            comment: '',
                            date: moment().format('DD-MM-YYYY'),
                            add: false,
                        },
                    };
                    newRows.push(newRow);
                }
            });

            listToUpdate.interest.forEach(interestItem => {
                const matchingRow = newRows.find(row => row.code === interestItem.code);

                if (matchingRow) {
                    matchingRow.interest = true;
                } else {
                    const newRow = {
                        code: interestItem.code,
                        name: interestItem.name,
                        knows: {
                            level: '',
                            experience: 0,
                            primary: false,
                            add: false,
                        },
                        workwith: false,
                        master: true,
                        interest: false,
                        certificates: {
                            comment: '',
                            date: moment().format('DD-MM-YYYY'),
                            add: false,
                        },
                    };
                    newRows.push(newRow);
                }
            });

            listToUpdate.work_with.forEach(workWithItem => {
                const matchingRow = newRows.find(row => row.code === workWithItem.code);

                if (matchingRow) {
                    matchingRow.workwith = true;
                } else {
                    const newRow = {
                        code: workWithItem.code,
                        name: workWithItem.name,
                        knows: {
                            level: '',
                            experience: 0,
                            primary: false,
                            add: false,
                        },
                        workwith: false,
                        master: true,
                        interest: false,
                        certificates: {
                            comment: '',
                            date: moment().format('DD-MM-YYYY'),
                            add: false,
                        },
                    };
                    newRows.push(newRow);
                }
            });
            rowsRef.current = newRows;
        }
    }, [listToUpdate]);


    useEffect(() => {
        if (skill) {
            const skillExists = rowsRef.current.some(row => row.code === skill.nodeId);

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
                    notAssigned: true,
                };
                console.log("insertar", rowsRef.current)
                rowsRef.current = [newRow, ...rowsRef.current];
                setRows(rowsRef.current);
            }
        }
    }, [skill]);


    const handleCheckboxChange = (event, rowIndex, field) => {
        const updatedRows = [...rowsRef.current];
        updatedRows[rowIndex][field] = event.target.checked;
        if (updatedRows[rowIndex].notAssigned) {
            updatedRows[rowIndex].notAssigned = false;
        }
        rowsRef.current = updatedRows;
        setRows(rowsRef.current);
    };

    const handleComplexCheckboxChange = (event, rowIndex, field) => {
        const [mainField, nestedField] = field.split('.');
        const updatedRows = [...rowsRef.current];
        updatedRows[rowIndex][mainField][nestedField] = event.target.checked;
        if (updatedRows[rowIndex].notAssigned) {
            updatedRows[rowIndex].notAssigned = false;
        }
        rowsRef.current = updatedRows;
        setRows(rowsRef.current);
    };

    const handleSelectChange = (event, rowIndex, field) => {
        const [mainField, nestedField] = field.split('.');
        const updatedRows = [...rowsRef.current];
        if (nestedField === 'experience') {
            const newValue = parseInt(event.target.value);
            updatedRows[rowIndex][mainField][nestedField] = Math.max(newValue, 0);
        } else {
            updatedRows[rowIndex][mainField][nestedField] = event.target.value;
        }
        rowsRef.current = updatedRows;
        setRows(rowsRef.current);
    };

    const handleDateChange = (date, field, rowIndex) => {
        const updatedRows = [...rowsRef.current];
        updatedRows[rowIndex][field].date = moment(date).format('DD-MM-YYYY');
        rowsRef.current = updatedRows;
        setRows(rowsRef.current);
    };

    const handleDeleteRow = (rowIndex) => {
        const updatedRows = rowsRef.current.filter((row, index) => index !== rowIndex);
        rowsRef.current = updatedRows;
        setRows(rowsRef.current);
    };

    const handleReturnRows = () => {
        onReturnRows(rowsRef.current);
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
                            <TableCell>Certificates</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {rowsRef.current
                            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                            .map((row, rowIndex) => (
                                <TableRow key={rowIndex}>
                                    <TableCell>{row.name}</TableCell>
                                    <TableCell align="center"
                                        style={{ verticalAlign: 'top' }}>
                                        <FormControlLabel
                                            control={
                                                <Checkbox
                                                    checked={row.knows.add}
                                                    onChange={(event) => handleComplexCheckboxChange(
                                                        event, rowIndex,
                                                        'knows.add')}
                                                />
                                            }
                                        />
                                        <InputLabel id="Level-select">Level</InputLabel>
                                        <Select select
                                            labelId="Level-select"
                                            value={row.knows.level}
                                            onChange={(event) => handleSelectChange(event, rowIndex, 'knows.level')}
                                            sx={{
                                                width: '100%',
                                                height: 50,
                                            }}
                                        >
                                            {levels.map((level) => (
                                                <MenuItem key={level} value={level}>
                                                    {level}
                                                </MenuItem>
                                            ))}
                                        </Select>
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
                                    </TableCell>
                                    <TableCell align="center"
                                        style={{ verticalAlign: 'top' }}>
                                        <FormControlLabel
                                            label={"add"}
                                            control={
                                                <Checkbox
                                                    checked={row.workwith}
                                                    onChange={(event) => handleCheckboxChange(event, rowIndex, 'workwith')}
                                                />
                                            }
                                        />
                                    </TableCell>
                                    <TableCell align="center"
                                        style={{ verticalAlign: 'top' }}>
                                        <FormControlLabel
                                            control={
                                                <Checkbox
                                                    checked={row.master}
                                                    onChange={(event) => handleCheckboxChange(event, rowIndex, 'master')}
                                                />
                                            }
                                        />
                                    </TableCell>
                                    <TableCell align="center"
                                        style={{ verticalAlign: 'top' }}>
                                        <FormControlLabel
                                            control={
                                                <Checkbox
                                                    checked={row.interest}
                                                    onChange={(event) => handleCheckboxChange(event, rowIndex, 'interest')}
                                                />
                                            }
                                        />
                                    </TableCell>
                                    <TableCell align="center"
                                        style={{ verticalAlign: 'top' }}>
                                        <FormControlLabel
                                            control={
                                                <Checkbox
                                                    checked={row.certificates.add}
                                                    onChange={(event) => handleComplexCheckboxChange(
                                                        event, rowIndex,
                                                        'certificates.add')}
                                                />
                                            }
                                        />
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
                    count={rowsRef.current.length}
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