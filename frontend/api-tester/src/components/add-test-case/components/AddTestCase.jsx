import React from "react";
import { Grid, Box, Select, MenuItem, InputLabel, FormControl, TextField } from "@mui/material";

function AddTestCase() {
  return (
    <div>
      <Box>
        <Grid container spacing={2} style={{margin: 2}}>
          <Grid item xs={2}>
          <FormControl required fullWidth>
          <InputLabel id="demo-simple-select-required-label">Type</InputLabel>
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
          <Grid item xs={10}>
          <TextField
                        fullWidth
                        required
                        // value={username}
                        label="URL"
                        // onChange={(e) => setUsername(e.target.value)}
                    />
          </Grid>
        </Grid>
      </Box>

    </div>
  );
}

export default AddTestCase;
