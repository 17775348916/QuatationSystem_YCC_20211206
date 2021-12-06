
package com.evan.wj.dao;

import com.evan.wj.pojo.Feasible_project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Feasible_projectDAO extends JpaRepository<Feasible_project,Integer> {
//    public void save(Feasible_project feasible_project);
    public boolean existsByProjectid(int projectid);

    public Feasible_project findByProjectid(int projectid);
}
