import 'react-datepicker/dist/react-datepicker.css';
import '../network.css';

import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import TreeItem from '@mui/lab/TreeItem';
import TreeView from '@mui/lab/TreeView';
import { Checkbox, FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import Autocomplete from '@mui/material/Autocomplete';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import { format } from 'date-fns';
import { useEffect, useState } from 'react';
import DatePicker from 'react-datepicker';
import VisGraph from 'react-vis-graph-wrapper';

import Footer from '../components/Footer';
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDButton from '../components/MDButton';
import MDInput from '../components/MDInput';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';

const CreateProject = () => {
  const [skillList, setSkillList] = useState([]);
  const [clientList, setClientList] = useState([]);

  const [endDate, setEndDate] = useState(new Date());
  const [initDate, setInitDate] = useState(new Date());

  const [searchSkill, setSearchSkill] = useState('');

  const [selectedSkills, setSelectedSkills] = useState([]);

  const [expandAll, setExpandAll] = useState([]);
  const [expand, setExpand] = useState([]);

  const [form, setForm] = useState({
    code: '',
    clientCode: '',
    tag: '',
    name: '',
    desc: '',
    initDate: '',
    endDate: '',
    area: '',
    domain: '',
    duration: '',
    guards: 'NONE',
    skills: [],
  });

  const graphTemp = {
    nodes: [],
    edges: []
  };

  const [graph, setGraph] = useState({
    nodes: [],
    edges: []
  });

  const options = {
    layout: {
      improvedLayout: true
    },
    nodes: {
      shape: 'dot',
      scaling: { min: 10, label: false }
    },
    edges: {
      color: '#000000',
      smooth: {
        enabled: true,
        type: 'discrete',
        roundness: 0.5
      }
    },
    groups: {
      client: { color: { background: 'red' }, borderWidth: 3 },
      skills: { color: { background: 'green' }, borderWidth: 3 }
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

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setForm({ ...form, [name]: value });
  };

  useEffect(() => {
    const recursive = (dataList) => {
      let list = [];
      if (!!dataList) {
        dataList.forEach(data => {
          setExpandAll(nodes => [...nodes, data.code]);
          list.push({ nodeId: data.code, name: data.name, children: recursive(data.subSkills) });
        });
      }
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

    fetch(`http://${window.location.hostname}:9080/api/client`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      }
    })
      .then(clients => clients.json())
      .then(clients => {
        setClientList(clients);
      });

  }, []);

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
        {form.skills.map((nodeId, index) => (
          <MDBox key={index}>
            <hr />
            <label>Skill: {getNodeNameFromId(skillList, nodeId)}</label>
            <MDButton onClick={() => handleRemoveSkill(index)}>Remove</MDButton>
            <hr />
          </MDBox>
        ))}
      </MDBox>
    );
  };

  const getNodeNameFromId = (nodes, nodeId) => {
    for (const node of nodes) {
      if (node.nodeId === nodeId) {
        return node.name;
      } else if (node.children && node.children.length > 0) {
        const nodeName = getNodeNameFromId(node.children, nodeId);
        if (nodeName) {
          return nodeName;
        }
      }
    }
    return null;
  };

  const handleAddSelectedSkills = () => {
    const selectedSkillsData = selectedSkills.map((nodeId) => ({
      nodeId: nodeId,
      skillName: getNodeNameFromId(skillList, nodeId),
    }));

    setForm((prevForm) => ({
      ...prevForm,
      skills: [...prevForm.skills, ...selectedSkillsData.map((data) => data.nodeId)],
    }));

    setSelectedSkills([]);
  };

  const handleCheckboxChange = (event) => {
    event.stopPropagation();
    const nodeId = event.target.value;
    if (event.target.checked) {
      setSelectedSkills((prevSelectedSkills) => [...prevSelectedSkills, nodeId]);
    } else {
      setSelectedSkills((prevSelectedSkills) => prevSelectedSkills.filter((item) => item !== nodeId));
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

      const isSelected = selectedSkills.includes(treeItemData.nodeId);

      if (isMatched) {
        return (
          <TreeItem
            key={treeItemData.nodeId}
            nodeId={treeItemData.nodeId}
            label={
              <div>
                <Checkbox
                  value={treeItemData.nodeId}
                  checked={isSelected}
                  onChange={handleCheckboxChange}
                  onClick={(event) => event.stopPropagation()}
                />
                <span
                  onClick={(event) => event.stopPropagation()}
                >
                  {treeItemData.name}
                </span>
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

  const createProject = (event) => {

    const requestBody = JSON.stringify(form);

    fetch(`http://${window.location.hostname}:9080/api/project`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      },
      body: requestBody,
    })
      .then(response => { return response.json() })
      .then(response => {
        let i = 1;
        let temp = {
          Code: response.code, Name: response.name, InitDate: response.initDate, Descripcion: response.desc, Area: response.area,
          Guards: response.guards, Duration: response.duration, Domain: response.domain, Tag: response.tag
        }
        graphTemp.nodes.push({ id: i, label: response.name, title: JSON.stringify(temp, '', 2) });

        i++;
        graphTemp.nodes.push({ id: i, label: response.clientCode, title: JSON.stringify(response.clientCode, '', 2), groups: 'client' });
        graphTemp.edges.push({ from: 1, to: i, label: 'FOR_CLIENT', title: response.clientCode });

        if (!!response.skills) {
          response.skills.forEach(element => {
            i++;
            graphTemp.nodes.push({ id: i, label: element, title: element, groups: 'skills' });
            graphTemp.edges.push({ from: 1, to: i, label: 'REQUIRE', title: response.clientCode });
          });
        }
        setGraph(prev => graphTemp);
      })
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    createProject();

    setGraph({
      nodes: [],
      edges: []
    });

    setForm({
      code: '',
      clientCode: '',
      tag: '',
      name: '',
      desc: '',
      initDate: '',
      endDate: '',
      area: '',
      domain: '',
      duration: '',
      guards: 'NONE',
      skills: [],
    });
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
                'info' > <MDTypography variant='h6' color='white'>Create Project</MDTypography>
              </MDBox>
              <form id='projectForm' onSubmit={handleSubmit}>
                <MDBox pt={3}>
                  <Grid container spacing={6}>
                    <Grid item xs={6}>
                      <MDTypography variant='h6' fontWeight='medium'>Project code</MDTypography>
                      <MDInput type="text" value={form.code} onChange={handleInputChange} name="code" />
                      <Grid item xs={6}>
                        <MDTypography variant='h6' fontWeight='medium'>Client Name</MDTypography>
                        <Autocomplete
                          options={clientList}
                          getOptionLabel={(client) => client.name}
                          value={clientList.find((client) => client.code === form.clientCode) || null}
                          onChange={(event, newValue) => {
                            handleInputChange({ target: { name: "clientCode", value: newValue?.code || '' } });
                          }}
                          renderInput={(params) => (
                            <MDInput {...params} label="Select a client" name="clientCode" />
                          )}
                        />
                      </Grid>
                      <MDTypography variant='h6' fontWeight='medium'>Project name</MDTypography>
                      <MDInput type="text" value={form.name} onChange={handleInputChange} name="name" />
                      <MDTypography variant='h6' fontWeight='medium'>Tag</MDTypography>
                      <MDInput type="text" value={form.tag} onChange={handleInputChange} name="tag" />
                      <MDTypography variant='h6' fontWeight='medium'>Description</MDTypography>
                      <MDInput type="text" value={form.desc} onChange={handleInputChange} name="desc" />
                      <MDTypography variant='h6' fontWeight='medium'>Init Date</MDTypography>
                      <DatePicker
                        selected={initDate}
                        dateFormat="dd-MM-yyyy"
                        onSelect={(date) => setInitDate(date)}
                        onChange={(date) => handleInputChange({ target: { name: "initDate", value: format(date, 'dd-MM-yyyy') } })}
                      />
                      <MDTypography variant='h6' fontWeight='medium'>End Date</MDTypography>
                      <DatePicker
                        selected={endDate}
                        dateFormat="dd-MM-yyyy"
                        onSelect={(date) => setEndDate(date)}
                        onChange={(date) => handleInputChange({ target: { name: "endDate", value: format(date, 'dd-MM-yyyy') } })}
                      />
                      <MDTypography variant='h6' fontWeight='medium'>Area</MDTypography>
                      <MDInput type="text" value={form.area} onChange={handleInputChange} name="area" />
                    </Grid>
                    <Grid item xs={6}>
                      <MDTypography variant='h6' fontWeight='medium'>Domain</MDTypography>
                      <MDInput type='text' value={form.domain} onChange={handleInputChange} name='domain' />
                      <MDTypography variant='h6' fontWeight='medium'>Duration</MDTypography>
                      <MDInput type="text" value={form.duration} onChange={handleInputChange} name="duration" />
                      <MDTypography variant='h6' fontWeight='medium'>Guard</MDTypography>
                      <FormControl fullWidth>
                        <InputLabel>Select an option</InputLabel>
                        <Select name='guards' value={form.guards} onChange={handleInputChange}>
                          <MenuItem value='PASSIVE'>Passive</MenuItem>
                          <MenuItem value="ACTIVE">Active</MenuItem>
                          <MenuItem value='NONE'>None</MenuItem>
                          <MenuItem value="UNKNOWN">Unknown</MenuItem>
                        </Select>
                      </FormControl>
                      <MDButton variant="gradient" color="dark" onClick={handleExpandClick}> {expand.length === 0 ? 'Expand all' : 'Collapse all'} </MDButton>
                      <br />
                      <MDInput
                        type='text'
                        value={searchSkill}
                        onChange={(e) => setSearchSkill(e.target.value)}
                        placeholder='Search'
                      />
                      <DataTreeView />
                      {selectedSkills &&
                        <MDButton onClick={handleAddSelectedSkills}>Add Selected Skills</MDButton>
                      }
                      {form.skills.length > 0 && <SkillsList />}
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
        {graph &&
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
                    getNetwork={network => { }}
                  />
                </MDBox>
              </Card>
            </Grid>
          </Grid>
        }
      </MDBox>
      <Footer />
    </DashboardLayout>
  );
};

export default CreateProject;
