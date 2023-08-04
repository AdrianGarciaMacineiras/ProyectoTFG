import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import KeyboardDoubleArrowDownIcon from '@mui/icons-material/KeyboardDoubleArrowDown';
import KeyboardDoubleArrowUpIcon from '@mui/icons-material/KeyboardDoubleArrowUp';
import TreeItem from '@mui/lab/TreeItem';
import TreeView from '@mui/lab/TreeView';
import {IconButton, Table, TableBody, TableCell, TableHead, TablePagination, TableRow} from '@mui/material';
import Card from '@mui/material/Card';
import Checkbox from '@mui/material/Checkbox';
import Collapse from '@mui/material/Collapse';
import Grid from '@mui/material/Grid';
import {Fragment, useEffect, useState} from 'react';
import VisGraph from 'react-vis-graph-wrapper';

import Footer from '../components/Footer';
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDButton from '../components/MDButton';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';

function SkillList() {
  const [skillList, setSkillList] = useState([]);

  const [selected, setSelected] = useState([]);

  const [isToggled, setToggled] = useState(false);
  const [showTable, setShowTable] = useState(false);

  const [tableData, setTableData] = useState([]);

  const [searchSkill, setSearchSkill] = useState('');

  var auxList = [];

  const graphTemp = {nodes: [], edges: []};

  const [graph, setGraph] = useState({nodes: [], edges: []});

  const [aux, setAux] = useState([]);

  const options = {
    layout: {improvedLayout: true},
    nodes: {shape: 'dot', scaling: {min: 10, label: false}},
    edges: {
      color: '#000000',
      smooth: {enabled: true, type: 'discrete', roundness: 0.5}
    },
    groups: {
      mainSkill: {color: {background: 'red'}, borderWidth: 3},
    },
    height: '800px',
    physics: {
      barnesHut: {
        gravitationalConstant: -11500,
        centralGravity: 0.5,
        springLength: 270,
        springConstant: 0.135,
        avoidOverlap: 0.02
      },
      minVelocity: 0.75
    }
  };

  const events = {
    select: function(event) {
      var {nodes, edges} = event;
    }
  };

  const handleCheckboxChange = (event) => {
    const nodeId = event.target.value;
    if (event.target.checked) {
      setSelected((prevSelected) => [...prevSelected, '"' + nodeId + '"']);
    } else {
      setSelected(
          (prevSelected) =>
              prevSelected.filter((item) => item !== '"' + nodeId + '"'));
    }
  };

  const getTreeItemsFromData = (treeItems, searchValue) => {
    const filteredItems = treeItems.filter((treeItemData) => {
      const isMatched =
          treeItemData.name.toLowerCase().includes(searchValue.toLowerCase()) ||
          getTreeItemsFromData(treeItemData.children, searchValue).length > 0;

      return isMatched;
    });

    return filteredItems.map(treeItemData => {
      const isMatched =
          treeItemData.name.toLowerCase().includes(searchValue.toLowerCase());

      const isSelected = selected.includes('"' + treeItemData.nodeId + '"');

      if (isMatched) {
        return (
          <TreeItem
        key = {treeItemData.nodeId} nodeId = {treeItemData.nodeId} label = {
          <>< Checkbox
                  checked={isSelected}
                  onChange={handleCheckboxChange}
                  value={
          treeItemData.nodeId}
                />
                {treeItemData.name}
              </>
            }
          >
            {getTreeItemsFromData(treeItemData.children, searchValue)}
          </TreeItem>
        );
      }
      return getTreeItemsFromData(treeItemData.children, searchValue);
    });
  };


  const collapseAll = (e) => {
    e.preventDefault();
    setSkillList(
      skillList.map((item) =>
        Object.assign({}, item, {
          expanded: false,
        })
      )
    );
  };

  const DataTreeView = () => {
    return (
      <TreeView
        defaultCollapseIcon={<ExpandMoreIcon />
      }
        defaultExpandIcon={<ChevronRightIcon />}
      >
        {getTreeItemsFromData(skillList, searchSkill)}
      </TreeView>
    );
  };

  const NestedTableComponent = ({ data }) => {
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5); 

    const handleChangePage = (event, newPage) => {
      setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
      setRowsPerPage(parseInt(event.target.value, 10));
      setPage(0); 
    };

    const paginatedData = data.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage);

    return (
      <Fragment>
        <Table>
          <TableHead sx={{ display: "table-header-group" }}>
            <TableRow>
              <TableCell>Code</TableCell>
              <TableCell>Primary</TableCell>
              <TableCell>Experience</TableCell>
              <TableCell>Level</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {paginatedData.map((row, index) => (
              <TableRow key={index}>
                <TableCell style={{ width: "5%" }}>{row.Code}</TableCell>
                <TableCell>{row.Primary}</TableCell>
                <TableCell>{row.Experience}</TableCell>
                <TableCell>{row.Level}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>

        <TablePagination
    rowsPerPageOptions = {[5, 10, 25, 50]} component = 'div'
          count={data.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={
      handleChangeRowsPerPage}
        />
      </Fragment>
    );
  };



  const DataPerson = ({row}) => {
    const [open, setOpen] = useState(false);

    return (
      <Fragment>
        <TableRow sx={
      { '& > *': {borderBottom: 'unset'} }} key={row.index}>
          <TableCell>
            <IconButton
    aria-label = 'expand row'
    size = 'small'
                          onClick={() => setOpen(!open)}
                          style={{
      width: '5%' }}
            >
              {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
            </IconButton>
          </TableCell>
          <TableCell scope='row'>{row.Name}</TableCell>
          <TableCell>{row.Surname}</TableCell>
          <TableCell>{row.Email}</TableCell>
          <TableCell>{row.Title}</TableCell>
          <TableCell>{row.EmployeeId}</TableCell>
          <TableCell>{row.FriendlyName}</TableCell>
          <TableCell>{row.BirthDate}</TableCell>
        </TableRow>
        <TableRow>
          <TableCell style={
      { paddingBottom: 0, paddingTop: 0 }} colSpan={8}>
            <Collapse in={open} timeout='auto' unmountOnExit>
              <MDBox sx={{
      margin: 1 }}>
                <NestedTableComponent data={
      row.Knows} />
              </MDBox>
            </Collapse>
          </TableCell>
        </TableRow>
      </Fragment>
    );
  };

  function TableComponent({data}) {
    const [open, setOpen] = useState(false);

    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);

    const handleChangePage = (event, newPage) => {
      setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
      setRowsPerPage(parseInt(event.target.value, 10));
      setPage(0);
    };

    const paginatedData =
        data.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage);

    return (
      <>
        <Table sx={{
      minWidth: 650 }}>
          <TableHead sx={{
      display: 'table-header-group' }}>
            <TableRow>
              <TableCell width='100' component='th' scope='row'>
                <IconButton
    aria-label = 'expand row'
    size = 'small'
                  onClick={() => setOpen(!open)}
                >
                  {open ? <KeyboardDoubleArrowUpIcon /> : <KeyboardDoubleArrowDownIcon />}
                </IconButton>
              </TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Surname</TableCell>
              <TableCell>Email</TableCell>
              <TableCell>Title</TableCell>
              <TableCell>EmployeeId</TableCell>
              <TableCell>FriendlyName</TableCell>
              <TableCell>BirthDate</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {paginatedData.map((row, index) => (
              <DataPerson key={index} row={row} />
            ))
  }
  </TableBody>
        </Table>< TablePagination
  rowsPerPageOptions = {[5, 10, 25, 50]} component = 'div'
          count={data.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </>
    );
}

const handleClick =
    () => {
      setToggled(!isToggled)
      fetch(
          `http://${window.location.hostname}:9080/people/skills?skillList=${
              selected}`,
          {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json',
              'Access-Control-Allow-Origin': '*'
            }
          })
          .then(response => {return response.json()})
          .then(data => {
            const tableData = data.map(
                (element) => ({
                  Name: element.name,
                  Surname: element.surname,
                  Email: element.email,
                  Title: element.title,
                  EmployeeId: element.employeeId,
                  FriendlyName: element.friendlyName,
                  BirthDate: element.birthDate,
                  Knows: element.knows.map((knows) => ({
                                             Code: knows.code,
                                             Primary: knows.primary?.toString(),
                                             Experience: knows.experience,
                                             Level: knows.level
                                           }))
                }));
            setTableData(tableData);
            var i = 1;
            var j = 2;
            data.forEach(element => {
              var temp = {
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
                /*const foundElement = graphTemp.nodes.find(node => node.label
                 * === knows.name);*/
                var temp = {
                  Code: knows.code,
                  Primary: knows.primary,
                  Experience: knows.experience,
                  Level: knows.level
                } /* if(foundElement !== undefined && foundElement !== null){
                     graphTemp.edges.push({from:i, to:foundElement.id, label:
                   "KNOWS", title: JSON.stringify(temp,'',2) }); } else {*/
                           graphTemp.nodes.push(
                               {id: j, label: knows.name, title: knows.name});
                graphTemp.edges.push({
                  from: i,
                  to: j,
                  label: 'KNOWS',
                  title: JSON.stringify(temp, '', 2)
                });
                ///}
                j++
              })
              i = j++;
            })
            setGraph(prev => graphTemp);
          });
    }

useEffect(() => {
  const recursive =
      (dataList) => {
        var list = [];
        dataList.forEach(data => {list.push({
                           nodeId: data.code,
                           name: data.name,
                           children: recursive(data.subSkills)
                         })});
        return list;
      }

                    fetch(`http://${window.location.hostname}:9080/skills`, {
                      method: 'GET',
                      headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                      }
                    })
                        .then(response => {return response.json()})
                        .then(response => {
                          setSkillList(recursive(response));
                        });
}, [])

const handleShowTable = () => {
  setShowTable((prevShowTable) => !prevShowTable);
};


  return (
    <DashboardLayout>
      <DashboardNavbar />
      <MDBox pt={6} pb={3}>
        <Grid container spacing={6}>
          <Grid item xs={12}>
            <Card>
              <MDBox>
                <MDButton onClick={collapseAll}> Collapse all </MDButton>
                <br />
                <input
  type = 'text'
                  value={searchSkill}
                  onChange={(e) => setSearchSkill(e.target.value)}
                  placeholder='Search'
                />
                <DataTreeView />
                {(selected && selected.length > 0) && <MDButton color='black' onClick={handleClick}> Submit </MDButton>}

                {isToggled && <MDButton onClick={handleShowTable}>
                  {showTable ? "Show Graph" : "Show Table"}
                </MDButton>
                }

                {isToggled && (!showTable ? (<VisGraph
                  graph={graph}
                  options={options}
                  events={events}
                  getNetwork={
  network => {
    //  if you want access to vis.js network api you can set the state in a
    //  parent component using this property
  }}
                />) : (
                  <TableComponent data={tableData} />
                )
                )
                }

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

