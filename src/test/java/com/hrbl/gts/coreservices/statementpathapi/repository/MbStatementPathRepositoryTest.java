package com.hrbl.gts.coreservices.statementpathapi.repository;

import com.hrbl.gts.coreservices.statementpathapi.model.MbStatementPathTbl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@DataJpaTest
public class MbStatementPathRepositoryTest {

    @Autowired
    private MbStatementPathRepository mbStatementPathRepository;

    private MbStatementPathTbl mbStatementPath;

    @BeforeEach
    void init(){
        mbStatementPath = new MbStatementPathTbl();
        mbStatementPath.setMbId("123");
        mbStatementPath.setStatementType("RO");
        mbStatementPath.setStatementPathId(345);
        mbStatementPath.setRoyaltyMonth(Date.valueOf(LocalDate.of(2010, Month.JANUARY,1)));
        mbStatementPath.setDocumentPath("samplefile.pdf");
        mbStatementPath.setCreationDate(Date.valueOf(LocalDate.now()));
        mbStatementPath.setLanguageId(1);
    }

    @Test
    @DisplayName("It should return MbStatementPath details")
    void getMbStatementPathDetails() throws ParseException {
        //this test uses in-memory database(h2 database) and not actual database
        mbStatementPathRepository.save(mbStatementPath);

        List<MbStatementPathTbl> mbStatementPathList = mbStatementPathRepository
                .fetchMbStatementPathDetails(mbStatementPath.getMbId()
                        ,Date.valueOf(LocalDate.of(2010, Month.JANUARY,1))
                        ,Date.valueOf(LocalDate.of(2010, Month.JANUARY,1)));

        assertThat(mbStatementPathList.size()).isEqualTo(1);
    }
}
