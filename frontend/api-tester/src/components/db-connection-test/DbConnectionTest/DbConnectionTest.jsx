import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import { Grid, TextField } from "@mui/material";
import LoadingButton from '@mui/lab/LoadingButton';
import SendIcon from '@mui/icons-material/Send';
import Snackbar from '@mui/material/Snackbar';
import { getClientDBCredentials, testClientDBConnection } from "../../../services/DbTestService";
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import Alert from '@mui/material/Alert';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CancelIcon from '@mui/icons-material/Cancel';
// eslint-disable-next-line no-unused-vars
import styles from './DbConnectionTest.scss'
const DbConnectionTest = (props) => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [url, setUrl] = useState("");
    const [btnLoader, setBtnLoader] = useState(false);
    const [openSnackBar, setOpenSnackBar] = useState(false);
    const [testMessage, setTestMessage] = useState('');
    const [snackBarType, setSnackBarType] = useState('success');

    useEffect(() => {
        getClientDBCredentials().then((data) => {
            const databaseDetails = data.data;
            setUrl(databaseDetails.databaseUrl);
            setPassword(databaseDetails.password);
            setUsername(databaseDetails.userName);
        });
        
    }, [])

    async function testConnection() {
        setBtnLoader(true)
        testClientDBConnection(url, username, password).then((data) => {
            setBtnLoader(false);
            setTestMessage(data.message);
            setSnackBarType("success")
            setOpenSnackBar(true);
        }).catch((error) => {
            setBtnLoader(false);
            setSnackBarType("error");
            setTestMessage(error.response.data.message);
            setOpenSnackBar(true);
        });
    }

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        setOpenSnackBar(false);
    };

    const action = (
        <React.Fragment>
            <IconButton
                size="small"
                aria-label="close"
                color="inherit"
                onClick={handleClose}
            >
                <CloseIcon fontSize="small" />
            </IconButton>
        </React.Fragment>
    );

    const getDBResultIcon = () => {
        console.log('test message', testMessage)
        if(!testMessage) return;
        if(snackBarType === 'success') {
            return <CheckCircleIcon color={snackBarType}></CheckCircleIcon>
        } else {
            return <CancelIcon color={snackBarType}></CancelIcon>
        }
    }

    return <>
        <Box sx={{ width: 300, margin: 2 }}>

            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <div className="heading">
                        Test Database Connection
                    </div>
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        fullWidth
                        required
                        value={username}
                        defaultValue={username}
                        label="User Name"
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </Grid>

                <Grid item xs={12}>
                    <TextField
                        fullWidth
                        required
                        label="Password"
                        type="password"
                        value={password}
                        autoComplete="current-password"
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        fullWidth
                        required
                        value={url}
                        label="DB URL"
                        onChange={(e) => setUrl(e.target.value)}
                    />
                </Grid>
                <Grid item xs={5}>
                    <LoadingButton
                        size="large"
                        endIcon={<SendIcon />}
                        loading={btnLoader}
                        loadingPosition="end"
                        variant="contained"
                        onClick={testConnection}
                    >
                        <span>Test</span>
                    </LoadingButton>
                </Grid>
                <Grid item xs={7}>
                    {
                        getDBResultIcon()
                    }
                </Grid>
            </Grid>
        </Box>
        <Snackbar
            anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
            open={openSnackBar}
            autoHideDuration={6000}
            onClose={handleClose}
            action={action}
        >
            <Alert onClose={handleClose} severity={snackBarType} sx={{ width: '100%' }}>
                {testMessage}
            </Alert>
        </Snackbar>
    </>;
}

export default DbConnectionTest;
