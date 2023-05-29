package com.hrbl.gts.coreservices.statementpathapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private String ErrorCode;
    private String ErrorDesc;
}
