import React, { useEffect, useState } from "react";
import {
  Grid,
  Box,
  Select,
  MenuItem,
  InputLabel,
  FormControl,
  TextField,
} from "@mui/material";
import TextareaAutosize from "@mui/base/TextareaAutosize";
import TestCaseDetails from "./TestCaseDetails";

function AddTestCase({ clientDBMetaData, getFinalTestObject }) {
  const [testCaseDetails, setTestCaseDetails] = useState({
    type: "",
    url: "",
    payload: "",
    tableName: "",
    primaryKeyName: "",
    primaryKeyValue: null, // this should be a number,
    columnValues: [],
  });

  useEffect(() => {
    getFinalTestObject(testCaseDetails);
  }, [testCaseDetails, getFinalTestObject])

  const handleTypeChange = (event) => {
    setTestCaseDetails({ ...{ ...testCaseDetails, type: event.target.value } });
  };

  const handleURLChange = (event) => {
    setTestCaseDetails({ ...{ ...testCaseDetails, url: event.target.value } });
  };

  const handlePayloadChange = (event) => {
    setTestCaseDetails({
      ...{ ...testCaseDetails, payload: event.target.value },
    });
  };

  const isPayloadDisabled = (testCaseDetails.type === 'delete' || testCaseDetails.type === 'get');

  const getTestCaseDetails = (otherTestCaseDetails) => {
    // console.log('test case details', testCaseDetails);
    // setTestCaseDetails({...testCaseDetails, ...otherTestCaseDetails});
    getFinalTestObject({...testCaseDetails, ...otherTestCaseDetails});
  }

  return (
    <div>
      <Box>
        <Grid container spacing={2} style={{ margin: 2 }}>
          <Grid item xs={2}>
            <FormControl required fullWidth>
              <InputLabel id="demo-simple-select-required-label">
                Type
              </InputLabel>
              <Select
                labelId="demo-simple-select-required-label"
                id="demo-simple-select-required"
                label="Type *"
                onChange={handleTypeChange}
              >
                <MenuItem value={"get"}>GET</MenuItem>
                <MenuItem value={"post"}>POST</MenuItem>
                <MenuItem value={"put"}>PUT</MenuItem>
                <MenuItem value={"delete"}>DELETE</MenuItem>
              </Select>
            </FormControl>
          </Grid>
          <Grid item xs={10}>
            <TextField
              fullWidth
              required
              label="URL"
              onChange={handleURLChange}
            />
          </Grid>
        </Grid>

        <Grid container spacing={2} style={{ margin: 2 }}>
          <Grid item xs={5}>
            <TextareaAutosize
              aria-label="minimum height"
              minRows={20}
              onBlur={handlePayloadChange}
              placeholder="Payload Body"
              style={{ width: 400 }}
              disabled={isPayloadDisabled}
            />
          </Grid>
          <Grid item xs={7}>
            {testCaseDetails.type && (
              <TestCaseDetails
                getTestCaseDetails={getTestCaseDetails}
                clientDBMetaData={{ ...clientDBMetaData }}
                testCaseDetailsType={testCaseDetails.type}
              ></TestCaseDetails>
            )}
          </Grid>
        </Grid>

       
      </Box>
    </div>
  );
}

export default AddTestCase;
