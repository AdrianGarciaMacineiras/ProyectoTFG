import 'react-datepicker/dist/react-datepicker.css';
import '../network.css';

import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import TreeItem from '@mui/lab/TreeItem';
import TreeView from '@mui/lab/TreeView';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import { format } from 'date-fns';
import moment from 'moment';
import { useEffect, useState } from 'react';
import DatePicker from 'react-datepicker';
import { useParams, useNavigate } from 'react-router-dom';

import Footer from '../components/Footer';
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDButton from '../components/MDButton';
import MDInput from '../components/MDInput';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';

import SkillTable from './components/SkillTable';

const UpdatePersonForm = () => {
  const [form, setForm] = useState({
    code: '',
    employeeId: '',
    name: '',
    surname: '',
    //birthDate: '',
    title: '',
    roles: [],
    knows: [],
    work_with: [],
    master: [],
    interest: [],
    certificates: [],
  });

  const [updatedPerson, setUpdatedPerson] = useState({
    code: '',
    employeeId: '',
    name: '',
    surname: '',
    //birthDate: '',
    title: '',
    roles: [],
    knows: [],
    work_with: [],
    master: [],
    interest: [],
    certificates: [],
  });


  const { employeeId } = useParams();

  const [skillList, setSkillList] = useState([]);
  const [searchSkill, setSearchSkill] = useState('');

  //const [birthDate, setBirthDate] = useState(new Date());

  const [isAddRoleVisible, setIsAddRoleVisible] = useState(false);
  const [isShowRoleListVisible, setIsShowRoleListVisible] = useState(false);

  const [selectedNode, setSelectedNode] = useState(null);

  const [expandAll, setExpandAll] = useState([]);
  const [expand, setExpand] = useState([]);

  const [listas, setListas] = useState({});

  const navigate = useNavigate();

  const [roleForm, setRoleForm] =
    useState({ role: '', category: '', initDate: new Date() });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUpdatedPerson({
      ...updatedPerson,
      code: form.code,
      employeeId: form.employeeId,
      [name]: value,
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
    let formattedDate = roleForm.initDate
    if (roleForm.initDate) {
      formattedDate = moment(roleForm.initDate, "DD-MM-YYYY").toDate();
    } else {
      formattedDate = format(new Date(), "dd-MM-yyyy");
    }
    const newRoleItem = {
      role: roleForm.role,
      category: roleForm.category,
      initDate: formattedDate,
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
        'Access-Control-Allow-Origin': '*',
      },
    })
      .then((response) => response.json())
      .then((response) => {
        const skillsData = recursive(response);
        setSkillList(skillsData);
      });

    fetch(`http://${window.location.hostname}:9080/api/person/${employeeId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      },
    })
      .then((response) => response.json())
      .then((data) => {

        /*let formattedBirthDate = data.birthDate
        if (data.birthDate) {
          formattedBirthDate = moment(data.birthDate, "DD-MM-YYYY").toDate();
        } else {
          formattedBirthDate = format(new Date(), "dd-MM-yyyy");
        }*/

        setForm({
          code: data.code,
          employeeId: data.employeeId,
          name: data.name,
          surname: data.surname,
          //birthDate: formattedBirthDate,
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
          //birthDate: formattedBirthDate,
          title: data.title,
          roles: data.roles || [],
          knows: data.knows || [],
          work_with: data.work_with || [],
          master: data.master || [],
          interest: data.interest || [],
          certificates: data.certificates || [],
        });
        setListas({
          knows: data.knows || [],
          master: data.master || [],
          interest: data.interest || [],
          work_with: data.work_with || [],
          certificates: data.certificates || [],
        });
      })
      .catch(error => console.error(error));
  }, [employeeId]);


  const handleNodeSelect = (event, item) => {
    event.stopPropagation();
    setSelectedNode(item);
  };

  const getTreeItemsFromData =
    (treeItems, searchValue) => {
      const filteredItems = treeItems.filter((treeItemData) => {
        const isMatched = treeItemData.name.toLowerCase().includes(
          searchValue.toLowerCase()) ||
          getTreeItemsFromData(treeItemData.children, searchValue).length > 0;

        return isMatched;
      });

      return filteredItems.map((treeItemData) => {
        const isMatched = treeItemData.name.toLowerCase().includes(searchValue.toLowerCase());

        if (isMatched) {
          return (
            <TreeItem
              key={treeItemData.nodeId}
              nodeId={treeItemData.nodeId}
              label=
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

  const handleSubmit = (event) => {

    event.preventDefault();

    fetch(`http://${window.location.hostname}:9080/api/people/${updatedPerson.code}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      body: JSON.stringify(updatedPerson),
    })
      .then((response) => response.json())
      .catch((error) => {
        console.error('Error updating people:', error);
      });

    navigate(`/listPeople`);
  };

  const handleReturnRows = (updatedRows) => {
    const updatedListas = {
      knows: [],
      master: [],
      interest: [],
      work_with: [],
      certificates: [],
      notAssigned: updatedRows.notAssigned,
    };

    updatedRows?.forEach((row) => {
      if (row.master) {
        updatedListas.master.push(row.code);
      }
      if (row.interest) {
        updatedListas.interest.push(row.code);
      }
      if (row.workwith) {
        updatedListas.work_with.push(row.code);
      }
      if (row.knows.add) {
        updatedListas.knows.push({
          code: row.code,
          name: row.name,
          ...row.knows,
        });
      }
      if (row.certificates.add) {
        updatedListas.certificates.push({
          code: row.code,
          name: row.name,
          ...row.certificates,
        });
      }
    });

    let hasMatchingSkill = false;

    updatedRows.forEach(row => {
      if (row.code === selectedNode) {
        hasMatchingSkill = true;
      }
    });

    if (!hasMatchingSkill) {
      setSelectedNode(null);
    }

    setForm((prevForm) => ({
      ...prevForm,
      master: updatedListas.master,
      interest: updatedListas.interest,
      work_with: updatedListas.work_with,
      knows: updatedListas.knows,
      certificates: updatedListas.certificates,
    }));

    setListas(updatedListas);
  };

  return (
    <DashboardLayout>
      <DashboardNavbar />
      <MDBox pt={6} pb={3}>
        <Grid container spacing={6}>
          <Grid item xs={12}>
            {(!form.name ?
              (<Card>
                <MDBox
                  mx={2} mt={-3} py={3} px={2} variant='gradient'
                  bgColor='info'
                  borderRadius='lg'
                  coloredShadow=
                  'info' >
                  <MDTypography variant='h6' color='white'>Loading...</MDTypography>
                </MDBox>
              </Card>)
              : (<Card>
                < MDBox
                  mx={2} mt={-3} py={3} px={2} variant='gradient'
                  bgColor='info'
                  borderRadius='lg'
                  coloredShadow=
                  'info' >
                  <MDTypography variant='h6' color='white'>Update Person</MDTypography>
                </MDBox>
                <form id='personForm'>
                  <Grid container spacing={6}><Grid item xs={6}><MDBox pt={3}>
                    <MDTypography variant='h6' fontWeight='medium'>Person Code: </MDTypography>
                    <MDInput type="text" value={form.code} onChange={handleInputChange} name="code" disabled />
                  </MDBox>
                    <MDBox>
                      <MDTypography variant='h6' fontWeight='medium'>Employee ID:</MDTypography>
                      <MDInput type='text' value={form.employeeId} onChange=
                        {handleInputChange} name='employeeId' disabled />
                    </MDBox>
                    <MDBox>
                      <MDTypography variant='h6' fontWeight='medium'>Name:</MDTypography>
                      <MDInput type='text' value={updatedPerson.name} onChange=
                        {handleInputChange} name='name' />
                    </MDBox>
                    <MDBox>
                      <MDTypography variant='h6' fontWeight='medium'>Surname:</MDTypography>
                      <MDInput type='text' value={updatedPerson.surname} onChange=
                        {handleInputChange} name='surname' />
                    </MDBox>
                    {/*<MDBox>
                        <MDTypography variant='h6' fontWeight='medium'>Birth Date:</MDTypography><
                          DatePicker
                          selected={updatedPerson.birthDate} dateFormat='dd-MM-yyyy'
                          onSelect={(date) => setBirthDate(date)} onChange=
                          {(date) => handleInputChange(
                            { target: { name: 'birthDate', value: format(date, 'dd-MM-yyyy') } })
                          } />
                        </MDBox >*/}
                    <MDBox>
                      <MDTypography variant='h6' fontWeight='medium'>Title: </MDTypography>
                      <MDInput type="text" value={updatedPerson.title} onChange={handleInputChange} name="title" />
                    </MDBox>
                  </Grid>
                    <Grid item xs={6}>
                      <MDBox>
                        <MDTypography variant='h6' fontWeight='medium'>Roles: </MDTypography>
                        {!isAddRoleVisible && (
                          <MDBox>
                            <MDButton variant="gradient" color="dark" onClick={handleShowAddRoleForm}>Add Role</MDButton>
                          </MDBox>
                        )}
                        {isAddRoleVisible && (
                          <MDBox>
                            <MDBox>
                              <MDTypography variant='h6' fontWeight='medium'>Role:</MDTypography>
                              <MDInput type='text'
                                value={roleForm.role}
                                onChange={(e) => setRoleForm({ ...roleForm, role: e.target.value })}
                              />
                            </MDBox>
                            <MDBox>
                              <MDTypography variant='h6' fontWeight='medium'>Category: </MDTypography>
                              <MDInput type="text" value={roleForm.category}
                                onChange={(e) => setRoleForm({ ...roleForm, category: e.target.value })}  />
                            </MDBox>
                            <MDBox>
                              <MDTypography variant='h6' fontWeight='medium'>Init Date:</MDTypography><
                                DatePicker
                                selected={roleForm.initDate} dateFormat='dd-MM-yyyy'
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
                                    <MDTypography variant='h6' fontWeight='medium'>Init Date: {format(role.initDate, "dd-MM-yyyy")}</MDTypography>
                                    <MDButton variant='gradient' color='dark'
                                      onClick={() => handleRemoveFromArray('roles', index)}>Remove</MDButton>
                                  </MDBox>
                                ))}
                              </MDBox>
                            )}
                          </MDBox>
                        )}
                      </MDBox>
                    </Grid>
                  </Grid>
                  <Grid container spacing={12}>
                    <Grid item xs={12}>
                      <MDBox>
                        <MDButton variant="gradient" color="dark" onClick={handleExpandClick}>
                          {expand.length === 0 ? 'Expand all' : 'Collapse all'} </MDButton>
                        <MDBox>
                          < MDInput
                            type='text'
                            value={searchSkill} onChange=
                            {(e) => setSearchSkill(e.target.value)} placeholder=
                            'Search' />
                        </MDBox>
                        <DataTreeView />
                      </MDBox>
                      {skillList &&
                        <SkillTable skill={selectedNode} onReturnRows={handleReturnRows} listToUpdate={listas}/>
                      }
                      <MDButton color='black' onClick={handleSubmit}>Submit</MDButton>
                    </Grid>
                  </Grid>
                </form>
              </Card>
              ))}
          </Grid>
        </Grid>
      </MDBox>
      <Footer />
    </DashboardLayout>
  );
};

export default UpdatePersonForm;
