package com.evan.wj.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjWithTimeVo {



    /**
     * project_overview info
     **/
    private int projectid;
    private String projectname;
    private String projectdetails;
    private String projectsl;
    private String cas;
    private String khname;
    private String khryname;
    private String khrytype;
    private String khrycontact;
    private String khryisreal;
    private String khtype;
    private String cohistory;
    private String ismoney;
    private String isacceptsw;
    private String bz;
    private String modelname;
    private LocalDateTime createdate;
    private String createname;

    /**
     * projectzt info
     **/
    private String projectztjs;
    private String evaluationname;
    private LocalDateTime evaluationdate;
    private String istaskjs;
    /**
     * 下达人
     **/
    private String taskname;
    /**
     * 下达时间
     **/
    private LocalDateTime taskdate;
    /**
     * 反馈状态,已反馈，未反馈（默认）
     * C-1-1页面，点击【提交原料信息】或【买不到原料反馈】，将反馈状态修改为“已反馈”；
     * 如果是“已反馈”状态，T-1-1-1页面 可以查到反馈信息
     **/
    private String fkztkf;
    /**
     * 反馈人
     **/
    private String fkname;
    /**
     * 反馈时间
     **/
    private LocalDateTime fkdate;
    /**
     * 项目成交结果,“待定”“成交”“未成交（默认）”(客服人员填写)
     * C-3-1页面选择的成交状态结果来确定
     **/
    private String projectresultkf;
    /**
     * 项目成交录入人
     **/
    private String projectresultname;
    /**
     * 项目成交时间,	B-2页面(二)计算耗时
     **/
    private LocalDateTime projectresultdate;
    /**
     * 客户反馈	,下单客户对项目报价、时间等的反馈信息
     * C-3-1页面反馈
     **/
    private String khfeedback;
    /**
     * 客户反馈录入人
     **/
    private String khfeedbackname;
    /**
     * 客户反馈时间
     **/
    private LocalDateTime khfeedbackdate;
    private int dura;


    public ProjWithTimeVo(int dura,
                          int projectid, String projectname, String projectdetails,
                          String projectsl, String cas, String khname, String khryname,
                          String khrytype, String khrycontact, String khryisreal,
                          String khtype, String cohistory, String ismoney,
                          String isacceptsw, String bz, String modelname,
                          LocalDateTime createdate, String createname,
                          String projectztjs, String evaluationname,
                          LocalDateTime evaluationdate, String istaskjs, String taskname,
                          LocalDateTime taskdate, String fkztkf, String fkname,
                          LocalDateTime fkdate, String projectresultkf,
                          String projectresultname, LocalDateTime projectresultdate,
                          String khfeedback, String khfeedbackname, LocalDateTime khfeedbackdate) {
        this.dura = dura;
        this.projectid = projectid;
        this.projectname = projectname;
        this.projectdetails = projectdetails;
        this.projectsl = projectsl;
        this.cas = cas;
        this.khname = khname;
        this.khryname = khryname;
        this.khrytype = khrytype;
        this.khrycontact = khrycontact;
        this.khryisreal = khryisreal;
        this.khtype = khtype;
        this.cohistory = cohistory;
        this.ismoney = ismoney;
        this.isacceptsw = isacceptsw;
        this.bz = bz;
        this.modelname = modelname;
        this.createdate = createdate;
        this.createname = createname;
        this.projectztjs = projectztjs;
        this.evaluationname = evaluationname;
        this.evaluationdate = evaluationdate;
        this.istaskjs = istaskjs;
        this.taskname = taskname;
        this.taskdate = taskdate;
        this.fkztkf = fkztkf;
        this.fkname = fkname;
        this.fkdate = fkdate;
        this.projectresultkf = projectresultkf;
        this.projectresultname = projectresultname;
        this.projectresultdate = projectresultdate;
        this.khfeedback = khfeedback;
        this.khfeedbackname = khfeedbackname;
        this.khfeedbackdate = khfeedbackdate;
    }
    public ProjWithTimeVo(int projectid, int dura){
        this.projectid = projectid;
        this.dura = dura;
    }

    public ProjWithTimeVo(int projectid){
        this.projectid = projectid;
    }
}
