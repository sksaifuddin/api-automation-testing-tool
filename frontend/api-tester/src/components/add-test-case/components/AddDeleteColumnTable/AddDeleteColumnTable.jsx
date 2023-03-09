import {
  Box,
  Grid,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  TextField,
  IconButton,
} from "@mui/material";
import AddIcon from '@mui/icons-material/Add';

import React, { useState } from "react";

function AddDeleteColumnTable() {
  const [items, setItems] = useState([{ column: "", value: "" }]);
  const handleAddRow = () => {
    console.log("here in handle row");
    setItems([...items, { column: "", value: "" }]);
  };
  return (
    <div>
      <Box>
        {items.map((item, index) => (
          <Grid container spacing={5} marginBottom={2} key={index}>
          <Grid item xs={4}>
            <FormControl required sx={{ width: 200 }}>
              <InputLabel id="demo-simple-select-required-label">
                Column
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
          <Grid item xs={6}>
            <TextField
              width={200}
              required
              // value={username}
              label="value"
              // onChange={(e) => setUsername(e.target.value)}
            />
            {
                index === items.length-1 && (
                    <IconButton
                    aria-label="delete"
                    size="small"
                    onClick={() => handleAddRow()}
                  >
                    <AddIcon fontSize="inherit" />
                  </IconButton>
                )
            }
           
          </Grid>
        </Grid>
        ))}
      </Box>
    </div>
  );
}

export default AddDeleteColumnTable;
