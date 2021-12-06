package com.evan.wj.dao;

import com.evan.wj.pojo.Feasible_project;
import com.evan.wj.pojo.Quoting_model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Quoting_modelDAO extends JpaRepository<Quoting_model, Integer> {

    @Query(value = "select *\n" +
            "from quoting_model\n" +
            "where model_name in\n" +
            " (select model_name from kh_model_judgment \n" +
            "where kh_type = ?1 and is_money = ?2 and is_difficult = ?3);", nativeQuery = true)
    Quoting_model askmodel(String khtype, String ismoney, String isdifficult);
}
