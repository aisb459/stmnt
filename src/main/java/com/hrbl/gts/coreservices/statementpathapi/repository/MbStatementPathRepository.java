package com.hrbl.gts.coreservices.statementpathapi.repository;

import com.hrbl.gts.coreservices.statementpathapi.model.MbStatementPathTbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface MbStatementPathRepository extends JpaRepository<MbStatementPathTbl, Integer> {
    @Query(value = "select t from MbStatementPathTbl t where t.mbId=:mbId and t.languageId=1 and t.royaltyMonth between :startDate and :endDate order by t.royaltyMonth,t.statementPathId")
    List<MbStatementPathTbl> fetchMbStatementPathDetails(@Param("mbId") String mbId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
