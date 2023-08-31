
import ArrowDropUpIcon from '@mui/icons-material/ArrowDropUp';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import SortIcon from '@mui/icons-material/Sort';
import SortByAlphaIcon from '@mui/icons-material/SortByAlpha';
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TablePagination,
    TableRow
} from '@mui/material';
import React from 'react';

const CandidateList = (props) => {
    const { data, state, onStateChange, onPageChange, onRowsPerPageChange } = props;
    const { page = 0, rowsPerPage = 10, orderBy, sortDirection } = state || {};

    const handleSortChange = (column) => {
        const newSortDirection = orderBy === column ? (sortDirection === 'asc' ? 'desc' : 'asc') : 'asc';
        const newState = { page, rowsPerPage, orderBy: column, sortDirection: newSortDirection };
        onStateChange(newState);
    };

    const handlePageChange = (event, newPage) => {
        onPageChange(newPage);
    };

    const handleRowsPerPageChange = (event) => {
        const newRowsPerPage = parseInt(event.target.value, 10);
        onRowsPerPageChange(newRowsPerPage);
        onPageChange(0);
    };

    function getSortIcon(column) {
        if (orderBy === column) {
            return sortDirection === 'asc' ? <ArrowDropUpIcon /> : <ArrowDropDownIcon />;
        };
        if (column === 'status') {
            return <SortByAlphaIcon />;
        } else {
            return <SortIcon />;
        }
    }

    const DataCandidate = ({ row }) => {

        return (
            <React.Fragment >
                <TableRow sx={{ '& > *': { borderBottom: 'unset' } }} key={row.index}>
                    <TableCell align="left">{row.code}</TableCell>
                    <TableCell align='left'>{row.introductionDate}</TableCell>
                    <TableCell align="left">{row.resolutionDate}</TableCell>
                    <TableCell align='left'>{row.creationDate}</TableCell>
                    <TableCell align="left">{row.status}</TableCell>
                    <TableCell align="left">{row.candidateCode}</TableCell>
                </TableRow>
            </React.Fragment >
        );
    };

    return (
        <>
            <Table>
                <TableHead sx={{ display: "table-header-group" }}>
                    <TableRow>
                        <TableCell align='left' onClick={() => handleSortChange('code')}>
                            {getSortIcon('code')}
                            Code
                        </TableCell>
                        <TableCell align='left' onClick={() => handleSortChange('introductionDate')}>
                            {getSortIcon('introductionDate')}
                            Introduction Date
                        </TableCell>
                        <TableCell align='left' onClick={() => handleSortChange('resolutionDate')}>
                            {getSortIcon('resolutionDate')}
                            Resolution Date
                        </TableCell>
                        <TableCell align='left' onClick={() => handleSortChange('creationDate')}>
                            {getSortIcon('creationDate')}
                            Creation Date
                        </TableCell>
                        <TableCell align='left' onClick={() => handleSortChange('status')}>
                            {getSortIcon('status')}
                            Status
                        </TableCell>
                        <TableCell align='left' onClick={() => handleSortChange('candidateCode')}>
                            {getSortIcon('candidateCode')}
                            Candidate Code
                        </TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {data?.slice()
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
                            <DataCandidate key={index} row={row} />
                        ))}
                </TableBody>
            </Table>
            <TablePagination
                rowsPerPageOptions={[5, 10, 25, 50]} component='div'
                count={data.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handlePageChange}
                onRowsPerPageChange={
                    handleRowsPerPageChange}
            />
        </>
    );
};

export default CandidateList;