package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "material_info_lr")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data
public class Material_info_LR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_lrid")
    int materiallrid;

    @Column(name = "project_id")
    int projectid;

    @Column(name = "material_need_id")
    int materialneedid;

    @Column(name = "material_id")
    int materialid;

    @Column(name = "material_name")
    String materialname;

    @Column(name = "cas")
    String cas;

    @Column(name = "cg_sl")
    String cgsl;

    @Column(name = "price")
    double price ;

    @Column(name = "provider")
    String provider;

    @Column(name = "provider_phone")
    String  providerphone;

    @Column(name = "delete_flag")
    private String deleteflag;

    @Column(name = "is_buy")
    String isbuy;

    @Column(name = "not_reason")
    String notreason;
//    @Column(name = "fk_zt")
//    String fkzt;

    @Column(name = "create_name")
    String createname;

    @Column(name = "create_date")
    LocalDateTime createdate;

    @Column(name = "update_name")
    String updatename;

    @Column(name = "update_date")
    LocalDateTime updatedate;

//    public int getMaterial_LRID() {
//        return materiallrid;
//    }
//    public void setMaterial_LRID(int material_LRID) {
//        this.materiallrid = material_LRID;
//    }
//
//    public int getProject_ID() {
//        return projectid;
//    }
//    public void setProject_ID(int project_id) {
//        this.projectid = project_id;
//    }
//
//    public int getMaterial_Need_ID() {
//        return materialneedid;
//    }
//    public void setMaterial_Need_ID(int material_need_id) {
//        this.materialneedid = material_need_id;
//    }
//
//    public int getMaterial_ID() {
//        return materialid;
//    }
//    public void setMaterial_ID(int Material_ID) {
//        this.materialid = Material_ID;
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
////    public String getStructure() { return structure; }
////    public void setStructure(String structure) { this.structure = structure; }
////
////    public double getNeeded_SL() { return neededsl; }
////    public void setNeeded_SL(double needed_SL) { this.neededsl = needed_SL; }
//
//    public double getCG_SL() {
//        return cgsl;
//    }
//    public void setCG_SL(double CG_SL) {
//        this.cgsl = CG_SL;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public String getProvider() {
//        return provider;
//    }
//    public void setProvider(String provider) {
//        this.provider = provider;
//    }
//
//    public int getProvider_Phone() {
//        return providerphone;
//    }
//    public void setProvider_Phone(int provider_Phone) {
//        this.providerphone = provider_Phone;
//    }
//
////    public String getBZ() { return bz; }
////    public void setBZ(String BZ) { this.bz = BZ; }
//
//    public String getCreate_name() {
//        return createname;
//    }
//    public void setCreate_name(String create_name) {
//        this.createname = create_name;
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
//    public String getUpdatename() { return updatename; }
//    public void setUpdatename(String updatename) { this.updatename = updatename; }
//
//    public String getisbuy() { return isbuy; }
//    public void setIsbuy(String isbuy) { this.isbuy = isbuy; }
//
//    public String getNotreason() { return notreason; }
//    public void setNotreason(String notreason) { this.notreason = notreason; }
//
//    public String getFkzt() { return fkzt; }
//    public void setFkzt(String fkzt) { this.fkzt = fkzt; }


}

