package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "project_overview")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@Data
public class Project_Overview {

    /**
     * 项目ID
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int projectid;

    /**
     * 产品名称
     **/
    @Column(name = "project_name")
    private String projectname;

    /**
     * 产品内容，产品结构式图片等
     **/
    @Column(name = "project_details")
    private String projectdetails;

    /**
     * 需求量
     **/
    @Column(name = "project_sl")
    private String projectsl;

    /**
     * Cas号
     **/
    @Column(name = "cas")
    private String cas;

    /**
     * 询单单位名称
     **/
    @Column(name = "kh_name")
    private String khname;

    /**
     * 询单人员姓名
     **/
    @Column(name = "khry_name")
    private String khryname;

    /**
     * 询单人员身份
     **/
    @Column(name = "khry_type")
    private String khrytype;

    /**
     * 询单人联系方式
     **/
    @Column(name = "khry_contact")
    private String khrycontact;

    /**
     * 询单人身份是否真实
     **/
    @Column(name = "khry_Isreal")
    private String khryisreal;

    /**
     * 客户类型,“企业”或“高校”
     **/
    @Column(name = "kh_type")
    private String khtype;

    /**
     * 客户合作历史,“新客户”or“老客户”
     **/
    @Column(name = "co_history")
    private String cohistory;

    /**
     * 是否有钱,“有钱”、“一般”、“没钱”
     **/
    @Column(name = "is_money")
    private String ismoney;

    @Column(name = "is_deal")
    private String isdeal;

    /**
     * 有无成交机会,“有”“无”（商务人员填写）
     **/
    @Column(name = "is_accept_sw")
    private String isacceptsw;

    /**
     * 备注,客户对产品纯度，货期等方面的特殊要求
     **/
    @Column(name = "bz")
    private String bz;

    @Column(name="model_name")
    private String modelname;

    /**
     * 询单日期
     **/
    @Column(name = "create_date")
    private java.time.LocalDateTime createdate;

    /**
     * 修改日期
     **/
    @Column(name = "update_date")
    private java.time.LocalDateTime updatedate;

    @Column(name = "create_name")
    private String createname;

    @Column(name = "update_name")
    private String updatename;

    @Column(name = "test_result")
    private String testresult;

    @Column(name = "time")
    private int time;

    @OneToOne
    @JoinColumn(name = "project_id")
    private ProjectZt projectZt;
}
