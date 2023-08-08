import '../network.css';

import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from 'react-router-dom';

import Footer from '../components/Footer';
import DashboardLayout from '../components/LayoutContainers/DashboardLayout';
import MDBox from '../components/MDBox';
import MDButton from '../components/MDButton';
import MDInput from '../components/MDInput';
import MDTypography from '../components/MDTypography';
import DashboardNavbar from '../components/Navbars/DashboardNavbar';


const UpdateClientForm = () => {
    const { name } = useParams();

    const [clientData, setClientData] = useState({
        code: '',
        name: '',
        industry: '',
        country: ''
    });

    const [updatedClientData, setUpdatedClientData] = useState(null);

    const navigate = useNavigate();

    useEffect(() => {
        fetch(`http://${window.location.hostname}:9080/client/${name}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
            },
        })
            .then((response) => response.json())
            .then((data) => {
                setClientData({
                    code: data.code,
                    name: data.name,
                    industry: data.industry,
                    country: data.country,
                });
                setUpdatedClientData({
                    industry: data.industry,
                    country: data.country,
                });
            });

    }, [name]);

    const handleChange = (event) => {
        const { name, value } = event.target;
        setUpdatedClientData((prevClientData) => ({
            ...prevClientData,
            code: clientData.code,
            name: clientData.name,
            [name]: value,
        }));
    };

    const handleSubmit = (event) => {

        event.preventDefault();

        fetch(`http://${window.location.hostname}:9080/client/${clientData.name}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
            },
            body: JSON.stringify(updatedClientData),
        })
            .then((response) => response.json())

        navigate(`/listClients`);

    };

    return (
        <DashboardLayout>
            <DashboardNavbar />
            <MDBox pt={6} pb={3}>
                <Grid container spacing={6}>
                    <Grid item xs={12}>
                        {(!clientData.name ? (<Card><MDBox
                                mx={2} mt={-3} py={3} px={2} variant='gradient'
                                bgColor='info'
                                borderRadius='lg'
                                coloredShadow=
                                'info' > <MDTypography variant='h6' color='white'>Loading...</MDTypography>
                            </MDBox></Card>):(
                        <Card>
                            <MDBox
                                mx={2} mt={-3} py={3} px={2} variant='gradient'
                                bgColor='info'
                                borderRadius='lg'
                                coloredShadow=
                                'info' > <MDTypography variant='h6' color='white'>Update Client</MDTypography>
                            </MDBox>
                            <form onSubmit={handleSubmit}>
                                <MDBox pt={3}>
                                    <MDTypography variant='h6' fontWeight='medium'>Code:</MDTypography>
                                    <MDInput
                                        type="text"
                                        id="code"
                                        name="code"
                                        value={clientData.code}
                                        onChange={handleChange}
                                        disabled
                                    />
                                </MDBox>
                                <MDBox>
                                    <MDTypography htmlFor="name">Name:</MDTypography>
                                    <MDInput
                                        type='text'
                                        id='name'
                                        name='name'
                                        value={clientData.name}
                                        onChange={handleChange}
                                        disabled
                                    />
                                </MDBox>
                                <MDBox>
                                    <MDTypography htmlFor="industry">Industry:</MDTypography>
                                    <MDInput
                                        type='text'
                                        id='industry'
                                        name='industry'
                                        value={updatedClientData.industry}
                                        onChange={handleChange}
                                    />
                                </MDBox>
                                <MDBox>
                                    <MDTypography htmlFor='country'>Country:</MDTypography>
                                    <MDInput
                                        type="text"
                                        id="country"
                                        name="country"
                                        value={updatedClientData.country}
                                        onChange={handleChange}
                                    />
                                </MDBox>
                                <MDButton variant="gradient" color="dark" onClick={handleSubmit}>Submit</MDButton>
                            </form>
                        </Card>
                        ))}
                    </Grid>
                </Grid>
            </MDBox>
            <Footer />
        </DashboardLayout>
    )

}

export default UpdateClientForm;