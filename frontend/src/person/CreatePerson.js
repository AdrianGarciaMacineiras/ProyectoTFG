import React, { useEffect, useState } from "react";
import TreeView from '@mui/lab/TreeView';
import TreeItem from "@mui/lab/TreeItem";
import Checkbox from "@mui/material/Checkbox";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { format } from 'date-fns';
import "../network.css";
import MDBox from "../components/MDBox";
import VisGraph from 'react-vis-graph-wrapper';

const CreatePerson = () => {
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
    LevelRequired: "",
    minLevel: "",
    minExp: "",
  });

  const [certificateForm, setCertificateForm] = useState({
    comments: "",
    date: new Date(),
  });

  const [roleForm, setRoleForm] = useState({
    role: "",
    category: "",
    initDate: new Date()
  })

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
      knows: {color:{background:'red'}, borderWidth:3},
      interest: {color:{background:'blue'}, borderWidth:3},
      work_with: {color:{background:'green'}, borderWidth:3},
      master: {color:{background:'orange'}, borderWidth:3},
      have_certificate: {color:{background:'yellow'}, borderWidth:3},
      position: {color:{background:'white'}, borderWidth:3},
      candidate: {color:{background:'pink'}, borderWidth:3},
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

  const handleCheckboxChange = (event, arrayName) => {
    const { checked } = event.target;
  
  if (arrayName === "interest") {
    setTempInterest(checked);
  }
  if (arrayName === "master") {
    setTempMaster(checked);
  }
  if (arrayName === "work_with") {
    setTempWorkWith(checked);
  }
  if (arrayName === "knows") {
    setShowKnowsForm(true);
    setTempKnows(checked);
  }
  if (arrayName === "certificates") {
    setShowCertificateForm(true);
    setTempCertificates(checked);
  }

  };

  const handleCancel = () => {
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
    setForm((prevForm) => ({
      ...prevForm,
      knows: [...prevForm.knows, newKnowsItem],
    }));
    setShowKnowsForm(false);
    setKnowsForm({
      LevelRequired: "MANDATORY",
      minLevel: "HIGH",
      minExp: "",
    });
    setShowKnowsForm(false);
  };

  const handleCertificateFormSubmit = (e) => {
    e.preventDefault();
    const newCertificateItem = {
      code: selectedNode.nodeId,
      comments: certificateForm.comments,
      date: format(certificateForm.date, "dd-MM-yyyy"),
    };
    setForm((prevForm) => ({
      ...prevForm,
      certificates: [...prevForm.certificates, newCertificateItem],
    }));
    setShowCertificateForm(false);
    setCertificateForm({
      comments: "",
      date: new Date(),
    });
    setShowCertificateForm(false);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setForm({
      ...form,
      [name]: value,
    });
  };

  const createPerson = () => {

    const requestBody = JSON.stringify(form);

    fetch("http://localhost:9080/people", {
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
          var temp ={Code: response.code, Name:response.name, Surname:response.surname, Email:response.email, EmployeeId:response.employeeId,
            FriendlyName:response.friendlyName, Title:response.title, BirthDate: response.birthDate}
          graphTemp.nodes.push({id:i, label: response.name + ' ' + response.surname, title: JSON.stringify(temp,'',2)});
          response.knows.forEach(element => {
            i++;
            var temp = {Code:element.code, Primary:element.primary, Experience: element.experience, Level: element.level}
            graphTemp.nodes.push({id:i, label: element.name, title:  JSON.stringify(temp,'',2), group: 'knows', value: element.experience*150});
            graphTemp.edges.push({from:1, to:i, label: "KNOWS"});
          });

          response.interest.forEach(element => {
            i++;
            graphTemp.nodes.push({id:i, label: element, title: element, group: 'interest', value: 10});
            graphTemp.edges.push({from:1, to:i, label: "INTEREST"});
          });

          response.work_with.forEach(element => {
            i++;
            graphTemp.nodes.push({id:i, label: element, title: element, group: 'work_with'});
            graphTemp.edges.push({from:1, to:i, label: "WORK_WITH"});
          });

          response.master.forEach(element => {
            i++;
            graphTemp.nodes.push({id:i, label: element, title: element, group: 'master'});
            graphTemp.edges.push({from:1, to:i, label: "MASTER"});
          });
          response.certificates.forEach(element => {
            i++;
            var temp = {Code:element.code, Name: element.name, Comments: element.comments, Date: element.date}
            graphTemp.nodes.push({id:i, label: element.code, title: JSON.stringify(temp,'',2), group: 'have_certificate'});
            graphTemp.edges.push({from:1, to:i, label: "HAVE_CERTIFICATE"});
          });

         
          
          setGraph(prev => graphTemp);
        });

  }


  const handleSubmit = (event) => {
    event.preventDefault();

    console.log(skillList);
    console.log(form);

    createPerson();

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
      role: "",
      category: "",
      date: new Date(),
    });
  };

  const handleAddRoleSubmit = (e) => {
    e.preventDefault();
    const newRoleItem = {
      role: roleForm.role,
      category: roleForm.category,
      initDate: format(roleForm.initDate, "dd-MM-yyyy"),
    };
    setForm((prevForm) => ({
      ...prevForm,
      roles: [...prevForm.roles, newRoleItem],
    }));
    setRoleForm({
      role: "",
      category: "",
      date: new Date(),
    });

    setIsAddRoleVisible(false);
  };

  const handleRemoveFromArray = (arrayName, nodeId) => {
    setForm((prevForm) => ({
      ...prevForm,
      [arrayName]: prevForm[arrayName].filter((element) => element.nodeId !== nodeId),
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
        list.push({ nodeId: data.code, name: data.name, children: recursive(data.subSkills) });
      });
      return list;
    };

    fetch(`http://localhost:9080/skills`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
    })
      .then((response) => response.json())
      .then((response) => {
        const skillsData = recursive(response);
        setSkillList(skillsData);
      });
  }, []);


  const handleNodeSelect = (event, item) => {
    setSelectedNode(item);
    setShowCheckboxes(true);
    setShowKnowsForm(false);
    setShowCertificateForm(false);
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

  return (
    <>
      <form id="personForm">
        <label>
          Person Code:
          <input type="text" value={form.code} onChange={handleInputChange} name="code" />
        </label>
        <br />
        <label>
          Employee ID:
          <input type="text" value={form.employeeId} onChange={handleInputChange} name="employeeId" />
        </label>
        <br />
        <label>
          Name:
          <input type="text" value={form.name} onChange={handleInputChange} name="name" />
        </label>
        <br />
        <label>
          Surname:
          <input type="text" value={form.surname} onChange={handleInputChange} name="surname" />
        </label>
        <br />
        <label>
          Birth Date:
          <DatePicker
            selected={birthDate}
            dateFormat="dd-MM-yyyy"
            onSelect={(date) => setBirthDate(date)}
            onChange={(date) => handleInputChange({ target: { name: "birthDate", value: format(date, 'dd-MM-yyyy') } })}
          />
        </label>
        <br />
        <label>
          Title:
          <input type="text" value={form.title} onChange={handleInputChange} name="title" />
        </label>
        <br />
        <label>
          Roles:
          {!isAddRoleVisible && (
            <div>
              <button onClick={handleShowAddRoleForm}>Add Role</button>
            </div>
          )}
          {isAddRoleVisible && (
            <div>
              <label>
                Role:
                <input type="text" value={roleForm.role} onChange={(e) => setRoleForm({ ...roleForm, role: e.target.value })} />
              </label>
              <br />
              <label>
                Category:
                <input type="text" value={roleForm.category} onChange={(e) => setRoleForm({ ...roleForm, category: e.target.value })} />
              </label>
              <br />
              <label>
                Init Date:
                <DatePicker
                  selected={roleForm.initDate}
                  dateFormat="dd-MM-yyyy"
                  onSelect={(date) => setRoleForm({ ...roleForm, initDate: date })}
                  onChange={(date) => setRoleForm({ ...roleForm, initDate: date })}
                />
              </label>
              <br />
              <button onClick={(e) => handleAddRoleSubmit(e)}>Save</button>
              <button onClick={handleCancelAddRole}>Cancel</button>
            </div>
          )}
          {form.roles.length > 0 && (
            <div>
              <button onClick={handleShowRoleList}>Show Role List</button>
              {isShowRoleListVisible && (
                <div>
                  {form.roles.map((role, index) => (
                    <div key={index}>
                      <hr />
                      <label>Role: {role.role}</label>
                      <br />
                      <label>Category: {role.category}</label>
                      <br />
                      <label>Init Date: {role.initDate}</label>
                      <br />
                      <button onClick={() => handleRemoveFromArray("roles", index)}>Remove</button>
                      <hr />
                    </div>
                  ))}
                </div>
              )}
            </div>
          )}
        </label>
        <br />
        <button onClick={collapseAll}> Collapse all </button>
        <br/>
        <input
          type="text"
          value={searchSkill}
          onChange={(e) => setSearchSkill(e.target.value)}
          placeholder="Search"
        />
        <DataTreeView/>

        {selectedNode && showCheckboxes && (
        <>
          <h2>Select element: {selectedNode.name}</h2>
          <div>
            <label>
              <Checkbox
                checked={tempInterest}
                onChange={(e) => handleCheckboxChange(e, "interest")}
                value={selectedNode.nodeId}
              />
              Interest
            </label>
          </div>
          <div>
            <label>
              <Checkbox
                checked={tempMaster}
                onChange={(e) => handleCheckboxChange(e, "master")}
                value={selectedNode}
              />
              Master
            </label>
          </div>
          <div>
            <label>
              <Checkbox
                checked={tempWorkWith}
                onChange={(e) => handleCheckboxChange(e, "work_with")}
                value={selectedNode}
              />
              Work With
            </label>
          </div>
          <div>
            <label>
              <Checkbox
                checked={tempKnows}
                onChange={(e) => handleCheckboxChange(e, "knows")}
                value={selectedNode}
              />
              Knows
            </label>
          </div>
          <div>
            <label>
              <Checkbox
                checked={tempCertificates}
                onChange={(e) => handleCheckboxChange(e, "certificates")}
                value={selectedNode}
              />
              Certificate
            </label>
          </div>
          <button onClick={handleAddToArray}>Add</button>
          <button onClick={handleCancel}>Cancel</button>
        </>
        )}

        {selectedNode && showKnowsForm && (
          <div>
          <h2>Knows Form: {selectedNode.name}</h2>
          <label>
            Level Required:
            <select
              value={knowsForm.LevelRequired}
              onChange={(e) => setKnowsForm({ ...knowsForm, LevelRequired: e.target.value })}
            >
              <option value="MANDATORY">Mandatory</option>
              <option value="NICE_TO_HAVE">Nice to have</option>
            </select>
          </label>
          <br />
          <label>
            Min Level:
            <select
              value={knowsForm.minLevel}
              onChange={(e) => setKnowsForm({ ...knowsForm, minLevel: e.target.value })}
            >
              <option value="HIGH">High</option>
              <option value="MEDIUM">Medium</option>
              <option value="LOW">Low</option>
            </select>
          </label>
          <br />
          <label>
            Min Experience:
            <input
              type="number"
              value={knowsForm.minExp}
              onChange={(e) => setKnowsForm({ ...knowsForm, minExp: e.target.value })}
            />
          </label>
          <br />
          <button onClick={handleKnowsFormSubmit}>Add to Knows</button>
          </div>
        )}

        {selectedNode && showCertificateForm && (
          <div>
          <h2>Certificate Form:{selectedNode.name}</h2>
          <label>
            Comments:
            <input
              type="text"
              value={certificateForm.comments}
              onChange={(e) => setCertificateForm({ ...certificateForm, comments: e.target.value })}
            />
          </label>
          <br />
          <label>
            Date:
            <DatePicker
              selected={certificateForm.date}
              dateFormat="dd-MM-yyyy"
              onSelect={(date) => setCertificateForm({ ...certificateForm, date: date })}
              onChange={(date) => setCertificateForm({ ...certificateForm, date: date })}
            />
          </label>
          <br />
          <button onClick={handleCertificateFormSubmit}>Add to Certificate</button>
        </div>
        )}
        <br/>
        <button onClick={handleSubmit}>Submit</button>
      </form>
      <VisGraph
            graph={graph}
            options={options}
            events={events}
            getNetwork={network => {
              //  if you want access to vis.js network api you can set the state in a parent component using this property
            }}
      /> 
    </>
  );
}

export default CreatePerson;
