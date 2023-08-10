import React, { useState } from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  TablePagination,
} from '@mui/material';


const NestedKnows = ({ data }) => {
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);

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

  const getExperienceValue = (experience) => {
    switch (experience) {
      case 'LOW':
        return 1;
      case 'MIDDLE':
        return 2;
      case 'ADVANCED':
        return 3;
      default:
        return 0;
    }
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const paginatedData = data.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage);

  return (
    <>
      <Table>
        <TableHead sx={{ display: "table-header-group" }}>
          <TableRow>
            <TableCell align='left' onClick={() => handleSortChange('code')}>
              <strong>{
                orderBy === 'code' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') : null}</strong>
              Code
            </TableCell>
            <TableCell align='left' onClick={() => handleSortChange('level')}>
              <strong>{
                orderBy === 'level' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') : null}</strong>
              Level
            </TableCell>
            <TableCell align='left' onClick={() => handleSortChange('experience')}>
              <strong>{
                orderBy === 'experience' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') : null}</strong>
              Experience
            </TableCell>
            <TableCell align='left' onClick={() => handleSortChange('primary')}>
              <strong>{
                orderBy === 'primary' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') : null}</strong>
              Primary
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {paginatedData?.slice()
            .sort((a, b) => {
              if (orderBy === 'level') {
                const aValue = getExperienceValue(a[orderBy]);
                const bValue = getExperienceValue(b[orderBy]);

                if (sortDirection === 'asc') {
                  return aValue - bValue;
                } else {
                  return bValue - aValue;
                }
              } else {
                const aValue = a[orderBy];
                const bValue = b[orderBy];

                if (sortDirection === 'asc') {
                  return aValue < bValue ? -1 : aValue > bValue ? 1 : 0;
                } else {
                  return aValue > bValue ? -1 : aValue < bValue ? 1 : 0;
                }
              }
            })
            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
            .map((row, index) => (
              <TableRow key={index}>
                <TableCell style={{ width: "5%" }}>{row.code}</TableCell>
                <TableCell>{row.level}</TableCell>
                <TableCell>{row.experience}</TableCell>
                <TableCell>{row.primary}</TableCell>
              </TableRow>
            ))}
        </TableBody>
      </Table>

      <TablePagination
        rowsPerPageOptions={[5, 10, 25, 50]}
        component="div"
        count={data.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
    </>
  );
};

export default NestedKnows;