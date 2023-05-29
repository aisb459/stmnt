package com.hrbl.gts.coreservices.statementpathapi.utility;

import com.hrbl.gts.coreservices.statementpathapi.constants.EnvironmentConstants;
import com.hrbl.gts.coreservices.statementpathapi.model.StatementPathRequest;
import com.hrbl.gts.coreservices.statementpathapi.service.EnvironmentService;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class FTPFetchFileDetailsUtil {
    String fileLastModifiedTime = null;
    String folderName = null;
    String filepath = null;
    String path = null;


    public List<String> getDataFromFTP(StatementPathRequest request, EnvironmentService environmentService) throws Exception {
        List<String> pathEDetails = null;
        List<String> pathDetails = new ArrayList<>();
        String memberId = request.getDsId().concat("_");

        Date orderStartDate = request.getOrderStartDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(orderStartDate);
        Integer orderStartMonth = calendar.get(Calendar.MONTH);
        Integer orderStartYear = calendar.get(Calendar.YEAR);

        Date orderEndDate = request.getOrderEndDate();
        calendar.setTime(orderEndDate);
        Integer orderEndMonth = calendar.get(Calendar.MONTH);
        Integer orderEndYear = calendar.get(Calendar.YEAR);


        if (orderStartMonth >= 1 && orderStartMonth <= 6) {
            orderStartMonth = 6;
        } else {
            orderStartMonth = 12;
        }

        if (orderEndMonth >= 1 && orderEndMonth <= 6) {
            orderEndMonth = 6;
        } else {
            orderEndMonth = 12;
        }


        FTPClient ftpClient = null;
        ftpClient = createFtpClient(environmentService.getFTPHost(), environmentService.getFTPPort(), environmentService.getFTPUserName(), environmentService.getFTPPassword());

        // use local passive mode to pass firewall
        //ftpClient.enterLocalPassiveMode();
        //ftpClient.configure(new FTPClientConfig(FTPClientConfig.SYST_UNIX));
        String[] pathPrefix = {"E", "O"};
        for (int i = 0; i < 2; i++) {
            path = environmentService.getFTPBaseFilePath().concat(pathPrefix[i]);
            pathEDetails = readFile(ftpClient, path, memberId, orderStartMonth, orderEndMonth, orderStartYear, orderEndYear);
            pathDetails.addAll(pathEDetails);

        }
        closeFtpConnection(ftpClient);
        System.out.println(pathDetails);
        return pathDetails;
    }

    public void closeFtpConnection(FTPClient ftpClient) throws IOException {
        ftpClient.logout();
        ftpClient.disconnect();
    }

    public FTPClient createFtpClient(String ftpHost, Integer ftpPort, String ftpUserName, String ftpPassword) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(ftpHost, ftpPort);
        boolean success = ftpClient.login(ftpUserName, ftpPassword);
        if (!success) {
            System.out.println("ftp login failed");
            //throw new ErrorFetchingStatementException("Unable to connect to FTP. Wrong Credentials");
        }
        ftpClient.configure(new FTPClientConfig(FTPClientConfig.SYST_UNIX));
        return ftpClient;
    }

    public List<String> readFile(FTPClient ftpClient, String path, String memberId, Integer orderStartMonth, Integer orderEndMonth, Integer orderStartYear, Integer orderEndYear) throws IOException {
        File folder = null;
        List pathDetails = new ArrayList();
        System.out.println("start for  filename " + LocalDateTime.now());

        for (int yr = orderStartYear; yr <= orderEndYear; yr++) {

            for (int mnth = orderStartMonth; orderStartMonth <= orderEndMonth; mnth++) {
                folderName = path.concat("/").concat(String.valueOf(yr)).concat("/").concat(String.valueOf(mnth));
                for (FTPFile file : ftpClient.listFiles(folderName)) {
                    String fileName = file.getName();
                    if (fileName != null && fileName != "" && fileName.indexOf(memberId) > -1) {
                        filepath = folderName.concat("/").concat(fileName);
                        Date modifDate = new Date(file.getTimestamp().getTimeInMillis());
                        fileLastModifiedTime = EnvironmentConstants.OUTPUT_DATE_FORMAT.format(modifDate);
                        pathDetails.add(filepath.concat("=").concat(fileLastModifiedTime));
                    }
                }

                if (mnth == 12) {
                    break;
                }
            }
            orderStartMonth = 1;
        }
        System.out.println("end for  filename " + LocalDateTime.now());

        return pathDetails;
    }
}
