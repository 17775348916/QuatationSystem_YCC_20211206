package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "quoting_model")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data
public class Quoting_model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    int modelid;

    @Column(name = "model_name")
    String modelname;

    @Column(name = "h_price")
    double hprice;

    @Column(name = "c_price")
    double cprice;

    @Column(name = "m_price")
    double mprice;

    @Column(name = "rate")
    double rate;

    @Column(name = "firm_wage")
    double firmwage;

    @Column(name = "college_wage")
    double collegewage;

    @Column(name = "firm_coefficient")
    double firmcoefficient;

    @Column(name = "college_coefficient")
    double collegecoefficient;

    @Column(name = "product_coefficient")
    double productcoefficient;

    @Column(name = "create_date")
    LocalDateTime createdate;

    @Column(name = "update_date")
    LocalDateTime updatedate;

}

