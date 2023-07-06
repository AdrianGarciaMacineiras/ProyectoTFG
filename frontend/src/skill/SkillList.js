import { useState } from "react";
import TreeView from '@mui/lab/TreeView';
import TreeItem from "@mui/lab/TreeItem";
import Checkbox from '@mui/material/Checkbox';
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import Mock from "./Mock.json";

function SkillList() {

  const [skillList, setSkillList] = useState([]);

  const [selected, setSelected] = useState([]);

  const auxList = [];

  const recursive = (dataList) => {
    var list = [];
    dataList.forEach(data => {
      list.push({nodeId:data.code, name:data.name, children:recursive(data.subSkills)})
    });
    return list;
  }
  
  const FindSkills = () =>
	    fetch(`http://localhost:9080/skills`, {method: "GET", headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*"
          }})
          .then(response => {return response.json()})
          .then(response => {
            setSkillList(recursive(response));
          });

  const getTreeItemsFromData = treeItems => {
    return treeItems.map(treeItemData => {
      let children = undefined;
      if (treeItemData.children && treeItemData.children.length > 0) {
        children = getTreeItemsFromData(treeItemData.children);
    }

    const handleChange = (event, nodeId) => {
      //console.log("Evento", event)
      //console.log("Id", nodeId)

      const foundElement = treeItems.find(element => element.nodeId === nodeId)
      if(foundElement.active){
        foundElement.active = false
        const index = auxList.indexOf(foundElement.name)
        
      }else{
        foundElement.active = true
        auxList.push(foundElement.name)
        console.log("AuxList añadido", auxList)
      }
      setSelected(selected.concat(auxList))
      console.log("Selected", selected)
    };

      return (
        <TreeItem
          key={treeItemData.nodeId}
          nodeId={treeItemData.nodeId}
          label={
            <>
            <Checkbox
              checked = {treeItemData.active}
              onChange= {(event)=>handleChange(event,treeItemData.nodeId)}
              onClick={e => (e.stopPropagation())}
            />
            {treeItemData.name}
            </>
          }
          children={children}
        />
      );
    });
  };
      
  const DataTreeView = ({ treeItems }) => {
    //console.log(treeItems)
    return (
      <TreeView
        defaultCollapseIcon={<ExpandMoreIcon />}
        defaultExpandIcon={<ChevronRightIcon />}
      >
      {getTreeItemsFromData(treeItems)}
      </TreeView>
    );
  };
       
  const handleClick = () => {
    FindSkills();
  };

  return (
    <div>
      <button onClick={handleClick} type="submit">Submit</button>
      <DataTreeView  treeItems={skillList} />
    </div>
  );
}

export default SkillList;

