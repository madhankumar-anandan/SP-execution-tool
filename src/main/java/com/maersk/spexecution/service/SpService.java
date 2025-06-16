package com.maersk.spexecution.service;

import com.maersk.spexecution.DatabaseException;
import com.maersk.spexecution.common.BillingConstants;
import com.maersk.spexecution.model.DBResponse;
import com.maersk.spexecution.repository.BillingDBDAOImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpService {

    @Autowired
    private BillingDBDAOImpl billingDBDao;

    public String lookUpActions(String requestString, String userId, String operationType) {
        try {
            log.info("lookUpActions: request -> {}", requestString);
            log.info("lookUpActions: Actions - ADD");
            // userId = "madhankumar.anandan@maersk.com";
         //   operationType = BillingConstants.LOOKUP_ADD;
            DBResponse dbResponse = billingDBDao.GenericWebAPIRequest(BillingConstants.JSON,
                    userId , operationType , requestString);
            log.info("dbResponse --> {}", dbResponse);
        } catch (DatabaseException e) {
            log.error(e.getMessage());
        }

        return "{\"message\":\"Successfully added lookup data\",\"status\":\"200\"}"; // Construct a proper response based on the operation result
    }
}
