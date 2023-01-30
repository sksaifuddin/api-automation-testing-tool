import * as React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { Container, Paper, Button } from '@mui/material';

export default function DbTest() {
    const paperstyle = {padding: '50px 20px', width:600, margin:"20px auto"}
    const[username, setUsername] = React.useState('')
    const[[password], setPassword] = React.useState('')
    const[[url], setUrl] = React.useState('')
    
  return (
    <Container>
        <Paper elevation={3} style={paperstyle}>
            <h1>API Tester</h1>
            <Box
            component="form"
            sx={{
                '& > :not(style)': { m: 1 },
            }}
            noValidate
            autoComplete="off"
            >
            <TextField id="outlined-basic" label="Username" variant="outlined" fullWidth
            value={username}
            onChange={(e)=>setUsername(e.target.value)}
            />
            <TextField id="outlined-basic" label="Password" variant="outlined" fullWidth
            value={password}
            onChange={(e)=>setPassword(e.target.value)}
            />
            <TextField id="outlined-basic" label="URL" variant="outlined" fullWidth
            value={url}
            onChange={(e)=>setUrl(e.target.value)}
            />
            <Button variant="contained" color="success">
            Test Connection
            </Button>
            </Box>
        </Paper>
    </Container>
  );
}
