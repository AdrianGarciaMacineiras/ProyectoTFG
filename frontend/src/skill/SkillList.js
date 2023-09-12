import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
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
import TreeItem from '@mui/lab/TreeItem';
import TreeView from '@mui/lab/TreeView';
import Clear from '@mui/icons-material/Clear';
import {
  IconButton,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TablePagination,
  TableRow,
  TableContainer,
  Paper
} from '@mui/material';
import Card from '@mui/material/Card';
import Collapse from '@mui/material/Collapse';
import Grid from '@mui/material/Grid';
import React, { useEffect, useState } from 'react';
import VisGraph from 'react-vis-graph-wrapper';
import MDTypography from '../components/MDTypography';

import Footer from '../components/Footer';
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDButton from '../components/MDButton';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';
import NestedKnows from '../person/components/NestedKnows';
import SkillSelect from '../position/components/SkillSelect';

function SkillList() {
  const [skillList, setSkillList] = useState([]);

  const [selected, setSelected] = useState([]);

  const [isToggled, setToggled] = useState(false);
  const [showTable, setShowTable] = useState(false);

  const [tableData, setTableData] = useState([]);

  const [expandAll, setExpandAll] = useState([]);
  const [expand, setExpand] = useState([]);
  const [selectedItem, setSelectedItem] = useState(null);
  const [levelReq, setLevelReq] = useState('');
  const [minLevel, setMinLevel] = useState('');
  const [minExp, setMinExp] = useState(0);
  const [skill, setSkill] = useState(null);
  const [skillName, setSkillName] = useState(null);

  const [searchSkill, setSearchSkill] = useState('');

  const graphTemp = { nodes: [], edges: [] };

  const [graph, setGraph] = useState({ nodes: [], edges: [] });

  const options = {
    layout: { improvedLayout: true },
    nodes: { shape: 'dot', scaling: { min: 10, label: false } },
    edges: {
      color: '#000000',
      smooth: { enabled: true, type: 'discrete', roundness: 0.5 }
    },
    groups: {
      skills: { color: { background: 'red' }, borderWidth: 3 },
    },
    height: '800px',
    physics: {
      forceAtlas2Based: {
        gravitationalConstant: -26,
        centralGravity: 0.005,
        springLength: 230,
        springConstant: 0.18,
        avoidOverlap: 0.02
      },
      maxVelocity: 146,
      solver: "forceAtlas2Based",
      timestep: 0.35,
      stabilization: {
        enabled: true,
        iterations: 2000,
        updateInterval: 25,
      },
    }
  };

  const events = {
    select: function (event) {
      let { nodes, edges } = event;
    }
  };

  const DataPerson = ({ row }) => {
    const [open, setOpen] = useState(false);
    const [nestedKnowStates, setNestedKnowStates] = useState({});

    const handleNestedKnowStateChange = (personCode, newState) => {
      setNestedKnowStates(prevStates => ({
        ...prevStates,
        [personCode]: newState
      }));
    };

    const handleNestedKnowPageChange = (personCode, newPage) => {
      setNestedKnowStates(prevStates => ({
        ...prevStates,
        [personCode]: {
          ...prevStates[personCode],
          page: newPage
        }
      }));
    };

    const handleNestedKnowRowsPerPageChange = (personCode, newRowsPerPage) => {
      setNestedKnowStates(prevStates => ({
        ...prevStates,
        [personCode]: {
          ...prevStates[personCode],
          rowsPerPage: newRowsPerPage,
          page: 0
        }
      }));
    };

    return (
      <React.Fragment>
        <TableRow sx={
          { '& > *': { borderBottom: 'unset' } }} key={row.index}>
          <TableCell>
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
          </TableCell>
          <TableCell scope='row'>{row.name}</TableCell>
          <TableCell>{row.surname}</TableCell>
          <TableCell>{row.email}</TableCell>
          <TableCell>{row.title}</TableCell>
          <TableCell>{row.employeeId}</TableCell>
          <TableCell>{row.friendlyName}</TableCell>
        </TableRow>
        <TableRow>
          <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={8}>
            <Collapse in={open} timeout='auto' unmountOnExit>
              <MDBox sx={{ margin: 1 }}>
                <NestedKnows
                  key={row.code}
                  data={row.knows}
                  state={nestedKnowStates[row.code]}
                  onStateChange={newState => handleNestedKnowStateChange(
                    row.code, newState)}
                  onPageChange={newPage => handleNestedKnowPageChange(
                    row.code, newPage)}
                  onRowsPerPageChange={newRowsPerPage => handleNestedKnowRowsPerPageChange(
                    row.code, newRowsPerPage)}
                />
              </MDBox>
            </Collapse>
          </TableCell>
        </TableRow>
      </React.Fragment>
    );
  };

  function TableComponent({ data }) {
    const [open, setOpen] = useState(false);

    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(50);

    const [orderBy, setOrderBy] = useState('');
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
      setRowsPerPage(parseInt(event.target.value, 50));
      setPage(0);
    };

    function getSortIcon(column) {
      if (orderBy === column) {
        return sortDirection === 'asc' ? <ArrowDropUpIcon /> :
          <ArrowDropDownIcon />;
      }

      if (column !== 'code') {
        return <SortByAlphaIcon />;
      } else {
        return <SortIcon />;
      }
    }

    return (
      <React.Fragment>
        <Table sx={{ minWidth: 650 }}>
          <TableHead sx={{ display: 'table-header-group' }}>
            <TableRow>
              <TableCell width='100' component='th' scope='row'>
                <IconButton
                  aria-label='expand row'
                  size='small'
                  onClick={() => setOpen(!open)}
                >
                  {open ?
                    <KeyboardDoubleArrowUpIcon /> :
                    <KeyboardDoubleArrowDownIcon />
                  }
                </IconButton>
              </TableCell>
              <TableCell align='left'
                onClick={() => handleSortChange('name')}>
                {getSortIcon('name')}
                Name
              </TableCell>
              <TableCell align='left'
                onClick={() => handleSortChange('surname')}>
                {getSortIcon('surname')}
                Surname
              </TableCell>
              <TableCell align='left'
                onClick={() => handleSortChange('email')}>
                {getSortIcon('email')}
                Email
              </TableCell>
              <TableCell align='left'
                onClick={() => handleSortChange('title')}>
                {getSortIcon('title')}
                Title
              </TableCell>
              <TableCell align='left'
                onClick={() => handleSortChange('employeeId')}>
                {getSortIcon('employeeId')}
                Employee ID
              </TableCell>
              <TableCell align='left'
                onClick={() => handleSortChange('friendlyName')}>
                {getSortIcon('friendlyName')}
                Friendly Name
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
                <DataPerson key={index} row={row} />
              ))}
          </TableBody>
        </Table>
        < TablePagination
          rowsPerPageOptions={[5, 10, 25, 50]} component='div'
          count={data?.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </React.Fragment>
    );
  }

  const handleClick = () => {
    setToggled(!isToggled)
    console.log(selected);
    fetch(
      `http://${window.location.hostname}:9080/api/people/skills?skillList=${selected}`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*'
        }
      })
      .then(response => {
        return response.json()
      })
      .then(data => {
        setTableData(data);
        let i = 1;
        let j = 2;
        data.forEach(element => {
          let temp = {
            Code: element.code,
            Name: element.name,
            Surname: element.surname,
            Email: element.email,
            EmployeeId: element.employeeId,
            FriendlyName: element.friendlyName,
            Title: element.title,
            BirthDate: element.birthDate
          };
          graphTemp.nodes.push({
            id: i,
            label: element.name + ' ' + element.surname,
            title: JSON.stringify(temp, '', 2)
          });
          element.knows.forEach(knows => {
            let temp = {
              Code: knows.code,
              Primary: knows.primary,
              Experience: knows.experience,
              Level: knows.level
            }
            graphTemp.nodes.push(
              { id: j, label: knows.name, title: knows.name, group: 'skills' });
            graphTemp.edges.push({
              from: i,
              to: j,
              label: 'KNOWS',
              title: JSON.stringify(temp, '', 2)
            });
            j++
          })
          i = j++;
        })
        setGraph(prev => graphTemp);
      });
    setSelected([]);
  }

  useEffect(() => {
    const recursive =
      (dataList) => {
        let list = [];
        dataList.forEach(data => {
          setExpandAll(nodes => [...nodes, data.code]);
          list.push({
            nodeId: data.code,
            name: data.name,
            children: recursive(data.subSkills)
          })
        });
        return list;
      }

    fetch(`http://${window.location.hostname}:9080/api/skills`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      }
    })
      .then(response => {
        return response.json()
      })
      .then(response => {
        const skillsData = recursive(response);
        setSkillList(skillsData);
      });

  }, [])

  const handleShowTable = () => {
    setShowTable((prevShowTable) => !prevShowTable);
  };

  const handleNodeSelect = (event, item) => {
    event.stopPropagation();
    setSelectedItem(item);
    setSkill(item.nodeId);
    setSkillName(item.name);
    setLevelReq('');
    setMinLevel('');
    setMinExp(0);
  };

  const getTreeItemsFromData = (treeItems, searchValue) => {
    const filteredItems = treeItems.filter((treeItemData) => {
      const isMatched =
        treeItemData.name.toLowerCase().includes(searchValue.toLowerCase()) ||
        getTreeItemsFromData(treeItemData.children, searchValue).length > 0;

      return isMatched;
    });

    return filteredItems.map((treeItemData) => {
      const isMatched =
        treeItemData.name.toLowerCase().includes(searchValue.toLowerCase());

      if (isMatched) {
        return (
          <TreeItem
            key={treeItemData.nodeId}
            nodeId={treeItemData.nodeId} label=
            {
              <div onClick={(event) => {
                event.stopPropagation();
                handleNodeSelect(event, treeItemData)
              }}>
                {treeItemData.name}
              </div>
            }
          >
            {getTreeItemsFromData(treeItemData.children, searchValue)}
          </TreeItem>
        );
      }

      return getTreeItemsFromData(treeItemData.children, searchValue);
    });
  };

  const handleExpandClick = () => {
    setExpand((oldExpanded) =>
      oldExpanded.length === 0 ? expandAll : [],
    );
  };

  const handleToggle = (event, nodeIds) => {
    setExpand(nodeIds)
  }

  const DataTreeView = () => {
    return (
      <MDBox>
        <TreeView
          defaultCollapseIcon={<ExpandMoreIcon />}
          defaultExpandIcon={<ChevronRightIcon />}
          defaultExpanded={expandAll}
          expanded={expand}
          onNodeToggle={handleToggle}
        >
          {getTreeItemsFromData(skillList, searchSkill)}
        </TreeView>
      </MDBox>
    );
  };

  const handleModalSubmit = (event) => {
    event.preventDefault();
    const newItem = { skill: skill, skillName, levelReq, minLevel, minExp };
    setSelected(selected => [...selected, newItem]);
    setSelectedItem(null)
    setSkill(null);
    setSkillName(null)
    setLevelReq('');
    setMinLevel('');
    setMinExp(0);
  };

  const handleRemoveSkill =
    (index) => {
      const updatedSkills = [...selected];
      updatedSkills.splice(index, 1);
      setSelected(updatedSkills);
    };

  const SelectedList =
    () => {
      return (
        <React.Fragment>
          <MDBox>
            <TableContainer component={Paper}>
              <Table sx={{ minWidth: 650 }} aria-label='simple table'>
                <TableHead sx={{ display: 'table-header-group' }}>
                  <TableRow>
                    <TableCell>Skill</TableCell>
                    <TableCell>Level Required</TableCell>
                    <TableCell>Minimum Level</TableCell>
                    <TableCell>Minimum Experience</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {selected.map((skill, index) => (
                    <TableRow key={index}>
                      {console.log(skill.skillName)}
                      <TableCell>{skill.skillName}</TableCell>
                      <TableCell>{skill.levelReq}</TableCell>
                      <TableCell>{skill.minLevel}</TableCell>
                      <TableCell>{skill.minExp}</TableCell>
                      <TableCell>
                        <IconButton
                          color='error'
                          onClick={() => handleRemoveSkill(index)}
                        >
                          <Clear />
                        </IconButton>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </MDBox>
        </React.Fragment>
      );
    };

  return (
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
                coloredShadow='info'>
                <MDTypography variant='h6' color='white'>Find People by
                  Skill</MDTypography>
              </MDBox>
              <Grid container spacing={12}>
                <Grid item xs={12}>
                  <MDBox pt={3}>
                    <SkillSelect
                      expand={expand}
                      handleExpandClick={handleExpandClick}
                      searchSkill={searchSkill}
                      setSearchSkill={setSearchSkill}
                      DataTreeView={DataTreeView}
                      selectedItem={selectedItem}
                      setLevelReq={setLevelReq}
                      setMinLevel={setMinLevel}
                      setMinExp={setMinExp}
                      levelReq={levelReq}
                      minLevel={minLevel}
                      minExp={minExp}
                      handleModalSubmit={handleModalSubmit}
                      setSelectedItem={setSelectedItem}
                    />
                  </MDBox>
                </Grid>
                  {selected.length > 0 && 
                  (<Grid item xs={12}>
                    <SelectedList />
                  </Grid>)}
                <Grid item xs={12}>
                  {(selected && selected.length > 0) &&
                    <MDButton color='black'
                      onClick={handleClick}> Submit </MDButton>
                  }
                  {isToggled &&
                    <MDButton color='black' onClick={handleShowTable}>
                      {showTable ? "Show Table" : "Show Graph"}
                    </MDButton>
                  }
                </Grid>
              </Grid>
            </Card>
          </Grid>
        </Grid>
      </MDBox>
      <MDBox pt={6} pb={3}>
        <Grid container spacing={6}>
          <Grid item xs={12}>
            <Card>
              <MDBox>
                {isToggled && (showTable ? (
                  <>
                    <MDBox mx={2} mt={-3} py={3} px={2} variant='gradient'
                      bgColor='info'
                      borderRadius='lg'
                      coloredShadow='info'
                    >
                      <MDTypography variant='h6' color='white'>Person
                        Graph</MDTypography>
                    </MDBox>
                    <VisGraph
                      graph={graph}
                      options={options}
                      events={events}
                      getNetwork={network => {
                      }}
                    />
                  </>
                ) : (
                  <>
                    <MDBox mx={2} mt={-3} py={3} px={2} variant='gradient'
                      bgColor='info'
                      borderRadius='lg'
                      coloredShadow=
                      'info'>
                      <MDTypography variant='h6' color='white'>Person
                        Table</MDTypography>
                    </MDBox>
                    <TableComponent data={tableData} />
                  </>
                ))}
              </MDBox>
            </Card>
          </Grid>
        </Grid>
      </MDBox>
      <Footer />
    </DashboardLayout>
  );
}

export default SkillList;

