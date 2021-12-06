package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "material_need")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data

public class Material_need {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_need_id")
    private int materialneedid;

    @Column(name = "project_id")
    private int projectid;

    @Column(name = "material_id")
    private int materialid;

    @Column(name = "material_name")
    private String materialname;

    @Column(name = "cas")
    private String cas;

    @Column(name = "material_sl")
    private String materialsl;

    @Column(name = "picture")
    private String picture;

    @Column(name = "bz")
    private String bz;

    @Column(name = "delete_flag")
    private String deleteflag;
//    @Column(name = "is_task")
//    String istask;


    @Column(name = "create_date")
    private LocalDateTime createdate;

    @Column(name = "update_date")
    private LocalDateTime updatedate;

    @Column(name = "create_name")
    private String createname;

    @Column(name = "update_name")
    private String updatename;


//
//    public int getMaterial_Need_ID() {
//        return materialneedid;
//    }
//    public void setMaterial_Need_ID(int material_need_id) {
//        this.materialneedid = material_need_id;
//    }
//
//    public int getProject_ID() {
//        return projectid;
//    }
//    public void setProject_ID(int project_ID) {
//        this.projectid = project_ID;
//    }
//
//    public int getMaterial_ID() {
//        return materialid;
//    }
//    public void setMaterial_ID(int material_ID) {
//        this.materialid = material_ID;
//    }
//
//    public String getMaterial_name() {
//        return materialname;
//    }
//    public void setMaterial_name(String material_name) {
//        this.materialname = material_name;
//    }
//
//    public String getCas() {
//        return cas;
//    }
//    public void setCas(String cas) {
//        this.cas = cas;
//    }
//
//    public double getMaterial_SL() {
//        return materialsl;
//    }
//    public void setMaterial_SL(double material_SL) {
//        this.materialsl = material_SL;
//    }
//
//    public String getPicture() {
//        return picture;
//    }
//    public void setPicture(String picture) { this.picture = picture; }
//
//    public String getBZ() {
//        return bz;
//    }
//    public void setBZ(String BZ) {
//        this.bz = BZ;
//    }
//
//    public String getIs_task() {
//        return istask;
//    }
//    public void setIs_task(String is_task) {
//        this.istask = is_task;
//    }
//
//    public LocalDateTime getCreate_date() {
//        return createdate;
//    }
//    public void setCreate_date(LocalDateTime create_date) {
//        this.createdate = create_date;
//    }
//
//    public LocalDateTime getUpdate_date() {
//        return updatedate;
//    }
//    public void setUpdate_date(LocalDateTime update_date) {
//        this.updatedate = update_date;
//    }
//
//    public String getCreatename() { return createname; }
//    public void setCreatename(String createname) { this.createname = createname; }
//
//    public String getUpdatename() { return updatename; }
//    public void setUpdatename(String updatename) { this.updatename = updatename; }


}

