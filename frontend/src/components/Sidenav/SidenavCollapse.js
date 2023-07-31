/**
=========================================================
* Material Dashboard 2 React - v2.2.0
=========================================================

* Product Page: https://www.creative-tim.com/product/material-dashboard-react
* Copyright 2023 Creative Tim (https://www.creative-tim.com)

Coded by www.creative-tim.com

 =========================================================

* The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
*/

// prop-types is a library for typechecking of props.
import Collapse from '@mui/material/Collapse';
import Icon from '@mui/material/Icon';
// @mui material components
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
// Material Dashboard 2 React components
import MDBox from '../../components/MDBox';
// Custom styles for the SidenavCollapse
import {collapseIcon, collapseIconBox, collapseItem, collapseText,} from './styles/sidenavCollapse';
// Material Dashboard 2 React context
import {useMaterialUIController} from '../../context';
import PropTypes from 'prop-types';
import {NavLink} from 'react-router-dom';
import { List } from '@mui/material';
import React from 'react';
function SidenavCollapse({icon, name, active, children, ...rest}) {
  const [controller] = useMaterialUIController();
  const {
    miniSidenav,
    transparentSidenav,
    whiteSidenav,
    darkMode,
    sidenavColor
  } = controller;

  return (
    <ListItem component='li'>
      <MDBox
        {...rest}
        sx={(theme) =>
          collapseItem(theme, {
            active,
            transparentSidenav,
            whiteSidenav,
            darkMode,
            sidenavColor,
          })
        }
      >
        <ListItemIcon
            sx = {(theme) => collapseIconBox(
            theme, {transparentSidenav, whiteSidenav, darkMode, active})} > {typeof icon === 'string' ? (
            <Icon sx={(theme) => collapseIcon(theme, { active })}>{icon}</Icon>
          ) : (
            icon
          )}
        </ListItemIcon>

        <ListItemText
          primary={name}
          sx={(theme) =>
            collapseText(theme, {
              miniSidenav,
              transparentSidenav,
              whiteSidenav,
              active,
            })
          }
        />
         <ListItem component='li' >
          <List>
            {children.map(item => 
                <ListItem component='li' >
                    <NavLink key={item.key} to={item.route}>
                    <MDBox
                        {...rest}
                        sx={(theme) =>
                          collapseItem(theme, {
                            active,
                            transparentSidenav,
                            whiteSidenav,
                            darkMode,
                            sidenavColor,
                          })
                        }
                      >
                      <ListItemText primary={item.name} sx={(theme) =>
                        collapseText(theme, {
                          miniSidenav,
                          transparentSidenav,
                          whiteSidenav,
                          active,
                        })}></ListItemText>
                    </MDBox>
                  </NavLink>
                </ListItem>
)
            }
          </List>
        </ListItem>

      </MDBox>
    </ListItem>
  );
}

// Setting default values for the props of SidenavCollapse
SidenavCollapse.defaultProps = {
  active: false,
};

// Typechecking props for the SidenavCollapse
SidenavCollapse.propTypes = {
  icon: PropTypes.node.isRequired,
  name: PropTypes.string.isRequired,
  active: PropTypes.bool,
};

export default SidenavCollapse;
