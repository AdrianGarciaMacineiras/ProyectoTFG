import 'react-datepicker/dist/react-datepicker.css';
import '../network.css';

import { useState, useEffect } from 'react';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import TreeItem from '@mui/lab/TreeItem';
import TreeView from '@mui/lab/TreeView';
import DatePicker from "react-datepicker";
import { format } from 'date-fns';
import VisGraph from 'react-vis-graph-wrapper';
import MDBox from "../components/MDBox";
import MDTypography from "../components/MDTypography";
import Footer from "../components/Footer";
import MDButton from "../components/MDButton";
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import MDInput from '../components/MDInput';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';
import { InputLabel, Select, MenuItem, FormControl } from "@mui/material";
import Autocomplete from '@mui/material/Autocomplete';

function CreatePosition() {
  const [form, setForm] = useState({
    code: '',
    closingDate: '',
    openingDate: '',
    open: '',
    skills: [],
    name: '',
    projectCode: '',
    priority: '',
    mode: '',
    role: '',
    managedBy: '',
    office: ''
  });

  const [skillList, setSkillList] = useState([]);
  const [projectList, setProjectList] = useState([]);
  const [peopleList, setPeopleList] = useState([]);
  const [officeList, setOfficeList] = useState([]);


  const [closingDate, setClosingDate] = useState(new Date());
  const [openingDate, setOpeningDate] = useState(new Date());

  const [selectedItem, setSelectedItem] = useState(null);
  const [skill, setSkill] = useState(null);
  const [skillName, setSkillName] = useState(null);
  const [levelReq, setLevelReq] = useState('');
  const [minLevel, setMinLevel] = useState('');
  const [minExp, setMinExp] = useState(0);

  const [searchProjectCode, setSearchProjectCode] = useState('');
  const [searchProjectName, setSearchProjectName] = useState('');
  const [searchPersonName, setSearchPersonName] = useState('');
  const [searchOfficeName, setSearchOfficeName] = useState('');
  const [searchSkill, setSearchSkill] = useState('');

  const graphTemp = {
    nodes: [],
    edges: []
  };

  const [graph, setGraph] = useState({
    nodes: [],
    edges: []
  });

  const [aux, setAux] = useState([]);

  const options = {
    layout: {
      improvedLayout: true
    },
    nodes: {
      shape: "dot",
      scaling: { min: 10, label: false }
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
      managedBy: { color: { background: 'red' }, borderWidth: 3 },
      candidates: { color: { background: 'green' }, borderWidth: 3 },
      project: { color: { background: 'yellow' }, borderWidth: 3 },
      skill: { color: { background: 'pink' }, borderWidth: 3 }
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
    select: function (event) {
      let { nodes, edges } = event;
    }
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
            nodeId={treeItemData.nodeId}
            label={
              <div
                onClick={(event) => {
                  event.stopPropagation();
                  handleItemClick(treeItemData);
                }}
              >
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
      <MDBox>
        <TreeView
          defaultCollapseIcon={<ExpandMoreIcon />}
          defaultExpandIcon={<ChevronRightIcon />}
        >
          {getTreeItemsFromData(skillList, searchSkill, false)}
        </TreeView>
      </MDBox>
    );
  };

  const handleRemoveSkill = (index) => {
    const updatedSkills = [...form.skills];
    updatedSkills.splice(index, 1);
    setForm((prevForm) => ({
      ...prevForm,
      skills: updatedSkills,
    }));
  };

  const SkillsList = () => {
    return (
      <MDBox>
        {form.skills.map((skill, index) => (
          <MDBox key={index}>
            <hr />
            <label>Skill: {skill.skillName}</label>
            <br />
            <label>Level Required: {skill.levelReq}</label>
            <br />
            <label>Minimum Level: {skill.minLevel}</label>
            <br />
            <label>Minimum Experience: {skill.minExp}</label>
            <br />
            <button onClick={() => handleRemoveSkill(index)}>Remove</button>
            <hr />
          </MDBox>
        ))}
      </MDBox>
    );
  };

  useEffect(() => {
    const recursive = (dataList) => {
      let list = [];
      dataList.forEach(data => {
        list.push({ nodeId: data.code, name: data.name, children: recursive(data.subSkills) });
      });
      return list;
    };

        fetch(`http://${window.location.hostname}:9080/skills`, {
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

        fetch(`http://${window.location.hostname}:9080/project`, {
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

        fetch(`http://${window.location.hostname}:9080/people`, {
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

        fetch(`http://${window.location.hostname}:9080/office`, {
     method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*"
      }
    })
      .then(response => response.json())
      .then(response => {
        const skillsData = recursive(response)
        setSkillList(skillsData);
      });

    fetch(`http://localhost:9080/project`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*"
      }
    })
      .then(response => response.json())
      .then(response => {
        setProjectList(response);
      });

    fetch(`http://localhost:9080/people`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*"
      }
    })
      .then(data => data.json())
      .then(data => {
        setPeopleList(data);
      });

    fetch(`http://localhost:9080/office`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*"
      }
    })
      .then(office => office.json())
      .then(office => {
        setOfficeList(office);
      });
  }, []);

  const createPosition = (event) => {

    const requestBody = JSON.stringify(form);

    fetch("http://localhost:9080/position", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*"
      },
      body: requestBody,
    })
      .then(response => response.json())
      .then(response => {
        setAux(response);
        let i = 1
        let temp = { Code: response.code, Active: response.open, Role: response.role, EndDate: response.closingDate, InitDate: response.openingDate }
        graphTemp.nodes.push({ id: i, label: response.code, title: JSON.stringify(temp, '', 2) });

        i++
        graphTemp.nodes.push({ id: i, label: response.projectCode, title: JSON.stringify(response.projectCode, '', 2), group: "project" });
        graphTemp.edges.push({ from: i, to: 1, label: "FOR_PROJECT", title: JSON.stringify(response.projectCode, '', 2) });

        response.candidates.forEach(element => {
          i++;
          graphTemp.nodes.push({ id: i, label: element.candidateCode, title: element.candidateCode, group: "candidates" });
          let temp = { Code: element.code, Status: element.status, IntroductionDate: element.introductionDate, ResolutionDate: element.resolutionDate, CreationDate: element.creationDate }
          graphTemp.edges.push({ from: 1, to: i, label: "CANDIDATE", title: JSON.stringify(temp, '', 2) });
        })

        i++;
        graphTemp.nodes.push({ id: i, label: response.managedBy, title: response.managedBy, group: "managedBy" });
        graphTemp.edges.push({ from: i, to: 1, label: "MANAGED", title: JSON.stringify(temp, '', 2) });

        response.skills.forEach(element => {
          i++;
          let temp = { Skill: element.skill, LevelReq: element.levelReq, MinExp: element.minExp, MinLevel: element.minLevel }
          graphTemp.nodes.push({ id: i, label: element.skill, title: JSON.stringify(temp, '', 2), group: "skill" });
          graphTemp.edges.push({ from: 1, to: i, label: "NEED", title: JSON.stringify(temp, '', 2) });
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

  const handleItemClick = (item) => {
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

    setGraph({
      nodes: [],
      edges: []
    });

    setForm({
      code: '',
      closingDate: '',
      openingDate: '',
      open: '',
      skills: [],
      name: '',
      projectCode: '',
      priority: '',
      mode: '',
      role: '',
      managedBy: '',
      office: ''
    });
  };

  const handleModalSubmit = () => {
    const newItem = {
      skill: skill,
      skillName,
      levelReq,
      minLevel,
      minExp
    };
    const updatedSkills = [...form.skills, newItem];
    setForm((prevForm) => ({
      ...prevForm,
      skills: updatedSkills
    }));
    setSelectedItem(null);
    setSkill(null);
    setSkillName(null)
    setLevelReq('');
    setMinLevel('');
    setMinExp(0);
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
                coloredShadow=
                'info' > <MDTypography variant='h6' color='white'>Create Position</MDTypography>
              </MDBox>
              <form id="positionForm" onSubmit={handleSubmit}>
                <MDBox pt={3}>
                  <Grid container spacing={6}>
                    <Grid item xs={6}>
                      <MDTypography variant='h6' fontWeight='medium'>Position code</MDTypography>
                      <MDInput type="text" value={form.positionCode} onChange={handleInputChange} name="positionCode" />

                      <Grid item xs={6}>
                        <MDTypography variant="h6" fontWeight="medium">
                          Project Name
                        </MDTypography>
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
                      </Grid>

                      <Grid item xs={6}>
                        <MDTypography variant="h6" fontWeight="medium">
                          Manager
                        </MDTypography>
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
                      </Grid>

                      <MDTypography variant='h6' fontWeight='medium'>Role</MDTypography>
                      <MDInput type="text" value={form.role} onChange={handleInputChange} name="role" />
                      <MDTypography variant='h6' fontWeight='medium'>Init Date</MDTypography>
                      <DatePicker
                        selected={openingDate}
                        dateFormat="dd-MM-yyyy"
                        onSelect={(date) => setOpeningDate(date)}
                        onChange={(date) => handleInputChange({ target: { name: "openingDate", value: format(date, 'dd-MM-yyyy') } })}
                      />
                      <MDTypography variant='h6' fontWeight='medium'>Mode</MDTypography>
                      <FormControl fullWidth>
                        <InputLabel>Select an option</InputLabel>
                        <Select name="mode" value={form.mode} onChange={handleInputChange}>
                          <MenuItem value="REMOTE">Remote</MenuItem>
                          <MenuItem value="PRESENTIAL">Presential</MenuItem>
                          <MenuItem value="MIX">Mix</MenuItem>
                          <MenuItem value="UNKNOWN">Unknown</MenuItem>
                        </Select>
                      </FormControl>
                    </Grid>
                    <Grid item xs={6}>
                      <MDTypography variant='h6' fontWeight='medium'>Name</MDTypography>
                      <MDInput type="text" value={form.name} onChange={handleInputChange} name="name" />
                      <MDTypography variant='h6' fontWeight='medium'>Priority</MDTypography>
                      <MDInput type="text" value={form.priority} onChange={handleInputChange} name="priority" />
                      <MDTypography variant='h6' fontWeight='medium'>Charge</MDTypography>
                      <MDTypography variant='h6' fontWeight='medium'>End Date</MDTypography>
                      <DatePicker
                        selected={closingDate}
                        dateFormat="dd-MM-yyyy"
                        onSelect={(date) => setClosingDate(date)}
                        onChange={(date) => handleInputChange({ target: { name: "closingDate", value: format(date, 'dd-MM-yyyy') } })}
                      />
                      <MDTypography variant='h6' fontWeight='medium'>Active</MDTypography>
                      <FormControl fullWidth>
                        <InputLabel>Select an option</InputLabel>
                        <Select name="active" value={form.active} onChange={handleInputChange}>
                          <MenuItem value="true">YES</MenuItem>
                          <MenuItem value="false">NO</MenuItem>
                        </Select>
                      </FormControl>
                      {selectedItem && (
                        <MDBox>
                          <h2>Selected Item: {selectedItem.name}</h2>
                          <FormControl fullWith>
                            <MDTypography variant='h6' fontWeight='medium'>Level Required</MDTypography>
                            <InputLabel>Select an Option</InputLabel>
                            <Select value={levelReq} onChange={(e) => setLevelReq(e.target.value)}>
                              <MenuItem value="MANDATORY">MANDATORY</MenuItem>
                              <MenuItem value="NICE_TO_HAVE">NICE TO HAVE</MenuItem>
                            </Select>
                          </FormControl>
                          <FormControl fullWidth>
                            <MDTypography variant='h6' fontWeight='medium'>Level Required</MDTypography>
                            <InputLabel>
                              Select an option
                            </InputLabel>
                            <Select value={minLevel} onChange={(e) => setMinLevel(e.target.value)}>
                              <MenuItem value="HIGH">HIGH</MenuItem>
                              <MenuItem value="MEDIUM">MEDIUM</MenuItem>
                              <MenuItem value="LOW">LOW</MenuItem>
                            </Select>
                          </FormControl>
                          <FormControl fullWidth>
                            <MDTypography variant='h6' fontWeight='medium'>Minimum Experience</MDTypography>
                            <MDInput type="number" value={minExp} onChange={(e) => setMinExp(parseInt(e.target.value))} />
                          </FormControl>
                          <MDButton onClick={handleModalSubmit}>Save</MDButton>
                          <MDButton onClick={() => setSelectedItem(null)}>Cancel</MDButton>
                        </MDBox>
                      )}

                      <MDButton onClick={collapseAll}> Collapse all </MDButton>
                      <br />
                      <input
                        type="text"
                        value={searchSkill}
                        onChange={(e) => setSearchSkill(e.target.value)}
                        placeholder="Search"
                      />
                      <DataTreeView />
                      <SkillsList />
                    </Grid>
                    <Grid item xs={12}>
                      <MDButton variant="gradient" color="dark" onClick={handleSubmit}>Submit</MDButton>
                    </Grid>
                  </Grid>
                </MDBox>
              </form>
            </Card>
          </Grid>
        </Grid>
      </MDBox>
      <MDBox pt={6} pb={3}>

        <Grid container spacing={6}>
          <Grid item xs={12}>
            <Card>
              <MDBox mx={2} mt={-3} py={3} px={2} variant='gradient'
                bgColor='info'
                borderRadius='lg'
                coloredShadow='info' >
                <MDTypography variant='h6' color='white'>Position Graph</MDTypography>
              </MDBox>
              <MDBox pt={3}>
                <VisGraph
                  graph={graph}
                  options={options}
                  events={events}
                  getNetwork={network => {
                    //  if you want access to vis.js network api you can set the state in a parent component using this property
                  }}
                />
              </MDBox>

            </Card>
          </Grid>
        </Grid>
      </MDBox>
      <Footer />
    </DashboardLayout>
  );
}

export default CreatePosition;
