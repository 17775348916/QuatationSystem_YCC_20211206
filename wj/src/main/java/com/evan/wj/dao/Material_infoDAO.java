package com.evan.wj.dao;

import com.evan.wj.pojo.Material_info;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface Material_infoDAO extends JpaRepository<Material_info,Integer> {

    @Query(value = "select * from material_info where material_name like CONCAT('%',:info,'%') or cas like CONCAT('%',:info,'%')",nativeQuery = true)
    List<Material_info> askinfo(@Param("info") String info);

//    @Transactional
//    void deleteByMaterialid(int id);

    boolean existsByMaterialid(int id);

    Material_info findByCreatedateAndCreatename(LocalDateTime Createdate, String Createname);

}
