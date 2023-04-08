import {
  Box,
  Grid,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  TextField,
  IconButton,
} from "@mui/material";
import AddIcon from '@mui/icons-material/Add';

import React, { useEffect, useState } from "react";

function AddDeleteColumnTable({columns, getColumnValues}) {
  const [columnsNames, setColumnNames] = useState([])
  const [items, setItems] = useState([{ columnName: "", expectedValue: "" }]);
  const handleAddRow = () => {
    setItems([...items, { columnName: "", expectedValue: "" }]);
  };
  
  useEffect(() => {
    setColumnNames(columns.map(column => {
      return Object.keys(column)[0]
    })
    )
  
  }, [columns])

  const getColumnMenu = () => {
    return columnsNames.map((column, index) => (
      <MenuItem key={index} value={column}>{column}</MenuItem>
    ))
  }

  const handleColumnNameSelected = (event) => {
    const emptyItemIndex = items.findIndex((item) => item.columnName === "");
    const objectTobeUpdated = items[emptyItemIndex];
    objectTobeUpdated.columnName = event.target.value;
    items[emptyItemIndex] = objectTobeUpdated;
    setItems([...items]);
    getColumnValues(items)
  }

  const handleColumnValueChange = (event, selectedItem) => {
    const emptyItemIndex = items.findIndex((item) => item.columnName === selectedItem.columnName);
    const objectTobeUpdated = items[emptyItemIndex];
    objectTobeUpdated.expectedValue = event.target.value;
    items[emptyItemIndex] = objectTobeUpdated;
    setItems([...items]);
    getColumnValues(items)
  }

  return (
    <div>
      <Box>
        {items.map((item, index) => (
          <Grid container spacing={5} marginBottom={2} key={index}>
          <Grid item xs={4}>
            <FormControl required sx={{ width: 200 }}>
              <InputLabel id="demo-simple-select-required-label">
                Column
              </InputLabel>
              <Select
                labelId="demo-simple-select-required-label"
                id="demo-simple-select-required"
                value={item.columnName}
                label="Type *"
                onChange={(e) => handleColumnNameSelected(e)}
              >
                {getColumnMenu()}
              </Select>
            </FormControl>
          </Grid>
          <Grid item xs={6}>
            <TextField
              width={200}
              required
              disabled={!item.columnName}
              value={item.expectedValue || undefined}
              label="value"
              onChange={(event) => handleColumnValueChange(event, item)}
            />
            {
                index === items.length-1 && (
                    <IconButton
                    aria-label="delete"
                    size="small"
                    onClick={() => handleAddRow()}
                  >
                    <AddIcon fontSize="inherit" />
                  </IconButton>
                )
            }
           
          </Grid>
        </Grid>
        ))}
      </Box>
    </div>
  );
}

export default AddDeleteColumnTable;
