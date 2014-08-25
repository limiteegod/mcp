package com.mcp.scheme.dao;

import com.mcp.scheme.model.SchemeZh;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SchemeZhDao extends PagingAndSortingRepository<SchemeZh, String>, JpaSpecificationExecutor<SchemeZh> {
	
	@Modifying
	@Query("update SchemeZh t set t.status=?1 where t.id=?2")
	public int updateStatusById(int status, String id);
	
	@Modifying
	@Query("update SchemeZh t set t.status=?1 where t.gameCode=?2 and t.startTermCode=?3 and t.status=?4")
	public int updateStatusByGameCodeAndStartTermCodeAndStatus(int newStatus, String gameCode, String startTermCode, int status);
	
}
