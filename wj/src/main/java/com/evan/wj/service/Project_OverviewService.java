package com.evan.wj.service;

import com.evan.wj.dao.ProjectZtDAO;
import com.evan.wj.dao.Project_OverviewDAO;
import com.evan.wj.dao.Receive_khDAO;
import com.evan.wj.pojo.ProjectZt;
import com.evan.wj.pojo.Project_Overview;
import com.evan.wj.vo.OverViewAndFeasibleVo;
import com.evan.wj.vo.ProjWithTimeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class Project_OverviewService {
    @Autowired
    Project_OverviewDAO project_overviewdao;
    @Autowired
    ProjectZtDAO projectZtDAO;
    @Autowired
    Account_informationService account_informationService;
    @Autowired
    Receive_khDAO receive_khDAO;
    @Autowired
    FileService fileService;

    public void add(Project_Overview proj) {
        project_overviewdao.save(proj);
    }

    public boolean xiada(int projectid, String cname) {
        if(projectZtDAO.existsByProjectid(projectid)) {
            ProjectZt pzt = projectZtDAO.findByProjectid(projectid);
            pzt.setIstaskjs("已下达");
            pzt.setTaskname(cname);
            pzt.setTaskdate(LocalDateTime.now());
            pzt.setFkztkf("未反馈");
            projectZtDAO.save(pzt);
            return true;
        } else {
            return false;
        }
    }



    public List<Project_Overview> queryprojunzt(int interval){
        return project_overviewdao.askprojunzt(interval);
    }

    public List<LocalDateTime> querytimeunzt(int interval) {
        return project_overviewdao.asktimeunzt(interval);
    }

    public void save(Project_Overview newproj) {
        project_overviewdao.save(newproj);
    }

    public List<ProjWithTimeVo> queryunzt(int interval) {
        project_overviewdao.updatetimed();
        return project_overviewdao.askunzt(interval);
    }

    public List<ProjWithTimeVo> queryhavezt(int interval, String resultkf) {
        project_overviewdao.updatetimed();
        if(resultkf.equals("所有")){
            return project_overviewdao.askhaveztall(interval);
        } else {
            return project_overviewdao.askhavezt(interval, resultkf);
        }
    }

//    public List<ProjWithTimeVo> queryAllInfo(int interval,String resultkf){
//        if(resultkf.equals("所有")){
//            return project_overviewdao.askhaveztall(interval);
//        } else {
//            return project_overviewdao.queryAllInfo(interval, resultkf);
//        }
//    }
    public ProjWithTimeVo queryproj(int projectid) {
        ProjWithTimeVo proj = new ProjWithTimeVo(-1);
        if(project_overviewdao.existsByProjectid(projectid) && projectZtDAO.existsByProjectid(projectid)){
//            project_overviewdao.updateonetimed(projectid);
            proj = project_overviewdao.askproj(projectid);
            proj.setDura(project_overviewdao.asktime(projectid));
        }
        return proj;
    }

    public boolean addnewproj(Project_Overview newproj) {

        String tmp = newproj.getCreatename();
        if(tmp == null) return false;
        tmp = account_informationService.namebyId(tmp);
        newproj.setCreatename(tmp);
        newproj.setUpdatename(tmp);
        newproj.setCreatedate(LocalDateTime.now());
        newproj.setUpdatedate(LocalDateTime.now());

        if (newproj.getIsdeal().equals("有") && newproj.getKhryisreal().equals("真实") && (newproj.getIsmoney().equals("有钱") || newproj.getIsmoney().equals("一般"))) {
            newproj.setIsacceptsw("有");
        } else {
            newproj.setIsacceptsw("无");
        }

        log.info("新项目信息为" + newproj.toString());

        try {
            this.save(newproj);
        } catch (Exception e) {
            return false;
        }
//        File dir = new File("D:\\QuatationSystem\\prjmng\\static\\file\\proj\\" + newproj.getProjectid());
//        dir.mkdir();
//        String pfolder = "D:/QuatationSystem/prjmng/static/file/proj/";
//        String folder = "D:/QuatationSystem/prjmng/static/file/proj/" + newproj.getProjectid() + "/";
//        String pic = newproj.getProjectdetails();
//        File oripic = new File(pfolder + pic);
//        oripic.renameTo(new File(folder + pic));
//        log.info("移动：" + pfolder + pic + "\n" + folder + pic);
//        newproj.setProjectdetails("/static/file/proj/" + newproj.getProjectid() + "/" + pic);
//        this.save(newproj);

        fileService.morefile(newproj.getProjectdetails(), newproj.getProjectid(), true);

        return true;
    }

    public List<ProjWithTimeVo> queryunfinish(int interval) {
        project_overviewdao.updatetimed();
        List<ProjWithTimeVo> pwt = project_overviewdao.askunfinish(interval);
        for(ProjWithTimeVo proj : pwt) {
            log.info(proj.getCreatedate() + "   " + proj.getEvaluationdate() + "\n");
            proj.setDura((int) java.time.Duration.between(proj.getCreatedate(),  proj.getEvaluationdate()).toHours());
        }
        return pwt;
    }

    public List<ProjWithTimeVo> queryhavefinish(int interval) {
        project_overviewdao.updatetimed();
        List<ProjWithTimeVo> pwt = project_overviewdao.askhavefinish(interval);
        for(ProjWithTimeVo proj : pwt) {
            log.info(proj.getCreatedate() + "   " + proj.getEvaluationdate() + "\n");
            proj.setDura((int) java.time.Duration.between(proj.getCreatedate(),  proj.getEvaluationdate()).toHours());
        }
        return pwt;
    }

    public boolean updatekhfeedback(int projectid, String feedback, String name) {
        if(projectZtDAO.existsByProjectid(projectid)) {
            ProjectZt pzt = projectZtDAO.findByProjectid(projectid);
            if (feedback.equals("")) {
                pzt.setKhfeedback("无");
            } else {
                pzt.setKhfeedback(feedback);
            }
            name = account_informationService.namebyId(name);
            pzt.setKhfeedbackname(name);
            pzt.setKhfeedbackdate(LocalDateTime.now());
            projectZtDAO.save(pzt);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateprojectresult(int projectid, String projectresult, String name) {
        if(projectZtDAO.existsByProjectid(projectid)) {
            ProjectZt pzt = projectZtDAO.findByProjectid(projectid);
            if (projectresult.equals("成交") || projectresult.equals("未成交")) {
                pzt.setProjectresultkf(projectresult);
            } else {
                return false;
            }
            name = account_informationService.namebyId(name);
            pzt.setProjectresultname(name);
            pzt.setProjectresultdate(LocalDateTime.now());
            projectZtDAO.save(pzt);
            return true;
        } else {
            return false;
        }
    }


    public List<ProjWithTimeVo> queryunreceive(int interval) {
        project_overviewdao.updatetimed();
        List<ProjWithTimeVo> pwt = project_overviewdao.askreceive(interval);
        pwt.removeIf(projWithTimeVo -> receive_khDAO.existsByProjectidAndTestresult(projWithTimeVo.getProjectid(), "成功") || receive_khDAO.existsByProjectidAndTestresult(projWithTimeVo.getProjectid(), "失败"));
        for(ProjWithTimeVo proj : pwt) {
            log.info(proj.getCreatedate() + "   " + proj.getEvaluationdate() + "\n");
            proj.setDura((int) java.time.Duration.between(proj.getCreatedate(),  proj.getEvaluationdate()).toHours());
        }
        return pwt;
    }

    public List<ProjWithTimeVo> queryhavereceive(int interval) {
        project_overviewdao.updatetimed();
        List<ProjWithTimeVo> pwt = project_overviewdao.askreceive(interval);
        pwt.removeIf(projWithTimeVo -> !(receive_khDAO.existsByProjectidAndTestresult(projWithTimeVo.getProjectid(), "成功") || receive_khDAO.existsByProjectidAndTestresult(projWithTimeVo.getProjectid(), "失败")));
        for(ProjWithTimeVo proj : pwt) {
            log.info(proj.getCreatedate() + "   " + proj.getEvaluationdate() + "\n");
            proj.setDura((int) java.time.Duration.between(proj.getCreatedate(),  proj.getEvaluationdate()).toHours());
        }
        return pwt;
    }

    public List<ProjWithTimeVo> queryall() {
        project_overviewdao.updatetimed();
        List<ProjWithTimeVo> pwt = project_overviewdao.askall();
        return pwt;
    }
}
