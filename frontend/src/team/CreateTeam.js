import React, {useRef, useEffect, useState } from "react";
import "react-datepicker/dist/react-datepicker.css";
import "../network.css";
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
import Autocomplete from '@mui/material/Autocomplete';
import { Cancel, Tag } from "@mui/icons-material";
import {Stack, TextField, Typography , InputLabel, Select, MenuItem, FormControl} from "@mui/material";
import { Box } from "@mui/system";

const CreateTeam = () => {

    const [selectedPerson, setSelectedPerson] = useState(null);
    const [selectedPersonId, setSelectedPersonId] = useState(null); 


    const [charge, setCharge] = useState("");
    const [peopleList, setPeopleList] = useState([]);

    const [form, setForm] = useState({
        code: '',
        name: '',
        description: '',
        tags: [],
        members: []
      });

    const tagRef = useRef();

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
          team: {color:{background:'red'}, borderWidth:3},
          members: {color:{background:'blue'}, borderWidth:3},
          skills: {color:{background:'green'}, borderWidth:3},
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
        },
        configure: {
          enabled: true,
          filter: 'physics, layout',
          showButton: true
       },
        interaction: {
          hover: true,
          hoverConnectedEdges: true,
          selectable: true,
          selectConnectedEdges: true
        }
      };
      
      const events = {
        select: function(event) {
          var { nodes, edges } = event;
        }
      };

    const handleDelete = (value) => {
        const newTags = form.tags.filter((val) => val !== value);
        setForm({
            ...form,
            tags: newTags,
          });
      };

      const handleOnSubmitTag = (e) => {
        e.preventDefault();
        setForm({
          ...form,
          tags: [...form.tags, tagRef.current.value]
        });
        tagRef.current.value = "";
      };
    
      const handleKeyDown = (e) => {
        if (e.keyCode === 13) {
          e.preventDefault();
          setForm({
            ...form,
            tags: [...form.tags, tagRef.current.value],
          });
          tagRef.current.value = "";
        }
      };

    const Tags = ({ data, handleDelete }) => {
        return (
          <Box
            sx={{
              background: "#283240",
              height: "100%",
              display: "flex",
              padding: "0.4rem",
              margin: "0 0.5rem 0 0",
              justifyContent: "center",
              alignContent: "center",
              color: "#ffffff",
            }}
          >
            <Stack direction='row' gap={1}>
              <MDTypography>{data}</MDTypography>
              <Cancel
                sx={{ cursor: "pointer" }}
                onClick={() => {
                  handleDelete(data);
                }}
              />
            </Stack>
          </Box>
        );
      };

    useEffect(() =>{
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
        },[]); 

    const handleSelectPerson = (event, person) => {
        setSelectedPersonId(person.code); 
        setCharge(""); 
    };

    const handleSubmitPerson = (e) => {
        e.preventDefault();
        if (!selectedPersonId || !charge) return;
    
        const selectedPerson = peopleList.find((person) => person.code === selectedPersonId);
    
        if (!selectedPerson) {
          console.error("Selected person not found in peopleList!");
          return;
        }
    
        const newMember = {
          people: { name: selectedPerson.name, code: selectedPerson.code },
          charge: charge,
        };
    
        setForm({
          ...form,
          members: [...form.members, newMember]
        });
    
        setSelectedPersonId(null);
        setCharge("");
      };


    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setForm({
        ...form,
        [name]: value,
        });
    };


    const createTeam = () => {

        const requestBody = JSON.stringify(form);
        console.log(requestBody);

        fetch("http://localhost:9080/team", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*"
        },
        body: requestBody,
        })
        .then(response => {return response.json()})
        .then(response => {
          setAux(response);
          var i = 1;
          var temp = {Code: response.code, Name: response.name, Description: response.description, Tags: response.tags}      
          graphTemp.nodes.push({id: i, label: response.name, title: JSON.stringify(temp, '', 2), group: "team"})  
          
          response.members.forEach(element=>{
            i++
            var temp ={Code: element.people.code, Name:element.people.name, Surname:element.people.surname, Email:element.people.email, EmployeeId:element.people.employeeId,
                FriendlyName:element.people.friendlyName, Title:element.people.title, BirthDate: element.people.birthDate}
              graphTemp.nodes.push({id:i, label: element.people.name + ' ' + element.people.surname, title: JSON.stringify(temp,'',2), group: "members"});
              graphTemp.edges.push({from:i, to: 1, label: "MEMBER_OF", title: element.charge});
          });
          
          /*response.strategics.forEach(element=>{
            i++
            var temp = {Name: element.name, Code: element.code}
            graphTemp.nodes.push({id:i, label: element.name, title: JSON.stringify(temp,'',2), group:"skills"});
            graphTemp.edges.push({from:1, to: i, label: "STRATEGIC"});
          });*/

          setGraph(prev => graphTemp);
        })
    };

    const handleSubmit = (event) => { 
        event.preventDefault();

        createTeam();

        console.log(form);
        
        setGraph({
            nodes: [],
            edges: []
        });

        setForm({
            code: '',
            name: '',
            description: '',
            tags: [],
            members: []
          });
    }

    return (
        <DashboardLayout>
      <DashboardNavbar />
      <MDBox pt={6} pb={3}>
        <Grid container spacing={6}>
          <Grid item xs={12}>
            <Card>
              <MDBox mx={2} mt={-3} py={3} px={2} variant="gradient" bgColor="info" borderRadius="lg" coloredShadow="info">
                <MDTypography variant="h6" color="white">
                  Create Team
                </MDTypography>
              </MDBox>
              <form id="teamForm" onSubmit={handleSubmit}>
                <MDBox pt={3}>
                  <Grid container spacing={6}>
                    <Grid item xs={6}>
                      <MDTypography variant="h6" fontWeight="medium">
                        Team code
                      </MDTypography>
                      <MDInput type="text" value={form.code} onChange={handleInputChange} name="code" />

                      <MDTypography variant="h6" fontWeight="medium">
                        Team name
                      </MDTypography>
                      <MDInput type="text" value={form.name} onChange={handleInputChange} name="name" />

                      <MDTypography variant="h6" fontWeight="medium">
                        Description
                      </MDTypography>
                      <MDInput type="text" value={form.description} onChange={handleInputChange} name="description" />

                      <Box sx={{ flexGrow: 1 }}>
                        <TextField
                          inputRef={tagRef}
                          fullWidth
                          variant="standard"
                          size="small"
                          sx={{ margin: "1rem 0" }}
                          margin="none"
                          placeholder={form.tags.length < 5 ? "Enter tags" : ""}
                          InputProps={{
                            startAdornment: (
                              <Box sx={{ margin: "0 0.2rem 0 0", display: "flex" }}>
                                {form.tags.map((data, index) => {
                                  return <Tags data={data} handleDelete={handleDelete} key={index} />;
                                })}
                              </Box>
                            )
                          }}
                          onKeyDown={handleKeyDown}
                        />
                      </Box>
                      <MDButton variant="gradient" color="dark" onClick={handleOnSubmitTag}>
                        Add Tag
                      </MDButton>
                    </Grid>
                    <Grid item xs={12} sm={6}>
                      <MDTypography variant="h6" fontWeight="medium">
                        New Member
                      </MDTypography>
                      <Card>
                      <Autocomplete
                        options={peopleList}
                        getOptionLabel={(people) => people.name + " " + people.surname}
                        value={selectedPersonId ? peopleList.find((person) => person.code === selectedPersonId) : null} 
                        onChange={handleSelectPerson}
                        renderInput={(params) => <TextField {...params} label="Search for a person" />}
                    />
                        {selectedPersonId !== null && (
                            <Card>
                                 <MDTypography variant="h6" fontWeight="medium">
                                    New Member
                                </MDTypography>
                            <p>Name: {selectedPersonId && peopleList.find((person) => person.code === selectedPersonId)?.name + " " + peopleList.find((person) => person.code === selectedPersonId)?.surname}</p>
                            <MDTypography variant = 'h6' fontWeight = 'medium'>Charge</MDTypography>
                                  <FormControl fullWidth>
                                      <InputLabel id="demo-simple-select-label">Select an option</InputLabel>
                                      <Select name="charge" value={charge} onChange={(e) => setCharge(e.target.value)}>
                                        <MenuItem value="HEAD">Head</MenuItem>
                                        <MenuItem value="DIRECTOR">Director</MenuItem>
                                        <MenuItem value="UNKNOWN">Unknown</MenuItem>
                                      </Select>
                                  </FormControl>
                            <MDButton variant="gradient" color="dark" onClick={handleSubmitPerson}>
                              Submit
                            </MDButton>
                          </Card>
                        )}
                      </Card>
                    </Grid>
                    <Grid item xs={12} sm={6}>
                      <Card>
                        <h3>Members List:</h3>
                        <ul>
                          {form.members.map((member, index) => (
                            <li key={index}>
                              {member.people.name} - {member.charge}
                            </li>
                          ))}
                        </ul>
                      </Card>
                    </Grid>
                  </Grid>
                  <Grid item xs={12}>
                    <MDButton variant="gradient" color="dark" type="submit" onClick={handleSubmit}>
                      Submit
                    </MDButton>
                  </Grid>
                </MDBox>
              </form>
              <MDBox>
                <VisGraph graph={graph} options={options} events={events} getNetwork={(network) => {}} />
              </MDBox>
            </Card>
          </Grid>
        </Grid>
      </MDBox>
      <Footer />
    </DashboardLayout>
    );
};

export default CreateTeam;