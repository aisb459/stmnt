package com.hrbl.gts.coreservices.statementpathapi.controller;

import com.hrbl.gts.coreservices.statementpathapi.exception.NoDataFoundException;
import com.hrbl.gts.coreservices.statementpathapi.model.ErrorDetails;
import com.hrbl.gts.coreservices.statementpathapi.model.StatementPathRequest;
import com.hrbl.gts.coreservices.statementpathapi.model.StatementPathResponse;
import com.hrbl.gts.coreservices.statementpathapi.model.StatementPathItem;
import com.hrbl.gts.coreservices.statementpathapi.service.FTPFetchFileDetailsService;
import com.hrbl.gts.coreservices.statementpathapi.service.MbStatementPathService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class MbStatementPathController {
    @Autowired
    private MbStatementPathService mbStatementPathService;

    @Autowired
    private FTPFetchFileDetailsService ftpFetchFileDetailsService;

    @PostMapping("/statement-path-details")
    public ResponseEntity<StatementPathResponse> getFileDetails(@Valid @RequestBody StatementPathRequest request) throws Exception {
        List<StatementPathItem> statementPathItemList = mbStatementPathService.getFileDetailsFromDB(request);
        statementPathItemList.addAll(ftpFetchFileDetailsService.getFileDetailsFromFTP(request));
        if (statementPathItemList.isEmpty()) {
            throw new NoDataFoundException("ERROR: No Data Found for dsId " + request.getDsId());
        }
        ErrorDetails errorDetails = new ErrorDetails("S", "Success");
        StatementPathResponse statementPathResponse = new StatementPathResponse(statementPathItemList, errorDetails);
        //if exception occurs in fetching and processing response from db the flow stops and error msg is sent
        //any exception caught in fetching response from ftp is not thrown. If there are any DB results they are sent as response
        return new ResponseEntity<>(statementPathResponse, HttpStatus.OK);
    }

}
