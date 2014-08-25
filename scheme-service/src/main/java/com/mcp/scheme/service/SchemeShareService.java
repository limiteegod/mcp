/**
 * 
 */
package com.mcp.scheme.service;

import com.mcp.scheme.dao.SchemeShareDao;
import com.mcp.scheme.model.SchemeShare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ming.li
 *
 */
@Service("schemeShareService")
public class SchemeShareService {
	
	@Autowired
	private SchemeShareDao schemeShareDao;

	public void save(SchemeShare schemeShare)
	{
		this.schemeShareDao.save(schemeShare);
	}

    public List<SchemeShare> findAllBySchemeId(String schemeId)
    {
        return this.schemeShareDao.findAllBySchemeId(schemeId);
    }

    @Transactional
    public void updateBonusById(long bonus, String id)
    {
        this.schemeShareDao.updateBonusById(bonus, id);
    }

}
