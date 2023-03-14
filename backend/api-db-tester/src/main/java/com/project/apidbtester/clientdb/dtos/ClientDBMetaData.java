package com.project.apidbtester.clientdb.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ClientDBMetaData {
    private List<String> primaryKeyList;
    private List<Map<String, String>> columnsList;
}
