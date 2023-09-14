import 'react-datepicker/dist/react-datepicker.css';
import '../network.css';
import Clear from '@mui/icons-material/Clear';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import TreeItem from '@mui/lab/TreeItem';
import TreeView from '@mui/lab/TreeView';
import {
  FormControl,
  IconButton,
  InputLabel,
  MenuItem,
  Paper,
  Select,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from '@mui/material';
import Autocomplete from '@mui/material/Autocomplete';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import { format } from 'date-fns';
import React, { useEffect, useState } from 'react';
import DatePicker from 'react-datepicker';
import VisGraph from 'react-vis-graph-wrapper';

import Footer from '../components/Footer';
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDButton from '../components/MDButton';
import MDInput from '../components/MDInput';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';
import SkillSelect from './components/SkillSelect';


function CreatePosition() {
  const [form, setForm] = useState({
    code: '',
    closingDate: '',
    openingDate: '',
    active: '',
    skills: [],
    name: '',
    projectCode: '',
    priority: '',
    mode: '',
    role: '',
    managedBy: '',
    //office: ''
  });

  const [skillList, setSkillList] = useState([]);
  const [projectList, setProjectList] = useState([]);
  const [peopleList, setPeopleList] = useState([]);
  //const [officeList, setOfficeList] = useState([]);

  const [closingDate, setClosingDate] = useState(new Date());
  const [openingDate, setOpeningDate] = useState(new Date());

  const [selectedItem, setSelectedItem] = useState(null);
  const [skill, setSkill] = useState(null);
  const [skillName, setSkillName] = useState(null);
  const [levelReq, setLevelReq] = useState('');
  const [minLevel, setMinLevel] = useState('');
  const [minExp, setMinExp] = useState(0);

  const [searchSkill, setSearchSkill] = useState('');

  const [expandAll, setExpandAll] = useState([]);
  const [expand, setExpand] = useState([]);

  const graphTemp = { nodes: [], edges: [] };

  const [graph, setGraph] = useState(null);

  const options = {
    layout: { improvedLayout: true },
    nodes: { shape: 'dot', scaling: { min: 10, label: false } },
    edges: {
      color: '#000000',
      smooth: { enabled: true, type: 'discrete', roundness: 0.5 }
    },
    groups: {
      managedBy: { color: { background: 'red' }, borderWidth: 3 },
      candidates: { color: { background: 'green' }, borderWidth: 3 },
      project: { color: { background: 'yellow' }, borderWidth: 3 },
      skill: { color: { background: 'pink' }, borderWidth: 3 }
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

  const getTreeItemsFromData =
    (treeItems, searchValue) => {
      const filteredItems = treeItems.filter((treeItemData) => {
        return treeItemData.name.toLowerCase().includes(
          searchValue.toLowerCase()) ||
          getTreeItemsFromData(treeItemData.children, searchValue).length > 0;
      });

      return filteredItems.map(
        (treeItemData) => {
          const isMatched = 
          treeItemData.name.toLowerCase().includes(searchValue.toLowerCase());

          if (isMatched) {
            return (
              <React.Fragment>
                <TreeItem
                  key={treeItemData.nodeId}
                  nodeId={treeItemData.nodeId}
                  label=
                  {
                    < div
                      onClick={(event) => {
                        event.stopPropagation();
                        handleItemClick(event, treeItemData);
                      }}
                    >
                      {treeItemData.name}
                    </div>
                  }
                >
                  {getTreeItemsFromData(treeItemData.children, searchValue)}
                </TreeItem>
              </React.Fragment>
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

  const DataTreeView =
    () => {
      return (
        <React.Fragment>
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
        </React.Fragment>
      );
    };

  const handleRemoveSkill =
    (index) => {
      const updatedSkills = [...form.skills];
      updatedSkills.splice(index, 1);
      setForm((prevForm) => ({
        ...prevForm,
        skills: updatedSkills,
      }));
    };

  const SkillsList =
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
                  {form.skills.map((skill, index) => (
                    <TableRow key={index}>
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

  useEffect(() => {
    const recursive = (dataList) => {
      let list = [];
      dataList.forEach(data => {
        setExpandAll(nodes => [...nodes, data.code]);
        list.push({
          nodeId: data.code,
          name: data.name,
          children: recursive(data.subSkills)
        });
      });
      return list;
    };

    fetch(`http://${window.location.hostname}:9080/api/skills`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      }
    })
      .then(response => response.json())
      .then(response => {
        const skillsData = recursive(response)
        setSkillList(skillsData);
      });

    fetch(`http://${window.location.hostname}:9080/api/project`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      }
    })
      .then(response => response.json())
      .then(response => {
        setProjectList(response);
      });

    fetch(`http://${window.location.hostname}:9080/api/people/shortinfo`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      }
    })
      .then(data => data.json())
      .then(data => {
        setPeopleList(data);
      });

    fetch(`http://${window.location.hostname}:9080/api/project/names`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      }
    })
      .then(response => response.json())
      .then(response => {
        setProjectList(response);
      });

    fetch(`http://${window.location.hostname}:9080/api/people`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      }
    })
      .then(data => data.json())
      .then(data => {
        setPeopleList(data);
      });

    /*fetch(`http://${window.location.hostname}:9080/api/office`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      }
    })
      .then(office => office.json())
      .then(office => {
        setOfficeList(office);
      });*/

  }, []);

  const createPosition = (event) => {
    const requestBody = JSON.stringify(form);
    console.log(form);
    fetch(`http://${window.location.hostname}:9080/api/position`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      },
      body: requestBody,
    })
      .then(response => response.json())
      .then(response => {
        let i = 1
        let temp = {
          Code: response.code,
          Active: response.active,
          Role: response.role,
          EndDate: response.closingDate,
          InitDate: response.openingDate
        };
        graphTemp.nodes.push({
          id: i,
          label: response.code,
          title: JSON.stringify(temp, '', 2)
        });

        i++;
        graphTemp.nodes.push({
          id: i,
          label: response.projectCode,
          title: JSON.stringify(response.projectCode, '', 2),
          group: 'project'
        });
        graphTemp.edges.push({
          from: 1,
          to: i,
          label: 'FOR_PROJECT',
          title: JSON.stringify(response.projectCode, '', 2)
        });

        response.candidates.forEach(element => {
          i++;
          graphTemp.nodes.push({
            id: i,
            label: element.candidateCode,
            title: element.candidateCode,
            group: 'candidates'
          });
          let temp = {
            Code: element.code,
            Status: element.status,
            IntroductionDate: element.introductionDate,
            ResolutionDate: element.resolutionDate,
            CreationDate: element.creationDate
          };
          graphTemp.edges.push({
            from: 1,
            to: i,
            label: 'CANDIDATE',
            title: JSON.stringify(temp, '', 2)
          });
        })

        i++;
        graphTemp.nodes.push({
          id: i,
          label: response.managedBy,
          title: response.managedBy,
          group: 'managedBy'
        });
        graphTemp.edges.push({
          from: i,
          to: 1,
          label: 'MANAGED',
          title: JSON.stringify(temp, '', 2)
        });

        response.skills.forEach(element => {
          i++;
          let temp = {
            Skill: element.skill,
            LevelReq: element.levelReq,
            MinExp: element.minExp,
            MinLevel: element.minLevel
          };
          graphTemp.nodes.push({
            id: i,
            label: element.skill,
            title: JSON.stringify(temp, '', 2),
            group: 'skill'
          });
          graphTemp.edges.push({
            from: 1,
            to: i,
            label: 'NEED',
            title: JSON.stringify(temp, '', 2)
          });
        })
        setGraph(prev => graphTemp);
      })
  };


  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setForm({
      ...form,
      [name]: value,
    });
  };

  const handleItemClick = (event, item) => {
    event.stopPropagation();
    setSelectedItem(item);
    setSkill(item.nodeId);
    setSkillName(item.name);
    setLevelReq('');
    setMinLevel('');
    setMinExp(0);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    createPosition();

    setForm({
      code: '',
      closingDate: '',
      openingDate: '',
      active: '',
      skills: [],
      name: '',
      projectCode: '',
      priority: '',
      mode: '',
      role: '',
      managedBy: '',
      //office: ''
    });
    setSearchSkill('');
  };

  const handleModalSubmit = (event) => {
    event.preventDefault();
    const newItem = { skill: skill, skillName, levelReq, minLevel, minExp };
    const updatedSkills = [...form.skills, newItem];
    setForm((prevForm) => ({ ...prevForm, skills: updatedSkills }));
    setSelectedItem(null);
    setSkill(null);
    setSkillName(null)
    setLevelReq('');
    setMinLevel('');
    setMinExp(0);
  };

  return (
    <React.Fragment>
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
                  coloredShadow=
                  'info' >
                  <MDTypography variant='h6' color='white'>Create Position</MDTypography>
                </MDBox>
                <form id='positionForm' onSubmit={handleSubmit}>
                  <MDBox pt={3}>
                    <Grid container spacing={6}>
                      <Grid item xs={6}>
                        <MDTypography variant='h6' fontWeight='medium'>Position code</MDTypography>
                        <MDInput type="text" value={form.code} onChange={handleInputChange} name="code" />
                        <MDTypography variant='h6' fontWeight='medium'>Name</MDTypography>
                        <MDInput type='text' value={form.name} onChange={handleInputChange} name='name' />
                        <MDTypography variant='h6' fontWeight='medium'>Role</MDTypography>
                        <MDInput type="text" value={form.role} onChange={handleInputChange} name="role" />
                        <MDTypography variant='h6' fontWeight='medium'>Init Date</MDTypography>
                        <DatePicker
                          selected={openingDate}
                          dateFormat="dd-MM-yyyy"
                          onSelect={(date) => setOpeningDate(date)}
                          onChange={(date) => handleInputChange({ target: { name: "openingDate", value: format(date, 'dd-MM-yyyy') } })}
                        />
                        <MDTypography variant='h6' fontWeight='medium'>End Date</MDTypography>
                        <DatePicker
                          selected={closingDate} dateFormat='dd-MM-yyyy'
                          onSelect={(date) => setClosingDate(date)} onChange=
                          {(date) => handleInputChange({ target: { name: 'closingDate', value: format(date, 'dd-MM-yyyy') } })}
                        />
                      </Grid>
                      <Grid item xs={6}>
                      <MDTypography variant='h6' fontWeight='medium'>Mode</MDTypography>
                        <FormControl fullWidth>
                          <InputLabel id="Mode-select">Mode</InputLabel>
                          <Select name='mode' value={form.mode} onChange={handleInputChange} labelId="Mode-select"
                            sx={{
                              width: 250,
                              height: 50,
                            }}>
                            <MenuItem value='REMOTE'>Remote</MenuItem>
                            <MenuItem value="PRESENTIAL">Presential</MenuItem>
                            <MenuItem value='MIX'>Mix</MenuItem>
                            <MenuItem value="UNKNOWN">Unknown</MenuItem>
                          </Select>
                        </FormControl>
                        <MDTypography variant='h6' fontWeight='medium'>Priority</MDTypography>
                        <FormControl fullWidth>
                          <InputLabel id="Priority-select">Priority</InputLabel>
                          <Select name="priority" value={form.priority}
                            onChange={handleInputChange}
                            labelId="Priority-select"
                            sx={{
                              width: 250,
                              height: 50,
                            }}>
                            <MenuItem value='CRITICAL'>CRITICAL</MenuItem>
                            <MenuItem value='HIGH'>HIGH</MenuItem>
                            <MenuItem value='MEDIUM'>MEDIUM</MenuItem>
                            <MenuItem value='LOW'>LOW</MenuItem>
                          </Select>
                        </FormControl>
                        <MDTypography variant='h6' fontWeight='medium'>Active</MDTypography >
                        <FormControl fullWidth>
                          <InputLabel id="Active-select">Active</InputLabel>
                          <Select name="active" value={form.active} onChange={handleInputChange} labelId="Active-select"
                            sx={{
                              width: 250,
                              height: 50,
                            }}>
                            <MenuItem value="true">YES</MenuItem>
                            <MenuItem value='false'>NO</MenuItem>
                          </Select>
                        </FormControl>
                        <Grid item xs={6}>
                          <MDTypography variant='h6' fontWeight='medium'>Project Name</MDTypography>
                          <Autocomplete
                            options={projectList}
                            getOptionLabel={(project) => project.name}
                            value={projectList.find((project) => project.code === form.projectCode) || null}
                            onChange={(event, newValue) => {
                              handleInputChange({ target: { name: "projectCode", value: newValue?.code || '' } });
                            }}
                            renderInput={(params) => (
                              <MDInput
                                {...params}
                                label="Select a project"
                                name="projectCode"
                              />
                            )}
                          />
                        </Grid >
                        <Grid item xs={6}>
                          <MDTypography variant='h6' fontWeight='medium'>Manager</MDTypography>
                          <Autocomplete
                            options={peopleList}
                            getOptionLabel={(people) => people.name + " " + people.surname}
                            value={peopleList.find((people) => people.code === form.managedBy) || null}
                            onChange={(event, newValue) => {
                              handleInputChange({ target: { name: "managedBy", value: newValue?.code || '' } });
                            }}
                            renderInput={(params) => (
                              <MDInput
                                {...params}
                                label="Select a person"
                                name="managedBy"
                              />
                            )}
                          />
                        </Grid >
                      </Grid>
                    </Grid>
                    <Grid container spacing={12}>
                      <Grid item xs={5}>
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
                      <Grid item xs={12}>
                        {form.skills.length > 0 && <SkillsList />}
                        <MDButton variant="gradient" color="dark"
                          onClick={handleSubmit}>Submit</MDButton>
                      </Grid>
                    </Grid>
                  </MDBox>
                </form>
              </Card>
            </Grid>
          </Grid>
        </MDBox>
        {graph &&
          <MDBox pt={6} pb={3}>
            <Grid container spacing={6}>
              <Grid item xs={12}>
                <Card>
                  <MDBox mx={2} mt={-3} py={3} px={2} variant='gradient'
                    bgColor='info'
                    borderRadius='lg'
                    coloredShadow='info'>
                    <MDTypography variant='h6' color='white'>Position Graph</MDTypography>
                  </MDBox>
                  <MDBox pt={3}>
                    < VisGraph
                      graph={graph}
                      options={options}
                      events={events}
                      getNetwork={network => { }}
                    />
                  </MDBox >
                </Card>
              </Grid>
            </Grid>
          </MDBox>
        }
        <Footer />
      </DashboardLayout>
    </React.Fragment >
  );
}

export default CreatePosition;