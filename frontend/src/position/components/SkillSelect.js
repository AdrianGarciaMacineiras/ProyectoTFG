import React from 'react';
import {
  Grid,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
} from '@mui/material';
import MDButton from '../../components/MDButton';
import MDBox from '../../components/MDBox';
import MDInput from '../../components/MDInput';
import MDTypography from '../../components/MDTypography';

function SkillSelect({
  expand,
  handleExpandClick,
  searchSkill,
  setSearchSkill,
  DataTreeView,
  selectedItem,
  setLevelReq,
  setMinLevel,
  setMinExp,
  levelReq,
  minLevel,
  minExp,
  handleModalSubmit,
  setSelectedItem
}) {
  return (
    <Grid container spacing={12}>
      <Grid item xs={5}>
        <MDButton variant="gradient" color="dark" onClick={handleExpandClick}>
          {expand.length === 0 ? 'Expand all' : 'Collapse all'}
        </MDButton>
        <MDBox>
          <MDInput
            type='text'
            value={searchSkill}
            onChange={(e) => setSearchSkill(e.target.value)}
            placeholder='Search'
          />
        </MDBox>
        <DataTreeView />
      </Grid>
      <Grid item xs={7}>
        {selectedItem && (
          <MDBox>
            <h2>Selected Item: {selectedItem.name}</h2>
            <MDTypography variant='h6' fontWeight='medium'>
                Level Required
              </MDTypography>
            <FormControl fullWidth>
            <InputLabel id="Minimum-select">Level Required</InputLabel>
              <Select
                value={levelReq}
                onChange={(e) => setLevelReq(e.target.value)}
                sx={{
                  width: 250,
                  height: 50,
                }}
              >
                <MenuItem value='MANDATORY'>MANDATORY</MenuItem>
                <MenuItem value="NICE_TO_HAVE">NICE TO HAVE</MenuItem>
              </Select>
            </FormControl>
            <MDTypography variant='h6' fontWeight='medium'>
                Minimum Required
              </MDTypography>
            <FormControl fullWidth>
              <InputLabel id="Minimum-select">Minimum Required</InputLabel>
              <Select
                value={minLevel}
                onChange={(e) => setMinLevel(e.target.value)}
                sx={{
                  width: 250,
                  height: 50,
                }}
              >
                <MenuItem value='HIGH'>HIGH</MenuItem>
                <MenuItem value="MEDIUM">MEDIUM</MenuItem>
                <MenuItem value='LOW'>LOW</MenuItem>
              </Select>
            </FormControl>
            <FormControl fullWidth>
              <MDTypography variant='h6' fontWeight='medium'>
                Minimum Experience
              </MDTypography>
              <MDInput
                type="text"
                value={minExp}
                onChange={(e) => {
                  const value = parseInt(e.target.value);
                  if (!isNaN(value)) {
                    setMinExp(value);
                  } else {
                    setMinExp('');
                  }
                }}
                sx={{
                  width: 250,
                  height: 50,
                }}
              />
            </FormControl>
            <MDButton onClick={(event) => handleModalSubmit(event)}>
              Save
            </MDButton>
            <MDButton onClick={() => setSelectedItem(null)}>Cancel</MDButton>
          </MDBox>
        )}
      </Grid>
    </Grid>
  );
}

export default SkillSelect;
