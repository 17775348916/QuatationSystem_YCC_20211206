package com.evan.wj.dao;

import com.evan.wj.pojo.Material_info;
import com.evan.wj.pojo.Material_info_LR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Material_info_LRDAO extends JpaRepository<Material_info_LR,Integer> {

    public List<Material_info_LR> findAllByProjectidAndDeleteflag(int projectid, String deleteflag);
}
