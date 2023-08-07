import 'react-datepicker/dist/react-datepicker.css';
import '../network.css';

import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import TreeItem from '@mui/lab/TreeItem';
import TreeView from '@mui/lab/TreeView';
import {FormControl, FormControlLabel, FormGroup, InputLabel, MenuItem, Select} from '@mui/material';
import Card from '@mui/material/Card';
import Checkbox from '@mui/material/Checkbox';
import Grid from '@mui/material/Grid';
import {format} from 'date-fns';
import React, {useEffect, useState} from 'react';
import DatePicker from 'react-datepicker';
import {useParams} from 'react-router-dom';
import VisGraph from 'react-vis-graph-wrapper';

import Footer from '../components/Footer';
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDButton from '../components/MDButton';
import MDInput from '../components/MDInput';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';

const UpdatePersonForm = () => {
  const [form, setForm] = useState({
    code: '',
    employeeId: '',
    name: '',
    surname: '',
    birthDate: '',
    title: '',
    roles: [],
    knows: [],
    work_with: [],
    master: [],
    interest: [],
    certificates: [],
  });

  const [updatedPerson, setUpdatedPerson] = useState(null);


  const {employeeId} = useParams();

  const [skillList, setSkillList] = useState([]);
  const [searchSkill, setSearchSkill] = useState('');

  const [birthDate, setBirthDate] = useState(new Date());

  const [isAddRoleVisible, setIsAddRoleVisible] = useState(false);
  const [isShowRoleListVisible, setIsShowRoleListVisible] = useState(false);

  const [selectedNode, setSelectedNode] = useState(null);
  const [showCheckboxes, setShowCheckboxes] = useState(false);
  const [showKnowsForm, setShowKnowsForm] = useState(false);
  const [showCertificateForm, setShowCertificateForm] = useState(false);

  const [tempInterest, setTempInterest] = useState(false);
  const [tempMaster, setTempMaster] = useState(false);
  const [tempWorkWith, setTempWorkWith] = useState(false);
  const [tempKnows, setTempKnows] = useState(false);
  const [tempCertificates, setTempCertificates] = useState(false);

  const [knowsForm, setKnowsForm] = useState({
    LevelRequired: '',
    minLevel: '',
    minExp: '',
  });

  const [certificateForm, setCertificateForm] = useState({
    comments: '',
    date: new Date(),
  });

  const [roleForm, setRoleForm] =
      useState({role: '', category: '', initDate: new Date()})

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
      knows: {color: {background: 'red'}, borderWidth: 3},
      interest: {color: {background: 'blue'}, borderWidth: 3},
      work_with: {color: {background: 'green'}, borderWidth: 3},
      master: {color: {background: 'orange'}, borderWidth: 3},
      have_certificate: {color: {background: 'yellow'}, borderWidth: 3},
      position: {color: {background: 'white'}, borderWidth: 3},
      candidate: {color: {background: 'pink'}, borderWidth: 3},
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
    },
    configure: {enabled: true, filter: 'physics, layout', showButton: true},
    interaction: {
      hover: true,
      hoverConnectedEdges: true,
      selectable: true,
      selectConnectedEdges: true
    }
  };

  const events = {
    select: function(event) {
      var {nodes, edges} = event;
    }
  };

  const handleCheckboxChange = (event, arrayName) => {
    const {checked} = event.target;

    if (arrayName === 'interest') {
      setTempInterest(checked);
    }
    if (arrayName === 'master') {
      setTempMaster(checked);
    }
    if (arrayName === 'work_with') {
      setTempWorkWith(checked);
    }
    if (arrayName === 'knows') {
      setShowKnowsForm(true);
      setTempKnows(checked);
    }
    if (arrayName === 'certificates') {
      setShowCertificateForm(true);
      setTempCertificates(checked);
    }
  };

  const handleCancel =
      () => {
        setShowCheckboxes(!showCheckboxes);
        setSelectedNode(null);
      }

  const handleAddToArray = () => {
    if (showKnowsForm || showCertificateForm) {
      setShowCheckboxes(false);
      setSelectedNode(null);
      return;
    }

    if (tempInterest) {
      setForm((prevForm) => ({
                ...prevForm,
                interest: [...prevForm.interest, selectedNode.nodeId],
              }));
    }
    if (tempMaster) {
      setForm((prevForm) => ({
                ...prevForm,
                master: [...prevForm.master, selectedNode.nodeId],
              }));
    }
    if (tempWorkWith) {
      setForm((prevForm) => ({
                ...prevForm,
                work_with: [...prevForm.work_with, selectedNode.nodeId],
              }));
    }
    if (tempKnows) {
      setShowKnowsForm(true);
    }
    if (tempCertificates) {
      setShowCertificateForm(true);
    }

    setTempInterest(false);
    setTempMaster(false);
    setTempWorkWith(false);
    setTempKnows(false);
    setTempCertificates(false);

    setShowCheckboxes(false);
    setSelectedNode(null);
  };

  const handleKnowsFormSubmit = (e) => {
    e.preventDefault();
    const newKnowsItem = {
      code: selectedNode.nodeId,
      LevelRequired: knowsForm.LevelRequired,
      minLevel: knowsForm.minLevel,
      minExp: knowsForm.minExp,
    };
    setUpdatedPerson((prevForm) => ({
                       ...prevForm,
                       knows: [...prevForm.knows, newKnowsItem],
                     }));
    setShowKnowsForm(false);
    setKnowsForm({
      LevelRequired: 'MANDATORY',
      minLevel: 'HIGH',
      minExp: '',
    });
    setShowKnowsForm(false);
  };

  const handleCertificateFormSubmit = (e) => {
    e.preventDefault();
    const newCertificateItem = {
      code: selectedNode.nodeId,
      comments: certificateForm.comments,
      date: format(certificateForm.date, 'dd-MM-yyyy'),
    };
    setUpdatedPerson(
        (prevForm) => ({
          ...prevForm,
          certificates: [...prevForm.certificates, newCertificateItem],
        }));
    setShowCertificateForm(false);
    setCertificateForm({
      comments: '',
      date: new Date(),
    });
    setShowCertificateForm(false);
  };

  const handleInputChange = (e) => {
    const {name, value} = e.target;
    setUpdatedPerson({
      ...updatedPerson,
      code: form.code,
      employeeId: form.employeeId,
      [name]: value,
    });
  };


  const handleSubmit = (event) => {
    event.preventDefault();

    setForm({
      code: '',
      employeeId: '',
      name: '',
      surname: '',
      birthDate: '',
      title: '',
      roles: [],
      knows: [],
      work_with: [],
      master: [],
      interest: [],
      certificates: [],
    });
  };

  const handleShowAddRoleForm = () => {
    setIsAddRoleVisible(true);
  };

  const handleCancelAddRole = (e) => {
    e.preventDefault();
    setIsAddRoleVisible(false);
    setRoleForm({
      role: '',
      category: '',
      date: new Date(),
    });
  };

  const handleAddRoleSubmit = (e) => {
    e.preventDefault();
    const newRoleItem = {
      role: roleForm.role,
      category: roleForm.category,
      initDate: format(roleForm.initDate, 'dd-MM-yyyy'),
    };
    setUpdatedPerson((prevForm) => ({
                       ...prevForm,
                       roles: [...prevForm.roles, newRoleItem],
                     }));
    setRoleForm({
      role: '',
      category: '',
      date: new Date(),
    });

    setIsAddRoleVisible(false);
  };

  const handleRemoveFromArray = (arrayName, nodeId) => {
    setUpdatedPerson((prevForm) => ({
                       ...prevForm,
                       [arrayName]: prevForm[arrayName].filter(
                           (element) => element.nodeId !== nodeId),
                     }));
  };

  const handleShowRoleList = (e) => {
    e.preventDefault();
    setIsShowRoleListVisible(!isShowRoleListVisible);
  };

  useEffect(() => {
    const recursive = (dataList) => {
      var list = [];
      dataList.forEach((data) => {
        list.push({
          nodeId: data.code,
          name: data.name,
          children: recursive(data.subSkills)
        });
      });
      return list;
    };

    fetch(`http://http://${window.location.hostname}:9080/skills`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      },
    })
        .then((response) => response.json())
        .then((response) => {
          const skillsData = recursive(response);
          setSkillList(skillsData);
        });

    fetch(
        `http://http://${window.location.hostname}:9080/person/${employeeId}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
          },
        })
        .then((response) => response.json())
        .then((data) => {
          const formattedBirthDate = data.birthDate ?
              format(new Date(data.birthDate), 'dd-MM-yyyy') :
              format(new Date(), 'dd-MM-yyyy');

          setForm({
            code: data.code,
            employeeId: data.employeeId,
            name: data.name,
            surname: data.surname,
            birthDate: formattedBirthDate,
            title: data.title,
            roles: data.roles || [],
            knows: data.knows || [],
            work_with: data.work_with || [],
            master: data.master || [],
            interest: data.interest || [],
            certificates: data.certificates || [],
          });

          setUpdatedPerson({
            name: data.name,
            surname: data.surname,
            birthDate: formattedBirthDate,
            title: data.title,
            roles: data.roles || [],
            knows: data.knows || [],
            work_with: data.work_with || [],
            master: data.master || [],
            interest: data.interest || [],
            certificates: data.certificates || [],
          });
        });
  }, [employeeId]);


  const handleNodeSelect = (event, item) => {
    setSelectedNode(item);
    setShowCheckboxes(true);
    setShowKnowsForm(false);
    setShowCertificateForm(false);
  };

  const getTreeItemsFromData =
      (treeItems, searchValue) => {
        const filteredItems = treeItems.filter((treeItemData) => {
          const isMatched = treeItemData.name.toLowerCase().includes(
                                searchValue.toLowerCase()) ||
              getTreeItemsFromData(treeItemData.children, searchValue).length >
                  0;

          return isMatched;
        });

    return filteredItems.map((treeItemData) => {
      const isMatched =
          treeItemData.name.toLowerCase().includes(searchValue.toLowerCase());

      if (isMatched) {
        return (
          <TreeItem
        key = {treeItemData.nodeId} nodeId = {treeItemData.nodeId} label =
        {
              <div onClick={(event) => handleNodeSelect(event, treeItemData)}>
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
      setSkillList(skillList.map((item) => Object.assign({}, item, {
        expanded: false,
      })));
  };

  const DataTreeView = () => {
    return (
      <MDBox>
        <TreeView
          defaultCollapseIcon={<ExpandMoreIcon />}
          defaultExpandIcon={<ChevronRightIcon />}
        >
          {getTreeItemsFromData(skillList, searchSkill)}
        </TreeView>
      </MDBox>
    );
  };

  if (!form.employeeId) {
      return <div>Loading...<
                 /div>;
}

  return (
    <DashboardLayout>
      <DashboardNavbar />
          <MDBox pt = {6} pb = {3}><Grid container spacing = {6}>
          <Grid item xs = {12}><Card>< MDBox
      mx = {2} mt = {-3} py = {3} px = {2} variant = 'gradient'
      bgColor = 'info'
      borderRadius = 'lg'
      coloredShadow =
          'info' > <MDTypography variant = 'h6' color = 'white'>Create
      Person</MDTypography>
              </MDBox><form id = 'personForm'>
          <Grid container spacing = {6}><Grid item xs = {6}><MDBox pt = {3}>
          <MDTypography variant = 'h6' fontWeight = 'medium'>Person Code: <
              /MDTypography>
                      <MDInput type="text" value={form.code} onChange={handleInputChange} name="code" disabled/>
          </MDBox>
                    <MDBox>
                      <MDTypography variant='h6' fontWeight='medium'>Employee ID:</MDTypography>
          <MDInput type = 'text' value = {form.employeeId} onChange =
               {handleInputChange} name = 'employeeId' />
          </MDBox>
                    <MDBox>
                      <MDTypography variant='h6' fontWeight='medium'>Name:</MDTypography>
          <MDInput type = 'text' value = {updatedPerson.name} onChange =
               {handleInputChange} name = 'name' />
          </MDBox>
                    <MDBox>
                      <MDTypography variant='h6' fontWeight='medium'>Surname:</MDTypography>
          <MDInput type = 'text' value = {updatedPerson.surname} onChange =
               {handleInputChange} name = 'surname' />
          </MDBox>
                    <MDBox>
                      <MDTypography variant='h6' fontWeight='medium'>Birth Date:</MDTypography><
          DatePicker
      selected = {birthDate} dateFormat = 'dd-MM-yyyy'
      onSelect = {(date) => setBirthDate(date)} onChange =
      {(date) => handleInputChange(
           {target: {name: 'birthDate', value: format(date, 'dd-MM-yyyy')}})
      } />
                    </MDBox > <MDBox>
          <MDTypography variant = 'h6' fontWeight = 'medium'>Title: <
              /MDTypography>
                      <MDInput type="text" value={updatedPerson.title} onChange={handleInputChange} name="title" />
          </MDBox>
                  </Grid><Grid item xs = {6}><MDBox>
          <MDTypography variant = 'h6' fontWeight = 'medium'>Roles: <
              /MDTypography>
                      {!isAddRoleVisible && (
                        <MDBox>
                          <MDButton variant="gradient" color="dark" onClick={handleShowAddRoleForm}>Add Role</MDButton>
          </MDBox>
                      )}
                      {isAddRoleVisible && (
                        <MDBox>
                          <MDBox>
                            <MDTypography variant='h6' fontWeight='medium'>Role:</MDTypography>
          <MDInput type = 'text' value = {roleForm.role} onChange =
           {(e) => setRoleForm({...roleForm, role: e.target.value})
           } />
                          </MDBox><MDBox>
          <MDTypography variant = 'h6' fontWeight = 'medium'>Category: <
              /MDTypography>
                            <MDInput type="text" value={roleForm.category} onChange={(e) => setRoleForm({ ...roleForm, category: e.target.value })} />
          </MDBox>
                          <MDBox>
                            <MDTypography variant='h6' fontWeight='medium'>Init Date:</MDTypography><
          DatePicker
      selected = {roleForm.initDate} dateFormat = 'dd-MM-yyyy'
                              onSelect={(date) => setRoleForm({ ...roleForm, initDate: date })}
                              onChange={(date) => setRoleForm({ ...roleForm, initDate: date })}
                            />
                          </MDBox>
                          <MDBox>
                            <MDButton variant='gradient' color='dark' onClick={(e) => handleAddRoleSubmit(e)}>Save</MDButton>
                            <MDButton variant="gradient" color="dark" onClick={handleCancelAddRole}>Cancel</MDButton>
                          </MDBox>
                        </MDBox>
                      )}
                      {updatedPerson.roles?.length > 0 && (
                        <MDBox>
                          <MDButton variant='gradient' color='dark' onClick={handleShowRoleList}>Show Role List</MDButton>
                          {isShowRoleListVisible && (
                            <MDBox>
                              {updatedPerson.roles.map((role, index) => (
                                <MDBox key={index}>
                                  <MDTypography variant='h6' fontWeight='medium'>Role: {role.role}</MDTypography>
                                  <MDTypography variant='h6' fontWeight='medium'>Category: {role.category}</MDTypography>
                                  <MDTypography variant='h6' fontWeight='medium'>Init Date: {role.initDate}</MDTypography>
                                  <MDButton variant='gradient' color='dark' onClick={() => handleRemoveFromArray('roles', index)}>Remove</MDButton>
                                </MDBox>
                              ))}
                            </MDBox>
                          )}
                        </MDBox>
                      )
      }</MDBox>
                    <MDBox>
                      <MDButton variant="gradient" color="dark" onClick={collapseAll}> Collapse all </MDButton>
      <MDBox>< MDInput
  type = 'text'
  value = {searchSkill} onChange =
      {(e) => setSearchSkill(e.target.value)} placeholder =
          'Search' / > </MDBox>
                      <DataTreeView />
      </MDBox>

                    {selectedNode && showCheckboxes && (
                      <Grid item xs={6}>
                        <br /><
      MDBox
  mx = {2} mt = {-3} py = {3} px = {2} variant = 'gradient'
  bgColor = 'info'
  borderRadius = 'lg'
  coloredShadow =
      'info' >
      <MDTypography variant = 'h6' color = 'white'>Select element: {selectedNode
                                                                        .name}<
          /MDTypography>
                          <FormGroup>
                            <FormControlLabel
                              label="Knows"
                              control={
                                <Checkbox
                                  checked={tempKnows}
                                  onChange={(e) => handleCheckboxChange(e, "knows")}
                                  value={selectedNode.nodeId}
                                />
} />
                            <FormControlLabel
                              label="Work With"
                              control={
                                <Checkbox
                                  checked={tempWorkWith}
                                  onChange={(e) => handleCheckboxChange(e, "work_with")}
                                  value={selectedNode.nodeId}
                                / >
}
/>
                            <FormControlLabel
                              label="Interest"
                              control={
                                <Checkbox
                                  checked={tempInterest}
                                  onChange={(e) => handleCheckboxChange(e, "interest")}
                                  value={selectedNode.nodeId}
                                / >
}
/>
                            <FormControlLabel
                              label="Master"
                              control={
                                <Checkbox
                                  checked={tempMaster}
                                  onChange={(e) => handleCheckboxChange(e, "master")}
                                  value={selectedNode.nodeId}
                                / >
}
/>
                            <FormControlLabel
                              label="Certificate"
                              control={
                                <Checkbox
                                  checked={tempCertificates}
                                  onChange={(e) => handleCheckboxChange(e, "certificates")}
                                  value={selectedNode.nodeId}
                                / >
}
                            />
                          </FormGroup>
                          <MDButton onClick={handleAddToArray}>Add</MDButton>
                          <MDButton onClick={handleCancel}>Cancel</MDButton>
                        </MDBox>
                      </Grid>
                    )
                            }

                    {selectedNode && showKnowsForm && (
                      <Grid item xs={6}>
                        <br />
                        <MDBox
                    mx = {2} mt = {-3} py = {3} px = {2} variant = 'gradient'
                    bgColor = 'info'
                    borderRadius = 'lg'
                    coloredShadow =
                        'info' >
                        <MDTypography variant = 'h6' color =
                             'white'>Knows Form: {selectedNode
                                                      .name}<
                            /MDTypography>
                          <FormControl fullWidth>
                            <InputLabel variant="standard" id="requiredLevelLabel">Level Required:</InputLabel><
                        Select
                    labelId = 'requiredLevelLabel'
                    value = {knowsForm.LevelRequired} label = 'Level'
                              onChange={(e) => setKnowsForm({ ...knowsForm, LevelRequired: e.target.value })}
                            >
                              <MenuItem value='MANDATORY'>Mandatory</MenuItem>
                              <MenuItem value="NICE_TO_HAVE">Nice to have</MenuItem>
                            </Select>
                          </FormControl>
                          <FormControl fullWidth>
                            <InputLabel variant='standard' id='minLevelLabel'>Min Level:</InputLabel>
                            <Select
                              labelId="minLevelLabel"
                              value={knowsForm.minLevel}
                              onChange={(e) => setKnowsForm({ ...knowsForm, minLevel: e.target.value })}
                            >
                              <MenuItem value="HIGH">High</MenuItem>
                              <MenuItem value='MEDIUM'>Medium</MenuItem>
                              <MenuItem value="LOW">Low</MenuItem>
                            </Select>
                          </FormControl>
                          <MDBox>
                            <MDTypography variant='h6' fontWeight='medium'>Min Experience:</MDTypography>
                            <MDInput
                              type="number"
                              value={knowsForm.minExp}
                              onChange={(e) => setKnowsForm({ ...knowsForm, minExp: e.target.value })}
                            />
                          </MDBox>
                          <MDButton variant="gradient" color="dark" onClick={handleKnowsFormSubmit}>Add to Knows</MDButton>
                        </MDBox>
                      </Grid>
                    )}

                    {selectedNode && showCertificateForm && (
                      <Grid item xs={6}>
                        <br />
                        <MDBox
                              mx = {2} mt = {-3} py = {3} px = {2} variant =
                                  'gradient'
                              bgColor = 'info'
                              borderRadius = 'lg'
                              coloredShadow =
                                  'info' >
                                  <MDTypography variant = 'h6' color =
                                       'white'>Certificate Form: {selectedNode
                                                                      .name}<
                                      /MDTypography>
                          <MDBox mx={2} mt={-3} py={3} px={2} variant='gradient'
                            bgColor='info'
                            borderRadius='lg'
                            coloredShadow='info' >
                            <MDTypography variant='h6' fontWeight='medium'>Comments:</MDTypography><
                                  MDInput
                              type = 'text'
                              value={certificateForm.comments}
                              onChange={
  (e) => setCertificateForm({...certificateForm, comments: e.target.value})}
                            />
                          </MDBox>
                          <MDBox>
                            <MDTypography variant='h6' color='white'>Date:</MDTypography>
                            <DatePicker
                              selected={certificateForm.date}
                              dateFormat="dd-MM-yyyy"
                              onSelect={(date) => setCertificateForm({ ...certificateForm, date: date })}
                              onChange={(date) => setCertificateForm({ ...certificateForm, date: date })}
                            />
                          </MDBox>
                          <MDButton onClick={handleCertificateFormSubmit}>Add to Certificate</MDButton>
                        </MDBox>
                      </Grid>
                    )}
                    <MDButton color='black' onClick={handleSubmit}>Submit</MDButton>
                  </Grid>
                </Grid>
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
                              bgColor = 'info'
                              borderRadius = 'lg'
                              coloredShadow =
                                  'info' >
                                  <MDTypography variant = 'h6' color = 'white'>
                                      Person Graph<
                                          /MDTypography>
              </MDBox>
                                  <MDBox pt = {3}>< VisGraph
                              graph = {graph} options = {options} events =
                                  {events} getNetwork = {
                                    network => {
                                      //  if you want access to vis.js network
                                      //  api you can set the state in a parent
                                      //  component using this property
                                    }
                                  } />
              </MDBox >

                                  </Card>
          </Grid>
                                  </Grid>
      </MDBox><Footer /><
                                  /DashboardLayout>
  );
}

export default UpdatePersonForm;
