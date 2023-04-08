import Box from "@mui/material/Box";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import CancelIcon from "@mui/icons-material/Cancel";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
// eslint-disable-next-line no-unused-vars
import styles from "./TestOutput.scss";
const TestOutput = ({ data }) => {
  function iconForTestCasesPassed(isPassed) {
    return isPassed ? (
      <CheckCircleIcon color="success"></CheckCircleIcon>
    ) : (
      <CancelIcon color="error"></CancelIcon>
    );
  }

  const getErrorMessage = (statusCode) => {
    switch (statusCode) {
      case 503:
        return "Unable to make connection";
      case 405:
        return "Wrong Request Type";
      case 400:
        return "Invalid request or payload body";
      case 404:
        return "Request url not found";
      case 200:
        return "Request Successful";
      default:
        return "Unknown error";
    }
  };

  const getApiTestCaseResultIcon = (data) => {
    if(data.columnValues) {
      return iconForTestCasesPassed(true);
    }

    return iconForTestCasesPassed(data.allTestPassed)
  }

  return (
    <>
      <div className="container">
        <div style={{ display: "flex" }}>
          <Box sx={{ width: 1000, marginBottom: 2 }}>
            <div className="result-heading">API Result:</div>
            <TableContainer component={Paper} sx={{ width: 1050 }}>
              <Table aria-label="test output table" size="small">
                <TableHead>
                  <TableRow>
                    {/* <TableCell sx={{ fontWeight: "bold" }}>API Result</TableCell> */}
                    <TableCell sx={{ fontWeight: "bold" }}>Result</TableCell>
                    <TableCell sx={{ fontWeight: "bold" }}>
                      Status Code
                    </TableCell>
                    <TableCell sx={{ fontWeight: "bold" }}>
                      Error Message
                    </TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  <TableRow>
                    <TableCell>
                      {getApiTestCaseResultIcon(data)}
                    </TableCell>
                    <TableCell>{data.httpStatusCode}</TableCell>
                    <TableCell>
                      {getErrorMessage(data.httpStatusCode)}
                    </TableCell>
                  </TableRow>
                </TableBody>
              </Table>
            </TableContainer>
          </Box>
        </div>
        {data && data.columnValues && data.columnValues.length > 0 && (
          <Box sx={{ width: 1000 }}>
            <div className="result-heading">Database Result:</div>
            <TableContainer component={Paper} sx={{ width: 1050 }}>
              <Table aria-label="test output table" size="small">
                <TableHead>
                  <TableRow>
                    <TableCell sx={{ fontWeight: "bold" }}>Result</TableCell>
                    <TableCell sx={{ fontWeight: "bold" }}>Column</TableCell>
                    <TableCell sx={{ fontWeight: "bold" }}>
                      Expected Value
                    </TableCell>
                    <TableCell sx={{ fontWeight: "bold" }}>
                      Actual Value
                    </TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {data?.columnValues?.map((row) => (
                    <TableRow>
                      <TableCell>
                        {iconForTestCasesPassed(row.passed)}
                      </TableCell>
                      <TableCell>{row.columnName}</TableCell>
                      <TableCell>{row.expectedValue}</TableCell>
                      <TableCell>{row.actualValue}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Box>
        )}
      </div>
    </>
  );
};

export default TestOutput;
