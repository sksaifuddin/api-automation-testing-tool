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
import React, { useEffect, useState } from "react";

function TestCaseDetails({ clientDBMetaData, getTestCaseDetails, testCaseDetailsType }) {
  const [testCaseDetails, setTestCaseDetails] = useState({
    tableName: "",
    primaryKeyName: "",
    primaryKeyValue: 0,
    columnValues: [],
  });
  const [selectedTable, setSelectedTable] = useState("");
  const [selectedPrimaryKeyValue, setSelectedPrimaryKeyValue] = useState({
    key: "",
    value: "",
  });

  const isPrimaryKeyDisabled = testCaseDetailsType === 'post';

  useEffect(() => {
    // console.log('this is test case details', testCaseDetails);
    getTestCaseDetails(testCaseDetails)
  }, [testCaseDetails, getTestCaseDetails])

  const getColumnValues = (columnValues) => {
    // console.log("column values in test case details", columnValues);
    setTestCaseDetails({ ...testCaseDetails, columnValues });
   
  };

  const getTableMenuItems = () => {
    return Object.keys(clientDBMetaData).map((key, index) => (
      <MenuItem value={key} key={index}>
        {key}
      </MenuItem>
    ));
  };

  const getSelectedTableData = (selectedTableName) => {
    return Object.entries(clientDBMetaData)
      .map((entry) => {
        const [key, value] = entry;
        if (key === selectedTableName) return { tableName: key, data: value };
        return null;
      })
      .filter(Boolean);
  };

  const handleSelectedTableChange = (event) => {
    const selectedValue = event.target.value;
    setSelectedTable(selectedValue);
    getSelectedTableData(selectedValue);
    setSelectedPrimaryKeyValue({
      value: selectedPrimaryKeyValue.value,
      key: getSelectedTableData(selectedValue)[0].data.primaryKeyList[0],
    });
    // testCaseDetails = {...testCaseDetails, tableName: selectedValue}
    setTestCaseDetails({
      ...testCaseDetails,
      tableName: selectedValue,
      primaryKeyName:
        getSelectedTableData(selectedValue)[0].data.primaryKeyList[0],
    });
    
  };

  const handlePrimaryKeyValueChange = (event) => {
    setSelectedPrimaryKeyValue({
      value: +event.target.value,
      key: selectedPrimaryKeyValue.key,
    });
    // testCaseDetails = {...testCaseDetails, primaryKeyValue: selectedPrimaryKeyValue.value}
    setTestCaseDetails({
      ...testCaseDetails,
      primaryKeyValue: +event.target.value,
    });
   
  };

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
                <MenuItem value={selectedPrimaryKeyValue.key}>
                  {selectedPrimaryKeyValue.key}
                </MenuItem>
              </Select>
            </FormControl>
          </Grid>
          <Grid item xs={8}>
            <TextField
              width={200}
              required
              type="number"
              // value={selectedPrimaryKeyValue.value || undefined}
              label="value"
              onBlur={(e) => handlePrimaryKeyValueChange(e)}
              disabled={isPrimaryKeyDisabled}
            />
          </Grid>
        </Grid>
        <Grid container>
          <Grid item xs={12}>
            {selectedTable && testCaseDetailsType!=='delete' && (
              <AddDeleteColumnTable
                columns={
                  getSelectedTableData(selectedTable)[0]?.data.columnsList
                }
                getColumnValues={getColumnValues}
              ></AddDeleteColumnTable>
            )}
          </Grid>
        </Grid>
      </Box>
    </div>
  );
}

export default TestCaseDetails;
