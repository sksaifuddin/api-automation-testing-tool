package com.project.apidbtester.clientdb.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * ClientDBMetaData contains the metadata of a table in the client application db:
 * the list of primary keys and the columns and their data type
 */
@Data
@AllArgsConstructor
public class ClientDBMetaData {
    private List<String> primaryKeyList;
    private List<Map<String, String>> columnsList;
}
