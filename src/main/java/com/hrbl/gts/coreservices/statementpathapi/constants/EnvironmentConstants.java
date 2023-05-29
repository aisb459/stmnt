package com.hrbl.gts.coreservices.statementpathapi.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public interface EnvironmentConstants {
    String DB_HOST = "STATEMENT_PATH_DB_URL";
    String DB_USERNAME = "STATEMENT_PATH_DB_USERNAME";
    String DB_PASSWORD = "STATEMENT_PATH_DB_PASSWORD";
    String FTP_USER_NAME = "STATEMENT_PATH_FTP_USERNAME";
    String FTP_PORT = "STATEMENT_PATH_PORT";
    String FTP_PASSWORD = "STATEMENT_PATH_FTP_PASSWORD";
    String FTP_HOST = "STATEMENT_PATH_HOST";
    String FTP_BASE_FILE_PATH = "STATEMENT_PATH_BASE_FILE_PATH";
    String FTP_EXTENSION_FILE_PATH1 = "STATEMENT_PATH_EXTENSION_FILE_PATH1";
    String FTP_EXTENSION_FILE_PATH2 = "STATEMENT_PATH_EXTENSION_FILE_PATH2";
    DateFormat OUTPUT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
    DateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat FILESERVER_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
}
