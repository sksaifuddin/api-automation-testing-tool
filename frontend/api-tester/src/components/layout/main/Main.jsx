import React from "react";
import Box from "@mui/material/Box";
import TestOutput from "../../test-output/TestOutput/TestOutput";
import AddTestCaseContainer from "../../add-test-case/container/add-test-case-container/AddTestCaseContainer";

const main = (props) => {
    return <>
     
        <Box/>
        <AddTestCaseContainer></AddTestCaseContainer>
        <div>
           <TestOutput data={{
            "httpStatusCode": 200,
            "httpErrorMsg": "",
            "allTestPassed": true,
            "columnValues": [{
                "columnName": "first_name",
                "expectedValue": "saif",
                "actualValue": "saif",
                "passed": true
            }, {
                "columnName": "last_name",
                "expectedValue": "shaik",
                "actualValue": "shaik",
                "passed": true
            }, {
                "columnName": "film_count",
                "expectedValue": "25",
                "actualValue": "25",
                "passed": false
            }, {
                "columnName": "film_count",
                "expectedValue": "25",
                "actualValue": "25",
                "passed": false
            }, {
                "columnName": "film_count",
                "expectedValue": "25",
                "actualValue": "25",
                "passed": false
            }]
          }}></TestOutput>
        </div>
    </>;
}

export default main;
