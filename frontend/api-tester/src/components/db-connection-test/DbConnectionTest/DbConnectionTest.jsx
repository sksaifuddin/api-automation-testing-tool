import React from "react";
import Box from "@mui/material/Box";
import { Grid, TextField} from "@mui/material";
import LoadingButton from '@mui/lab/LoadingButton';
import SendIcon from '@mui/icons-material/Send';
const DbConnectionTest = (props) => {
    return <>
        <Box sx={{width: 300, margin: 2}}>

            <Grid container spacing={2}>
                <Grid item xs={12}>
                    Test Connection
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        fullWidth
                        required
                        label="User Name"
                    />
                </Grid>

                <Grid item xs={12}>
                    <TextField
                        fullWidth
                        required
                        label="Password"
                        type="password"
                        autoComplete="current-password"
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        fullWidth
                        required
                        label="DB URL"
                    />
                </Grid>
                <Grid item xs={12}>
                    <LoadingButton
                        size="large"
                        endIcon={<SendIcon />}
                        loading={true}
                        loadingPosition="end"
                        variant="contained"
                    >
                        <span>Test</span>
                    </LoadingButton>
                </Grid>
            </Grid>
        </Box>
    </>;
}

export default DbConnectionTest;
