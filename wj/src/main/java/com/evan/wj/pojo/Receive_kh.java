package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;


@Entity
@Table(name = "receive_kh")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data
public class Receive_kh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receive_kh_id")
    int receivekhid;

    @Column(name = "project_id")
    int projectid;

    @Column(name = "receive_kh_name")
    String receivekhname;

    @Column(name = "receive_kh_phone")
    String receivekhphone;

    @Column(name = "receive_intention")
    String receiveintention;

    @Column(name = "is_final_receive")
    String isfinalreceive;

    @Column(name = "receive_price")
    double receiveprice;

    @Column(name = "needed_time")
    double neededtime;

    @Column(name = "test_result")
    String testresult;

    @Column(name = "create_date")
    LocalDateTime createdate;

    @Column(name = "update_date")
    LocalDateTime updatedate;

    @Column(name = "create_name")
    String createname;

    @Column(name = "update_name")
    String updatename;

}

