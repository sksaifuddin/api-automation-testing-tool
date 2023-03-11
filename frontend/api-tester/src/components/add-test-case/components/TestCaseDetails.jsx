import {
  Box,
  Grid,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  TextField,
} from "@mui/material";
import AddDeleteColumnTable from "./AddDeleteColumnTable/AddDeleteColumnTable";
import React, { useState } from "react";

function TestCaseDetails({ clientDBMetaData }) {
  const [selectedTable, setSelectedTable] = useState("");
  const [selectedPrimaryKeyValue, setSelectedPrimaryKeyValue] = useState({key: "", value: ""});
  const getTableMenuItems = () => {
    return Object.keys(clientDBMetaData).map((key, index) => (
        <MenuItem value={key} key={index}>{key}</MenuItem>
    ));
  };

  const getSelectedTableData = (selectedTableName)  => {
      console.log('--->', selectedTableName);
      
  }

  const handleSelectedTableChange = (event) => {
    const selectedValue = event.target.value;
    setSelectedTable(selectedValue);
    getSelectedTableData(selectedValue);
    setSelectedPrimaryKeyValue({ 
        value: selectedPrimaryKeyValue.value, 
        key: Object.entries(clientDBMetaData).filter((entry) => {
          const [key] = entry;
          if(key === selectedValue) {
            return true;
          } 
          return false;
        }).map(data => data[1])[0].primaryKeyList[0]
    });
    // console.log('selected primarty key', selectedPrimaryKeyValue)
  }

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
              value={selectedTable}
              label="Table *"
              onChange={(e) => handleSelectedTableChange(e)}
            >
              {getTableMenuItems()}
            </Select>
          </FormControl>
        </Grid>
        <Grid container spacing={5} marginBottom={2}>
          <Grid item xs={4}>
            <FormControl required sx={{ width: 200 }}>
              <InputLabel id="demo-simple-select-required-label">
                Primary Key
              </InputLabel>
              <Select
                labelId="demo-simple-select-required-label"
                id="demo-simple-select-required"
                value={selectedPrimaryKeyValue.key}
                label="Primary Key *"
                disabled
              >
                <MenuItem value={selectedPrimaryKeyValue.key}>{selectedPrimaryKeyValue.key}</MenuItem>
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
        <Grid container>
          <Grid item xs={12}>
            <AddDeleteColumnTable></AddDeleteColumnTable>
          </Grid>
        </Grid>
      </Box>
    </div>
  );
}

export default TestCaseDetails;
