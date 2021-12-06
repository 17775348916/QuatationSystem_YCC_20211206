package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_information")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})

public class Account_information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    int accountid;

    @Column(name = "keywords")
    String keywords;

    @Column(name = "account_name")
    String accountname;

    @Column(name = "name")
    String name;

    @Column(name = "type_id")
    int typeid;

    @Column(name = "create_date")
    LocalDateTime createdate;
    @Column(name = "update_date")
    LocalDateTime updatedate;

    public int getAccountid() {
        return accountid;
    }
    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public String getAccountname(){return accountname;}

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getKeywords() {
        return keywords;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeid() {
        return typeid;
    }
    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }


    public LocalDateTime getCreatedate() {
        return createdate;
    }

    public void setCreatedate(LocalDateTime createdate) {
        this.createdate = createdate;
    }

    public LocalDateTime getUpdatedate() {
        return updatedate;
    }
    public void setUpdatedate(LocalDateTime Updatedate) {
        this.updatedate = updatedate;
    }
}