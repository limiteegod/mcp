package com.mcp.order.gateway.control;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.DesUtil;
import com.mcp.core.util.StringUtil;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.gateway.annotation.JsonHead;
import com.mcp.order.gateway.annotation.JsonTypeBody;
import com.mcp.order.gateway.annotation.McpTypeUser;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.admin.*;
import com.mcp.order.inter.operation.RepAP01Body;
import com.mcp.order.inter.operation.ReqAP01Body;
import com.mcp.order.inter.util.PageInfoUtil;
import com.mcp.order.model.admin.Admini;
import com.mcp.order.model.admin.Station;
import com.mcp.order.model.admin.UserOperation;
import com.mcp.order.model.mongo.MgLogin;
import com.mcp.order.model.mongo.MgPrint;
import com.mcp.order.model.mongo.MgTermReport;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.mongo.common.MgConstants;
import com.mcp.order.mongo.service.MgAutoIncrIdService;
import com.mcp.order.mongo.service.MgLoginService;
import com.mcp.order.mongo.service.MgPrintService;
import com.mcp.order.mongo.service.MgTermReportService;
import com.mcp.order.service.*;
import com.mcp.order.status.TicketState;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * Created by limitee on 2014/7/30.
 */
@Controller
@RequestMapping("/admin/operation")
public class AdminOperationControl {

    private static Logger log = Logger.getLogger(AdminOperationControl.class);

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
    private OperationService operationService;

    @Autowired
    private UserOperationService userOperationService;

    /**
     * 管理员权限查询
     */
    @RequestMapping(value = "/query.htm")
    public String query(@JsonHead(value="head", checkChannel=false) Head head, @McpTypeUser("3") Admini admini, @JsonTypeBody(value="body", group="AP", cmd="01") ReqAP01Body body,
                      ModelMap modelMap) throws Exception {
        RepAP01Body repAP01Body = new RepAP01Body();

        List<UserOperation> userOperationList = this.userOperationService.findAllByUserType(head.getUserType());
        for(UserOperation userOperation:userOperationList)
        {
            userOperation.setOperation(this.operationService.findOneById(userOperation.getOperationId()));
        }
        repAP01Body.setRst(userOperationList);

        modelMap.put("response", repAP01Body);
        return "plainTypeJsonView";
    }
}
