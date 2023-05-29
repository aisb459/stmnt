package com.hrbl.gts.coreservices.statementpathapi.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mb_statement_path_t")
public class MbStatementPathTbl {
    @Id
    @Column(name = "STMT_PATH_ID")
    private int statementPathId;

    @Column(name = "MB_ID")
    private String mbId;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @Column(name = "ROYALTY_MONTH")
    private Date royaltyMonth;

    @Column(name = "LANGUAGE_ID")
    private int languageId;

    @Column(name = "STATEMENT_TYPE")
    private String statementType;

    @Column(name = "DOCUMENT_PATH")
    private String documentPath;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    @Column(name = "LAST_UPDATE_DATE")
    private Date lastUpdateDate;

    @Column(name = "PROGRAM_CODE")
    private String programCode;


    @Column(name = "VERSION_NUM")
    private Integer versionNum;


}
