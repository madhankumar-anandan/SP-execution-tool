package com.maersk.spexecution.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class TableSchema {
    private String schema;
    private String table;
    private List<ColumnSchema> columns = new ArrayList<>();
    private List<Relationship> relationships = new ArrayList<>();

    public TableSchema(String schema, String table) {
        this.schema = schema;
        this.table = table;
    }
}
