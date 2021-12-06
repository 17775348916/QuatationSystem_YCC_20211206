package com.evan.wj.dao;
import com.evan.wj.pojo.No_feasible_project;
import org.springframework.data.jpa.repository.JpaRepository;
public interface No_feasible_projectDAO extends JpaRepository<No_feasible_project, Integer> {

    public boolean existsByProjectid(int projectid);

    public No_feasible_project findByProjectid(int projectid);
}
