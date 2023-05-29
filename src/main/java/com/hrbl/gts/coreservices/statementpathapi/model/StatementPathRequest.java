package com.hrbl.gts.coreservices.statementpathapi.model;

import com.hrbl.gts.coreservices.statementpathapi.constraints.DateConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DateConstraint(first = "orderStartDate", second =  "orderEndDate", message = "Invalid input Date")
public class StatementPathRequest {

    @NotBlank(message = "dsId is mandatory")
    private String dsId;

    //@NotNull(message = "orderStartDate is mandatory")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderStartDate;

    //@NotBlank(message = "orderEndDate is mandatory")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderEndDate;
}
