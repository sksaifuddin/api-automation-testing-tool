import React, { useEffect, useState } from "react";
import { getClientDBMetadata } from "../../../../services/ClientDBMetaData";
import AddTestCase from "../../components/AddTestCase";
// eslint-disable-next-line no-unused-vars
import styles from "./AddTestCaseContainer.scss";
import LoadingButton from "@mui/lab/LoadingButton";
import SendIcon from "@mui/icons-material/Send";
import { Grid } from "@mui/material";
import { executeTestCase } from "../../../../services/executeTestCase";

const AddTestCaseContainer = ({getTestCaseExecutionResult}) => {
  const [clientDBMetaData, setClientDBMetaData] = useState({});
  const [btnLoader, setBtnLoader] = useState(false);
  let finalTestObject = {testCaseDetails: {}, columnValues: {}};
  useEffect(() => {
    getClientDBMetadata().then((data) => {
      setClientDBMetaData(data);
    });
  }, []);

  const getFinalTestObject = (testObject) => {
    // console.log("test object", testObject);
    const {columnValues, ...testCaseDetails} = testObject
    finalTestObject= {
      testCaseDetails,
      columnValues
    }
  
  };

  const testCaseExecution = () => {
    setBtnLoader(true);
    executeTestCase(finalTestObject).then((data) => {
      getTestCaseExecutionResult(data);
      setBtnLoader(false);
    })
  }

  return (
    <>
      <div className="add-test-case-container">
        <AddTestCase
          clientDBMetaData={{ ...clientDBMetaData }}
          getFinalTestObject={getFinalTestObject}
        ></AddTestCase>
        <div>
          <Grid
            container
            direction="row"
            justifyContent="flex-end"
            alignItems="center"
          >
            <Grid item>
              <LoadingButton
                size="large"
                endIcon={<SendIcon />}
                loading={btnLoader}
                loadingPosition="end"
                variant="contained"
                onClick={testCaseExecution}
              >
                <span>Test</span>
              </LoadingButton>
            </Grid>
          </Grid>
        </div>
      </div>
    </>
  );
};

export default AddTestCaseContainer;
