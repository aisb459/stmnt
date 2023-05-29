package com.hrbl.gts.coreservices.statementpathapi.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.hrbl.gts.coreservices.statementpathapi.constants.EnvironmentConstants;

@ExtendWith(MockitoExtension.class)
public class EnvironmentServiceTest {
    @InjectMocks
    private EnvironmentService environmentService;
    @Mock
    private Environment environment;

    public static final Map<String, String> ENVIRONMENT_VARIABLES = new HashMap<>() {{
        put(EnvironmentConstants.DB_HOST, "test_db_host");
        put(EnvironmentConstants.DB_USERNAME, "test_db_username");
        put(EnvironmentConstants.DB_PASSWORD, "test_db_password");
        put(EnvironmentConstants.FTP_USER_NAME, "test_ftp_username");
        put(EnvironmentConstants.FTP_PORT, "123");
        put(EnvironmentConstants.FTP_PASSWORD, "test_ftp_password");
        put(EnvironmentConstants.FTP_HOST, "test_ftp_host");
        put(EnvironmentConstants.FTP_BASE_FILE_PATH, "test_ftp_base_file_path");
        put(EnvironmentConstants.FTP_EXTENSION_FILE_PATH1, "test_ftp_extension_file_path1");
        put(EnvironmentConstants.FTP_EXTENSION_FILE_PATH2, "test_ftp_extension_file_path2");

    }};

    public void removeEnvironmentVariables(Map<String, String> environmentVariables) {
        environmentVariables.keySet().forEach(System::clearProperty);
    }
    @Test
    @DisplayName("It should return DB Host")
    void validateDBHost() {
        String expectedValue = ENVIRONMENT_VARIABLES.get(EnvironmentConstants.DB_HOST);
        when(environment.getProperty(anyString())).thenReturn(expectedValue);
        String actualValue = environmentService.getDBHost();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("It should throw exception if DB Host is missing")
    void shouldThrowExceptionIfDBHostMissing() {
        removeEnvironmentVariables(ENVIRONMENT_VARIABLES);
        assertThrows(RuntimeException.class, () -> this.environmentService.getDBHost());
    }

    @Test
    @DisplayName("It should return DB Username")
    void validateDBUsername() {
        String expectedValue = ENVIRONMENT_VARIABLES.get(EnvironmentConstants.DB_USERNAME);
        when(environment.getProperty(anyString())).thenReturn(expectedValue);
        String actualValue = environmentService.getDBUsername();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("It should throw Exception if DB Username is missing")
    void shouldThrowExceptionIfDBUsernameMissing() {
        removeEnvironmentVariables(ENVIRONMENT_VARIABLES);
        assertThrows(RuntimeException.class, () -> this.environmentService.getDBUsername());
    }

    @Test
    @DisplayName("It should return DB Password")
    void validateDBPassword() {
        String expectedValue = ENVIRONMENT_VARIABLES.get(EnvironmentConstants.DB_PASSWORD);
        when(environment.getProperty(anyString())).thenReturn(expectedValue);
        String actualValue = environmentService.getDBPassword();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("It should throw Exception if DB Username is missing")
    void shouldThrowExceptionIfDBPasswordMissing() {
        removeEnvironmentVariables(ENVIRONMENT_VARIABLES);
        assertThrows(RuntimeException.class, () -> this.environmentService.getDBPassword());
    }


    @Test
    @DisplayName("It should return FTP Username")
    void validateFTPUsername() {
        String expectedValue = ENVIRONMENT_VARIABLES.get(EnvironmentConstants.FTP_USER_NAME);
        when(environment.getProperty(anyString())).thenReturn(expectedValue);
        String actualValue = environmentService.getFTPUserName();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("It should throw exception if FTP Username is missing")
    void shouldThrowExceptionIfFTPUserNameMissing() {
        removeEnvironmentVariables(ENVIRONMENT_VARIABLES);
        assertThrows(RuntimeException.class, () -> this.environmentService.getFTPUserName());
    }
    @Test
    @DisplayName("It should return FTP Port")
    void validateFTPPort() {
        String expectedValue = ENVIRONMENT_VARIABLES.get(EnvironmentConstants.FTP_PORT);
        when(environment.getProperty(anyString())).thenReturn(expectedValue);
        String actualValue = String.valueOf(environmentService.getFTPPort());
        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("It should throw exception if FTP Port is missing")
    void shouldThrowExceptionIfFTPPortMissing() {
        removeEnvironmentVariables(ENVIRONMENT_VARIABLES);
        assertThrows(RuntimeException.class, () -> this.environmentService.getFTPPort());
    }
    @Test
    @DisplayName("It should return FTP Password")
    void validateFTPPassword() {
        String expectedValue = ENVIRONMENT_VARIABLES.get(EnvironmentConstants.FTP_PASSWORD);
        when(environment.getProperty(anyString())).thenReturn(expectedValue);
        String actualValue = environmentService.getFTPPassword();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("It should throw exception if FTP Password is missing")
    void shouldThrowExceptionIfFTPPasswordMissing() {
        removeEnvironmentVariables(ENVIRONMENT_VARIABLES);
        assertThrows(RuntimeException.class, () -> this.environmentService.getFTPPassword());
    }
    @Test
    @DisplayName("It should return FTP Host")
    void validateFTPHost() {
        String expectedValue = ENVIRONMENT_VARIABLES.get(EnvironmentConstants.FTP_HOST);
        when(environment.getProperty(anyString())).thenReturn(expectedValue);
        String actualValue = environmentService.getFTPHost();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("It should throw exception if FTP Host is missing")
    void shouldThrowExceptionIfFTPHostMissing() {
        removeEnvironmentVariables(ENVIRONMENT_VARIABLES);
        assertThrows(RuntimeException.class, () -> this.environmentService.getFTPHost());
    }
    @Test
    @DisplayName("It should return FTP BaseFilePath")
    void validateFTPBaseFilePath() {
        String expectedValue = ENVIRONMENT_VARIABLES.get(EnvironmentConstants.FTP_BASE_FILE_PATH);
        when(environment.getProperty(anyString())).thenReturn(expectedValue);
        String actualValue = environmentService.getFTPBaseFilePath();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("It should throw exception if FTP BaseFilePath is missing")
    void shouldThrowExceptionIfFTPBaseFilePathMissing() {
        removeEnvironmentVariables(ENVIRONMENT_VARIABLES);
        assertThrows(RuntimeException.class, () -> this.environmentService.getFTPBaseFilePath());
    }
    @Test
    @DisplayName("It should return FTP ExtensionFilePath1")
    void validateFTPExtensionFilePath1() {
        String expectedValue = ENVIRONMENT_VARIABLES.get(EnvironmentConstants.FTP_EXTENSION_FILE_PATH1);
        when(environment.getProperty(anyString())).thenReturn(expectedValue);
        String actualValue = environmentService.getFTPExtensionFilePath1();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("It should throw exception if FTP ExtensionFilePath1 is missing")
    void shouldThrowExceptionIfFTPExtensionFilePath1Missing() {
        removeEnvironmentVariables(ENVIRONMENT_VARIABLES);
        assertThrows(RuntimeException.class, () -> this.environmentService.getFTPExtensionFilePath1());
    }
    @Test
    @DisplayName("It should return FTP ExtensionFilePath2")
    void validateFTPExtensionFilePath2() {
        String expectedValue = ENVIRONMENT_VARIABLES.get(EnvironmentConstants.FTP_EXTENSION_FILE_PATH2);
        when(environment.getProperty(anyString())).thenReturn(expectedValue);
        String actualValue = environmentService.getFTPExtensionFilePath2();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("It should throw exception if FTP ExtensionFilePath2 is missing")
    void shouldThrowExceptionIfFTPExtensionFilePath2Missing() {
        removeEnvironmentVariables(ENVIRONMENT_VARIABLES);
        assertThrows(RuntimeException.class, () -> this.environmentService.getFTPExtensionFilePath2());
    }
}
