import Box from "@mui/material/Box";
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CancelIcon from '@mui/icons-material/Cancel';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
const TestOutput = ({ data }) => {
  function iconForTestCasesPassed(isPassed) {
    return isPassed ? <CheckCircleIcon color="success"></CheckCircleIcon> : <CancelIcon color="error"></CancelIcon>;
  }

  function messageForAllTestCasesPassed() {
    return "Passed";
  }

  return <>
    <Box sx={{ width: 1000 }}>
      <TableContainer component={Paper} sx={{ width: 1050, height: 250 }}>
        <Table aria-label="test output table" size="small">
          <TableHead>
            <TableRow>
              <TableCell >{iconForTestCasesPassed(data.allTestPassed)}{messageForAllTestCasesPassed()}</TableCell>
              <TableCell ></TableCell>
              <TableCell ></TableCell>
              <TableCell ></TableCell>
            </TableRow>
            <TableRow>
              <TableCell sx={{ fontWeight: 'bold' }}>Result</TableCell>
              <TableCell align="right" sx={{ fontWeight: 'bold' }}>Column</TableCell>
              <TableCell align="right" sx={{ fontWeight: 'bold' }}>Expected Value</TableCell>
              <TableCell align="right" sx={{ fontWeight: 'bold' }}>Actual Value</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {data.columnValues.map((row) => (
              <TableRow>
                <TableCell>
                  {iconForTestCasesPassed(row.passed)}
                </TableCell>
                <TableCell align="right">{row.columnName}</TableCell>
                <TableCell align="right">{row.expectedValue}</TableCell>
                <TableCell align="right">{row.actualValue}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  </>;
}

export default TestOutput;
