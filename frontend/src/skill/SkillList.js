import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import KeyboardDoubleArrowDownIcon from '@mui/icons-material/KeyboardDoubleArrowDown';
import KeyboardDoubleArrowUpIcon from '@mui/icons-material/KeyboardDoubleArrowUp';
//import TreeItem from '@mui/lab/TreeItem';
//import TreeView from '@mui/lab/TreeView';
import { Tree } from 'react-arborist'
import { IconButton, Table, TableBody, TableCell, TableHead, TablePagination, TableRow } from '@mui/material';
import Card from '@mui/material/Card';
import Checkbox from '@mui/material/Checkbox';
import Collapse from '@mui/material/Collapse';
import Grid from '@mui/material/Grid';
import React, { StrictMode, useEffect, useState } from 'react';
import VisGraph from 'react-vis-graph-wrapper';
import MDInput from '../components/MDInput';
import MDTypography from '../components/MDTypography';

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

  const [searchTerm, setSearchTerm] = useState("");

  let auxList = [];

  const graphTemp = { nodes: [], edges: [] };

  const [graph, setGraph] = useState({ nodes: [], edges: [] });

  const [aux, setAux] = useState([]);

  const options = {
    layout: { improvedLayout: true },
    nodes: { shape: 'dot', scaling: { min: 10, label: false } },
    edges: {
      color: '#000000',
      smooth: { enabled: true, type: 'discrete', roundness: 0.5 }
    },
    groups: {
      mainSkill: { color: { background: 'red' }, borderWidth: 3 },
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
    select: function (event) {
      let { nodes, edges } = event;
    }
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
      <React.Fragment>
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
            {paginatedData?.map((row, index) => (
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
          rowsPerPageOptions={[5, 10, 25, 50]} component='div'
          count={data.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={
            handleChangeRowsPerPage}
        />
      </React.Fragment>
    );
  };



  const DataPerson = ({ row }) => {
    const [open, setOpen] = useState(false);

    return (
      <React.Fragment>
        <TableRow sx={
          { '& > *': { borderBottom: 'unset' } }} key={row.index}>
          <TableCell>
            <IconButton
              aria-label='expand row'
              size='small'
              onClick={() => setOpen(!open)}
              style={{
                width: '5%'
              }}
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
                margin: 1
              }}>
                <NestedTableComponent data={
                  row.Knows} />
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
      <React.Fragment>
        <Table sx={{
          minWidth: 650
        }}>
          <TableHead sx={{
            display: 'table-header-group'
          }}>
            <TableRow>
              <TableCell width='100' component='th' scope='row'>
                <IconButton
                  aria-label='expand row'
                  size='small'
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
            {paginatedData?.map((row, index) => (
              <DataPerson key={index} row={row} />
            ))
            }
          </TableBody>
        </Table>< TablePagination
          rowsPerPageOptions={[5, 10, 25, 50]} component='div'
          count={data.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </React.Fragment>
    );
  }

  const handleClick =
    () => {
      setToggled(!isToggled)
      fetch(
        `http://${window.location.hostname}:9080/api/people/skills?skillList=${selected}`,
        {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          }
        })
        .then(response => { return response.json() })
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
              /*const foundElement = graphTemp.nodes.find(node => node.label
               * === knows.name);*/
              let temp = {
                Code: knows.code,
                Primary: knows.primary,
                Experience: knows.experience,
                Level: knows.level
              } /* if(foundElement !== undefined && foundElement !== null){
                     graphTemp.edges.push({from:i, to:foundElement.id, label:
                   "KNOWS", title: JSON.stringify(temp,'',2) }); } else {*/
              graphTemp.nodes.push(
                { id: j, label: knows.name, title: knows.name });
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
      setSelected([]);
    }

  useEffect(() => {
    const recursive =
      (dataList) => {
        let list = [];
        dataList.forEach(data => {
          list.push({
            id: data.code,
            name: data.name,
            children: recursive(data.subSkills)
          })
        });
        return list.length === 0 ? null : list;
      }

    fetch(`http://${window.location.hostname}:9080/api/skills`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      }
    })
      .then(response => { return response.json() })
      .then(response => {
        setSkillList(recursive(response));
      });


  }, [])

  const handleShowTable = () => {
    setShowTable((prevShowTable) => !prevShowTable);
  };
  const INDENT_STEP = 15;

  function FolderArrow({ node }) {
    return (
      <span>
        {node.isInternal ? (
          node.isOpen ? (
            <ExpandMoreIcon />
          ) : (
            <ChevronRightIcon />
          )
        ) : null}
      </span>
    );
  }

  function Node({ node, style, dragHandle }) {

    return (
      <div
        ref={dragHandle}
        style={style}
      >
        <span onClick={() => node.isInternal && node.toggle()}>
          <FolderArrow node={node} />
        </span>
        <span onClick={(node) => handleSelect(node.data)}>
          {node.data.name}
        </span>
      </div>
    );
  }

  const handleSelect = (data) => {
    if (data) {
      const nodeId = data.id;
      console.log(nodeId);

      setSelected((prevSelected) => [...prevSelected, nodeId]);
    }
  };

  return (
    <DashboardLayout>
      <DashboardNavbar />
      <MDBox pt={6} pb={3}>
        <Grid container spacing={6}>
          <Grid item xs={12}>
            <Card>
              <MDBox>
                <StrictMode>
                  < MDInput
                    type="text"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.currentTarget.value)} />
                  <Tree
                    className="Tree"
                    initialData={skillList}
                    openByDefault={false}
                    searchTerm={searchTerm}
                    searchMatch={
                      (node, term) => node.data.name.toLowerCase().includes(term.toLowerCase())
                    }
                    indent={INDENT_STEP}
                    width={'300'}
                  >
                    {Node}
                  </Tree>
                  {console.log(selected)}
                </StrictMode>
                {(selected && selected.length > 0) &&
                  <Grid item xs={6}>
                    {selected}
                  </Grid>
                }
                {(selected && selected.length > 0) && <MDButton color='black' onClick={handleClick}> Submit </MDButton>}
              </MDBox>
            </Card>
            <Grid>
              <Card>
                <MDBox>
                  {isToggled && <MDButton onClick={handleShowTable}>
                    {showTable ? "Show Graph" : "Show Table"}
                  </MDButton>
                  }

                  {isToggled && (!showTable ? (
                    <>
                      <MDBox mx={2} mt={-3} py={3} px={2} variant='gradient'
                        bgColor='info'
                        borderRadius='lg'
                        coloredShadow=
                        'info' >
                        <MDTypography variant='h6' color='white'>Person Graph</MDTypography>
                        <VisGraph
                          graph={graph}
                          options={options}
                          events={events}
                          getNetwork={
                            network => {
                              //  if you want access to vis.js network api you can set the state in a
                              //  parent component using this property
                            }}
                        />
                      </MDBox>
                    </>
                  ) : (<>
                    <MDBox mx={2} mt={-3} py={3} px={2} variant='gradient'
                      bgColor='info'
                      borderRadius='lg'
                      coloredShadow=
                      'info' >
                      <MDTypography variant='h6' color='white'>Person Table</MDTypography>
                      <TableComponent data={tableData} />
                    </MDBox>
                  </>
                  ))}
                </MDBox>
              </Card>
            </Grid>
          </Grid>
        </Grid>
      </MDBox>
      <Footer />
    </DashboardLayout>
  );
}

export default SkillList;

