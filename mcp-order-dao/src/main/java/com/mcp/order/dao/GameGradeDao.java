package com.mcp.order.dao;

import com.mcp.order.model.ts.GameGrade;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface GameGradeDao extends PagingAndSortingRepository<GameGrade, String> {

	public List<GameGrade> findAllByGameCode(String gameCode);
    public List<GameGrade> findAllByGameCodeOrderByGLevelAsc(String gameCode);
}
