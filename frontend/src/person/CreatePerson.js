import 'react-datepicker/dist/react-datepicker.css';
import '../network.css';

import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import TreeItem from '@mui/lab/TreeItem';
import TreeView from '@mui/lab/TreeView';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import {format} from 'date-fns';
import {useEffect, useState} from 'react';
import DatePicker from 'react-datepicker';
import {useNavigate} from 'react-router-dom';
import VisGraph from 'react-vis-graph-wrapper';

import Footer from '../components/Footer';
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDButton from '../components/MDButton';
import MDInput from '../components/MDInput';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';
import SkillTable from './components/SkillTable';


const CreatePerson =
  () => {
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

    const [skillList, setSkillList] = useState([]);
    const [searchSkill, setSearchSkill] = useState('');

    //const [birthDate, setBirthDate] = useState(new Date());

    const [isAddRoleVisible, setIsAddRoleVisible] = useState(false);
    const [isShowRoleListVisible, setIsShowRoleListVisible] = useState(false);

    const [selectedNode, setSelectedNode] = useState(null);

    const [expandAll, setExpandAll] = useState([]);
    const [expand, setExpand] = useState([]);

    const [roleForm, setRoleForm] =
      useState({ role: '', category: '', initDate: new Date() })

    const navigate = useNavigate();

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
        knows: { color: { background: 'red' }, borderWidth: 3 },
        interest: { color: { background: 'blue' }, borderWidth: 3 },
        work_with: { color: { background: 'green' }, borderWidth: 3 },
        master: { color: { background: 'orange' }, borderWidth: 3 },
        has_certificate: { color: { background: 'yellow' }, borderWidth: 3 },
        position: { color: { background: 'white' }, borderWidth: 3 },
        candidate: { color: { background: 'pink' }, borderWidth: 3 },
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

    const handleInputChange = (e) => {
      const { name, value } = e.target;
      setForm({
        ...form,
        [name]: value,
      });
    };

    const createPerson =
      () => {
        const requestBody = JSON.stringify(form);

        fetch(`http://${window.location.hostname}:9080/api/people`, {
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
              Code: response.code,
              Name: response.name,
              Surname: response.surname,
              Email: response.email,
              EmployeeId: response.employeeId,
              FriendlyName: response.friendlyName,
              Title: response.title
              // BirthDate: response.birthDate
            };
            graphTemp.nodes.push({
              id: i,
              label: response.name + ' ' + response.surname,
              title: JSON.stringify(temp, '', 2)
            });
            response.knows?.forEach(element => {
              i++;
              let temp = {
                Code: element.code,
                Primary: element.primary,
                Experience: element.experience,
                Level: element.level
              };
              graphTemp.nodes.push({
                id: i,
                label: element.name,
                title: JSON.stringify(temp, '', 2),
                group: 'knows',
                value: element.experience * 150
              });
              graphTemp.edges.push({ from: 1, to: i, label: 'KNOWS' });
            });

            response.interest?.forEach(element => {
              i++;
              graphTemp.nodes.push({
                id: i,
                label: element,
                title: element,
                group: 'interest',
                value: 10
              });
              graphTemp.edges.push({ from: 1, to: i, label: 'INTEREST' });
            });

            response.work_with?.forEach(element => {
              i++;
              graphTemp.nodes.push({
                id: i,
                label: element,
                title: element,
                group: 'work_with'
              });
              graphTemp.edges.push({ from: 1, to: i, label: 'WORK_WITH' });
            });

            response.master?.forEach(element => {
              i++;
              graphTemp.nodes.push({
                id: i,
                label: element,
                title: element,
                group: 'master'
              });
              graphTemp.edges.push({ from: 1, to: i, label: 'MASTER' });
            });
            response.certificates?.forEach(element => {
              i++;
              let temp = {
                Code: element.code,
                Name: element.name,
                Comments: element.comments,
                Date: element.date
              };
              graphTemp.nodes.push({
                id: i,
                label: element.code,
                title: JSON.stringify(temp, '', 2),
                group: 'has_certificate'
              });
              graphTemp.edges.push(
                { from: 1, to: i, label: 'HAS_CERTIFICATE' });
            });
            setGraph(prev => graphTemp);
          });

        navigate(`/listPeople`);
      }


    const handleSubmit = (event) => {
      event.preventDefault();

      createPerson();

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
      setForm((prevForm) => ({
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
      setForm((prevForm) => ({
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
        let list = [];
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
    }, []);


    const handleNodeSelect = (event, item) => {
      event.stopPropagation();
      setSelectedNode(item);
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

    const handleReturnRows = (updatedRows) => {
      updatedRows?.forEach(row => {
        if (row.master) {
          setForm((prevForm) => ({
            ...prevForm,
            master: [...prevForm.master, row.code],
          }));
        }
        if (row.interest) {
          setForm((prevForm) => ({
            ...prevForm,
            interest: [...prevForm.interest, row.code],
          }));
        }
        if (row.workwith) {
          setForm((prevForm) => ({
            ...prevForm,
            work_with: [...prevForm.work_with, row.code],
          }));
        }
        if (row.knows.add) {
          setForm((prevForm) => ({
            ...prevForm,
            knows: [
              ...prevForm.knows,
              {
                code: row.code,
                name: row.name,
                ...row.knows,
              },
            ],
          }));
        }
        if (row.certificates.add) {
          setForm((prevForm) => ({
            ...prevForm,
            certificates: [
              ...prevForm.certificates,
              {
                code: row.code,
                name: row.name,
                ...row.certificates,
              },
            ],
          }));
        }
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
                  coloredShadow='info' >
                  <MDTypography variant='h6' color='white'>Create Person</MDTypography>
                </MDBox>
                <form id='personForm'>
                  <Grid container spacing={12}>
                    <Grid item xs={4}>
                      <MDBox pt={3}>
                        <MDTypography variant='h6' fontWeight='medium'>Person Code: </MDTypography>
                        <MDInput type="text" value={form.code} onChange={handleInputChange} name="code" />
                      </MDBox>
                      <MDBox>
                        <MDTypography variant='h6' fontWeight='medium'>Employee ID:</MDTypography>
                        <MDInput type='text' value={form.employeeId} onChange=
                          {handleInputChange} name='employeeId' />
                      </MDBox>
                      {/*<MDBox>
                        <MDTypography variant='h6' fontWeight='medium'>Birth Date:</MDTypography>
                        <DatePicker
                          selected={birthDate} dateFormat='dd-MM-yyyy'
                          onSelect={(date) => setBirthDate(date)} onChange=
                          {
                            (date) => handleInputChange(
                              { target: { name: 'birthDate', value: format(date, 'dd-MM-yyyy') } })
                          }
                        />
                        </MDBox >*/}
                      <MDBox>
                        <MDTypography variant='h6' fontWeight='medium'>Title: </MDTypography>
                        <MDInput type="text" value={form.title} onChange={handleInputChange} name="title" />
                      </MDBox>
                    </Grid>
                    <Grid item xs={4}>
                      <MDBox>
                        <MDTypography variant='h6'
                                      fontWeight='medium'>Name:</MDTypography>
                        <MDInput type='text' value={form.name} onChange=
                            {handleInputChange} name='name'/>
                      </MDBox>
                      <MDBox>
                        <MDTypography variant='h6'
                                      fontWeight='medium'>Surname:</MDTypography>
                        <MDInput type='text' value={form.surname} onChange=
                            {handleInputChange} name='surname'/>
                      </MDBox>
                    </Grid>
                    <Grid item xs={4}>
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
                              <MDInput type='text' value={roleForm.role} onChange={
                                (e) => setRoleForm({ ...roleForm, role: e.target.value })} />
                            </MDBox>
                            <MDBox>
                              <MDTypography variant='h6' fontWeight='medium'>Category: </MDTypography>
                              <MDInput type="text" value={roleForm.category} onChange={
                                (e) => setRoleForm({ ...roleForm, category: e.target.value })} />
                            </MDBox>
                            <MDBox>
                              <MDTypography variant='h6' fontWeight='medium'>Init Date:</MDTypography>
                              <DatePicker
                                selected={roleForm.initDate}
                                dateFormat='dd-MM-yyyy'
                                onSelect={(date) => setRoleForm({ ...roleForm, initDate: date })}
                                onChange={
                                  (date) => setRoleForm({ ...roleForm, initDate: date })}
                              />
                            </MDBox>
                            <MDBox>
                              <MDButton variant='gradient' color='dark' onClick={(e) => handleAddRoleSubmit(e)}>Save</MDButton>
                              <MDButton variant="gradient" color="dark" onClick={handleCancelAddRole}>Cancel</MDButton>
                            </MDBox>
                          </MDBox>
                        )}
                        {form.roles.length > 0 && (
                          <MDBox>
                            <MDButton variant='gradient' color='dark' onClick={handleShowRoleList}>Show Role List</MDButton>
                            {isShowRoleListVisible && (
                              <MDBox>
                                {form.roles.map((role, index) => (
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
                        )}
                      </MDBox>
                    </Grid>
                  </Grid>
                  <Grid container spacing={6}>
                    <Grid item xs={6}>
                      {form.certificates.length > 0 && (
                        <MDBox>
                          {/*Hace falta poner las cosas que se van añadiendo?
                          se añade con el boton de return rows? */}
                          {console.log(form)}
                        </MDBox>
                      )}
                    </Grid>
                  </Grid>
                  <Grid container spacing={12}>
                    <Grid item xs={2}>
                      <MDBox>
                        <MDButton variant="gradient" color="dark" onClick={handleExpandClick}> {expand.length === 0 ? 'Expand all' : 'Collapse all'} </MDButton>
                        <MDBox>
                          < MDInput
                            type='text'
                            value={searchSkill}
                            onChange={(e) => setSearchSkill(e.target.value)}
                            placeholder='Search' />
                        </MDBox>
                        <DataTreeView />
                      </MDBox>
                    </Grid>
                    <Grid item xs={9}>
                      {selectedNode &&
                        <SkillTable skill={selectedNode} onReturnRows={handleReturnRows} />
                      }
                    </Grid>
                    <Grid item xs={12}>
                      <MDButton color='black' onClick={handleSubmit}>Submit</MDButton>
                    </Grid>
                  </Grid>
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
                    coloredShadow=
                    'info' >
                    <MDTypography variant='h6' color='white'>Person Graph</MDTypography>
                  </MDBox>
                  <MDBox pt={3}>
                    < VisGraph
                      graph={graph}
                      options={options}
                      events={events}
                      getNetwork={network => { }} />
                  </MDBox >
                </Card>
              </Grid>
            </Grid>
          </MDBox>
        }
        <Footer />
      </DashboardLayout>
    );
  }

export default CreatePerson;
