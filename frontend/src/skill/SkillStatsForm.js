import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import MDInput from '../components/MDInput';
import MDButton from '../components/MDButton';
import { useState } from 'react';
import Sunburn from './Sunburn';


const SkillStatsForm = () => {
    const [form, setForm] = useState({ title: '' });
    const [data, setData] = useState([]);

    const handleTitle = (event) => {
        setForm({
            ...form,
            [event.target.id]: event.target.value,
        });
    };

    const skillStat = (title) =>
        fetch(`http://${window.location.hostname}:9080/api/skills/stats/${title}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        })
            .then(response => { return response.json(); })
            .then(response => {
                setData(response);
            });

    const handleSubmit = (event) => {
        event.preventDefault();

        skillStat(form.title);

        setForm({ title: '' });
    }

    return (
        <DashboardLayout>
            <DashboardNavbar />
            <MDBox pt={6} pb={3}>
                <Grid container spacing={6}>
                    <Grid item xs={12}>
                        <Card>
                            <MDBox
                                mx={2} mt={-3} py={3} px={2} variant='gradient'
                                bgColor='info'
                                borderRadius='lg'
                                coloredShadow='info' >
                                <MDTypography variant='h6' color='white'>
                                    Skill Stats
                                </MDTypography>
                            </MDBox>
                            <MDBox pt={3}>
                                <form onSubmit={handleSubmit}><MDBox>
                                    <MDTypography variant='h6' fontWeight='medium'>
                                        Title
                                    </MDTypography>
                                    <MDInput
                                        id="title"
                                        type="text"
                                        value={form.title}
                                        onChange={handleTitle} />
                                </MDBox>
                                    <MDButton variant="gradient" color="dark" onClick={handleSubmit}>Submit</MDButton>
                                </form>
                            </MDBox>
                        </Card>
                    </Grid>
                    {data &&
                        <Grid item xs={12}>
                            <Sunburn data={data} />
                        </Grid>
                    }
                </Grid>
            </MDBox>
        </DashboardLayout>
    );
};
export default SkillStatsForm;