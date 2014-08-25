package com.mcp.scheme.dao;

import com.mcp.scheme.model.SchemeHm;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SchemeHmDao extends PagingAndSortingRepository<SchemeHm, String>, JpaSpecificationExecutor<SchemeHm> {

    @Modifying
    @Query("update SchemeHm t set t.status=?1 where t.id=?2")
    public void updateStatusById(int status, String id);
}
