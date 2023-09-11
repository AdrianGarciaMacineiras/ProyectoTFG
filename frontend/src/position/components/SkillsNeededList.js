import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import ArrowDropUpIcon from '@mui/icons-material/ArrowDropUp';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import SortIcon from '@mui/icons-material/Sort';
import SortByAlphaIcon from '@mui/icons-material/SortByAlpha';
import {
    IconButton,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TablePagination,
    TableRow,
    Tooltip
} from '@mui/material';
import React, { useState } from 'react';

const SkillsNeededList = (props) => {
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
        if (column !== 'minExp') {
            return <SortByAlphaIcon />;
        } else {
            return <SortIcon />;
        }
    };
    const DataSkill = ({ row }) => {
        const [open, setOpen] = useState(false);

        return (
            <React.Fragment >
                <TableRow sx={{ '& > *': { borderBottom: 'unset' } }} key={row.index}>
                    <TableCell>
                        <Tooltip title="Expand row">
                            <IconButton
                                aria-label='expand row'
                                size='small'
                                onClick={() => setOpen(!open)}
                                style={{ width: '5%' }}
                            >
                                {open ?
                                    <KeyboardArrowUpIcon /> :
                                    <KeyboardArrowDownIcon />
                                }
                            </IconButton>
                        </Tooltip>
                    </TableCell>
                    <TableCell align="left">{row.skill}</TableCell>
                    <TableCell align='left'>{row.levelReq}</TableCell>
                    <TableCell align="left">{row.minLevel}</TableCell>
                    <TableCell align='left'>{row.minExp}</TableCell>
                </TableRow>
            </React.Fragment >
        );
    };

    return (
        <>
            <Table>
                <TableHead sx={{ display: "table-header-group" }}>
                    <TableCell align='left' onClick={() => handleSortChange('skill')}>
                        {getSortIcon('skill')}
                        Skill
                    </TableCell>
                    <TableCell align='left' onClick={() => handleSortChange('levelReq')}>
                        {getSortIcon('levelReq')}
                        Requirement Level
                    </TableCell>
                    <TableCell align='left' onClick={() => handleSortChange('minLevel')}>
                        {getSortIcon('minLevel')}
                        Minimum Level
                    </TableCell>
                    <TableCell align='left' onClick={() => handleSortChange('minExp')}>
                        {getSortIcon('minExp')}
                        Minimum Experience
                    </TableCell>
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
                            <DataSkill key={index} row={row} />
                        ))}
                </TableBody>
            </Table>
            <TablePagination
                rowsPerPageOptions={[5, 10, 25, 50]} component='div'
                count={data?.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handlePageChange}
                onRowsPerPageChange={
                    handleRowsPerPageChange}
            />
        </>
    );
};
export default SkillsNeededList;