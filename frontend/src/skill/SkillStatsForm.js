import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import MDInput from '../components/MDInput';
import MDButton from '../components/MDButton';
import {useState} from 'react';
import Sunburst from './Sunburst';


const SkillStatsForm = () => {
    const [form, setForm] = useState({ title: '' });
    const [data, setData] = useState('');

    const handleTitle = (event) => {
        const newValue = event.target.value.toUpperCase(); 
        setForm({
            ...form,
            [event.target.id]: newValue,
        });
    };

    const skillStat = (title) => {

        const recursive = (dataList) => {
            let list = [];
            if (!!dataList) {
                dataList.forEach((element) => {
                    if (element.subSkills.length === 0) {
                        list.push({
                            name: element.name,
                            value: element.total
                        });
                    } else {
                        list.push({
                            name: element.name,
                            children: recursive(element.subSkills)
                        });
                    }
                });
            }
            return list;
        }

        fetch(`http://${window.location.hostname}:9080/api/skills/stats/${title}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        })
            .then(response => { return response.json(); })
            .then(response => {
                const aux = {
                    name: response.name,
                    children: recursive(response.subSkills)
                };
                setData(aux);
            });
    };

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
                    {data && (
                        <>
                            <Grid item xs={12}>
                                <Card>
                                    < MDBox
                                        mx={2} mt={-3} py={3} px={2} variant='gradient'
                                        bgColor='info'
                                        borderRadius='lg'
                                        coloredShadow='info' >
                                        <MDTypography variant='h6' color='white'>
                                            Skills Graph
                                        </MDTypography>
                                    </MDBox>
                                    <MDBox pt={3}>
                                        <Sunburst data={data} />
                                    </MDBox>
                                </Card>
                            </Grid>
                        </>
                    )}
                </Grid>
            </MDBox>
        </DashboardLayout>
    );
};
export default SkillStatsForm;