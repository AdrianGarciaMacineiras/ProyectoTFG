import React, { useState } from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  TablePagination,
} from '@mui/material';


const NestedCertificate = ({ data }) => {
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

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
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const paginatedData = data?.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage);

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
            <TableCell>Comments</TableCell>
            <TableCell align='left' onClick={() => handleSortChange('date')}>
              <strong>{
                orderBy === 'date' ? (sortDirection === 'asc' ? '▲ ' : '▼ ') : null}</strong>
              Date
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {paginatedData?.slice()
            .sort((a, b) => {
              const aValue = a[orderBy];
              const bValue = b[orderBy];

              if (sortDirection === 'asc') {
                return aValue < bValue ? -1 : aValue > bValue ? 1 : 0;
              } else {
                return aValue > bValue ? -1 : aValue < bValue ? 1 : 0;
              }
            })
            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row, index) => (
              <TableRow key={index}>
                <TableCell style={{ width: "5%" }}>{row.code}</TableCell>
                <TableCell style={{ width: "5%" }}>{row.comments}</TableCell>
                <TableCell style={{ width: "5%" }}>{row.date}</TableCell>
              </TableRow>
            ))}
        </TableBody>
      </Table>

      <TablePagination
        rowsPerPageOptions={[5, 10, 25, 50]}
        component="div"
        count={data?.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
    </>
  );
};

export default NestedCertificate;