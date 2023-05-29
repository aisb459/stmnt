package com.hrbl.gts.coreservices.statementpathapi.service;

import com.hrbl.gts.coreservices.statementpathapi.model.StatementPathRequest;
import com.hrbl.gts.coreservices.statementpathapi.model.MbStatementPathTbl;
import com.hrbl.gts.coreservices.statementpathapi.model.StatementPath;
import com.hrbl.gts.coreservices.statementpathapi.model.StatementPathItem;
import com.hrbl.gts.coreservices.statementpathapi.repository.MbStatementPathRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MbStatementPathServiceTest {
    @InjectMocks
    private MbStatementPathService mbStatementPathService;
    @Mock
    private MbStatementPathRepository mbStatementPathRepository;

    private MbStatementPathTbl mbStatementPath;
    private StatementPathItem statementPathItem;
    private StatementPath statementPath;
    private StatementPathRequest fetchFileDetailsInputRequest;

    @BeforeEach
    void init() {
        mbStatementPath = new MbStatementPathTbl();
        mbStatementPath.setMbId("567");
        mbStatementPath.setStatementType("RO");
        mbStatementPath.setStatementPathId(3438636);
        mbStatementPath.setRoyaltyMonth(Date.valueOf(LocalDate.of(2010, Month.JANUARY, 1)));
        mbStatementPath.setDocumentPath("samplefi1e.pdf");
        mbStatementPath.setCreationDate(Date.valueOf(LocalDate.now()));
        mbStatementPath.setLanguageId(1);

        statementPathItem = new StatementPathItem();
        statementPathItem.setMbId("567");
        statementPathItem.setRoyaltyMonth("2010-01-01");
        statementPathItem.setStatementType("RO");

        statementPath = new StatementPath();
        statementPath.setStatementPathId("890");
        statementPath.setDocumentPath("samplefile.pdf");
        statementPath.setCreationDate("2011-02-28");

        List<StatementPath> statementPathList = new ArrayList<>();
        statementPathList.add(statementPath);

        statementPathItem.setStatementPaths(statementPathList);

        fetchFileDetailsInputRequest = new StatementPathRequest();
        fetchFileDetailsInputRequest.setDsId("123");
        fetchFileDetailsInputRequest.setOrderStartDate(Date.valueOf("2010-12-01"));
        fetchFileDetailsInputRequest.setOrderEndDate(Date.valueOf("2010-12-01"));
    }

    @Test
    @DisplayName("It should return list of StatementPathItem")

    void getFileDetails() throws ParseException {

        List<MbStatementPathTbl> mbStatementPathList = new ArrayList<>();
        mbStatementPathList.add(mbStatementPath);

        List<StatementPathItem> statementPathItemList = new ArrayList<>();
        statementPathItemList.add(statementPathItem);

        when(mbStatementPathRepository.fetchMbStatementPathDetails(anyString(), any(Date.class), any(Date.class))).thenReturn(mbStatementPathList);
        List<StatementPathItem> newStatementPathItemList = mbStatementPathService.getFileDetailsFromDB(fetchFileDetailsInputRequest);

        assertEquals(1,newStatementPathItemList.size());
        assertNotNull(newStatementPathItemList);

    }

    @Test
    @DisplayName("It should return more than 1 StatementPathItem")

    void shouldReturnMoreThanOneStatementPathItem() throws ParseException {
        MbStatementPathTbl  mbStatementPath1 = new MbStatementPathTbl();
        mbStatementPath1.setMbId("567");
        mbStatementPath1.setStatementType("RO");
        mbStatementPath1.setStatementPathId(3438636);
        mbStatementPath1.setRoyaltyMonth(Date.valueOf(LocalDate.of(2010, Month.JANUARY, 1)));
        mbStatementPath1.setDocumentPath("samplefi1e1.pdf");
        mbStatementPath1.setCreationDate(Date.valueOf(LocalDate.now()));
        mbStatementPath1.setLanguageId(1);

        List<MbStatementPathTbl> mbStatementPathList = new ArrayList<>();
        mbStatementPathList.add(mbStatementPath);
        mbStatementPathList.add(mbStatementPath1);

        List<StatementPathItem> statementPathItemList = new ArrayList<>();
        statementPathItemList.add(statementPathItem);

        when(mbStatementPathRepository.fetchMbStatementPathDetails(anyString(), any(Date.class), any(Date.class))).thenReturn(mbStatementPathList);
        List<StatementPathItem> newStatementPathItemList = mbStatementPathService.getFileDetailsFromDB(fetchFileDetailsInputRequest);

        assertEquals(1,newStatementPathItemList.size());
        assertNotNull(newStatementPathItemList);

    }

}
