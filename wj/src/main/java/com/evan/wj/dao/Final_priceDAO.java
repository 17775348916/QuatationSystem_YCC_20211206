package com.evan.wj.dao;

import com.evan.wj.pojo.Final_price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Final_priceDAO extends JpaRepository<Final_price, Integer> {

    boolean existsByProjectid(int projectid);

    Final_price findByProjectid(int projectid);
}
