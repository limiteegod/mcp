package com.mcp.order.dao;

import com.mcp.order.model.ts.Game;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface GameDao extends PagingAndSortingRepository<Game, String> {
	
	public List<Game> findAllByStatusOrderByCodeDesc(int status);

    public List<Game> findAllByStatus(int status);
	public List<Game> findAll();
	
	public Game findOneByCode(String code);
}
