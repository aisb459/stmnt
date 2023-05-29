package com.hrbl.gts.coreservices.statementpathapi.service;

import com.hrbl.gts.coreservices.statementpathapi.constants.EnvironmentConstants;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class EnvironmentService {
    private final Environment environment;

    public EnvironmentService(Environment environment) {
        this.environment = environment;
    }

    public String getDBHost() {
        String dbHost = this.environment.getProperty(EnvironmentConstants.DB_HOST);
        if (dbHost == null) {
            throw new RuntimeException("DBHost is incorrectly configured. DB_HOST env var missing!");
        }
        return dbHost;
    }

    public String getDBUsername() {
        String dbUsername = this.environment.getProperty(EnvironmentConstants.DB_USERNAME);
        if (dbUsername == null) {
            throw new RuntimeException("DB_USERNAME is incorrectly configured. DB_USERNAME env var missing!");
        }
        return dbUsername;
    }
    public String getDBPassword() {
        String dbPassword = this.environment.getProperty(EnvironmentConstants.DB_PASSWORD);
        if (dbPassword == null) {
            throw new RuntimeException("DB_PASSWORD is incorrectly configured. DB_PASSWORD env var missing!");
        }
        return dbPassword;
    }


    public String getFTPUserName() {
        String userName = this.environment.getProperty(EnvironmentConstants.FTP_USER_NAME);
        if (userName == null) {
            throw new RuntimeException("FTP_USER_NAME is incorrectly configured. FTP_USER_NAME env var missing!");
        }
        return userName;
    }

    public Integer getFTPPort() {
        String ftpPort = this.environment.getProperty(EnvironmentConstants.FTP_PORT);
        if (ftpPort == null) {
            throw new RuntimeException("FTP_PORT is incorrectly configured. FTP_PORT env var missing!");
        }
        Integer port = Integer.valueOf(ftpPort);
        return port;
    }
    public String getFTPPassword(){
        String password = this.environment.getProperty(EnvironmentConstants.FTP_PASSWORD);
        if (password == null) {
            throw new RuntimeException("FTP_PASSWORD is incorrectly configured. FTP_PASSWORD env var missing!");
        }
        return password;
    }
    public String getFTPHost(){
        String host = this.environment.getProperty(EnvironmentConstants.FTP_HOST);
        if (host == null) {
            throw new RuntimeException("Host is incorrectly configured. FTP_HOST env var missing!");
        }
        return host;
    }
    public String getFTPBaseFilePath(){
        String baseFilePath = this.environment.getProperty(EnvironmentConstants.FTP_BASE_FILE_PATH);
        if (baseFilePath == null) {
            throw new RuntimeException("BaseFilePath is incorrectly configured. FTP_BASE_FILE_PATH env var missing!");
        }
        return baseFilePath;
    }
    public String getFTPExtensionFilePath1(){
        String extensionFilePath1 = this.environment.getProperty(EnvironmentConstants.FTP_EXTENSION_FILE_PATH1);
        if (extensionFilePath1 == null) {
            throw new RuntimeException("ExtensionFilePath1 is incorrectly configured. FTP_EXTENSION_FILE_PATH1 env var missing!");
        }
        return extensionFilePath1;
    }
    public String getFTPExtensionFilePath2(){
        String extensionFilePath2 = this.environment.getProperty(EnvironmentConstants.FTP_EXTENSION_FILE_PATH2);
        if (extensionFilePath2 == null) {
            throw new RuntimeException("ExtensionFilePath2 is incorrectly configured. FTP_EXTENSION_FILE_PATH2 env var missing!");
        }
        return extensionFilePath2;
    }
}
