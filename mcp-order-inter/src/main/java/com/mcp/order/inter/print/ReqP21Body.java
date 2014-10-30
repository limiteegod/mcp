package com.mcp.order.inter.print;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

import com.mcp.order.inter.ReqBody;

import java.util.List;

/**
 * Created by CH on 2014/10/30.
 */
public class ReqP21Body  extends ReqBody{

    private List<ReqP02Body> reqP02BodyList;

    public List<ReqP02Body> getReqP02BodyList() {
        return reqP02BodyList;
    }

    public void setReqP02BodyList(List<ReqP02Body> reqP02BodyList) {
        this.reqP02BodyList = reqP02BodyList;
    }

    @Override
    public void validate() throws CoreException {

        if (reqP02BodyList == null){
            throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
        }else if (reqP02BodyList.isEmpty()){
            throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
        }
    }
}
