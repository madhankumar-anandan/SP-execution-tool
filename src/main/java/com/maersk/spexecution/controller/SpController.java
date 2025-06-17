package com.maersk.spexecution.controller;

import com.maersk.spexecution.model.SPRequestPayload;
import com.maersk.spexecution.model.TableSchema;
import com.maersk.spexecution.service.SpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/storeprocedure")
@RequiredArgsConstructor
@RestController
public class SpController {

    @Autowired
    private SpService spService;

    @PostMapping
    public ResponseEntity<String> addLookupData(
            @RequestBody SPRequestPayload requestPayload) {
        String response = spService.lookUpActions(requestPayload.getRequestString(),
                requestPayload.getUserId(), requestPayload.getOperationType());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<String> getAllProcedures() {
        return spService.listAllProcedures();
    }

    @GetMapping("/{name}")
    public String getProcedureScript(@PathVariable String name) {
        return spService.getProcedureScript(name);
    }

    @GetMapping("/tables")
    public List<Map<String, Object>> getTables() {
        return spService.getAllTables();
    }

    @GetMapping("/tables/{schema}/{table}/columns")
    public List<Map<String, Object>> getTableColumns(@PathVariable String schema, @PathVariable String table) {
        return spService.getTableColumns(schema, table);
    }

    @GetMapping("/tables-columns")
    public List<Map<String, Object>> getTablesAndColumns() {
        return spService.getTablesAndColumns();
    }

    @GetMapping("/relations")
    public List<Map<String, Object>> getTableRelations() {
        return spService.getTableRelations();
    }

    @GetMapping("/structured")
    public List<TableSchema> getStructuredSchema() {
        return spService.getStructuredSchemaWithRelations();
    }
}
