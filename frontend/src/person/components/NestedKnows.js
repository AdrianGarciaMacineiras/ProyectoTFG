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
  TableRow,
} from '@mui/material';

const NestedKnows = (props) => {
  const { data, state, onStateChange, onPageChange, onRowsPerPageChange } = props;
  const { page=0, rowsPerPage=10, orderBy, sortDirection } = state || {};

  const handleSortChange = (column) => {
    const newSortDirection = orderBy === column ? (sortDirection === 'asc' ? 'desc' : 'asc') : 'asc';
    const newState = { page, rowsPerPage, orderBy: column, sortDirection: newSortDirection };
    onStateChange(newState);
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
    if (column !== 'experience') {
      return <SortByAlphaIcon />;
    } else {
      return <SortIcon />;
    }
  };

  return (
    <>
      <Table>
        <TableHead sx={{ display: "table-header-group" }}>
          <TableRow>
            <TableCell align='left' onClick={() => handleSortChange('name')}>
              {getSortIcon('name')}
              Name
            </TableCell>
            <TableCell align='left' onClick={() => handleSortChange('level')}>
              {getSortIcon('level')}
              Level
            </TableCell>
            <TableCell align='left' onClick={() => handleSortChange('experience')}>
              {getSortIcon('experience')}
              Experience
            </TableCell>
            <TableCell align='left' onClick={() => handleSortChange('primary')}>
              {getSortIcon('primary')}
              Primary
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {data?.slice()
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
                <TableCell style={{ width: "5%" }}>{row.name}</TableCell>
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
        onPageChange={handlePageChange}
        onRowsPerPageChange={handleRowsPerPageChange}
      />
    </>
  );
};

export default NestedKnows;