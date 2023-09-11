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
import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Footer from '../components/Footer';
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDButton from '../components/MDButton';
import MDInput from '../components/MDInput';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';

function UpdatePositionForm() {
    const [form, setForm] = useState({
        code: '',
        //closingDate: '',
        //openingDate: '',
        open: '',
        skills: [],
        name: '',
        projectCode: '',
        priority: '',
        mode: '',
        role: '',
        managedBy: '',
        charge: ''
    });

    const [updatedPosition, setUpdatedPosition] = useState({
        code: '',
        //closingDate: '',
        //openingDate: '',
        open: '',
        skills: [],
        name: '',
        projectCode: '',
        priority: '',
        mode: '',
        role: '',
        managedBy: '',
        charge: ''
    });

    const { positionCode } = useParams();

    const navigate = useNavigate();

    const [skillList, setSkillList] = useState([]);
    const [peopleList, setPeopleList] = useState([]);

    const [closingDate, setClosingDate] = useState(new Date());
    const [openingDate, setOpeningDate] = useState(new Date());

    const [searchSkill, setSearchSkill] = useState('');

    const [expandAll, setExpandAll] = useState([]);
    const [expand, setExpand] = useState([]);



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
        const fetchData = async () => {
            try {
                const positionResponse = await fetch(`http://${window.location.hostname}:9080/api/position/${positionCode}`);
                const positionData = await positionResponse.json();

                setForm({
                    code: positionData.code,
                    open: positionData.open,
                    skills: positionData.skills || [],
                    name: positionData.name,
                    projectCode: positionData.projectCode,
                    priority: positionData.priority,
                    mode: positionData.mode,
                    role: positionData.role,
                    managedBy: positionData.managedBy,
                });

                setUpdatedPosition({
                    code: positionData.code,
                    open: positionData.open,
                    skills: positionData.skills || [],
                    name: positionData.name,
                    projectCode: positionData.projectCode,
                    priority: positionData.priority,
                    mode: positionData.mode,
                    role: positionData.role,
                    managedBy: positionData.managedBy,
                });

                /*  if (positionData.managedBy && positionData.managedBy !== "Unknown") {
                      const personResponse = await fetch(`http://${window.location.hostname}:9080/api/person/${positionData.managedBy}`);
                      const personData = await personResponse.json();

                      setForm((prevForm) => ({
                          ...prevForm,
                          managedBy: personData.name,
                      }));

                      setUpdatedPosition((prevUpdatedPosition) => ({
                          ...prevUpdatedPosition,
                          managedBy: personData.name,
                      }));
                  }*/
            } catch (error) {
                console.error('Error al cargar datos:', error);
            }
        };

        fetchData();

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

    }, [positionCode]);

    const getTreeItemsFromData =
        (treeItems, searchValue) => {
            const filteredItems = treeItems.filter((treeItemData) => {
                return treeItemData.name.toLowerCase().includes(
                    searchValue.toLowerCase()) ||
                    getTreeItemsFromData(treeItemData.children, searchValue).length > 0;
            });

            return filteredItems.map(
                (treeItemData) => {
                    const isMatched = treeItemData.name.toLowerCase().includes(searchValue.toLowerCase());

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

    const SkillsList = () => {
        const [editedSkills, setEditedSkills] = useState([...updatedPosition.skills]);

        const handleSkillInputChange = (index, fieldName, value) => {
            const updatedSkills = [...editedSkills];

            const editedSkill = updatedSkills[index];

            if (fieldName === 'minExp') {
                value = Math.max(0, value);
            }

            editedSkill[fieldName] = value;

            setEditedSkills(updatedSkills);
        };

        const handleSaveSkill = () => {
            setUpdatedPosition((prevUpdatedPosition) => ({
                ...prevUpdatedPosition,
                skills: editedSkills,
            }));
            setEditedSkills([]);
        };

        const handleDeleteSkill = (index) => {
            const updatedSkills = editedSkills.filter((_, i) => i !== index);
            setEditedSkills(updatedSkills); // Actualiza el estado con el nuevo array

            setUpdatedPosition((prevUpdatedPosition) => ({
                ...prevUpdatedPosition,
                skills: updatedSkills,
            }));
        };


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
                                {editedSkills?.map((skill, index) => (
                                    <TableRow key={index}>
                                        <TableCell>{skill.skillName}</TableCell>
                                        <TableCell>
                                            <Select
                                                value={skill.levelReq}
                                                onChange={(e) =>
                                                    handleSkillInputChange(index, 'levelReq', e.target.value)
                                                }
                                            >
                                                <MenuItem value="MANDATORY">MANDATORY</MenuItem>
                                                <MenuItem value="NICE_TO_HAVE">NICE TO HAVE</MenuItem>
                                            </Select>
                                        </TableCell>
                                        <TableCell>
                                            <Select
                                                value={skill.minLevel}
                                                onChange={(e) =>
                                                    handleSkillInputChange(index, 'minLevel', e.target.value)
                                                }
                                            >
                                                <MenuItem value="HIGH">HIGH</MenuItem>
                                                <MenuItem value="MEDIUM">MEDIUM</MenuItem>
                                                <MenuItem value="LOW">LOW</MenuItem>
                                            </Select>
                                        </TableCell>
                                        <TableCell>
                                            <MDInput
                                                type="number"
                                                value={skill.minExp}
                                                onChange={(e) =>
                                                    handleSkillInputChange(index, 'minExp', parseInt(e.target.value) || 0)
                                                }
                                            />
                                        </TableCell>
                                        <TableCell>
                                            <IconButton
                                                color='error'
                                                onClick={() => handleDeleteSkill(index)}
                                            >
                                                <Clear />
                                            </IconButton>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    {editedSkills.length > 0 && (
                        <React.Fragment>
                            <MDButton variant='gradient' color='dark' onClick={handleSaveSkill}>Save</MDButton>
                        </React.Fragment>
                    )}
                </MDBox>
            </React.Fragment>
        );
    };

    const updatePosition = () => {
        const requestBody = JSON.stringify(updatedPosition);

        fetch(
            `http://${window.location.hostname}:9080/api/position/${positionCode}`,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                body: requestBody,
            })
            .then(response => response.json())
    };


    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUpdatedPosition({
            ...updatedPosition,
            [name]: value,
        });
    };

    const handleItemClick = (event, item) => {
        event.stopPropagation();

        const newSkill = {
            skillName: item.name,
            levelReq: 'NICE_TO_HAVE',
            minLevel: 'LOW',
            minExp: 0,
        };

        setUpdatedPosition((prevForm) => ({
            ...prevForm,
            skills: [...prevForm.skills, newSkill],
        }));
    };


    const handleSubmit = (event) => {
        event.preventDefault();

        updatePosition();

        navigate(`/listPosition`);

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
            charge: ''
        });
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
                                    <MDTypography variant='h6' color='white'>Update Position</MDTypography>
                                </MDBox>
                                <form id='positionForm' onSubmit={handleSubmit}>
                                    <MDBox pt={3}>
                                        <Grid container spacing={6}>
                                            <Grid item xs={6}>
                                                <MDTypography variant='h6' fontWeight='medium'>Position code</MDTypography>
                                                <MDInput type="text" value={form.code} onChange={handleInputChange} name="positionCode" disabled />
                                                <MDTypography variant='h6' fontWeight='medium'>Project code</MDTypography>
                                                <MDInput type="text" value={form.projectCode} onChange={handleInputChange} name="projectCode" disabled />
                                                <Grid item xs={6}>
                                                    <MDTypography variant='h6' fontWeight='medium'>Manager</MDTypography>
                                                    <Autocomplete
                                                        options={peopleList}
                                                        getOptionLabel={(people) => people.name + " " + people.surname}
                                                        value={peopleList.find((people) => people.code === updatedPosition.managedBy) || null}
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
                                                <MDTypography variant='h6' fontWeight='medium'>Role</MDTypography>
                                                <MDInput type="text" value={updatedPosition.role} onChange={handleInputChange} name="role" />
                                                {/*<MDTypography variant='h6' fontWeight='medium'>Init Date</MDTypography>
                        <DatePicker
                          selected={openingDate}
                          dateFormat="dd-MM-yyyy"
                          onSelect={(date) => setOpeningDate(date)}
                          onChange={(date) => handleInputChange({ target: { name: "openingDate", value: format(date, 'dd-MM-yyyy') } })}
                            />*/}
                                                <MDTypography variant='h6' fontWeight='medium'>Mode</MDTypography>
                                                <FormControl fullWidth>
                                                    <InputLabel>Select an option</InputLabel>
                                                    <Select name='mode' value={updatedPosition.mode} onChange={handleInputChange}
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
                                            </Grid>
                                            <Grid item xs={6}>
                                                <MDTypography variant='h6' fontWeight='medium'>Name</MDTypography>
                                                <MDInput type='text' value={form.name} onChange={handleInputChange} name='name' disabled />
                                                <MDTypography variant='h6' fontWeight='medium'>Priority</MDTypography>
                                                <MDInput type="text" value={updatedPosition.priority} onChange={handleInputChange} name="priority" />
                                                <MDTypography variant='h6' fontWeight='medium'>Mode</MDTypography>
                                                <FormControl fullWidth>
                                                    <InputLabel>Select an option</InputLabel>
                                                    <Select name='charge' value={updatedPosition.charge} onChange={handleInputChange}
                                                        sx={{
                                                            width: 250,
                                                            height: 50,
                                                        }}>
                                                        <MenuItem value='DIRECTOR'>Director</MenuItem>
                                                        <MenuItem value="HEAD">Head</MenuItem>
                                                        <MenuItem value="UNKNOWN">Unknown</MenuItem>
                                                    </Select>
                                                </FormControl>
                                                {/*<MDTypography variant='h6' fontWeight='medium'>End Date</MDTypography>
                        <DatePicker
                          selected={closingDate} dateFormat='dd-MM-yyyy'
                          onSelect={(date) => setClosingDate(date)} onChange=
                          {(date) => handleInputChange({ target: { name: 'closingDate', value: format(date, 'dd-MM-yyyy') } })}
                        />*/}
                                                <MDTypography variant='h6' fontWeight='medium'>Active</MDTypography >
                                                <FormControl fullWidth><InputLabel>Select an option</InputLabel>
                                                    <Select name="active" value={updatedPosition.active} onChange={handleInputChange}
                                                        sx={{
                                                            width: 250,
                                                            height: 50,
                                                        }}>
                                                        <MenuItem value="true">YES</MenuItem>
                                                        <MenuItem value='false'>NO</MenuItem>
                                                    </Select>
                                                </FormControl>
                                            </Grid>
                                        </Grid>
                                        <Grid container spacing={12}>
                                            <Grid item xs={12}>
                                                <MDButton variant="gradient" color="dark"
                                                    onClick={handleExpandClick}> {expand.length === 0 ? 'Expand all' : 'Collapse all'}
                                                </MDButton>
                                                <MDBox>
                                                    < MDInput
                                                        type='text'
                                                        value={searchSkill}
                                                        onChange={(e) => setSearchSkill(e.target.value)}
                                                        placeholder='Search' />
                                                </MDBox>
                                                <DataTreeView />
                                                {updatedPosition.skills && updatedPosition.skills.length > 0 && <SkillsList />}
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
                <Footer />
            </DashboardLayout>
        </React.Fragment>
    );
}

export default UpdatePositionForm;