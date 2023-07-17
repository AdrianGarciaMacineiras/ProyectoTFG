/**
=========================================================
* Material Dashboard 2  React - v2.2.0
=========================================================

* Product Page: https://www.creative-tim.com/product/material-dashboard-react
* Copyright 2023 Creative Tim (https://www.creative-tim.com)

Coded by www.creative-tim.com

 =========================================================

* The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
*/

// @mui material components
import Card from '@mui/material/Card';
import Icon from '@mui/material/Icon';
import {ArcElement, Chart as ChartJS, Legend, Tooltip} from 'chart.js';
// DefaultDoughnutChart configurations
import configs from './configs';
// Material Dashboard 2 React components
import MDBox from '../../../MDBox';
import MDTypography from '../../../MDTypography';

import {useMemo} from 'react';
// react-chartjs-2 components
import {Doughnut} from 'react-chartjs-2';

ChartJS.register(ArcElement, Tooltip, Legend);

function DefaultDoughnutChart({icon, title, description, height, chart}) {
  const {data, options} =
      configs(chart.labels || [], chart.datasets || {}, chart.cutout);

  const renderChart = (
    <MDBox py={2} pr={2} pl={icon.component ? 1 : 2}>
      {
    title || description ?
        (<MDBox display = 'flex' px = {description ? 1 : 0} pt = {
              description ? 1 : 0}>{icon.component && (< MDBox
    width = '4rem'
    height = '4rem'
    bgColor = {icon.color || 'dark'} variant = 'gradient'
    coloredShadow = {icon.color || 'dark'} borderRadius = 'xl'
    display = 'flex'
    justifyContent = 'center'
    alignItems = 'center'
    color = 'white'
              mt={-5}
              mr={2}
            >
              <Icon fontSize='medium'>{icon.component}</Icon>
            </MDBox>
          )}
          <MDBox mt={icon.component ? -2 : 0}>
            {title && <MDTypography variant='h6'>{title}</MDTypography>}
            <MDBox mb={2}>
              <MDTypography component="div" variant="button" color="text">
                {description}
              </MDTypography>
            </MDBox>
          </MDBox>
        </MDBox>
      ) : null}
      {useMemo(
        () => (
          <MDBox height={height}>
            <Doughnut data={data} options={options} redraw />
          </MDBox>
        ),
        [chart, height]
      )}
    </MDBox>
  );

      return title || description ?
          <Card>{renderChart} < /Card> : renderChart;
}

/ /
              Setting default values for the props of DefaultDoughnutChart
      DefaultDoughnutChart.defaultProps = {
        icon: {color: 'info', component: ''},
        title: '',
        description: '',
        height: '19.125rem',
      };

      // Typechecking props for the DefaultDoughnutChart
      DefaultDoughnutChart.propTypes = {
        icon: PropTypes.shape({
          color: PropTypes.oneOf([
            'primary',
            'secondary',
            'info',
            'success',
            'warning',
            'error',
            'light',
            'dark',
          ]),
          component: PropTypes.node,
        }),
        title: PropTypes.string,
        description: PropTypes.oneOfType([PropTypes.string, PropTypes.node]),
        height: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
        chart: PropTypes
                   .objectOf(
                       PropTypes.oneOfType([PropTypes.array, PropTypes.object]))
                   .isRequired,
      };

      export default DefaultDoughnutChart;
