package com.maersk.spexecution.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maersk.spexecution.DatabaseException;
import com.maersk.spexecution.model.DBResponse;

import java.util.List;
import java.util.Map;

public interface BillingDBDAO {
    DBResponse GenericWebAPIRequestOld(String format, String userid, String operationType, String requestString)
        throws DatabaseException, JsonProcessingException;

    List<Map<String, Object>> getTimeZoneInfo() throws DatabaseException;

    DBResponse GenericWebAPIRequest(String format, String userid, String operationType,
                                       String requestString) throws DatabaseException;
}
