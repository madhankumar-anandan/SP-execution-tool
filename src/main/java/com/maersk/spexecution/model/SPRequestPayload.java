package com.maersk.spexecution.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SPRequestPayload {
   private String requestString;
   private String userId;
   private String operationType;
}
