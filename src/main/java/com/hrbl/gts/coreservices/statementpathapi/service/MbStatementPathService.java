package com.hrbl.gts.coreservices.statementpathapi.service;

import com.hrbl.gts.coreservices.statementpathapi.constants.EnvironmentConstants;
import com.hrbl.gts.coreservices.statementpathapi.model.*;
import com.hrbl.gts.coreservices.statementpathapi.repository.MbStatementPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MbStatementPathService {
    @Autowired
    private MbStatementPathRepository mbStatementPathRepository;

    public List<StatementPathItem> getFileDetailsFromDB(StatementPathRequest request)  {
        Date startDate = request.getOrderStartDate();
        Date endDate = request.getOrderEndDate();
        List<MbStatementPathTbl> mbStatementPathTblList = mbStatementPathRepository.fetchMbStatementPathDetails(request.getDsId(), startDate, endDate);
        return processDBRecords(mbStatementPathTblList);

    }

    protected List<StatementPathItem> processDBRecords(List<MbStatementPathTbl> statements) {
        List<StatementPathItem> statementPathItemList = new ArrayList<>();
        for (final MbStatementPathTbl statement : statements) {
            List<StatementPathItem> spi = getExistingStmntPathItemList(statementPathItemList, statement);
            addStatementPathItemToList(spi, statementPathItemList, statement);
        }
        return statementPathItemList;
    }

    protected List<StatementPathItem> getExistingStmntPathItemList(List<StatementPathItem> statementPathItemList, MbStatementPathTbl statement) {
        return statementPathItemList
                .stream()
                .filter(a -> a.getMbId().equals(statement.getMbId())
                        && a.getRoyaltyMonth().equals(EnvironmentConstants.OUTPUT_DATE_FORMAT.format(statement.getRoyaltyMonth()))
                        && a.getStatementType().equals(statement.getStatementType()))
                .collect(Collectors.toList());
    }

    protected void addStatementPathItemToList(List<StatementPathItem> spi, List<StatementPathItem> statementPathItems, MbStatementPathTbl statement) {
        StatementPathItem statementPathItem;
        if (spi.size() == 0) {
            statementPathItem = new StatementPathItem();
            statementPathItem.setMbId(statement.getMbId());
            statementPathItem.setRoyaltyMonth(EnvironmentConstants.OUTPUT_DATE_FORMAT.format(statement.getRoyaltyMonth()));
            statementPathItem.setStatementType(statement.getStatementType());
            statementPathItems.add(statementPathItem);

        } else {
            statementPathItem = spi.get(0);
        }
        StatementPath statementPath = new StatementPath();
        statementPath.setDocumentPath(statement.getDocumentPath());
        statementPath.setStatementPathId(String.valueOf(statement.getStatementPathId()));
        statementPath.setCreationDate(EnvironmentConstants.OUTPUT_DATE_FORMAT.format(statement.getCreationDate()));
//        if (statementPathItem.getStatementPaths() == null) {
//            statementPathItem.setStatementPaths(new ArrayList<StatementPath>());
//        }
        statementPathItem.getStatementPaths().add(statementPath);
    }


}
