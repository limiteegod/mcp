/**
 *
 */
package com.mcp.order.service.js;

import com.mcp.order.dao.jc.JOddsDao;
import com.mcp.order.dao.specification.JOddsSpecification;
import com.mcp.order.model.jc.JOdds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author ming.li
 */
@Service("jOddsService")
public class JOddsService {
	
    @Autowired
    private JOddsDao oddsDao;
    
    public JOdds save(JOdds jOdds)
    {
    	return this.oddsDao.save(jOdds);
    }
    
    /**
	 * 根据游戏的代码，场次，玩法，获得最新的赔率信息
	 */
	public JOdds getRecent(String gameCode, String matchCode, String oddsCode, String playTypeCode)
	{
		Specifications<JOdds> specs = Specifications.where(JOddsSpecification.isGameCodeEqual(gameCode));
		specs = specs.and(JOddsSpecification.isMatchCodeEqual(matchCode));
		specs = specs.and(JOddsSpecification.isPlayTypeCodeEqual(playTypeCode));
		specs = specs.and(JOddsSpecification.isOddsCodeEqual(oddsCode));
		
		Sort sort = new Sort(Direction.DESC, "createTime");
    	PageRequest pr = new PageRequest(0, 1, sort);
    	Page<JOdds> odPage = this.oddsDao.findAll(specs, pr);
    	List<JOdds> odList = odPage.getContent();
    	if(odList.size() > 0)
    	{
    		JOdds odds = odList.get(0);
    		return odds;
    	}
    	return null;
	}
}
