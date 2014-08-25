package com.mcp.order.gateway.control;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.DesUtil;
import com.mcp.core.util.StringUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.gateway.annotation.JsonBody;
import com.mcp.order.gateway.annotation.JsonHead;
import com.mcp.order.gateway.annotation.JsonTypeBody;
import com.mcp.order.gateway.annotation.McpTypeUser;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.account.RepA01Body;
import com.mcp.order.inter.account.ReqA01Body;
import com.mcp.order.inter.admin.*;
import com.mcp.order.inter.util.PageInfoUtil;
import com.mcp.order.model.admin.Admini;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.common.LotteryContext;
import com.mcp.order.model.mongo.MgLogin;
import com.mcp.order.model.mongo.MgPrint;
import com.mcp.order.model.mongo.MgTermReport;
import com.mcp.order.model.ts.*;
import com.mcp.order.mongo.common.MgConstants;
import com.mcp.order.mongo.service.MgAutoIncrIdService;
import com.mcp.order.mongo.service.MgLoginService;
import com.mcp.order.mongo.service.MgPrintService;
import com.mcp.order.mongo.service.MgTermReportService;
import com.mcp.order.service.AdminiService;
import com.mcp.order.service.StationService;
import com.mcp.order.service.TermService;
import com.mcp.order.service.TicketService;
import com.mcp.order.status.TicketState;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by limitee on 2014/7/30.
 */
@Controller
@RequestMapping("/admin")
public class AdminControl {

    private static Logger log = Logger.getLogger(AdminControl.class);

    @Autowired
    private AdminiService adminiService;

    @Autowired
    private MgLoginService mgLoginService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MgAutoIncrIdService mgAutoIncrIdService;

    @Autowired
    private MgPrintService mgPrintService;

    @Autowired
    private StationService stationService;

    @Autowired
    private MgTermReportService mgTermReportService;

    @Autowired
    private TermService termService;

    /**
     * 管理员登录
     */
    @RequestMapping(value = "/login.htm")
    public String login(@JsonHead(value="head", checkChannel=false) Head head, @McpTypeUser("*") Object guest, @JsonTypeBody(value="body", group="AD", cmd="01") ReqAD01Body body,
                      ModelMap modelMap) throws Exception {
        RepAD01Body repAD01Body = new RepAD01Body();
        log.info("name:" + body.getName() + ",pass:" + body.getPassword());
        Admini admini = this.adminiService.findOneByNameAndPassword(body.getName(), body.getPassword());
        if(admini == null)
        {
            throw new CoreException(ErrCode.E1004);
        }
        repAD01Body.setUser(admini);
        String idInMg = admini.getType() + "-" + body.getName();
        MgLogin mgLogin = mgLoginService.findById(idInMg);
        Date now = new Date();
        if(mgLogin == null || mgLogin.getExpiredTime().getTime() < now.getTime())
        {
            mgLogin = new MgLogin();
            mgLogin.setId(idInMg);
            mgLogin.setSt(DesUtil.getKey(24));
            mgLogin.setLastActiveTime(now);
            mgLogin.setExpiredTime(new Date(now.getTime() + Constants.LOGIN_EXPIRE_MILISECOND));
            mgLoginService.save(mgLogin);
        }
        else
        {
            mgLoginService.active(idInMg, admini.getType());
        }
        repAD01Body.setSt(mgLogin.getSt());
        modelMap.put("response", repAD01Body);
        return "plainTypeJsonView";
    }

    /**
     * 对某一张票重新出票，可以强制更新票的id，可以强制更新票的状态，也可以通过指定stationid来
     * 切换出票口。
     */
    @RequestMapping(value = "/addToPrintQueen.htm")
    public String addToPrintQueen(@JsonHead(value="head", checkChannel=false) Head head, @McpTypeUser("3") Admini admini, @JsonTypeBody(value="body", group="AD", cmd="02") ReqAD02Body body,
                        ModelMap modelMap) throws Exception {
        RepAD02Body repAD02Body = new RepAD02Body();
        String ticketId = body.getTicketId();
        TTicket ticket = this.ticketService.findOne(ticketId);
        if(ticket == null)
        {
            throw new CoreException(ErrCode.E2056);
        }
        if(ticket.getStatus() == TicketState.PRINT_SUCCESS.getCode() || ticket.getStatus() == TicketState.REFUNDED.getCode())
        {
            throw new CoreException(ErrCode.E2057);
        }
        if(body.isResetStatus())
        {
            this.ticketService.updateStatusById(TicketState.WAITING_PRINT.getCode(), ticketId);
        }
        if(!StringUtil.isEmpty(body.getStationId()))
        {
            this.ticketService.updatePrintStationIdById(body.getStationId(), ticketId);
        }
        if(body.isResetId())
        {
            String newId = CoreUtil.getUUID();
            this.ticketService.updateIdById(newId, ticketId);
            ticketId = newId;
        }
        MgPrint mgPrint = new MgPrint();
        mgPrint.setId(mgAutoIncrIdService.getAutoIdAndIncrByName(MgConstants.MG_PRINT_ID).getValue());
        mgPrint.setOrderId(ticket.getOrderId());
        mgPrint.setGameCode(ticket.getGameCode());
        mgPrint.setTermCode(ticket.getTermCode());

        Station pStation = this.stationService.findOne(ticket.getPrinterStationId());
        mgPrintService.save(pStation.getCode(), mgPrint);

        modelMap.put("response", repAD02Body);
        return "plainTypeJsonView";
    }

    /**
     * 获得期次统计信息
     * @param head
     * @param admini
     * @param body
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getTermReportInfo.htm")
    public String getTermReportInfo(@JsonHead(value="head", checkChannel=false) Head head, @McpTypeUser("3") Admini admini, @JsonTypeBody(value="body", group="AD", cmd="03") ReqAD03Body body,
                                  ModelMap modelMap) throws Exception {
        RepAD03Body repAD03Body = new RepAD03Body();
        PageRequest pr = new PageRequest(body.getPage(), body.getSize());
        Page<MgTermReport> pageRst = this.mgTermReportService.findAll(body.getGameCode(), body.getTermCode(), body.getStationCode(), body.getOrderType(), body.getRptType(), pr);
        repAD03Body.setReportList(pageRst.getContent());
        repAD03Body.setPi(PageInfoUtil.getPageInfo(pageRst));
        modelMap.put("response", repAD03Body);
        return "plainTypeJsonView";
    }

    /**
     * 获得期次信息
     * @param head
     * @param admini
     * @param body
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getTermInfo.htm")
    public String getTermInfo(@JsonHead(value="head", checkChannel=false) Head head, @McpTypeUser("3") Admini admini, @JsonTypeBody(value="body", group="AD", cmd="04") ReqAD04Body body,
                                    ModelMap modelMap) throws Exception {
        RepAD04Body repAD04Body = new RepAD04Body();
        PageRequest pr = new PageRequest(body.getPage(), body.getSize());
        int gameType = body.getGameType();
        if(gameType > 0)    //有游戏类型属性
        {
            List<String> gameCodeList = new ArrayList<String>();
            List<Game> gameList = LotteryContext.getInstance().getAllGames();
            for(Game g:gameList)
            {
                if(g.getType() == gameType)
                {
                    gameCodeList.add(g.getCode());
                }
            }
            Page<Term> rst = this.termService.query(gameCodeList, body.getStatusList(), body.getExStatusList(), pr);
            repAD04Body.setRst(rst.getContent());
            repAD04Body.setPi(PageInfoUtil.getPageInfo(rst));
        }
        else
        {
            Page<Term> rst = this.termService.query(body.getGameCode(), body.getTermCode(), body.getStatusList(), body.getExStatusList(), pr);
            repAD04Body.setRst(rst.getContent());
            repAD04Body.setPi(PageInfoUtil.getPageInfo(rst));
        }
        modelMap.put("response", repAD04Body);
        return "plainTypeJsonView";
    }


    /**
     * 订单查询
     * @param head
     * @param admini
     * @param body
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getOrderInfo.htm")
    public String getOrderInfo(@JsonHead(value="head", checkChannel=false) Head head, @McpTypeUser("3") Admini admini, @JsonTypeBody(value="body", group="AD", cmd="05") ReqAD05Body body,
                              ModelMap modelMap) throws Exception {
        RepAD05Body repAD05Body = new RepAD05Body();
        PageRequest pr = new PageRequest(body.getPage(), body.getSize());





        modelMap.put("response", repAD05Body);
        return "plainTypeJsonView";
    }
}
