package com.mcp.order.status;/*
 * User: yeeson he
 * Date: 13-8-27
 * Time: 下午12:13
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public enum TermState {
    INIT("初始状态", 1000) {
        public void next(StateCallback callback, Properties param) {
           callback.update(TermState.NOT_ON_SALE.getCode(), param);
        }

        public String getOperation() {
            return "<span>无需处理</span>";
        }
    },
    NOT_ON_SALE("未开售", 1100) {
        public void next(StateCallback callback, Properties param) {
            callback.update(TermState.ON_SALE.getCode(), param);
        }

        public String getOperation() {
            return "<span>无需处理</span>";
        }
    },
    PRE_ON_SALE("准备开售中", 1150) {
        public void next(StateCallback callback, Properties param) {
            
        }

        public String getOperation() {
            return "<span>无需处理</span>";
        }
    },
    ON_SALE("正在销售", 1200) {
        public void next(StateCallback callback, Properties param) {
            String op = param.getProperty("OPType");
            if("PAUSE".equals(op)){
                callback.update(TermState.PAUSE.getCode(), param);
            }else if("END".equals(op)){
                callback.update(TermState.END.getCode(), param);
            }else{
                throw new RuntimeException("无此操作");
            }
        }

        public String getOperation() {
            return "<a target=\"dialog\" href=\"man/term/pausePanel?code=#code#\" mask=\"true\" title=\"销售暂停\"><span>销售暂停</span></a>";
        }
    }, PAUSE("销售暂停", 1300) {
        public void next(StateCallback callback, Properties param) {
            callback.update(TermState.ON_SALE.getCode(), param);
        }

        public String getOperation() {
            return "<a target=\"dialog\" href=\"man/term/restorePanel?code=#code#\" mask=\"true\" title=\"恢复销售\"><span class=\"redword\">恢复销售</span></a>";
        }
    }, PREEND("销售结束中", 1390) {
        public void next(StateCallback callback, Properties param) {
            callback.update(TermState.SEND.getCode(), param);
        }

        public String getOperation() {
            return "<span>自动处理中</span>";
        }
    }, END("销售结束", 1400) {
        public void next(StateCallback callback, Properties param) {
            callback.update(TermState.SEND.getCode(), param);
        }

        public String getOperation() {
            return "<span>自动处理中</span>";
//            return "<a target=\"dialog\" width=\"650\" height=\"410\" href=\"man/term/kaijiangPanel?code=#code#\" mask=\"true\" title=\"录入开奖号码\">录入开奖号码</a>";
        }
    }, SEND("后台销售结束", 1450) {
        public void next(StateCallback callback, Properties param) {
            callback.update(TermState.SYNCHRONIZING.getCode(), param);
        }

        public String getOperation() {
            return "<span>自动处理中</span>";
//            return "<a target=\"dialog\" width=\"650\" height=\"410\" href=\"man/term/kaijiangPanel?code=#code#\" mask=\"true\" title=\"录入开奖号码\">录入开奖号码</a>";
        }
    }, SYNCHRONIZING("销售同步中", 1460) {
    	//TODO 增加task
        public void next(StateCallback callback, Properties param) {
        	//导出数据完成之后，等待手动开奖
            callback.update(TermState.SYNCHRONIZED.getCode(), param);
        }

        public String getOperation() {
            return "<span>自动处理中</span>";
        }
    }, SYNCHRONIZED("销售已同步", 1470) {
    	//TODO 增加task
        public void next(StateCallback callback, Properties param) {
        	//导出数据完成之后，等待手动开奖
            callback.update(TermState.DRAW_EXPORT_DATA.getCode(), param);
        }

        public String getOperation() {
            return "<span>自动处理中</span>";
        }
    }, DRAW_EXPORT_DATA("导出数据中", 1480) {
        public void next(StateCallback callback, Properties param) {
        	//导出数据完成之后，等待手动开奖
            callback.update(TermState.WAITING_DRAW_NUMBER.getCode(), param);
        }

        public String getOperation() {
            return "<span>自动处理中</span>";
        }
    }, WAITING_DRAW_NUMBER("等待录入开奖号码", 1490) {
        public void next(StateCallback callback, Properties param) {
        	//导出数据完成之后，等待手动开奖
            callback.update(TermState.DRAW.getCode(), param);
        }

        public String getOperation() {
            return "<a target=\"dialog\" width=\"650\" height=\"410\" href=\"man/term/kaijiangPanel?code=#code#\" mask=\"true\" title=\"录入开奖号码\">录入开奖号码</a>";
//            return "<span>自动处理中</span>";
        }
    }, DRAW("已开奖", 1500) {
        public void next(StateCallback callback, Properties param) {
            callback.update(TermState.IN_CALCULATE.getCode(), param);
        }

        public String getOperation() {
//            return "<a target=\"dialog\" width=\"650\" height=\"410\" href=\"man/term/suanjiangPanel?code=#code#\" mask=\"true\" title=\"算奖*中奖检索\">算奖*中奖检索</a>";
            return "<span>自动处理中</span>";
        }
    }, IN_CALCULATE("算奖中", 1600) {
        public void next(StateCallback callback, Properties param) {
            callback.update(TermState.CALCULATE.getCode(), param);
        }

        public String getOperation() {
            return "<span>自动处理中</span>";
        }
    }, CALCULATE("已算奖", 1601) {
        public void next(StateCallback callback, Properties param) {
            String op = param.getProperty("OPType");
            if("PASS".equals(op)){
                callback.update(TermState.CALCULATE_AUDITED.getCode(), param);
            }else if("FAIL".equals(op)){
                callback.update(TermState.WAITING_DRAW_NUMBER.getCode(), param);
            }else{
                throw new RuntimeException("无此操作");
            }
        }
        public String getOperation() {
            return "<a target=\"dialog\" width=\"850\" height=\"510\" href=\"man/term/suanjiangCKPanel?code=#code#\" mask=\"true\" title=\"算奖审核\">算奖审核</a>";
        }
    }, CALCULATE_AUDITED("算奖已审核", 1602) {
        public void next(StateCallback callback, Properties param) {
            callback.update(TermState.IN_PAYOUT.getCode(), param);
        }

        public String getOperation() {
            return "<span>自动处理中</span>";
        }
    }, CALCULATE_AUDITED_FAILED("算奖已审核失败", 1603) {
        public void next(StateCallback callback, Properties param) {
            //callback.update(TermState.IN_PAYOUT.getCode(), param);
        }

        public String getOperation() {
            return "<span><b>审核失败，等待人工处理</b></span>";
        }
    }, IN_PAYOUT("返奖中", 1650) {
        public void next(StateCallback callback, Properties param) {
            callback.update(TermState.IN_PAYOUT.getCode(), param);
        }

        public String getOperation() {
            return "自动处理中";
        }
    }, OUTER_FILE("已经生成外部文件", 1675) {
        public void next(StateCallback callback, Properties param) {
            callback.update(TermState.PAYOUT.getCode(), param);
        }

        public String getOperation() {
            return "自动处理中";
        }
    }, PAYOUT("已返奖", 1701) {
        public void next(StateCallback callback, Properties param) {
            callback.update(TermState.IN_SETTLEMENT.getCode(), param);
        }

        public String getOperation() {
            return "<span>无需处理</span>";
        }
    }, IN_SETTLEMENT("结算中", 1800) {
        public void next(StateCallback callback, Properties param) {
            callback.update(TermState.SETTLEMENT.getCode(), param);
        }

        public String getOperation() {
            return "<span>自动处理中</span>";
        }
    }, SETTLEMENT("已结算", 1801) {
        public void next(StateCallback callback, Properties param) {
            callback.update(TermState.IN_SEAL.getCode(), param);
        }

        public String getOperation() {
        	return "<span>无需处理</span>";
        }
    }, IN_SEAL("期次封存中", 1900) {
        public void next(StateCallback callback, Properties param) {
            callback.update(TermState.SEAL.getCode(), param);
        }

        public String getOperation() {
            return "<span>自动处理中</span>";
        }
    }, SEAL("期次已封存", 1901) {
        public void next(StateCallback callback, Properties param) {
            throw new RuntimeException("无此操作");
        }
        public String getOperation() {
            return "<a target=\"dialog\" width=\"650\" height=\"410\" href=\"man/term/finalPanel?code=#code#\" mask=\"true\" title=\"查看最终数据\">查看最终数据</a>";
        }
    };
    private boolean isTransient = false;
    private String desc = "";
    private int code = 0;
    private static Map<Integer, TermState> valuesMap = new HashMap<Integer, TermState>();

    public static Map<Integer, TermState> getValuesMap() {
        for (TermState s : TermState.values()) {
            valuesMap.put(s.getCode(), s);
        }
        return valuesMap;
    }

    public boolean isTransient() {
        return this.isTransient;
    }

    private TermState() {
    }

    private TermState(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    private TermState(boolean aTransient) {
        isTransient = aTransient;
    }

    public void next(StateCallback callback, Properties param) {

    }

    public String getOperation() {
        return "";
    }

    //状态机结束时，再进行调用得到的方法
    public void output() {
        throw new RuntimeException("当前期次已经封存，不能做任何操作！");
    }

    public String getDesc() {
        return desc;
    }


    public int getCode() {
        return code;
    }


    public static void main(String[] args) {
        for (TermState s : TermState.values()) {
            System.out.println(s.name());
        }
    }
}
