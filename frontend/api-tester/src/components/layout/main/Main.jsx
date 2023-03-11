import React, { useState } from "react";
import Box from "@mui/material/Box";
import TestOutput from "../../test-output/TestOutput/TestOutput";
import AddTestCaseContainer from "../../add-test-case/container/add-test-case-container/AddTestCaseContainer";

const Main = (props) => {
  const [executionResult, setTestExecutionResult] = useState({});
  const getTestCaseExecutionResult = (executedResult) => {
    setTestExecutionResult(executedResult);
  };
  return (
    <>
      <Box />
      <AddTestCaseContainer
        getTestCaseExecutionResult={getTestCaseExecutionResult}
      ></AddTestCaseContainer>
      <div>
        {
            Object.keys(executionResult).length > 0 && <TestOutput data={executionResult}></TestOutput>
        }
      </div>
    </>
  );
};

export default Main;
