import { useEffect, useState, Fragment } from "react";
import TreeView from '@mui/lab/TreeView';
import TreeItem from "@mui/lab/TreeItem";
import Checkbox from '@mui/material/Checkbox';
import Collapse from '@mui/material/Collapse';
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import VisGraph from 'react-vis-graph-wrapper';
import MDBox from "../components/MDBox";
import MDButton from "../components/MDButton";
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';
import Footer from "../components/Footer";
import { Table, TableHead, TableBody, TableRow, TableCell, IconButton } from '@mui/material';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardDoubleArrowDownIcon from '@mui/icons-material/KeyboardDoubleArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import KeyboardDoubleArrowUpIcon from '@mui/icons-material/KeyboardDoubleArrowUp';

function SkillList() {

  const [skillList, setSkillList] = useState([]);

  const [selected, setSelected] = useState([]);

  const [isToggled, setToggled] = useState(false);
  const [showTable, setShowTable] = useState(false);

  const [tableData, setTableData] = useState([]);


  var auxList = [];

  const graphTemp = {
    nodes:[],
    edges:[]
  };

  const [graph, setGraph] = useState({
    nodes:[],
    edges:[]
  });

  const [aux, setAux] = useState([]);

  const options = {
    layout: {
        improvedLayout: true
    },
    nodes:{
      shape: "dot",
      scaling: {min:10,label:false}
    },
    edges: {
      color: "#000000",
      smooth: {
        enabled: true,
        type: "discrete",
        roundness: 0.5
      }
    },
    groups: {
      mainSkill: {color:{background:'red'}, borderWidth:3},
    },
    height: "800px",
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
      var { nodes, edges } = event;
    }
  };
  
  const getTreeItemsFromData = treeItems => {
    return treeItems.map(treeItemData => {
      let children = undefined;
      if (treeItemData.children && treeItemData.children.length > 0) {
        children = getTreeItemsFromData(treeItemData.children);
    }

    const handleChange = (event, nodeId) => {
      event.stopPropagation();
      const foundElement = treeItems.find(element => element.nodeId === nodeId)
      if(foundElement.active){
        foundElement.active = false
        const items = selected.filter(item => item !== '"'+foundElement.nodeId+'"')
        setSelected(items)
      }else{
        foundElement.active = true
        auxList.push('"'+foundElement.nodeId+'"')
        setSelected(selected.concat(auxList))
      }
    };

    return (
        <TreeItem
          key={treeItemData.nodeId}
          nodeId={treeItemData.nodeId}
          label={
            <>
            <Checkbox
              checked = {treeItemData.active}
              onChange= {(event)=>handleChange(event,treeItemData.nodeId)}
              onClick={e => (e.stopPropagation())}
            />
            {treeItemData.name}
            </>
          }
          children={children}
        />
      );
    });
  };

  function TableComponent({ data }) {
    const [open, setOpen] = useState(false);
    return (
      <Table>
        <TableHead>
          <TableRow>
            <TableCell width="100" component="th" scope="row">
              <IconButton
                aria-label="expand row"
                size="small"
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
            <TableCell>Knows</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {data.map((row, index) => (
            <Fragment>
              <TableRow sx={{ '& > *': { borderBottom: 'unset' } }} key={index}>
                <TableCell width="100" component="th" scope="row">
                  <IconButton
                    aria-label="expand row"
                    size="small"
                    onClick={() => setOpen(!open)}
                  >
                    {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                  </IconButton>
                </TableCell>
                <TableCell>{row.Name}</TableCell>
                <TableCell>{row.Surname}</TableCell>
                <TableCell>{row.Email}</TableCell>
                <TableCell>{row.Title}</TableCell>
                <TableCell>{row.EmployeeId}</TableCell>
                <TableCell>{row.FriendlyName}</TableCell>
                <TableCell>{row.BirthDate}</TableCell>
              </TableRow>
              <TableRow>
                <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={8}>
                  <Collapse in={open} timeout="auto" unmountOnExit>
                    <MDBox sx={{ margin: 1 }}>
                      <Table>
                        <TableHead>
                          <TableRow>
                            <TableCell>Code</TableCell>
                            <TableCell>Primary</TableCell>
                            <TableCell>Experience</TableCell>
                            <TableCell>Level</TableCell>
                          </TableRow>
                        </TableHead>
                        <TableBody>
                          {row.Knows.map((nestedRow, nestedIndex) => (
                            <TableRow key={nestedIndex}>
                              <TableCell width="100">{nestedRow.Code}</TableCell>
                              <TableCell>{nestedRow.Primary}</TableCell>
                              <TableCell>{nestedRow.Experience}</TableCell>
                              <TableCell>{nestedRow.Level}</TableCell>
                            </TableRow>
                          ))}
                        </TableBody>
                      </Table>
                    </MDBox>
                  </Collapse>
                </TableCell>
              </TableRow>
            </Fragment>
          ))}
        </TableBody>
      </Table>
    );
  }

  const handleClick = () => {
    setToggled(!isToggled)
    fetch(`http://localhost:9080/people/skills?skillList=${selected}`, {method: "GET", headers: {
              "Content-Type": "application/json",
              "Access-Control-Allow-Origin": "*"
            }})
            .then(response => {return response.json()})
            .then(data => {
              const tableData = data.map((element) => ({
                Name: element.name,
                Surname: element.surname,
                Email: element.email,
                Title: element.title,
                EmployeeId: element.employeeId,
                FriendlyName: element.friendlyName,
                BirthDate: element.birthDate,
                Knows: element.knows.map((knows) => ({
                  Code: knows.code,
                  Primary: knows.code,
                  Experience: knows.experience,
                  Level: knows.level
                }))
              }));
              setTableData(tableData);
              var i = 1;
              var j = 2;
              data.forEach(element => {
                var temp ={Code: element.code, Name:element.name, Surname:element.surname, Email:element.email, EmployeeId:element.employeeId,
                  FriendlyName:element.friendlyName, Title:element.title, BirthDate: element.birthDate}
                graphTemp.nodes.push({id:i, label: element.name + ' ' + element.surname, title: JSON.stringify(temp,'',2)});
                element.knows.forEach(knows => {
                  /*const foundElement = graphTemp.nodes.find(node => node.label === knows.name);*/
                  var temp = {Code:knows.code, Primary:knows.primary, Experience: knows.experience, Level: knows.level}
                 /* if(foundElement !== undefined && foundElement !== null){
                    graphTemp.edges.push({from:i, to:foundElement.id, label: "KNOWS", title: JSON.stringify(temp,'',2) });
                  } else {*/
                    graphTemp.nodes.push({id:j, label: knows.name, title: knows.name  });
                    graphTemp.edges.push({from:i, to:j, label: "KNOWS", title: JSON.stringify(temp,'',2) });
                  ///}
                  j++
                })
                i = j++;
              })
              setGraph(prev => graphTemp);
            }); 
  }

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

  const DataTreeView = ({ treeItems }) => {
    return (
      <TreeView
        defaultCollapseIcon={<ExpandMoreIcon />}
        defaultExpandIcon={<ChevronRightIcon />}
      >
      {getTreeItemsFromData(treeItems)}
      </TreeView>
    );
  };
       
  useEffect(() => {
    const recursive = (dataList) => {
      var list = [];
      dataList.forEach(data => {
        list.push({nodeId:data.code, name:data.name, children:recursive(data.subSkills)})
      });
      return list;
    }

    fetch(`http://localhost:9080/skills`, {method: "GET", headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*"
        }})
        .then(response => {return response.json()})
        .then(response => {
          setSkillList(recursive(response));
        });
  },[])

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
                  <DataTreeView  treeItems={skillList} />
                  {(selected && selected.length > 0) && <MDButton onClick={handleClick}> Send </MDButton>}

                  {isToggled && <MDButton onClick={handleShowTable}>
                      {showTable ? "Show Graph" : "Show Table"}
                    </MDButton> 
                  }          

                  {isToggled && (!showTable ? (<VisGraph
                        graph={graph}
                        options={options}
                        events={events}
                        getNetwork={network => {
                          //  if you want access to vis.js network api you can set the state in a parent component using this property
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

