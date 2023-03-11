import React from "react";
import Header from "../Header/Header";
import Main from "../main/Main";
import Sidebar from "../Sidebar/Sidebar";
// import styles from './Layout.style.scss';
import Box from '@mui/material/Box';
import {Grid} from "@mui/material";

const Layout = (props) => {
    return <>
        <Box>
            <Grid container>
                <Grid item xs={12}>
                    <Header></Header>
                </Grid>
            </Grid>
            <Grid container>
                <Grid item xs={3}>
                    <Sidebar></Sidebar>
                </Grid>
                <Grid item xs={8}>
                    <Main></Main>
                </Grid>
            </Grid>

        </Box>
    </>;
}

export default Layout;
