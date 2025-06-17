package com.maersk.spexecution.service;

import com.maersk.spexecution.DatabaseException;
import com.maersk.spexecution.common.BillingConstants;
import com.maersk.spexecution.model.ColumnSchema;
import com.maersk.spexecution.model.DBResponse;
import com.maersk.spexecution.model.Relationship;
import com.maersk.spexecution.model.TableSchema;
import com.maersk.spexecution.repository.BillingDBDAOImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SpService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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


    // List all stored procedures
    public List<String> listAllProcedures() {
        String sql = "SELECT name FROM sys.procedures";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    // Get the script/definition of a stored procedure
    public String getProcedureScript(String procedureName) {
        String sql = "SELECT OBJECT_DEFINITION(OBJECT_ID(?)) AS Script";
        return jdbcTemplate.queryForObject(sql, new Object[]{procedureName}, String.class);
    }

    public List<Map<String, Object>> getAllTables() {
        String sql = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getTableColumns(String schema, String table) {
        String sql = "SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE, CHARACTER_MAXIMUM_LENGTH " +
                "FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
        return jdbcTemplate.queryForList(sql, schema, table);
    }

    // Get all tables and columns
    public List<Map<String, Object>> getTablesAndColumns() {
        String sql = "SELECT " +
                "t.TABLE_SCHEMA, t.TABLE_NAME, c.COLUMN_NAME, c.DATA_TYPE, c.IS_NULLABLE, c.CHARACTER_MAXIMUM_LENGTH " +
                "FROM INFORMATION_SCHEMA.TABLES t " +
                "JOIN INFORMATION_SCHEMA.COLUMNS c " +
                "ON t.TABLE_SCHEMA = c.TABLE_SCHEMA AND t.TABLE_NAME = c.TABLE_NAME " +
                "WHERE t.TABLE_TYPE = 'BASE TABLE' " +
                "ORDER BY t.TABLE_SCHEMA, t.TABLE_NAME, c.ORDINAL_POSITION";
        return jdbcTemplate.queryForList(sql);
    }

    // Get all foreign key relationships
    public List<Map<String, Object>> getTableRelations() {
        String sql = "SELECT " +
                "fk.name AS FK_NAME, " +
                "tp.name AS PARENT_TABLE, cp.name AS PARENT_COLUMN, " +
                "tr.name AS REF_TABLE, cr.name AS REF_COLUMN " +
                "FROM sys.foreign_keys fk " +
                "INNER JOIN sys.foreign_key_columns fkc ON fk.object_id = fkc.constraint_object_id " +
                "INNER JOIN sys.tables tp ON fkc.parent_object_id = tp.object_id " +
                "INNER JOIN sys.columns cp ON fkc.parent_object_id = cp.object_id AND fkc.parent_column_id = cp.column_id " +
                "INNER JOIN sys.tables tr ON fkc.referenced_object_id = tr.object_id " +
                "INNER JOIN sys.columns cr ON fkc.referenced_object_id = cr.object_id AND fkc.referenced_column_id = cr.column_id " +
                "ORDER BY tp.name, cp.name";
        return jdbcTemplate.queryForList(sql);
    }

    public List<TableSchema> getStructuredSchemaWithRelations() {
        // Get tables and columns
        String tableSql = "SELECT t.TABLE_SCHEMA, t.TABLE_NAME, c.COLUMN_NAME, c.DATA_TYPE, c.IS_NULLABLE, c.CHARACTER_MAXIMUM_LENGTH " +
                "FROM INFORMATION_SCHEMA.TABLES t " +
                "JOIN INFORMATION_SCHEMA.COLUMNS c ON t.TABLE_SCHEMA = c.TABLE_SCHEMA AND t.TABLE_NAME = c.TABLE_NAME " +
                "WHERE t.TABLE_TYPE = 'BASE TABLE' " +
                "ORDER BY t.TABLE_SCHEMA, t.TABLE_NAME, c.ORDINAL_POSITION";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(tableSql);

        // Get relationships
        String relSql = "SELECT " +
                "tp.name AS parent_table, cp.name AS parent_column, " +
                "tr.name AS ref_table, cr.name AS ref_column, fk.name AS fk_name " +
                "FROM sys.foreign_keys fk " +
                "INNER JOIN sys.foreign_key_columns fkc ON fk.object_id = fkc.constraint_object_id " +
                "INNER JOIN sys.tables tp ON fkc.parent_object_id = tp.object_id " +
                "INNER JOIN sys.columns cp ON fkc.parent_object_id = cp.object_id AND fkc.parent_column_id = cp.column_id " +
                "INNER JOIN sys.tables tr ON fkc.referenced_object_id = tr.object_id " +
                "INNER JOIN sys.columns cr ON fkc.referenced_object_id = cr.object_id AND fkc.referenced_column_id = cr.column_id " +
                "ORDER BY tp.name, cp.name";
        List<Map<String, Object>> relRows = jdbcTemplate.queryForList(relSql);

        // Group columns by table
        Map<String, TableSchema> tableMap = new LinkedHashMap<>();
        for (Map<String, Object> row : rows) {
            String schema = (String) row.get("TABLE_SCHEMA");
            String table = (String) row.get("TABLE_NAME");
            String key = schema + "." + table;

            TableSchema tableSchema = tableMap.computeIfAbsent(key, k -> new TableSchema(schema, table));
            tableSchema.getColumns().add(new ColumnSchema(
                    (String) row.get("COLUMN_NAME"),
                    (String) row.get("DATA_TYPE"),
                    (String) row.get("IS_NULLABLE"),
                    row.get("CHARACTER_MAXIMUM_LENGTH") != null ? ((Number) row.get("CHARACTER_MAXIMUM_LENGTH")).intValue() : null
            ));
        }

        // Add relationships to tables
        for (Map<String, Object> rel : relRows) {
            String parentTable = (String) rel.get("parent_table");
            String refTable = (String) rel.get("ref_table");
            String parentColumn = (String) rel.get("parent_column");
            String refColumn = (String) rel.get("ref_column");
            String fkName = (String) rel.get("fk_name");

            // Find the table schema object
            for (TableSchema tableSchema : tableMap.values()) {
                if (tableSchema.getTable().equals(parentTable)) {
                    tableSchema.getRelationships().add(new Relationship(
                            fkName, parentColumn, refTable, refColumn
                    ));
                }
            }
        }

        return new ArrayList<>(tableMap.values());
    }
}
