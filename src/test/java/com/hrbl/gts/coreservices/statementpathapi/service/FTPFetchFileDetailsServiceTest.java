package com.hrbl.gts.coreservices.statementpathapi.service;

import com.hrbl.gts.coreservices.statementpathapi.model.StatementPathItem;
import com.hrbl.gts.coreservices.statementpathapi.model.StatementPathRequest;
import com.hrbl.gts.coreservices.statementpathapi.utility.FTPFetchFileDetailsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FTPFetchFileDetailsServiceTest {
    @InjectMocks
    private FTPFetchFileDetailsService ftpFetchFileDetailsService;
    @Mock
    private FTPFetchFileDetailsUtil ftpFetchFileDetailsUtil;
    @Autowired
    private  EnvironmentService environmentService;

    private List<String> fileAndDateList = new ArrayList<>();
    private StatementPathRequest statementPathRequest;

    @BeforeEach
    void init() {
        fileAndDateList.add("//ifs/USSLCSTOR01/personalbussinessrpt/Active/E/2015/12/100496617_E_201512_01.pdf=2016-02-09T12:00:00+0530");

        statementPathRequest = new StatementPathRequest();
        statementPathRequest.setDsId("123");
        statementPathRequest.setOrderStartDate(Date.valueOf("2010-12-01"));
        statementPathRequest.setOrderEndDate(Date.valueOf("2010-12-01"));
    }

    @Test
    @DisplayName("It should return StatementPathItem list")
    void getFileDetailsFromFTP() throws Exception {
        when(ftpFetchFileDetailsUtil.getDataFromFTP(eq(statementPathRequest),eq(environmentService)))
                .thenReturn(fileAndDateList);
        List<StatementPathItem> actualStatementPathItem = ftpFetchFileDetailsService.getFileDetailsFromFTP(statementPathRequest);
        assertEquals(1,actualStatementPathItem.size());
        assertNotNull(actualStatementPathItem);

    }

    @Test
    @DisplayName("It should throw Exception from FTP")
    void shouldThrowExceptionFromFTP() throws Exception {
        fileAndDateList.clear();
        fileAndDateList.add("invalidPath/invalidFile.pdf=invalidTimeStamp");
        when(ftpFetchFileDetailsUtil.getDataFromFTP(eq(statementPathRequest),eq(environmentService)))
                .thenReturn(fileAndDateList);
        List<StatementPathItem> actualStatementPathItem = ftpFetchFileDetailsService.getFileDetailsFromFTP(statementPathRequest);
        assertEquals(0,actualStatementPathItem.size());
    }


}
