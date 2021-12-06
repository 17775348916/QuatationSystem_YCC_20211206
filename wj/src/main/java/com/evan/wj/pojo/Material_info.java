package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "material_info")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data
public class Material_info {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    int materialid;

    @Column(name = "material_name")
    String materialname;

    @Column(name = "cas")
    String cas;

    @Column(name = "structure")
    String structure;

    @Column(name = "quantities")
    String quantities;

    @Column(name = "qurity")
    String qurity;

    @Column(name = "price")
    String price;

    @Column(name = "provider")
    String provider;

    @Column(name = "provider_phone")
    String providerphone;

    @Column(name = "bz")
    String bz;

    @Column(name = "create_name")
    String createname;

    @Column(name = "update_name")
    String updatename;

    @Column(name = "create_date")
    LocalDateTime createdate;

    @Column(name = "update_date")
    LocalDateTime updatedate;

    @Column(name = "select_date")
    LocalDateTime selectdate;

    @Column(name = "select_name")
    String selectname;

}

