package com.hrbl.gts.coreservices.statementpathapi.service;


import com.hrbl.gts.coreservices.statementpathapi.constants.EnvironmentConstants;
import com.hrbl.gts.coreservices.statementpathapi.model.*;
import com.hrbl.gts.coreservices.statementpathapi.utility.FTPFetchFileDetailsUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Service
public class FTPFetchFileDetailsService {
    @Autowired
    private EnvironmentService environmentService;
    @Autowired
    private FTPFetchFileDetailsUtil ftpFetchFileDetailsUtil;


    public List<StatementPathItem> getFileDetailsFromFTP(@NotNull StatementPathRequest request) {
        List<StatementPathItem> statementPathItemList = new ArrayList<>();
        try {
            List<String> pathDetails = new ArrayList<>();
            pathDetails = ftpFetchFileDetailsUtil.getDataFromFTP(request, environmentService);
            if (pathDetails.size() > 0) {
                for (int j = 0; j < pathDetails.size(); j++) {
                    StatementPathItem statementPathItem = new StatementPathItem();
                    statementPathItem.setMbId(request.getDsId());

                    String[] c = pathDetails.get(j).split("/");
                    String[] a = c[9].split("_");
                    String[] b = c[9].split("=");
                    java.util.Date rDate = null;
                    String strDate = null;
                    rDate = EnvironmentConstants.FILESERVER_DATE_FORMAT.parse(a[2] + 01);
                    strDate = EnvironmentConstants.OUTPUT_DATE_FORMAT.format(rDate);
                    statementPathItem.setRoyaltyMonth(strDate);
                    statementPathItem.setStatementType(c[6]);

                    StatementPath statementPath = new StatementPath();
                    statementPath.setStatementPathId(a[2]);
                    statementPath.setDocumentPath("\\".concat(c[6]).concat("\\").concat(c[7]).concat("\\").concat(c[8]).concat("\\").concat(b[0]));
                    statementPath.setCreationDate(b[1]);

                    statementPathItem.getStatementPaths().add(statementPath);
                    statementPathItemList.add(statementPathItem);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            //any exception in fetching response from ftp is not thrown. If there are any DB results they are sent as response
        }
        return statementPathItemList;
    }

}




