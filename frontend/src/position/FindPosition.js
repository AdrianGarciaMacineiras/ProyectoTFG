import '../network.css';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import React, { useState } from 'react';
import VisGraph from 'react-vis-graph-wrapper';
import { TableCell, TableRow } from '@mui/material';
import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';
import PropTypes from 'prop-types';
import Footer from '../components/Footer';
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDButton from '../components/MDButton';
import MDInput from '../components/MDInput';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';
import CandidateList from './CandidateList';
import SkillsNeededList from './SkillsNeededList';
import AssignedList from './AssignedList';
import { useNavigate } from 'react-router-dom';

function FindPosition() {
  const [form, setForm] = useState({ positionCode: '' });

  const graphTemp = { nodes: [], edges: [] };

  const [graph, setGraph] = useState({ nodes: [], edges: [] });

  const [valor, setValor] = useState(0);

  const [aux, setAux] = useState('');

  const navigate = useNavigate();

  const options = {
    layout: { improvedLayout: true },
    nodes: { shape: 'dot', scaling: { min: 10, label: false } },
    edges: {
      color: '#000000',
      smooth: { enabled: true, type: 'discrete', roundness: 0.5 }
    },
    groups: {
      assigned: { color: { background: 'red' }, borderWidth: 3 },
      candidates: { color: { background: 'green' }, borderWidth: 3 },
      project: { color: { background: 'yellow' }, borderWidth: 3 },
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
    configure: { enabled: true, filter: 'physics, layout', showButton: true },
    interaction: {
      hover: true,
      hoverConnectedEdges: true,
      selectable: true,
      selectConnectedEdges: true
    }
  };

  const events = {
    select: function (event) {
      var { nodes, edges } = event;
    }
  };

  const FindPosition = (positionCode) =>
    fetch(
      `http://${window.location.hostname}:9080/api/position/${positionCode}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      }
    })
      .then(response => { return response.json() })
      .then(response => {
        setAux(response);
        var i = 1
        var temp = {
          Code: response.code,
          Name: response.name,
          Charge: response.charge,
          Active: response.active,
          Role: response.role,
          EndDate: response.closingDate,
          InitDate: response.openingDate
        };
        graphTemp.nodes.push({
          id: i,
          label: response.name,
          title: JSON.stringify(temp, '', 2)
        });

        response.assignedPeople?.forEach(element => {
          i++;
          var temp = {
            AssignDate: element.assignDate,
            InitDate: element.initDate,
            EndDate: element.endDate,
            Dedication: element.dedication,
            Role: element.role
          };
          graphTemp.nodes.push({
            id: i,
            label: element.assigned,
            title: JSON.stringify(element.assigned, '', 2),
            group: 'assigned'
          });
          graphTemp.edges.push({
            from: i,
            to: 1,
            label: 'COVER',
            title: JSON.stringify(temp, '', 2)
          })
        });

        i++;
        graphTemp.nodes.push({
          id: i,
          label: response.projectCode,
          title: JSON.stringify(response.projectCode, '', 2),
          group: 'project'
        });
        graphTemp.edges.push({
          from: i,
          to: 1,
          label: 'FOR_PROJECT',
          title: JSON.stringify(response.projectCode, '', 2)
        });

        response.candidates?.forEach(element => {
          i++;
          graphTemp.nodes.push({
            id: i,
            label: element.candidateCode,
            title: element.candidateCode,
            group: 'candidates'
          });
          var temp = {
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

        setGraph(prev => graphTemp);
      });

  const handlePositionCode = (event) => {
    setForm({
      ...form,
      [event.target.id]: event.target.value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    FindPosition(form.positionCode);

    setForm({ positionCode: '' })
  };

  function CustomTabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
      <div
        role='tabpanel'
        hidden={value !== index}
        id={`simple-tabpanel-${index}`}
        aria-labelledby={`simple-tab-${index}`} {...other} >
        {value === index &&
          (<MDBox sx={{ p: 3 }}>
            <MDTypography>{children}</MDTypography>
          </MDBox>)}
      </div>
    );
  };

  CustomTabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired,
  };

  function a11yProps(index) {
    return {
      id: `simple-tab-${index}`,
      'aria-controls': `simple-tabpanel-${index}`,
    };
  };

  const handleChangeTab = (event, newValue) => {
    setValor(newValue);
  };

  const initialCandidateState = {
    page: 0,
    rowsPerPage: 10
  };

  const [candidateState, setCandidateState] = useState(initialCandidateState);

  const handleCandidateStateChange = (newState) => {
    setCandidateState(prevState => ({
      ...prevState,
      ...newState
    }));
  };

  const handleCandidatePageChange = (newPage) => {
    handleCandidateStateChange({ page: newPage });
  };

  const handleCandidateRowsPerPageChange = (newRowsPerPage) => {
    handleCandidateStateChange({
      rowsPerPage: newRowsPerPage,
      page: 0
    });
  };

  const initialAssignedState = {
    page: 0,
    rowsPerPage: 10
  };

  const [assignedState, setAssignedState] = useState(initialAssignedState);

  const handleAssignedStateChange = (newState) => {
    setAssignedState(prevState => ({
      ...prevState,
      ...newState
    }));
  };

  const handleAssignedPageChange = (newPage) => {
    handleAssignedStateChange({ page: newPage });
  };

  const handleAssignedRowsPerPageChange = (newRowsPerPage) => {
    handleAssignedStateChange({
      rowsPerPage: newRowsPerPage,
      page: 0
    });
  };

  const initialSkillListState = {
    page: 0,
    rowsPerPage: 10
  };

  const [skillListState, setSkillListState] = useState(initialSkillListState);

  const handleSkillListStateChange = (newState) => {
    setSkillListState(prevState => ({
      ...prevState,
      ...newState
    }));
  };

  const handleSkillListPageChange = (newPage) => {
    handleSkillListStateChange({ page: newPage });
  };

  const handleSkillListRowsPerPageChange = (newRowsPerPage) => {
    handleSkillListStateChange({
      rowsPerPage: newRowsPerPage,
      page: 0
    });
  };

  const regenerateCandidates = () => {
    const fetchData = async () => {
      const regeneratedCandidates = await fetch(`http://${window.location.hostname}:9080//api/position/${form.positionCode}/candidates`);
      const candidatesData = await regeneratedCandidates.json();

      if (candidatesData) {
        const positionData = await fetch(`http://${window.location.hostname}:9080/api/position/${form.positionCode}`);
        const position = await positionData.json();
        setAux(position);
        var i = 1
        var temp = {
          Code: position.code,
          Name: position.name,
          Charge: position.charge,
          Active: position.active,
          Role: position.role,
          EndDate: position.closingDate,
          InitDate: position.openingDate
        };
        graphTemp.nodes.push({
          id: i,
          label: position.name,
          title: JSON.stringify(temp, '', 2)
        });

        position.assignedPeople?.forEach(element => {
          i++;
          var temp = {
            AssignDate: element.assignDate,
            InitDate: element.initDate,
            EndDate: element.endDate,
            Dedication: element.dedication,
            Role: element.role
          };
          graphTemp.nodes.push({
            id: i,
            label: element.assigned,
            title: JSON.stringify(element.assigned, '', 2),
            group: 'assigned'
          });
          graphTemp.edges.push({
            from: i,
            to: 1,
            label: 'COVER',
            title: JSON.stringify(temp, '', 2)
          })
        });

        i++;
        graphTemp.nodes.push({
          id: i,
          label: position.projectCode,
          title: JSON.stringify(position.projectCode, '', 2),
          group: 'project'
        });
        graphTemp.edges.push({
          from: i,
          to: 1,
          label: 'FOR_PROJECT',
          title: JSON.stringify(position.projectCode, '', 2)
        });

        position.candidates?.forEach(element => {
          i++;
          graphTemp.nodes.push({
            id: i,
            label: element.candidateCode,
            title: element.candidateCode,
            group: 'candidates'
          });
          var temp = {
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

        setGraph(prev => graphTemp);
      }
    };
    fetchData();
  }

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
                <MDTypography variant='h6' color='white'>Find Position</MDTypography>
              </MDBox>
              <MDBox pt={3}>
                <form onSubmit={handleSubmit}>
                  <MDBox>
                    <MDTypography variant='h6' fontWeight='medium'>Position code</MDTypography>
                    <MDInput
                      id="positionCode"
                      type="text"
                      value={form.positionCode}
                      onChange={handlePositionCode} />
                  </MDBox>
                  <MDButton variant="gradient" color="dark" onClick={handleSubmit}>Submit</MDButton>
                </form>
              </MDBox>
            </Card>
          </Grid>
        </Grid>
      </MDBox>
      {aux && (
        <React.Fragment>
          <MDBox pt={6} pb={3}>
            <Grid container spacing={6}>
              <Grid item xs={12}>
                <Card>
                  <MDBox>
                    <MDBox
                      mx={2} mt={-3} py={3} px={2} variant='gradient'
                      bgColor='info'
                      borderRadius='lg'
                      coloredShadow='info'
                      display='flex'
                      justifyContent='space-between'
                      alignItems=
                      'center' >
                      <MDTypography variant='h6' color='white'>More Information</MDTypography>
                    </MDBox>
                    <MDButton variant="gradient" color="dark" onClick={regenerateCandidates}>Regenerate Candidates</MDButton>
                    <TableRow>
                      <TableCell
                        style={{ paddingBottom: 0, paddingTop: 0 }}
                        colSpan={8}>
                        <MDBox sx={{ margin: 1 }}>
                          <MDBox sx={{ borderBottom: 1, borderColor: 'divider' }}>
                            <Tabs
                              value={valor}
                              onChange={(event, newValue) =>
                                handleChangeTab(event, newValue, aux)} aria-label='basic tabs example'>
                              <Tab label='Skills' {...a11yProps(0)} />
                              <Tab label="Candidate" {...a11yProps(1)} />
                              <Tab label='Assigned' {...a11yProps(2)} />
                            </Tabs>
                          </MDBox>
                          <CustomTabPanel value={valor} index={0}>
                            <SkillsNeededList
                              key={aux.code}
                              data={aux.skills}
                              state={skillListState}
                              onStateChange={newState => handleSkillListStateChange(newState)}
                              onPageChange={newPage => handleSkillListPageChange(newPage)}
                              onRowsPerPageChange={newRowsPerPage => handleSkillListRowsPerPageChange(newRowsPerPage)} />
                          </CustomTabPanel>
                          <CustomTabPanel value={valor} index={1}>
                            <CandidateList
                              key={aux.code}
                              data={aux.candidates}
                              state={candidateState}
                              onStateChange={newState => handleCandidateStateChange(newState)}
                              onPageChange={newPage => handleCandidatePageChange(newPage)}
                              onRowsPerPageChange={newRowsPerPage => handleCandidateRowsPerPageChange(newRowsPerPage)} />
                          </CustomTabPanel>
                          <CustomTabPanel value={valor} index={2}>
                            <AssignedList
                              key={aux.code}
                              data={aux.assignedPeople}
                              state={assignedState}
                              onStateChange={newState => handleAssignedStateChange(newState)}
                              onPageChange={newPage => handleAssignedPageChange(newPage)}
                              onRowsPerPageChange={newRowsPerPage => handleAssignedRowsPerPageChange(newRowsPerPage)} />
                          </CustomTabPanel>
                        </MDBox>
                      </TableCell>
                    </TableRow>
                  </MDBox>
                </Card>
              </Grid>
            </Grid>
          </MDBox>
          {graph &&
            <MDBox pt={6} pb={3}>
              <Grid container spacing={6}>
                <Grid item xs={12}>
                  <Card>
                    < MDBox
                      mx={2} mt={-3} py={3} px={2} variant='gradient'
                      bgColor='info'
                      borderRadius='lg'
                      coloredShadow=
                      'info' >
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
        </React.Fragment>
      )}
      <Footer />
    </DashboardLayout>
  );
}

export default FindPosition;