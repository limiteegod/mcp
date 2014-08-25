package com.mcp.scheme.dao;

import com.mcp.scheme.model.SchemeShare;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SchemeShareDao extends PagingAndSortingRepository<SchemeShare, String>, JpaSpecificationExecutor<SchemeShare> {

	public List<SchemeShare> findAllBySchemeId(String schemeId);

    @Modifying
    @Query("update SchemeShare t set t.bonus=?1 where t.id=?2")
    public void updateBonusById(long bonus, String id);

}
