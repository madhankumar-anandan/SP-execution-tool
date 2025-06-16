package com.maersk.spexecution.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class DBResult {

    private HashMap<String ,String> data;
    private int errorcode;
    private String errormessage;
    private String status;

}
