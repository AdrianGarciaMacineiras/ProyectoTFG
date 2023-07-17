import React, { useEffect, useState } from "react";
import TreeView from '@mui/lab/TreeView';
import TreeItem from "@mui/lab/TreeItem";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { format } from 'date-fns';
import "../network.css";
import VisGraph from 'react-vis-graph-wrapper';
import MDBox from "../components/MDBox";

function CreatePosition() {
  const [form, setForm] = useState({
    positionCode: '',
    charge: '',
    closingDate: '',
    openingDate: '',
    active: '',
    skills: [],
    name: '',
    projectCode: '',
    priority: '',
    mode: '',
    role: '',
    managedBy: ''
  });

  const [skillList, setSkillList] = useState([]);
  const [projectList, setProjectList] = useState([]);

  const [closingDate, setClosingDate] = useState(new Date());
  const [openingDate, setOpeningDate] = useState(new Date());

  const [selectedItem, setSelectedItem] = useState(null);
  const [skill, setSkill] = useState(null);
  const [skillName, setSkillName] = useState(null);
  const [levelReq, setLevelReq] = useState('');
  const [minLevel, setMinLevel] = useState('');
  const [minExp, setMinExp] = useState(0);

  const [searchProjectCode, setSearchProjectCode] = useState('');
  const [searchSkill, setSearchSkill] = useState('');

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
      assigned: {color:{background:'red'}, borderWidth:3},
      candidates: {color:{background:'green'}, borderWidth:3},
      project: {color:{background:'yellow'}, borderWidth:3},
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

  const getTreeItemsFromData = (treeItems, searchValue) => {
    const filteredItems = treeItems.filter((treeItemData) => {
      var isMatched =  treeItemData.name.toLowerCase().includes(searchValue.toLowerCase()) ||
        getTreeItemsFromData(treeItemData.children, searchValue).length > 0;
      
      return isMatched;
    });

    return filteredItems.map((treeItemData) => {
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
          {getTreeItemsFromData(skillList, searchSkill)}
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
            <br/>
            <label>Level Required: {skill.levelReq}</label>
            <br/>
            <label>Minimum Level: {skill.minLevel}</label>
            <br/>
            <label>Minimum Experience: {skill.minExp}</label>
            <br/>
            <button onClick={() => handleRemoveSkill(index)}>Remove</button>
            <hr />
          </MDBox>
        ))}
      </MDBox>
    );
  };

  useEffect(() => {
    const recursive = (dataList) => {
      var list = [];
      dataList.forEach(data => {
        list.push({ nodeId: data.code, name: data.name, children: recursive(data.subSkills) });
      });
      return list;
    };

    fetch(`http://localhost:9080/skills`, {
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
    
  }, []);

  const createPosition = (event) => {
    console.log(JSON.stringify(form,'',2))
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
        var i = 1
        var temp = {Code: response.code, Active: response.active, Role: response.role, EndDate: response.closingDate, InitDate: response.openingDate}
        graphTemp.nodes.push({id:i, label: response.code, title: JSON.stringify(temp,'',2)});

        i++
        graphTemp.nodes.push({id: i, label: response.projectCode, title: JSON.stringify(response.projectCode,'',2), group:"project" });
        graphTemp.edges.push({from: i, to: 1, label: "FOR_PROJECT", title: JSON.stringify(response.projectCode,'',2)});

        response.candidates.forEach(element => {
          i++;
          graphTemp.nodes.push({id:i, label: element.candidateCode, title: element.candidateCode, group:"candidates" });
          var temp ={Code: element.code, Status:element.status, IntroductionDate:element.introductionDate, ResolutionDate:element.resolutionDate, CreationDate:element.creationDate}
          graphTemp.edges.push({from: 1, to: i, label: "CANDIDATE", title: JSON.stringify(temp,'',2)});
        })

        console.log(graphTemp);
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

    setForm({
      positionCode: '',
      charge: '',
      closingDate: '',
      openingDate: '',
      active: '',
      skills: [],
      name: '',
      projectCode: '',
      priority: '',
      mode: '',
      role: '',
      managedBy: ''
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
    <div>
        <form id="positionForm" onSubmit={handleSubmit}>
        <label>
          Position Code:
          <input type="text" value={form.positionCode} onChange={handleInputChange} name="positionCode" />
        </label>
        <br />
        <label>
          Name:
          <input type="text" value={form.name} onChange={handleInputChange} name="name" />
        </label>
        <br />
        <label>
          Project Code:
          <select name="projectCode" value={form.projectCode} onChange={handleInputChange}>
            <option value="">Select a project name</option>
              {projectList.filter((project) => project.name.toLowerCase().includes(searchProjectCode.toLowerCase())).map((project) => (
                <option key={project.code} value={project.code}>
                {project.name}
                </option>
              ))}
          </select>
          <input type="text" value={searchProjectCode} onChange={(e) => setSearchProjectCode(e.target.value)} placeholder="Search project code"/>
        </label>
        <br />
        <label>
          Charge:
          <select name="charge" value={form.charge} onChange={handleInputChange}>
            <option value="">Select an option</option>
            <option value="director">Director</option>
            <option value="head">Head</option>
            <option value="unknown">Unknown</option>
          </select>
        </label>
        <br />
        <label>
          End Date:
          <DatePicker
            selected={closingDate}
            dateFormat="dd-MM-yyyy"
            onSelect={(date) => setClosingDate(date)}
            onChange={(date) => handleInputChange({ target: { name: "closingDate", value: format(date, 'dd-MM-yyyy') } })}
          />
        </label>
        <br/>
        <label>
          Init Date:
          <DatePicker
            selected={openingDate}
            dateFormat="dd-MM-yyyy"
            onSelect={(date) => setOpeningDate(date)}
            onChange={(date) => handleInputChange({ target: { name: "openingDate", value: format(date, 'dd-MM-yyyy') } })}
          />
        </label>
        <br />
        <label>
          Priority:
          <input type="text" value={form.priority} onChange={handleInputChange} name="priority" />
        </label>
        <br />
        <label>
          Mode:
          <select name="mode" value={form.mode} onChange={handleInputChange}>
            <option value="">Select an option</option>
            <option value="REMOTE">Remote</option>
            <option value="PRESENTIAL">Presential</option>
            <option value="MIX">Mix</option>
            <option value="UNKNOWN">Unknown</option>
          </select>
        </label>
        <br />
        <label>
          Role:
          <input type="text" value={form.role} onChange={handleInputChange} name="role" />
        </label>
        <br />
        <label>
          Active:
          <select name="active" value={form.active} onChange={handleInputChange}>
            <option value="">Select an option</option>
            <option value="true">YES</option>
            <option value="false">NO</option>
          </select>
        </label>
        <br />

        {selectedItem && (
          <div>
            <h2>Selected Item: {selectedItem.name}</h2>
            <label>
              Level Required:
              <select value={levelReq} onChange={(e) => setLevelReq(e.target.value)}>
                <option value="">Select an option</option>
                <option value="MANDATORY">MANDATORY</option>
                <option value="NICE_TO_HAVE">NICE TO HAVE</option>
              </select>
            </label>
            <br />
            <label>
              Minimum Level:
              <select value={minLevel} onChange={(e) => setMinLevel(e.target.value)}>
                <option value="">Select an option</option>
                <option value="HIGH">HIGH</option>
                <option value="MEDIUM">MEDIUM</option>
                <option value="LOW">LOW</option>
              </select>
            </label>
            <br />
            <label>
              Minimum Experience:
              <input type="number" value={minExp} onChange={(e) => setMinExp(parseInt(e.target.value))}/>
            </label>
            <br />
            <button onClick={handleModalSubmit}>Save</button>
            <button onClick={() => setSelectedItem(null)}>Cancel</button>
          </div>
        )}

        <button onClick={collapseAll}> Collapse all </button>
        <br/>
        <input
          type="text"
          value={searchSkill}
          onChange={(e) => setSearchSkill(e.target.value)}
          placeholder="Search"
        />
        <DataTreeView/>
        <SkillsList />
        <br/>
        <button type="submit" form="positionForm">Submit</button>
      </form>
      <VisGraph
            graph={graph}
            options={options}
            events={events}
            getNetwork={network => {
              //  if you want access to vis.js network api you can set the state in a parent component using this property
            }}
      /> 
    </div>
  );
}

export default CreatePosition;
