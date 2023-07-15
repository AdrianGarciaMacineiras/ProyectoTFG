import { useEffect, useState } from "react";
import TreeView from '@mui/lab/TreeView';
import TreeItem from "@mui/lab/TreeItem";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { format } from 'date-fns';


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


  var auxList = new Set();

  const [aux, setAux] = useState([]);

  const getTreeItemsFromData = (treeItems, searchValue) => {
    const filteredItems = treeItems.filter((treeItemData) => {
      var isMatched =  treeItemData.name.toLowerCase().includes(searchValue.toLowerCase()) ||
        getTreeItemsFromData(treeItemData.children, searchValue).length > 0;
      if(isMatched)
        auxList.add(treeItemData.nodeId)
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

  const expandAll = (e) => {
    e.preventDefault();
    setSkillList(
      skillList.map((item) =>
        Object.assign({}, item, {
          expanded: true,
        })
      )
    );
  };


  const DataTreeView = () => {
    return (
      <div>
        <TreeView
          defaultCollapseIcon={<ExpandMoreIcon />}
          defaultExpandIcon={<ChevronRightIcon />}
          //defaultExpanded={[... auxList]}
        >
          {getTreeItemsFromData(skillList, searchSkill)}
        </TreeView>
      </div>
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
      <div>
        {form.skills.map((skill, index) => (
          <div key={index}>
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
          </div>
        ))}
      </div>
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

    console.log(form); // Display the final form data

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
        <button onClick={expandAll}> Expand all </button>
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
    </div>
  );
}

export default CreatePosition;
