import Icon from '@mui/material/Icon';

import CreateClient from './client/CreateClient'
import FindClient from './client/FindClient'
import UpdateClient from './client/UpdateClient'

import CreatePerson from './person/CreatePerson'
import FindPerson from './person/FindPerson'
import UpdatePerson from './person/UpdatePerson'
import UpdatePersonForm from './person/UpdatePersonForm'

import CreatePosition from './position/CreatePosition'
import FindPosition from './position/FindPosition'

import CreateProject from './project/CreateProject'
import FindProject from './project/FindProject'

import FindSkill from './skill/FindSkill'

import SkillList from './skill/SkillList'

import CreateTeam from './team/CreateTeam'
import FindTeam from './team/FindTeam'

const routes = [
  {
    type: 'title',
    name: 'Person',
    key: 'person',
    icon: <Icon fontSize='small'>person</Icon>,
    child: [
      {
        type: 'collapse',
        name: 'Find Person',
        key: 'findPerson',
        icon: <Icon fontSize='small'>dashboard</Icon>,
        route: '/findPerson',
        component: <FindPerson />,
      },
      {
        type: 'collapse',
        name: 'Create Person',
        key: 'createPerson',
        icon: <Icon fontSize='small'>assignment</Icon>,
        route: '/createPerson',
        component: <CreatePerson />,
      },
      {
        type: 'collapse',
        name: 'Update Person',
        key: 'updatePerson',
        icon: <Icon fontSize='small'>assignment</Icon>,
        route: '/updatePerson',
        component: <UpdatePerson />,
      },
      {
        type: 'hidden',
        name: 'Update Person Form',
        key: 'updatePersonForm',
        icon: <Icon fontSize='small'>assignment</Icon>,
        route: '/updatePersonForm/:code',
        component: <UpdatePersonForm />,
      }
    ]
  },
  {
    type: 'title',
    name: 'Client',
    key: 'client',
    icon: <Icon fontSize='small'>handshake</Icon>,
    child: [{
      type: 'collapse',
      name: 'Create Client',
      key: 'createClient',
      icon: <Icon fontSize='small'>assignment</Icon>,
      route: '/createClient',
      component: <CreateClient />,
      child: []
    },
    {
      type: 'collapse',
      name: 'Find Client',
      key: 'findClient',
      icon: <Icon fontSize='small'>table_view</Icon>,
      route: '/findClient',
      component: <FindClient />,
      child: []
    },
    {
      type: 'collapse',
      name: 'Update Client',
      key: 'updateClient',
      icon: <Icon fontSize='small'>table_view</Icon>,
      route: '/updateClient',
      component: <UpdateClient />,
      child: []
    }]
  },
  {
    type: 'title',
    name: 'Project',
    key: 'project',
    icon: <Icon fontSize='small'>precision_manufacturing</Icon>,
    child: [
      {
        type: 'collapse',
        name: 'Find Project',
        key: 'findProject',
        icon: <Icon fontSize='small'>format_textdirection_r_to_l</Icon>,
        route: '/findProject',
        component: <FindProject />,
        child: []
      },
      {
        type: 'collapse',
        name: 'Create Project',
        key: 'createProject',
        icon: <Icon fontSize='small'>assignment</Icon>,
        route: '/createProject',
        component: <CreateProject />,
        child: []
      }]
  },
  {
    type: 'title',
    name: 'Position',
    key: 'position',
    icon: <Icon fontSize='small'>badge</Icon>,
    child: [{
      type: 'collapse',
      name: 'Find Position',
      key: 'findPosition',
      icon: <Icon fontSize='small'>notifications</Icon>,
      route: '/findPosition',
      component: <FindPosition />,
      child: []
    },
    {
      type: 'collapse',
      name: 'Create Position',
      key: 'createPosition',
      icon: <Icon fontSize='small'>assignment</Icon>,
      route: '/createPosition',
      component: <CreatePosition />,
      child: []
    }]
  },
  {
    type: 'title',
    name: 'Team',
    key: 'team',
    icon: <Icon fontSize='small'>euro</Icon>,
    child: [{
      type: 'collapse',
      name: 'Find Team',
      key: 'findTeam',
      icon: <Icon fontSize='small'>person</Icon>,
      route: '/findTeam',
      component: <FindTeam />,
      child: []
    },
    {
      type: 'collapse',
      name: 'Create Team',
      key: 'createTeam',
      icon: <Icon fontSize='small'>person</Icon>,
      route: '/createTeam',
      component: <CreateTeam />,
      child: []
    }]
  },
  {
    type: 'title',
    name: 'Skills',
    key: 'skills',
    icon: <Icon fontSize='small'>self_improvement</Icon>,
    child: [
    {
      type: 'collapse',
      name: 'Find Skill',
      key: 'findSkill',
      icon: <Icon fontSize='small'>receipt_long</Icon>,
      route: '/findSkill',
      component: <FindSkill />,
      child: []
    },{
      type: 'collapse',
      name: 'Skill List',
      key: 'skill-list',
      icon: <Icon fontSize='small'>login</Icon>,
      route: '/skillList',
      component: <SkillList />,
      child: []
    }]
  }
];

    export default routes;