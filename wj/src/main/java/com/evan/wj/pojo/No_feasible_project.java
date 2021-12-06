package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;


@Entity
@Table(name = "no_feasible_project")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data
public class No_feasible_project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_feasible_project_id")
    int nofeasibleprojectid;

    @Column(name = "project_id")
    int projectid;

//    @Column(name = "experiment")
//    String experiment;

    @Column(name = "evaluation_js")
    String evaluationjs;

    @Column(name = "reasons_js")
    String reasonsjs;

    @Column(name = "evaluation_name")
    String evaluationname;

    @Column(name = "evaluation_date")
    LocalDateTime evaluationdate;

//    public int getNofeasibleprojectid() { return nofeasibleprojectid; }
//    public void setNofeasibleprojectid(int nofeasibleprojectid) { this.nofeasibleprojectid = nofeasibleprojectid; }
//
//    public int getProjectid() {
//        return projectid;
//    }
//    public void setProjectid(int projectid) {
//        this.projectid = projectid;
//    }
//
//    public String getExperiment() {
//        return experiment;
//    }
//    public void setExperiment(String experiment) {
//        this.experiment = experiment;
//    }
//
//    public String getEvaluationjs() {
//        return evaluationjs;
//    }
//    public void setEvaluationjs(String evaluationjs) {
//        this.evaluationjs = evaluationjs;
//    }
//
//    public String getReasonsjs() {
//        return reasonsjs;
//    }
//    public void setReasonsjs(String reasonsjs) {
//        this.reasonsjs = reasonsjs;
//    }
//
//    public LocalDateTime getEvaluationdate() {
//        return evaluationdate;
//    }
//    public void setEvaluationdate(LocalDateTime evaluationdate) {
//        this.evaluationdate = evaluationdate;
//    }
//
//    public String getEvaluationname() { return evaluationname; }
//    public void setEvaluationname(String evaluationname) { this.evaluationname = evaluationname; }





}

