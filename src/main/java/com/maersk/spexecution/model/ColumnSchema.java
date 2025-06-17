package com.maersk.spexecution.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ColumnSchema {

    private String name;
    private String dataType;
    private String isNullable;
    private Integer maxLength;

}
