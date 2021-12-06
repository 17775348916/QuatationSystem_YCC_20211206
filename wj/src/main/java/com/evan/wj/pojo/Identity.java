package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "identity")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data
public class Identity{

  /**身份ID**/
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "type_id")
  private int typeid;

  /**身份类型**/
  private String type;

  /**身份级别**/
  private String rank;

  /**创建日期**/
  @Column(name = "create_date")
  private LocalDateTime createdate;

  /**最后修改日期**/
  @Column(name = "update_date")
  private LocalDateTime updatedate;


}