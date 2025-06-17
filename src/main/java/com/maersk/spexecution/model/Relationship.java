package com.maersk.spexecution.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Relationship {

    private String fkName;
    private String column;
    private String referencedTable;
    private String referencedColumn;

}
