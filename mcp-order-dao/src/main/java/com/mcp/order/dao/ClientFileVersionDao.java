package com.mcp.order.dao;/*
 * User: yeeson he
 * Date: 13-8-5
 * Time: 下午12:17
 */

import com.mcp.order.model.admin.ClientFileVersion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ClientFileVersionDao extends PagingAndSortingRepository<ClientFileVersion, String> {
    
	public ClientFileVersion findOneByFileTypeCodeAndFileVersionCode(String type, String version);
    
    public List<ClientFileVersion> findOneByClientCodeAndFileTypeCodeOrderByFileVersionCodeDesc(String clientCode, String fileTypeCode, Pageable pageable);
}
