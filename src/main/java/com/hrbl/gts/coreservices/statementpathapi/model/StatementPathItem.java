package com.hrbl.gts.coreservices.statementpathapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementPathItem {

        private String mbId;
        private String royaltyMonth;
        private String statementType;
        private List<StatementPath> statementPaths = new ArrayList<StatementPath>();

}
