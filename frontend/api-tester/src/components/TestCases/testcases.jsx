import Box from "@mui/material/Box";
import LoadingButton from "@mui/lab/LoadingButton";
import AddIcon from "@mui/icons-material/Add";
import React, { useState, useEffect } from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableRow from "@mui/material/TableRow";
import TableHead from "@mui/material/TableHead";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import CancelIcon from "@mui/icons-material/Cancel";
import { getAllTestCases } from "../../services/TestCasesService";

const TestCases = (props) => {
  const [data, setData] = useState([]);

  useEffect(() => {
    getAllTestCases().then((data) => {
      setData(data);
    });
  }, []);

  function imagebutton(item) {
    if (item) {
      return <CheckCircleIcon color="success"></CheckCircleIcon>;
    } else {
      return <CancelIcon color="error"></CancelIcon>;
    }
  }

  function refresh() {
    window.location.reload(false);
  }

  return (
    <Box>
      <div style={{ textAlign: "center" }}>
        <LoadingButton
          variant="outlined"
          startIcon={<AddIcon />}
          style={{ width: "100%" }}
          onClick={() => refresh()}
        >
         <span>Add New Testcase</span>
        </LoadingButton>
      </div>
      <div style={{ height: "400px", overflow: "auto" }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>No</TableCell>
              <TableCell>Request Type</TableCell>
              <TableCell>Url</TableCell>
              <TableCell>Pass</TableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            {data.map((item, index) => (
              <TableRow key={item.id} hover role="checkbox" tabIndex={-1}>
                <TableCell>{index + 1}</TableCell>
                <TableCell style={{ width: "10px" }}>{item.type}</TableCell>
                <TableCell title={item.url}>
                  <div
                    style={{
                      whiteSpace: "nowrap",
                      textOverflow: "ellipsis",
                      width: "100px",
                      display: "block",
                      overflow: "hidden",
                    }}
                  >
                    {item.url}
                  </div>
                </TableCell>
                <TableCell>{imagebutton(item.passed)}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
    </Box>
  );
};

export default TestCases;