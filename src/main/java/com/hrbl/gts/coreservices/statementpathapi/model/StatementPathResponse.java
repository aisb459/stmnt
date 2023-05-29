package com.hrbl.gts.coreservices.statementpathapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementPathResponse {
    List<StatementPathItem> statementPathItemList = new ArrayList<StatementPathItem>();
    ErrorDetails errorDetails;
}
