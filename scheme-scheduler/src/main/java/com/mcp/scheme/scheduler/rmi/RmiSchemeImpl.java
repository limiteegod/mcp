/**
 * 
 */
package com.mcp.scheme.scheduler.rmi;

import com.mcp.core.util.CoreUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.status.SchemeState;
import com.mcp.order.status.TermState;
import com.mcp.rmi.inter.OrderInter;
import com.mcp.rmi.inter.SchemeInter;
import com.mcp.scheme.model.SchemeHm;
import com.mcp.scheme.model.SchemeShare;
import com.mcp.scheme.model.SchemeZh;
import com.mcp.scheme.mongo.service.MgZhService;
import com.mcp.scheme.scheduler.common.ZhModel;
import com.mcp.scheme.service.SchemeHmService;
import com.mcp.scheme.service.SchemeShareService;
import com.mcp.scheme.service.SchemeZhService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * @author ming.li
 *
 */
public class RmiSchemeImpl implements SchemeInter {

    public static Logger log = Logger.getLogger(RmiSchemeImpl.class);
	
	@Autowired
	private SchemeZhService schemeZhService;

    @Autowired
    private SchemeHmService schemeHmService;

    @Autowired
    private SchemeShareService schemeShareService;
	
	@Autowired
	private MgZhService mgZhService;
	
	@Autowired
	private OrderInter orderInter;
	
	@Override
	public Page<SchemeZh> query(String customerId, String gameCode, int status, Pageable page) {
		return schemeZhService.query(customerId, gameCode, status, page);
	}

	@Override
	public void cancelSchemeZh(String schemeId) {
		SchemeZh scheme = schemeZhService.cancel(schemeId);
		//退款
		try {
			orderInter.refundSchemeZh(scheme);
			this.mgZhService.delete(scheme.getGameCode(), scheme.getId());
			
			schemeZhService.updateStatusById(SchemeState.FINISHED.getCode(), scheme.getId());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据id查询追号方案
	 */
	@Override
	public SchemeZh queryZhById(String schemeZhId) {
		return schemeZhService.findOne(schemeZhId);
	}
	
	/**
	 * 保存追号方案，只有在方案已经支付的情况下，才放入mongodb的方案collection中
	 * @param scheme
	 */
	@Override
	public void saveZh(SchemeZh scheme)
	{
		scheme.setAcceptTime(new Date());
		schemeZhService.save(scheme);
		if(scheme.getStatus() == SchemeState.RUNNING.getCode())
		{
			mgZhService.save(scheme);
		}
	}

    /**
     * 保存合买方案
     * @param scheme
     */
    @Override
    public void saveHm(SchemeHm scheme)
    {
        this.schemeHmService.save(scheme);

        //标准的合买方案，记录发起人的所占份额
        if(scheme.getType() == ConstantValues.TSchemeHm_Type_Normal.getCode())
        {
            SchemeShare schemeShare = new SchemeShare();
            schemeShare.setId(CoreUtil.getUUID());
            schemeShare.setAmount(scheme.getcAmount());
            schemeShare.setCustomerId(scheme.getCustomerId());
            schemeShare.setSchemeId(scheme.getId());
            this.schemeShareService.save(schemeShare);
        }
    }

    /**
     * 保存方案份额信息
     * @param schemeShare
     */
    @Override
    public SchemeHm saveSchemeShare(SchemeShare schemeShare)
    {
        schemeShare.setAcceptTime(new Date());
        this.schemeShareService.save(schemeShare);
        return this.schemeHmService.incrfAmount(schemeShare.getAmount(), schemeShare.getSchemeId());
    }

    /**
     * 通过id查询合买方案
     * @param id
     * @return
     */
    @Override
    public SchemeHm queryHmById(String id)
    {
        return this.schemeHmService.findOne(id);
    }

	
	/**
	 * 处理方案中奖，追号方案，如果中奖后停追，得进行退款操作
	 */
	@Override
	public void hit(String schemeId, int schemeType, long bonus, long bonusBeforeTax) throws RemoteException {
        log.info("方案中奖,id:" + schemeId + ",type:" + schemeType + ",bonus:" + bonus);
		if(schemeType == ConstantValues.TScheme_Type_Follow.getCode())
		{
			SchemeZh scheme = schemeZhService.incrBonus(schemeId, bonus, bonusBeforeTax);
			if(scheme.getStatus() == SchemeState.CANCELED.getCode())
			{
				//退款
				orderInter.refundSchemeZh(scheme);
				
				schemeZhService.updateStatusById(SchemeState.FINISHED.getCode(), scheme.getId());
			}
		}
        else if(schemeType == ConstantValues.TScheme_Type_HeMai.getCode())
        {
            //合买方案，更新中奖金额及完成的订单数目
            this.schemeHmService.incrFinishedCount(schemeId, bonus, bonusBeforeTax);
        }
	}
	
	/**
	 * 把未支付的追号方案，设置成已经取消
	 */
	@Override
	public void cancelUnAffordedZh(String gameCode, String startTermCode)
			throws RemoteException {
		this.schemeZhService.cancelUnAfforded(gameCode, startTermCode);
	}
	
	/**
	 * 订单引擎支付完方案之后，调用此方法，更新方案状态
	 */
	@Override
	public void affordScheme(String schemeId, int schemeType)
			throws RemoteException {
		if(schemeType == ConstantValues.TScheme_Type_Follow.getCode())
		{
			SchemeZh zh = schemeZhService.afford(schemeId);
			mgZhService.save(zh);
		}
		else
		{
			throw new CoreException(ErrCode.E1026, ErrCode.codeToMsg(ErrCode.E1026));
		}
	}

    @Override
    public void termChange(String gameCode, String termCode, String nextTermCode, int status) throws RemoteException {
        try {
            if(status == TermState.CALCULATE.getCode())
            {
                //先处理追号方案
                ZhModel zh = new ZhModel();
                zh.termDrawed(gameCode, termCode, nextTermCode);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.info("处理期次状态变化过程中出现错误,gameCode:" + gameCode + ",termCode:" + termCode
                    + ",nextTermCode:" + nextTermCode + ",status:" + status);
        }
    }

}
