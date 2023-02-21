import React, { useState } from 'react';
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import { Container, Paper, Button } from "@mui/material";
import { testClientDBConnection } from '../services/DbTestService';

export default function DbTest() {
  const paperstyle = { padding: "50px 20px", width: 600, margin: "20px auto" };
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [url, setUrl] = useState("");
  const [testSuccessMessage, setTestSuccessMessage] = useState("");

  async function testConnection() {
    await testClientDBConnection(url, username, password).then((data) => {
      setTestSuccessMessage(data.message);
    }).catch((error) => {
      setTestSuccessMessage(error.response.data.message);
    });
    
  }

  return (
    <Container>
      <Paper elevation={3} style={paperstyle}>
        <h1>API Tester</h1>
        <Box
          component="form"
          sx={{
            "& > :not(style)": { m: 1 },
          }}
          noValidate
          autoComplete="off"
        >
          <TextField
            id="outlined-basic"
            label="Username"
            variant="outlined"
            fullWidth
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <TextField
            id="outlined-basic"
            label="Password"
            type="password"
            variant="outlined"
            fullWidth
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <TextField
            id="outlined-basic"
            label="URL"
            variant="outlined"
            fullWidth
            value={url}
            onChange={(e) => setUrl(e.target.value)}
          />
          <Button variant="contained" color="success" onClick={testConnection}>
            Test Connection
          </Button>
        </Box>
        {testSuccessMessage}
      </Paper>
    </Container>
  );
}
