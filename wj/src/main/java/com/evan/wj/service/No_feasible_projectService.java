package com.evan.wj.service;

import com.evan.wj.dao.Feasible_projectDAO;
import com.evan.wj.dao.No_feasible_projectDAO;
import com.evan.wj.dao.ProjectZtDAO;
import com.evan.wj.dao.Project_OverviewDAO;
import com.evan.wj.pojo.Feasible_project;
import com.evan.wj.pojo.No_feasible_project;
import com.evan.wj.pojo.ProjectZt;
import com.evan.wj.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class No_feasible_projectService {
    @Autowired
    No_feasible_projectDAO nofeasibleProjectDAO;
    @Autowired
    Project_OverviewDAO project_overviewDAO;
    @Autowired
    ProjectZtDAO projectZtDAO;
    @Autowired
    Account_informationService account_informationService;

    public boolean NewProjsave(No_feasible_project newfproj) {
        int projectid = newfproj.getProjectid();
        if (projectZtDAO.existsByProjectid(projectid)) {
            if (nofeasibleProjectDAO.existsByProjectid(projectid)) {
                return false;

            } else {
                LocalDateTime ntime = LocalDateTime.now();
                String evaname = account_informationService.namebyId(newfproj.getEvaluationname());
                newfproj.setEvaluationdate(ntime);
                newfproj.setEvaluationname(evaname);
                nofeasibleProjectDAO.save(newfproj);
                ProjectZt pzt = projectZtDAO.findByProjectid(projectid);
                pzt.setEvaluationname(evaname);
                pzt.setEvaluationdate(ntime);
                projectZtDAO.save(pzt);
                return true;

            }
        } else {
            return false;
        }
    }

    public Result<No_feasible_project> queryproj(int projectid) {
        if (nofeasibleProjectDAO.existsByProjectid(projectid)) {
            return new Result<No_feasible_project>(nofeasibleProjectDAO.findByProjectid(projectid));
        } else {
            return new Result<>("400", false, "项目id不正确");
        }
    }
}
