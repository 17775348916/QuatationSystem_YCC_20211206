package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "feasible_project")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Feasible_project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feasible_project_id")
    int feasibleprojectid;

    @Column(name = "project_id")
    int projectid;

    @Column(name = "h_sl")
    double hsl;

    @Column(name = "c_sl")
    double csl;

    @Column(name = "m_sl")
    double msl;

    @Column(name = "dtlx_sj_cs")
    double dtlxsjcs;

    @Column(name = "jll_sj_cs")
    double jllsjcs;

    @Column(name = "zsj_cs")
    double zsjcs;

    @Column(name = "is_difficult_js")
    String isdifficultjs;

    @Column(name = "papers_js")
    String papersjs;

    @Column(name = "time_needed")
    String timeneeded;

    @Column(name = "bz")
    String bz;

    @Column(name = "evaluation_js")
    String evaluationjs;

    @Column(name = "evaluation_name")
    String evaluationname;

    @Column(name = "evaluation_date")
    LocalDateTime evaluationdate;

    public int getFeasibleprojectid() {
        return feasibleprojectid;
    }
    public void setFeasibleprojectid(int feasibleprojectid) {
        this.feasibleprojectid = feasibleprojectid;
    }

    public int getProjectid() {
        return projectid;
    }
    public void setProjectid(int projectID) {
        this.projectid = projectID;
    }

    public double getHsl() {
        return hsl;
    }
    public void setHsl(double hsl) {
        this.hsl = hsl;
    }

    public double getCsl() {
        return csl;
    }
    public void setCsl(double csl) { this.csl = csl; }

    public double getMsl() {
        return msl;
    }
    public void setMsl(double msl) {
        this.msl = msl;
    }

    public double getDtlxsjcs() {
        return dtlxsjcs;
    }
    public void setDtlxsjcs(double DTLXSJCS) {
        this.dtlxsjcs = DTLXSJCS;
    }

    public double getJllsjcs() {
        return jllsjcs;
    }
    public void setJllsjcs(double JLLSJCS) {
        this.jllsjcs = JLLSJCS;
    }

    public double getZsjcs() {
        return zsjcs;
    }
    public void setZsjcs(double ZSJCS) {
        this.zsjcs = ZSJCS;
    }

    public String getIsdifficultjs() {
        return isdifficultjs;
    }
    public void setIsdifficultjs(String isdifficultjs) {
        this.isdifficultjs = isdifficultjs;
    }

    public String getPapersjs() {
        return papersjs;
    }
    public void setPapersjs(String papersjs) {
        this.papersjs = papersjs;
    }

    public String getTimeneeded() {
        return timeneeded;
    }
    public void setTimeneeded(String timeneeded) {
        this.timeneeded = timeneeded;
    }

    public String getBz() {
        return bz;
    }
    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getEvaluationjs() {
        return evaluationjs;
    }
    public void setEvaluationjs(String evaluationjs) {
        this.evaluationjs = evaluationjs;
    }

    public LocalDateTime getEvaluationdate() {
        return evaluationdate;
    }
    public void setEvaluationdate(LocalDateTime evaluationdate) {
        this.evaluationdate = evaluationdate;
    }

    public String getEvaluationname() { return evaluationname; }
    public void setEvaluationname(String evaluationname) { this.evaluationname = evaluationname; }


}

