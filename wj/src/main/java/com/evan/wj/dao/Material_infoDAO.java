package com.evan.wj.dao;

import com.evan.wj.pojo.Material_info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface Material_infoDAO extends JpaRepository<Material_info,Integer> {

    @Query(value = "select * from material_info where material_name = ?1 or cas = ?1",nativeQuery = true)
    List<Material_info> askinfo(String info);

//    @Transactional
//    void deleteByMaterialid(int id);

    boolean existsByMaterialid(int id);

    Material_info findByCreatedateAndCreatename(LocalDateTime Createdate, String Createname);

}
