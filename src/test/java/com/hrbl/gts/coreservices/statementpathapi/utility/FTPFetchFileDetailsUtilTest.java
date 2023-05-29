package com.hrbl.gts.coreservices.statementpathapi.utility;

import com.hrbl.gts.coreservices.statementpathapi.model.StatementPathRequest;
import com.hrbl.gts.coreservices.statementpathapi.service.EnvironmentService;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FTPFetchFileDetailsUtilTest {
    @InjectMocks
    private FTPFetchFileDetailsUtil ftpFetchFileDetailsUtil;

    @Mock
    private EnvironmentService environmentService;
    private FakeFtpServer fakeFtpServer;
    private FTPClient ftpClient;
    private StatementPathRequest statementPathRequest;
    Integer port;


    @BeforeEach
    public void setup() throws IOException {

        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount("user", "password", "/test/ifs/USSLCSTOR01/personalbussinessrpt/Active/E/2015/12/"));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/test/ifs/USSLCSTOR01/personalbussinessrpt/Active/E/2015/12/"));
        fileSystem.add(new FileEntry("/test/ifs/USSLCSTOR01/personalbussinessrpt/Active/E/2015/12/123_E_201512_01.pdf", "abcdef 1234567890"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(0);
        fakeFtpServer.start();

        port = fakeFtpServer.getServerControlPort();

        statementPathRequest = new StatementPathRequest();
        statementPathRequest.setDsId("123");
        statementPathRequest.setOrderStartDate(Date.valueOf("2015-12-01"));
        statementPathRequest.setOrderEndDate(Date.valueOf("2015-12-01"));

    }



    @Test
    void shouldReturnDataFromFTP() throws Exception {
        List<String> pathDetails = new ArrayList<>();
        when(environmentService.getFTPHost()).thenReturn("localhost");
        when(environmentService.getFTPPort()).thenReturn(port);
        when(environmentService.getFTPUserName()).thenReturn("user");
        when(environmentService.getFTPPassword()).thenReturn("password");
        when(environmentService.getFTPBaseFilePath()).thenReturn("/test/ifs/USSLCSTOR01/personalbussinessrpt/Active/");

        pathDetails = ftpFetchFileDetailsUtil.getDataFromFTP(statementPathRequest, environmentService);
        assertNotNull(pathDetails);

    }

    @Test
    void validateLoginFailed() throws Exception {
        List<String> pathDetails = new ArrayList<>();
        when(environmentService.getFTPHost()).thenReturn("localhost");
        when(environmentService.getFTPPort()).thenReturn(port);
        when(environmentService.getFTPUserName()).thenReturn("user");
        when(environmentService.getFTPPassword()).thenReturn("wrongpassword");
        when(environmentService.getFTPBaseFilePath()).thenReturn("/test/ifs/USSLCSTOR01/personalbussinessrpt/Active/");

        pathDetails = ftpFetchFileDetailsUtil.getDataFromFTP(statementPathRequest, environmentService);
        assertEquals(0, pathDetails.size());

    }

}
