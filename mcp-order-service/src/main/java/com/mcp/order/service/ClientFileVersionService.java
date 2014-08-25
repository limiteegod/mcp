package com.mcp.order.service;/*
 * User: yeeson he
 * Date: 13-8-5
 * Time: 下午12:10
 */

import com.mcp.order.dao.ClientFileVersionDao;
import com.mcp.order.model.admin.ClientFileVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clientFileVersionService")
public class ClientFileVersionService {
	
    @Autowired
    private ClientFileVersionDao clientFileVersionDao;

    /**
     * 获得最近的文件版本
     * @param clientCode
     * @param fileTypeCode
     * @return
     */
    public ClientFileVersion getLatest(String clientCode, String fileTypeCode)
    {
    	PageRequest pr = new PageRequest(0, 1);
    	List<ClientFileVersion> clientFileVersionList = this.clientFileVersionDao.findOneByClientCodeAndFileTypeCodeOrderByFileVersionCodeDesc(clientCode, fileTypeCode, pr);
    	if(clientFileVersionList.size() > 0)
    	{
    		return clientFileVersionList.get(0);
    	}
    	return null;
    }
}
