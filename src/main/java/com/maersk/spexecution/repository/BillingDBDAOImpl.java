package com.maersk.spexecution.repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.maersk.spexecution.DatabaseException;
import com.maersk.spexecution.common.BillingConstants;
import com.maersk.spexecution.config.BillingDBTemplateFactory;
import com.maersk.spexecution.model.DBResponse;
import com.maersk.spexecution.model.DBResult;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.object.GenericStoredProcedure;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Repository
public class BillingDBDAOImpl implements BillingDBDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingDBDAOImpl.class);


    private final com.maersk.spexecution.config.BillingDBTemplateFactory billingDBTemplateFactory;

    private static final String FORMAT = "c_Format";
    private static final String USER_ID = "c_UserID";
    private static final String OPERATION_TYPE = "c_OperationType";
    private static final String REQUEST_STRING = "c_RequestString";
    private static final String DEBUG = "b_Debug";
    private static final String SUCCESS = "b_Success";
    private static final String ERR_NO = "n_ErrNo";
    private static final String ERR_MSG = "c_ErrMsg";
    private static final String RESPONSE_STRING = "c_ResponseString";
    private static final String RESP_ORI_CONTENT = "b_RespOriContent";


    private static final String SQL_TIME_ZONE_INFO = "SELECT name, current_utc_offset FROM sys.time_zone_info WITH (NOLOCK) ORDER BY name";

    private static final String ISP_LWMS_GENERIC_WEBAPI_REQUEST = "[dbo].[isp_LWMS_Generic_WebAPI_Request]";

    @Autowired
    public BillingDBDAOImpl(BillingDBTemplateFactory billingDBTemplateFactory) {
        this.billingDBTemplateFactory = billingDBTemplateFactory;
    }

    public DBResponse GenericWebAPIRequestOld(String format, String userid, String operationType,
                                           String requestString)
            throws DatabaseException {
        int success = 0;
        int errNo = 0;
        int respOriContent = 0;
        String errMsg = "";
        String responseString = "";
        JdbcTemplate jdbcTemplate = billingDBTemplateFactory.getSecureDbJdbcTemplate();
        Map<String, Object> result;
        DBResult dbResult = new DBResult();
        DBResponse dbResponse = new DBResponse();
        try {
            StoredProcedure procedure = getStoredProcedure(jdbcTemplate);
            result = procedure.execute(format, userid, operationType, requestString, 0,
                    success, errNo, errMsg, responseString, respOriContent);
            ObjectMapper mapper = new ObjectMapper();
            String resString = String.valueOf(result.get(RESPONSE_STRING));
            if (!Objects.equals(resString, "")) {
                HashMap<String,String> cResponseString = mapper.readValue(resString, HashMap.class);
                dbResult.setData(cResponseString);
            }
            if (null != result.get(SUCCESS) && ((Integer) result.get(SUCCESS)) == 1) {
                dbResult.setStatus(BillingConstants.SUCCESS_STATUS_DB);
            } else {
                dbResult.setStatus(BillingConstants.FAIL_STATUS_DB);
            }
            dbResult.setErrorcode((Integer) result.get((ERR_NO)));
            dbResult.setErrormessage((String) result.get(ERR_MSG));
            dbResponse.setResult(dbResult);
        } catch (DataAccessException | IOException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return dbResponse;
    }

    private static StoredProcedure getStoredProcedure(JdbcTemplate jdbcTemplate) {
        SqlParameter[] parameters = {
                new SqlParameter(FORMAT, Types.VARCHAR),
                new SqlParameter(USER_ID, Types.VARCHAR),
                new SqlParameter(OPERATION_TYPE, Types.VARCHAR),
                new SqlParameter(REQUEST_STRING, Types.VARCHAR),
                new SqlParameter(DEBUG, Types.INTEGER),
                new SqlInOutParameter(SUCCESS, Types.INTEGER),
                new SqlInOutParameter(ERR_NO, Types.INTEGER),
                new SqlInOutParameter(ERR_MSG, Types.VARCHAR),
                new SqlInOutParameter(RESPONSE_STRING, Types.LONGNVARCHAR),
                new SqlInOutParameter(RESP_ORI_CONTENT, Types.INTEGER)
        };
        StoredProcedure procedure = new GenericStoredProcedure();
        procedure.setSql(ISP_LWMS_GENERIC_WEBAPI_REQUEST);
        procedure.setJdbcTemplate(jdbcTemplate);
        procedure.setDataSource(Objects.requireNonNull(jdbcTemplate.getDataSource()));
        procedure.setParameters(parameters);
        procedure.setFunction(false);
        procedure.compile();
        return procedure;
    }

    @Override
    public List<Map<String, Object>> getTimeZoneInfo() throws DatabaseException {
        try {
            JdbcTemplate jdbcTemplate = billingDBTemplateFactory.getSecureDbJdbcTemplate();
            return jdbcTemplate.queryForList(SQL_TIME_ZONE_INFO);
        } catch (DataAccessException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    public DBResponse GenericWebAPIRequest(String format, String userid, String operationType,
                                              String requestString) throws DatabaseException {
        LOGGER.info("search result from new implementation");
        DBResponse dbResponse = new DBResponse();
        try {
            JdbcTemplate jdbcTemplate = billingDBTemplateFactory.getSecureDbJdbcTemplate();
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName(ISP_LWMS_GENERIC_WEBAPI_REQUEST)
//                    .withSchemaName("dbo")
                    .withoutProcedureColumnMetaDataAccess()
                    .declareParameters(new SqlParameter(FORMAT, Types.VARCHAR),
                            new SqlParameter(USER_ID, Types.VARCHAR),
                            new SqlParameter(OPERATION_TYPE, Types.VARCHAR),
                            new SqlParameter(REQUEST_STRING, Types.VARCHAR),
                            new SqlParameter(DEBUG, Types.INTEGER),
                            new SqlOutParameter(SUCCESS, Types.INTEGER),
                            new SqlOutParameter(ERR_NO, Types.INTEGER),
                            new SqlOutParameter(ERR_MSG, Types.VARCHAR),
                            new SqlOutParameter(RESPONSE_STRING, Types.LONGNVARCHAR),
                            new SqlOutParameter(RESP_ORI_CONTENT, Types.INTEGER)
                    );

            Map<String, Object> parameterMap = new HashMap<>();

            parameterMap.put(FORMAT, format);
            parameterMap.put(USER_ID, userid);
            parameterMap.put(OPERATION_TYPE, operationType);
            parameterMap.put(REQUEST_STRING, requestString);
            parameterMap.put(DEBUG, 1);

            Map<String, Object> result = jdbcCall.execute(new MapSqlParameterSource(parameterMap));
            DBResult dbResult = new DBResult();
            ObjectMapper mapper = new ObjectMapper();
            String resString = String.valueOf(result.get(RESPONSE_STRING));
            if (!Objects.equals(resString, "")) {
                JSONObject jsonObject = new JSONObject(resString);
                HashMap<String,String> cResponseString = mapper.readValue(jsonObject.toString(), HashMap.class);
                dbResult.setData(cResponseString);
            }
            if (null != result.get(SUCCESS) && ((Integer) result.get(SUCCESS)) == 1) {
                dbResult.setStatus(BillingConstants.SUCCESS_STATUS_DB);
            } else {
                dbResult.setStatus(BillingConstants.FAIL_STATUS_DB);
            }
            dbResult.setErrorcode((Integer) result.get((ERR_NO)));
            dbResult.setErrormessage((String) result.get(ERR_MSG));
            dbResponse.setResult(dbResult);

        } catch (DataAccessException | IOException e) {
            LOGGER.error("GenericWebAPIRequest: DataAccessExceptions  --> {}", e.getMessage());
            throw new DatabaseException(e.getMessage(), e);
        }
        return dbResponse;
    }

}
