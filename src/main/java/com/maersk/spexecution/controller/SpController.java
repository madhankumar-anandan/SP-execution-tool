package com.maersk.spexecution.controller;

import com.maersk.spexecution.model.SPRequestPayload;
import com.maersk.spexecution.service.SpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/lookups/storeprocedure/")
@RequiredArgsConstructor
@RestController
public class SpController {

    @Autowired
    private SpService spService;

    @PostMapping("/add")
    public ResponseEntity<String> addLookupData(
            @RequestBody SPRequestPayload requestPayload) {
        String response = spService.lookUpActions(requestPayload.getRequestString(),
                requestPayload.getUserId(), requestPayload.getOperationType());
        return ResponseEntity.ok(response);
    }
}
