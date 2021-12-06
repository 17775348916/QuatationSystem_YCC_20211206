package com.evan.wj.pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_zt")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@Data
public class ProjectZt implements Serializable{

  /**项目ID**/
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "project_id")
  private int projectid;

  /**项目状态，“已评估”、“未评估（默认）”（技术人员填写）
T-1-1-2-No页面，点击【提交完成评估】，更新Project_ZT为‘已评估-不可行’
T-1-1-2-Yes-2页面，点击【完成项目评估】，更新Project_ZT_js为‘已评估-可行’
【这两个界面是二选一】**/
  @Column(name = "project_zt_js")
  private String projectztjs;

  /**评估人**/
  @Column(name = "evaluation_name")
  private String evaluationname;

  /**评估时间**/
  @Column(name = "evaluation_date")
  private LocalDateTime evaluationdate;

  /**原料任务是否下达，默认未下达，T-1-1-1页面，点击【确认原料信息】Is_task变为“已下达”**/
  @Column(name = "is_task_js")
  private String istaskjs;

  /**下达人**/
  @Column(name = "task_name")
  private String taskname;

  /**下达时间**/
  @Column(name = "task_date")
  private LocalDateTime taskdate;

  /**反馈状态,已反馈，未反馈（默认）
C-1-1页面，点击【提交原料信息】或【买不到原料反馈】，将反馈状态修改为“已反馈”；
如果是“已反馈”状态，T-1-1-1页面 可以查到反馈信息**/
  @Column(name = "fk_zt_kf")
  private String fkztkf;

  private String reason;
  /**反馈人**/
  @Column(name = "fk_name")
  private String fkname;

  /**反馈时间**/
  @Column(name = "fk_date")
  private LocalDateTime fkdate;

  /**项目成交结果,“待定”“成交”“未成交（默认）”(客服人员填写)
C-3-1页面选择的成交状态结果来确定**/
  @Column(name = "project_result_KF")
  private String projectresultkf;

  /**项目成交录入人**/
  @Column(name = "project_result_name")
  private String projectresultname;

  /**项目成交时间,	B-2页面(二)计算耗时**/
  @Column(name = "project_result_date")
  private LocalDateTime  projectresultdate;

  /**客户反馈	,下单客户对项目报价、时间等的反馈信息
C-3-1页面反馈**/
  @Column(name = "kh_feedback")
  private String khfeedback;

  /**客户反馈录入人**/
  @Column(name = "kh_feedback_name")
  private String khfeedbackname;

  /**客户反馈时间**/
  @Column(name = "kh_feedback_date")
  private LocalDateTime khfeedbackdate;

  /*已经经过时间*/
  private int timed;
}
