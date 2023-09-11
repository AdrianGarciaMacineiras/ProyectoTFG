import '../network.css';

import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import { useState } from 'react';
import VisGraph from 'react-vis-graph-wrapper';

import Footer from '../components/Footer';
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDButton from '../components/MDButton';
import MDInput from '../components/MDInput';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';

function FindPerson() {
  const [form, setForm] = useState({ personCode: '' });

  const graphTemp = { nodes: [], edges: [] };

  const [graph, setGraph] = useState(null);

  const [aux, setAux] = useState([]);

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
      have_certificate: { color: { background: 'yellow' }, borderWidth: 3 },
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

  const findPerson = (personCode) =>
    fetch(`http://${window.location.hostname}:9080/api/person/${personCode}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      }
    })
      .then(response => { return response.json() })
      .then(response => {
        setAux(response);
        let i = 1;
        let temp = {
          Code: response.code,
          Name: response.name,
          Surname: response.surname,
          Email: response.email,
          EmployeeId: response.employeeId,
          FriendlyName: response.friendlyName,
          Title: response.title,
          BirthDate: response.birthDate
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
          graphTemp.nodes.push(
            { id: i, label: element, title: element, group: 'work_with' });
          graphTemp.edges.push({ from: 1, to: i, label: 'WORK_WITH' });
        });

        response.master?.forEach(element => {
          i++;
          graphTemp.nodes.push(
            { id: i, label: element, title: element, group: 'master' });
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
            group: 'have_certificate'
          });
          graphTemp.edges.push({ from: 1, to: i, label: 'HAVE_CERTIFICATE' });
        });

        response.assigns?.forEach(element => {
          i++;
          graphTemp.nodes.push({
            id: i,
            label: element.name,
            title: element.name,
            group: 'position'
          });
          element.assignments?.forEach(element => {
            let temp = {
              AssignDate: element.assignDate,
              InitDate: element.initDate,
              EndDate: element.endDate,
              Dedication: element.dedication,
              Role: element.role
            };
            graphTemp.edges.push({
              from: 1,
              to: i,
              label: 'COVER',
              title: JSON.stringify(temp, '', 2)
            });
          })
        });

        response.candidacies?.forEach(element => {
          let temp = {
            Code: element.code,
            IntroductionDate: element.introductionDate,
            ResolutionDate: element.resolutionDate,
            CreationDate: element.creationDate,
            Status: element.status
          };
          graphTemp.edges.push({
            from: 1,
            to: i,
            label: 'CANDIDATE',
            title: JSON.stringify(temp, '', 2),
            group: 'candidate'
          });
        });

        setGraph(prev => graphTemp);
      });

  const handlePersonCode = (event) => {
    setForm({
      ...form,
      [event.target.id]: event.target.value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    findPerson(form.personCode);

    setForm({ personCode: '' })
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
                <MDTypography variant='h6' color='white'>Find Person</MDTypography>
              </MDBox>
              <MDBox pt={3}>
                <form onSubmit={handleSubmit}><MDBox>
                  <MDTypography variant='h6' fontWeight='medium'>Person Code or EmployeeId</MDTypography>
                  <MDInput
                    id="personCode"
                    type="text"
                    value={form.personCode}
                    onChange={handlePersonCode} />
                </MDBox>
                  <MDButton variant="gradient" color="dark" onClick={handleSubmit}>Submit</MDButton>
                </form>
              </MDBox>
            </Card>
          </Grid>
          {graph &&
            <Grid item xs={12}>
              <Card>
                < MDBox
                  mx={2} mt={-3} py={3} px={2} variant='gradient'
                  bgColor='info'
                  borderRadius='lg'
                  coloredShadow='info' >
                  <MDTypography variant='h6' color='white'>Person
                    Graph
                  </MDTypography>
                </MDBox>
                <MDBox pt={3}>
                  < VisGraph
                    graph={graph}
                    options={options}
                    events={events}
                    getNetwork={network => {}} />
                </MDBox >
              </Card>
            </Grid>
          }
        </Grid>
      </MDBox>
      <Footer />
    </DashboardLayout>
  );
}

export default FindPerson;
