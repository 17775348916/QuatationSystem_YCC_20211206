package com.evan.wj.dao;

import com.evan.wj.pojo.Material_info;
import com.evan.wj.pojo.Material_need;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Material_needDAO extends JpaRepository<Material_need,Integer> {

    List<Material_need> findAllByProjectidAndDeleteflag(int projectid, String deleteflag);
}
