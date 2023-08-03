import './App.css';

import CssBaseline from '@mui/material/CssBaseline';
// @mui material components
import {ThemeProvider} from '@mui/material/styles';
import {React, useEffect, useState} from 'react';
// react-router components
import {Navigate, Route, Routes, useLocation} from 'react-router-dom';

// Images
import brandWhite from './assets/images/logo-ct.png';
// Material Dashboard 2 React themes
import theme from './assets/theme';
// Material Dashboard 2 React Dark Mode themes
import Configurator from './components/Configurator';
// Material Dashboard 2 React example components
import Sidenav from './components/Sidenav';
// Material Dashboard 2 React contexts
import {setMiniSidenav, setOpenConfigurator, useMaterialUIController} from './context/index';
// Material Dashboard 2 React routes
import routes from './routes';

function App() {
  const [controller, dispatch] = useMaterialUIController();

  const {
    miniSidenav,
    direction,
    layout,
    openConfigurator,
    sidenavColor
  } = controller;
  const [onMouseEnter, setOnMouseEnter] = useState(false);
  const {pathname} = useLocation();

  // Open sidenav when mouse enter on mini sidenav
  const handleOnMouseEnter = () => {
    if (miniSidenav && !onMouseEnter) {
      setMiniSidenav(dispatch, false);
      setOnMouseEnter(true);
    }
  };

  // Close sidenav when mouse leave mini sidenav
  const handleOnMouseLeave = () => {
    if (onMouseEnter) {
      setMiniSidenav(dispatch, true);
      setOnMouseEnter(false);
    }
  };

  // Setting the dir attribute for the body element
  useEffect(() => {
    document.body.setAttribute('dir', direction);
  }, [direction]);

  // Setting page scroll to 0 when changing the route
  useEffect(() => {
    document.documentElement.scrollTop = 0;
    document.scrollingElement.scrollTop = 0;
  }, [pathname]);

  const getRoutes = (allRoutes) =>
    allRoutes.map((route) => {
      if (route.collapse) {
        return getRoutes(route.collapse);
      }
      if (route.child && route.child.length > 0) {
        return getRoutes(route.child);
      }
      if (route.route) {
        return <Route exact path={route.route} element={route.component} key={route.key} />;
      }

      return null;
    });

  return (
    <ThemeProvider theme={theme}>
         <CssBaseline />
         {layout === 'dashboard' && (
           <>
             <Sidenav
        color = {sidenavColor} brand = {brandWhite} brandName = 'Ubik Service'
               routes={routes}
               onMouseEnter={handleOnMouseEnter}
               onMouseLeave={
        handleOnMouseLeave}
             />
             <Configurator />
           </>
         )}
         {layout === "vr" && <Configurator />}
         <Routes>
           {getRoutes(routes)}
           <Route path='*' element={<Navigate to='/' />} />
         </Routes>
       </ThemeProvider>
  );
}

export default App;
