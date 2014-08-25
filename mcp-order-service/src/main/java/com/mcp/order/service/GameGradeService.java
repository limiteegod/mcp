/**
 * 
 */
package com.mcp.order.service;

import com.mcp.order.dao.GameGradeDao;
import com.mcp.order.model.ts.GameGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ming.li
 *
 */
@Service("gameGradeService")
public class GameGradeService {
	
	@Autowired
	private GameGradeDao gameGradeDao;
	
	public GameGrade save(GameGrade gameGrade)
	{
		return this.gameGradeDao.save(gameGrade);
	}
	
	public List<GameGrade> findAllByGameCode(String gameCode)
	{
		return this.gameGradeDao.findAllByGameCode(gameCode);
	}
    public List<GameGrade> findAllByGameCodeOrderByGLevelAsc(String gameCode)
    {
        return this.gameGradeDao.findAllByGameCodeOrderByGLevelAsc(gameCode);
    }
}
