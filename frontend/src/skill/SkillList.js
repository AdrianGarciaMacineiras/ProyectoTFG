import { useState } from "react";
import TreeView from '@mui/lab/TreeView';
import TreeItem from "@mui/lab/TreeItem";
import Checkbox from '@mui/material/Checkbox';
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";

function SkillList() {

  const [skillList, setSkillList] = useState([]);

  const [check, setCheck] = useState([]);

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

    const handleChange = (event) => {
      setCheck(!check);
    };

      return (
        <TreeItem
          key={treeItemData.nodeId}
          nodeId={treeItemData.nodeId}
          label={
            <>
            <Checkbox
              checked={check}
              onChange= {(event) =>
                handleChange(event.currentTarget.checked, treeItemData)}
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
    return (
      <TreeView
        multiSelect
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
    <div className="SkillList">
      <button onClick={handleClick} type="submit">Submit</button>
      <DataTreeView treeItems={skillList} />
    </div>
  );
}

export default SkillList;
