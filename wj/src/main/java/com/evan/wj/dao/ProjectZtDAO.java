package com.evan.wj.dao;

import com.evan.wj.pojo.ProjectZt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectZtDAO extends JpaRepository<ProjectZt, Integer> {

    public boolean existsByProjectid(int projectid);

    ProjectZt findByProjectid(int projectid);

    @Query(value = "select count(zt.projectid) from ProjectZt zt where zt.timed < 24 * ?2 \n" +
            " and zt.projectztjs like ?1% \n" +
            " and zt.fkztkf like '未反馈%' " )
    public int findNoProvideNumByStatusAndInterval(String status, int interval);

    @Query(value = "select count(zt.projectid) from ProjectZt zt where zt.timed < 24 * ?2 \n" +
            " and zt.projectztjs like ?1% \n" +
            " and zt.fkztkf like '已反馈%' " )
    public int findProvideNumByStatusAndInterval(String status, int interval);
}
