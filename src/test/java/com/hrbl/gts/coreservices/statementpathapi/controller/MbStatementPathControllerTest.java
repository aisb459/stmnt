package com.hrbl.gts.coreservices.statementpathapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrbl.gts.coreservices.statementpathapi.exception.NoDataFoundException;
import com.hrbl.gts.coreservices.statementpathapi.model.ErrorDetails;
import com.hrbl.gts.coreservices.statementpathapi.model.StatementPathRequest;
import com.hrbl.gts.coreservices.statementpathapi.model.StatementPath;
import com.hrbl.gts.coreservices.statementpathapi.model.StatementPathItem;
import com.hrbl.gts.coreservices.statementpathapi.service.FTPFetchFileDetailsService;
import com.hrbl.gts.coreservices.statementpathapi.service.MbStatementPathService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest
public class MbStatementPathControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MbStatementPathService mbStatementPathService;

    @MockBean
    private FTPFetchFileDetailsService ftpFetchFileDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private StatementPathItem statementPathItem;
    private StatementPath statementPath;

    private StatementPathRequest fetchFileDetailsInputRequest;

    @BeforeEach
    void init() {
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
    @DisplayName("It should return File Details as endpoint response")
    void getFileDetails() throws Exception {
        List<StatementPathItem> statementPathItemList = new ArrayList<>();
        statementPathItemList.add(statementPathItem);

        when(mbStatementPathService.getFileDetailsFromDB(any(StatementPathRequest.class))).thenReturn(statementPathItemList);
        when(ftpFetchFileDetailsService.getFileDetailsFromFTP(any(StatementPathRequest.class))).thenReturn(statementPathItemList);

        this.mockMvc.perform(post("/v1/api/statement-path-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fetchFileDetailsInputRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statementPathItemList", hasSize(2)));


    }

    @Test
    @DisplayName("It should throw NoDataFound Exception when there is no data")
    void shouldThrowNoDataException() throws Exception {
        List<StatementPathItem> statementPathItemList = new ArrayList<>();

        when(mbStatementPathService.getFileDetailsFromDB(any(StatementPathRequest.class))).thenReturn(statementPathItemList);
        when(ftpFetchFileDetailsService.getFileDetailsFromFTP(any(StatementPathRequest.class))).thenReturn(statementPathItemList);

        this.mockMvc.perform(post("/v1/api/statement-path-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fetchFileDetailsInputRequest)))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoDataFoundException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("ERROR: No Data Found for dsId ")));
    }

    @Test
    @DisplayName("It should throw  Exception when there is no dsId")
    void shouldThrowExceptionWhenThereIsNoDsId() throws Exception {
        List<StatementPathItem> statementPathItemList = new ArrayList<>();
        StatementPathRequest statementPathRequest = new StatementPathRequest(null, Date.valueOf("2015-12-01"),Date.valueOf("2015-12-01"));
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode("E");
        errorDetails.setErrorDesc("[dsId is mandatory]");

        when(mbStatementPathService.getFileDetailsFromDB(any(StatementPathRequest.class))).thenReturn(statementPathItemList);
        when(ftpFetchFileDetailsService.getFileDetailsFromFTP(any(StatementPathRequest.class))).thenReturn(statementPathItemList);

        this.mockMvc.perform(post("/v1/api/statement-path-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statementPathRequest)))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception))
                .andExpect(jsonPath("$.errorCode",is(errorDetails.getErrorCode())))
                .andExpect(jsonPath("$.errorDesc",is(errorDetails.getErrorDesc())));
    }

    @Test
    @DisplayName("It should throw  Exception when Start Date is after End date")
    void shouldThrowExceptionWhenStartDateIsAfterEndDate() throws Exception {
        List<StatementPathItem> statementPathItemList = new ArrayList<>();
        StatementPathRequest statementPathRequest = new StatementPathRequest("123", Date.valueOf("2015-12-01"),Date.valueOf("2010-12-01"));

        when(mbStatementPathService.getFileDetailsFromDB(any(StatementPathRequest.class))).thenReturn(statementPathItemList);
        when(ftpFetchFileDetailsService.getFileDetailsFromFTP(any(StatementPathRequest.class))).thenReturn(statementPathItemList);

        this.mockMvc.perform(post("/v1/api/statement-path-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statementPathRequest)))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Invalid input Date")));
    }
    @Test
    @DisplayName("It should throw  Exception when there is invalid date")
    void shouldThrowExceptionWhenThereIsInvalidDate() throws Exception {
        List<StatementPathItem> statementPathItemList = new ArrayList<>();
        StatementPathRequest statementPathRequestWithInvalidDate = new StatementPathRequest();
        statementPathRequestWithInvalidDate.setDsId("123");
        statementPathRequestWithInvalidDate.setOrderStartDate(null);
        statementPathRequestWithInvalidDate.setOrderEndDate(null);

        when(mbStatementPathService.getFileDetailsFromDB(any(StatementPathRequest.class))).thenReturn(statementPathItemList);
        when(ftpFetchFileDetailsService.getFileDetailsFromFTP(any(StatementPathRequest.class))).thenReturn(statementPathItemList);

        this.mockMvc.perform(post("/v1/api/statement-path-details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statementPathRequestWithInvalidDate)))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Invalid input Date")));
    }

}
