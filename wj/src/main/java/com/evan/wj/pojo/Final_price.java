package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "final_price")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data
public class Final_price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_final_price_id")
    int pfinalpriceid;

    @Column(name = "project_id")
    int projectid;

    @Column(name = "material_cost")
    double materialcost;

    @Column(name = "cs_material_cost")
    double csmaterialcost;

    @Column(name = "rj_material_cost")
    double rjmaterialcost;

    @Column(name = "work_cost")
    double workcost;

    @Column(name = "all_cost")
    double allcost;

    @Column(name = "price_model")
    String pricemodel;

    @Column(name = "wb_price")
    double wbprice;

    @Column(name = "final_price")
    double finalprice;

    @Column(name = "create_date")
    LocalDateTime createdate;

    @Column(name = "update_date")
    LocalDateTime updatedate;

    @Column(name = "create_name")
    String createname;

    @Column(name = "update_name")
    String updatename;


}

