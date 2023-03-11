import React from "react";
import Box from "@mui/material/Box";
import TestOutput from "../../test-output/TestOutput/TestOutput";

const main = (props) => {
    return <>
        <Box sx={{ width: 1000, height:300, margin: 2 }}/>
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
