import {
  Box,
  Grid,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  TextField
} from "@mui/material";
import React from "react";

function TestCaseDetails() {
  return (
    <div>
      <Box>
        <Grid container marginBottom={2}>
          <FormControl required sx={{ width: 200 }}>
            <InputLabel id="demo-simple-select-required-label">
              Table
            </InputLabel>
            <Select
              labelId="demo-simple-select-required-label"
              id="demo-simple-select-required"
              // value={}
              label="Type *"
              // onChange={handleChange}
            >
              <MenuItem value={"GET"}>GET</MenuItem>
              <MenuItem value={"POST"}>POST</MenuItem>
              <MenuItem value={"PUT"}>PUT</MenuItem>
              <MenuItem value={"DELETE"}>PUT</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid container spacing={5}>
        <Grid item xs={4}>
        <FormControl required sx={{ width: 200 }}>
            <InputLabel id="demo-simple-select-required-label">
              Primary Key
            </InputLabel>
            <Select
              labelId="demo-simple-select-required-label"
              id="demo-simple-select-required"
              // value={}
              label="Type *"
              // onChange={handleChange}
            >
              <MenuItem value={"GET"}>GET</MenuItem>
              <MenuItem value={"POST"}>POST</MenuItem>
              <MenuItem value={"PUT"}>PUT</MenuItem>
              <MenuItem value={"DELETE"}>PUT</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={8}>
        <TextField
                        width={200}
                        required
                        // value={username}
                        label="value"
                        // onChange={(e) => setUsername(e.target.value)}
                    />
        </Grid>
        </Grid>
      </Box>
    </div>
  );
}

export default TestCaseDetails;
