package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "kh_model_judgment")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})

public class Kh_model_judgment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kh_model_id")
    int khmodleid;

    @Column(name = "kh_type")
    String khtype;

    @Column(name = "is_money")
    String ismoney;

    @Column(name = "is_difficult")
    String isdifficult;

//    @Column(name = "modleid")
//    int modleid;

    @Column(name = "model_name")
    String modlename;

    @Column(name = "create_date")
    LocalDateTime createdate;

//    @Column(name = "create_name")
//    String createname;

    @Column(name = "update_date")
    LocalDateTime updatedate;


    public int getKH_Modle_ID() {
        return khmodleid;
    }
    public void setKH_Modle_ID(int KH_Modle_ID) {
        this.khmodleid = KH_Modle_ID;
    }

    public String getKH_type() {
        return khtype;
    }
    public void setKH_type(String KH_type) {
        this.khtype = KH_type;
    }

    public String getIs_money() {
        return ismoney;
    }
    public void setIs_money(String is_money) {
        this.ismoney = is_money;
    }

    public String getIs_difficult() {
        return isdifficult;
    }
    public void setIs_difficult(String is_difficult) {
        this.isdifficult = is_difficult;
    }

//    public int getModle_ID() { return modleid; }
//    public void setModle_ID(int modle_ID) { modleid = modle_ID; }

    public LocalDateTime getCreate_date() { return createdate; }
    public void setCreate_date(LocalDateTime create_date) { this.createdate = create_date; }

    public String getModlename() { return modlename; }
    public void setModlename(String modlename) { this.modlename = modlename; }

//    public String getCreatename() { return createname; }
//    public void setCreatename(String createname) { this.createname = createname; }

    public LocalDateTime getUpdatedate() {
        return updatedate;
    }
    public void setUpdatedate(LocalDateTime Updatedate) {
        this.updatedate = updatedate;
    }




}

