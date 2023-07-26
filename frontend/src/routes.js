import FindPerson from './person/FindPerson'
import CreatePerson from './person/CreatePerson'
import FindClient from './client/FindClient'
import CreateClient from './client/CreateClient'
import FindSkill from './skill/FindSkill'
import SkillList from './skill/SkillList'
import FindProject from './project/FindProject'
import CreateProject from './project/CreateProject'
import FindPosition from './position/FindPosition'
import CreatePosition from './position/CreatePosition'
import FindTeam from './team/FindTeam'
import CreateTeam from './team/CreateTeam'
import Icon from "@mui/material/Icon";

const routes = [
  {
    type: "collapse",
    name: "Find Person",
    key: "findPerson",
    icon: <Icon fontSize="small">dashboard</Icon>,
    route: "/findPerson",
    component: <FindPerson />,
  },
  {
    type: "collapse",
    name: "Create Person",
    key: "createPerson",
    icon: <Icon fontSize="small">assignment</Icon>,
    route: "/createPerson",
    component: <CreatePerson />,
  },
  {
    type: "collapse",
    name: "Find Client",
    key: "findClient",
    icon: <Icon fontSize="small">table_view</Icon>,
    route: "/findClient",
    component: <FindClient />,
  },
  {
    type: "collapse",
    name: "Create Client",
    key: "createClient",
    icon: <Icon fontSize="small">table_view</Icon>,
    route: "/createClient",
    component: <CreateClient />,
  },
  {
    type: "collapse",
    name: "Find Skill",
    key: "findSkill",
    icon: <Icon fontSize="small">receipt_long</Icon>,
    route: "/findSkill",
    component: <FindSkill />,
  },
  {
    type: "collapse",
    name: "Find Project",
    key: "findProject",
    icon: <Icon fontSize="small">format_textdirection_r_to_l</Icon>,
    route: "/findProject",
    component: <FindProject />,
  },
  {
    type: "collapse",
    name: "Create Project",
    key: "createProject",
    icon: <Icon fontSize="small">assignment</Icon>,
    route: "/createProject",
    component: <CreateProject />,
  },
  {
    type: "collapse",
    name: "Find Position",
    key: "findPosition",
    icon: <Icon fontSize="small">notifications</Icon>,
    route: "/findPosition",
    component: <FindPosition />,
  },
  {
    type: "collapse",
    name: "Create Position",
    key: "createPosition",
    icon: <Icon fontSize="small">assignment</Icon>,
    route: "/createPosition",
    component: <CreatePosition />,
  },
  {
    type: "collapse",
    name: "Find Team",
    key: "findTeam",
    icon: <Icon fontSize="small">person</Icon>,
    route: "/findTeam",
    component: <FindTeam />,
  },
  {
    type: "collapse",
    name: "Create Team",
    key: "createTeam",
    icon: <Icon fontSize="small">person</Icon>,
    route: "/createTeam",
    component: <CreateTeam />,
  },
  {
    type: "collapse",
    name: "Skill List",
    key: "skill-list",
    icon: <Icon fontSize="small">login</Icon>,
    route: "/skillList",
    component: <SkillList />,
  },
];

export default routes;