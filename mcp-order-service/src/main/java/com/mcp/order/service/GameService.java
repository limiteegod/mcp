/**
 * 
 */
package com.mcp.order.service;

import com.mcp.order.dao.GameDao;
import com.mcp.order.model.ts.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ming.li
 *
 */
@Service("gameService")
public class GameService {
	
	@Autowired
	private GameDao gameDao;
	
	public Game save(Game game)
	{
		return this.gameDao.save(game);
	}

	public List<Game> findAllByStatus(int status)
	{
		return this.gameDao.findAllByStatusOrderByCodeDesc(status);
	}
    @Transactional(propagation = Propagation.NOT_SUPPORTED ,readOnly = true)
    public List<Game> findAllByStatusNew(int status)
    {
        return this.gameDao.findAllByStatus(status);
    }
    public List<Game> findAll()
    {
        return this.gameDao.findAll();
    }
    
    public Game findOneByCode(String code)
    {
    	return this.gameDao.findOneByCode(code);
    }
}
