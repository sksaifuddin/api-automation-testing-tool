import Box from "@mui/material/Box";
import LoadingButton from '@mui/lab/LoadingButton';
import AddIcon from '@mui/icons-material/Add';
import React, { useState } from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import TableHead from '@mui/material/TableHead';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CancelIcon from '@mui/icons-material/Cancel';

const TestCases = (props) => {

    function imagebutton(item) {
        if (item) {
            return <CheckCircleIcon color="success" ></CheckCircleIcon>
        } else {
            return <CancelIcon color="error"></CancelIcon>
        }
    }

    const [dataa] = useState([

        {
            "id": "1",
            "url": "abc",
            "requestType": "get",
            "isPass": true
        },
        {
            "id": "2",
            "url": "xyz",
            "requestType": "post",
            "isPass": false
        },
        {
            "id": "3",
            "url": "pqr",
            "requestType": "put",
            "isPass": true
        },
        {
            "id": "4",
            "url": "bcd",
            "requestType": "get",
            "isPass": false
        },
        {
            "id": "5",
            "url": "efg",
            "requestType": "put",
            "isPass": false
        },
        {
            "id": "6",
            "url": "ijk",
            "requestType": "delete",
            "isPass": true
        },
        {
            "id": "7",
            "url": "123",
            "requestType": "get",
            "isPass": true
        }
    ]);
    return (
        <Box>
            <header style={{ textAlign: "center" }}>TEST CASES</header>
            <div style={{ textAlign: "center" }}>
                <LoadingButton
                    variant="outlined"
                    startIcon={<AddIcon />}
                    style={{ width: "100%" }}
                    onClick={{}}
                >
                    Add Test Cases
                </LoadingButton>
            </div>
            <div style={{ height: "230px", overflow: "auto" }}>
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
                        {dataa.map((item, index) => (
                            <TableRow key={item.id} hover role="checkbox" tabIndex={-1} >
                                <TableCell>{index + 1}</TableCell>
                                <TableCell>{item.requestType}</TableCell>
                                <TableCell>{item.url}</TableCell>
                                <TableCell>{imagebutton(item.isPass)}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </div>
        </Box>
    );
}

export default TestCases;