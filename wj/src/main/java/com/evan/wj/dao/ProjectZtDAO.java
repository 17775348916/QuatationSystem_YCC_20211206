package com.evan.wj.dao;

import com.evan.wj.pojo.ProjectZt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectZtDAO extends JpaRepository<ProjectZt, Integer> {

    public boolean existsByProjectid(int projectid);

    ProjectZt findByProjectid(int projectid);

}
