package com.evan.wj.dao;


import com.evan.wj.pojo.Project_Overview;
import com.evan.wj.vo.ProjWithTimeVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface Project_OverviewDAO extends JpaRepository<Project_Overview, Integer>,JpaSpecificationExecutor<Project_Overview>{

    Project_Overview findByProjectid(int projectid);

    @Query(value = "-- B2查询语句 未评估\n" +
            "select *\n" +
            "from project_overview t\n" +
            "where t.project_zt_js = '未评估'\n" +
            "  and t.create_date > date_sub(current_timestamp(), interval ?1 day)\n" +
            "  and t.create_date < current_timestamp;", nativeQuery = true)
    List<Project_Overview> askprojunzt(int interval);

    @Query(value = "-- B2查询语句 未评估\n" +
            "select TIMESTAMPDIFF(hour, t.create_date, current_timestamp) ddsj\n" +
            "from project_overview t\n" +
            "where t.project_zt_js = '未评估'\n" +
            "  and t.create_date > date_sub(current_timestamp(), interval ?1 day)\n" +
            "  and t.create_date < current_timestamp;", nativeQuery = true)
    List<LocalDateTime> asktimeunzt(int interval);

    @Query(value = "select TIMESTAMPDIFF(hour, create_date, current_timestamp)\n" +
            "from project_overview where project_id = ?1", nativeQuery = true)
    int asktime(int projectid);

    @Transactional
    @Modifying
    @Query(value = "update project_zt zt ,project_overview ov \n" +
            "set zt.timed = TIMESTAMPDIFF(hour, ov.create_date, current_timestamp) \n" +
            "where zt.project_id = ov.project_id;", nativeQuery = true)
    void updatetimed();

    @Transactional
    @Modifying
    @Query(value = "update project_overview ov \n" +
            "set ov.time = TIMESTAMPDIFF(hour, ov.create_date, current_timestamp) \n" +
            "where ov.is_accept_sw = '无';", nativeQuery = true)
    void updatetime();
//    @Transactional
//    @Modifying
//    @Query(value = "update project_zt zt ,project_overview ov \n" +
//            "set zt.timed = TIMESTAMPDIFF(hour, ov.create_date, zt.evaluation_date) \n" +
//            "where zt.project_id = ov.project_id and zt.project_zt_js!='未评估';", nativeQuery = true)
//    void updatetimedpg();

//    @Transactional
//    @Modifying
//    @Query(value = "update project_zt zt ,project_overview ov \n" +
//            "set zt.timed = TIMESTAMPDIFF(hour, ov.create_date, current_timestamp) \n" +
//            "where zt.project_id = ov.project_id and zt.project_id = ?1;", nativeQuery = true)
//    void updateonetimed(int projectid);

    @Query(value = "select new com.evan.wj.vo.ProjWithTimeVo(zt.timed,\n" +
            "                          ov.projectid, ov.projectname, ov.projectdetails,\n" +
            "                          ov.projectsl, ov.cas, ov.khname, ov.khryname,\n" +
            "                          ov.khrytype, ov.khrycontact, ov.khryisreal,\n" +
            "                          ov.khtype, ov.cohistory, ov.ismoney,\n" +
            "                          ov.isacceptsw, ov.bz, ov.modelname,\n" +
            "                          ov.createdate, ov.createname,\n" +
            "                          zt.projectztjs, zt.evaluationname,\n" +
            "                          zt.evaluationdate, zt.istaskjs, zt.taskname,\n" +
            "                          zt.taskdate, zt.fkztkf, zt.fkname,\n" +
            "                          zt.fkdate, zt.projectresultkf,\n" +
            "                          zt.projectresultname, zt.projectresultdate,\n" +
            "                          zt.khfeedback, zt.khfeedbackname, zt.khfeedbackdate)\n" +
            "from Project_Overview ov,ProjectZt zt\n" +
            "where ov.projectid = zt.projectid and\n" +
            "zt.timed < 24 * ?1 and\n" +
            "zt.projectztjs='未评估'")
    List<ProjWithTimeVo> askunzt(int interval);

    @Query(value = "select new com.evan.wj.vo.ProjWithTimeVo(zt.timed,\n" +
            "                          ov.projectid, ov.projectname, ov.projectdetails,\n" +
            "                          ov.projectsl, ov.cas, ov.khname, ov.khryname,\n" +
            "                          ov.khrytype, ov.khrycontact, ov.khryisreal,\n" +
            "                          ov.khtype, ov.cohistory, ov.ismoney,\n" +
            "                          ov.isacceptsw, ov.bz, ov.modelname,\n" +
            "                          ov.createdate, ov.createname,\n" +
            "                          zt.projectztjs, zt.evaluationname,\n" +
            "                          zt.evaluationdate, zt.istaskjs, zt.taskname,\n" +
            "                          zt.taskdate, zt.fkztkf, zt.fkname,\n" +
            "                          zt.fkdate, zt.projectresultkf,\n" +
            "                          zt.projectresultname, zt.projectresultdate,\n" +
            "                          zt.khfeedback, zt.khfeedbackname, zt.khfeedbackdate)\n" +
            "from Project_Overview ov,ProjectZt zt\n" +
            "where ov.projectid = zt.projectid and\n" +
            "zt.timed < 24 * ?1 and\n" +
            "zt.projectztjs <> '未评估' and\n" +
            "zt.projectresultkf = ?2")
    List<ProjWithTimeVo> askhavezt(int interval, String resultkf);


    @Query(value = "select new com.evan.wj.vo.ProjWithTimeVo(zt.timed,\n" +
            "                          ov.projectid, ov.projectname, ov.projectdetails,\n" +
            "                          ov.projectsl, ov.cas, ov.khname, ov.khryname,\n" +
            "                          ov.khrytype, ov.khrycontact, ov.khryisreal,\n" +
            "                          ov.khtype, ov.cohistory, ov.ismoney,\n" +
            "                          ov.isacceptsw, ov.bz, ov.modelname,\n" +
            "                          ov.createdate, ov.createname,\n" +
            "                          zt.projectztjs, zt.evaluationname,\n" +
            "                          zt.evaluationdate, zt.istaskjs, zt.taskname,\n" +
            "                          zt.taskdate, zt.fkztkf, zt.fkname,\n" +
            "                          zt.fkdate, zt.projectresultkf,\n" +
            "                          zt.projectresultname, zt.projectresultdate,\n" +
            "                          zt.khfeedback, zt.khfeedbackname, zt.khfeedbackdate)\n" +
            "from Project_Overview ov,ProjectZt zt\n" +
            "where ov.projectid = zt.projectid and\n" +
            "zt.timed < ?1 * 24 and\n" +
            "zt.projectztjs <> '未评估'")
    List<ProjWithTimeVo> askhaveztall(int interval);

    public boolean existsByProjectid(int projectid);

    @Query(value = "select new com.evan.wj.vo.ProjWithTimeVo(zt.timed,\n" +
            "                          ov.projectid, ov.projectname, ov.projectdetails,\n" +
            "                          ov.projectsl, ov.cas, ov.khname, ov.khryname,\n" +
            "                          ov.khrytype, ov.khrycontact, ov.khryisreal,\n" +
            "                          ov.khtype, ov.cohistory, ov.ismoney,\n" +
            "                          ov.isacceptsw, ov.bz, ov.modelname,\n" +
            "                          ov.createdate, ov.createname,\n" +
            "                          zt.projectztjs, zt.evaluationname,\n" +
            "                          zt.evaluationdate, zt.istaskjs, zt.taskname,\n" +
            "                          zt.taskdate, zt.fkztkf, zt.fkname,\n" +
            "                          zt.fkdate, zt.projectresultkf,\n" +
            "                          zt.projectresultname, zt.projectresultdate,\n" +
            "                          zt.khfeedback, zt.khfeedbackname, zt.khfeedbackdate)\n" +
            "from Project_Overview ov,ProjectZt zt\n" +
            "where ov.projectid = zt.projectid and\n" +
            "ov.projectid = ?1")
    ProjWithTimeVo askproj(int projectid);

    @Query(value = "select new com.evan.wj.vo.ProjWithTimeVo(zt.timed,\n" +
            "                          ov.projectid, ov.projectname, ov.projectdetails,\n" +
            "                          ov.projectsl, ov.cas, ov.khname, ov.khryname,\n" +
            "                          ov.khrytype, ov.khrycontact, ov.khryisreal,\n" +
            "                          ov.khtype, ov.cohistory, ov.ismoney,\n" +
            "                          ov.isacceptsw, ov.bz, ov.modelname,\n" +
            "                          ov.createdate, ov.createname,\n" +
            "                          zt.projectztjs, zt.evaluationname,\n" +
            "                          zt.evaluationdate, zt.istaskjs, zt.taskname,\n" +
            "                          zt.taskdate, zt.fkztkf, zt.fkname,\n" +
            "                          zt.fkdate, zt.projectresultkf,\n" +
            "                          zt.projectresultname, zt.projectresultdate,\n" +
            "                          zt.khfeedback, zt.khfeedbackname, zt.khfeedbackdate)\n" +
            "from Project_Overview ov,ProjectZt zt\n" +
            "where ov.projectid = zt.projectid and\n" +
            "zt.projectztjs <> '未评估' and\n" +
            "zt.projectresultkf = '待定' and\n" +
            "zt.timed < ?1 * 24")
    List<ProjWithTimeVo> askunfinish(int interval);

    @Query(value = "select new com.evan.wj.vo.ProjWithTimeVo(zt.timed,\n" +
            "                          ov.projectid, ov.projectname, ov.projectdetails,\n" +
            "                          ov.projectsl, ov.cas, ov.khname, ov.khryname,\n" +
            "                          ov.khrytype, ov.khrycontact, ov.khryisreal,\n" +
            "                          ov.khtype, ov.cohistory, ov.ismoney,\n" +
            "                          ov.isacceptsw, ov.bz, ov.modelname,\n" +
            "                          ov.createdate, ov.createname,\n" +
            "                          zt.projectztjs, zt.evaluationname,\n" +
            "                          zt.evaluationdate, zt.istaskjs, zt.taskname,\n" +
            "                          zt.taskdate, zt.fkztkf, zt.fkname,\n" +
            "                          zt.fkdate, zt.projectresultkf,\n" +
            "                          zt.projectresultname, zt.projectresultdate,\n" +
            "                          zt.khfeedback, zt.khfeedbackname, zt.khfeedbackdate)\n" +
            "from Project_Overview ov,ProjectZt zt\n" +
            "where ov.projectid = zt.projectid and\n" +
            "zt.projectztjs <> '未评估' and\n" +
            "zt.projectresultkf <> '待定' and\n" +
            "zt.timed < ?1 * 24")
    List<ProjWithTimeVo> askhavefinish(int interval);

    @Query(value = "select new com.evan.wj.vo.ProjWithTimeVo(zt.timed,\n" +
            "                          ov.projectid, ov.projectname, ov.projectdetails,\n" +
            "                          ov.projectsl, ov.cas, ov.khname, ov.khryname,\n" +
            "                          ov.khrytype, ov.khrycontact, ov.khryisreal,\n" +
            "                          ov.khtype, ov.cohistory, ov.ismoney,\n" +
            "                          ov.isacceptsw, ov.bz, ov.modelname,\n" +
            "                          ov.createdate, ov.createname,\n" +
            "                          zt.projectztjs, zt.evaluationname,\n" +
            "                          zt.evaluationdate, zt.istaskjs, zt.taskname,\n" +
            "                          zt.taskdate, zt.fkztkf, zt.fkname,\n" +
            "                          zt.fkdate, zt.projectresultkf,\n" +
            "                          zt.projectresultname, zt.projectresultdate,\n" +
            "                          zt.khfeedback, zt.khfeedbackname, zt.khfeedbackdate)\n" +
            "from Project_Overview ov,ProjectZt zt\n" +
            "where ov.projectid = zt.projectid and\n" +
            "zt.projectztjs = '已评估-可行' and\n" +
            "zt.projectresultkf = '成交' and\n" +
            "zt.timed < ?1 * 24")
    List<ProjWithTimeVo> askreceive(int interval);



    @Query(value = "select new com.evan.wj.vo.ProjWithTimeVo(zt.timed,\n" +
            "                          ov.projectid, ov.projectname, ov.projectdetails,\n" +
            "                          ov.projectsl, ov.cas, ov.khname, ov.khryname,\n" +
            "                          ov.khrytype, ov.khrycontact, ov.khryisreal,\n" +
            "                          ov.khtype, ov.cohistory, ov.ismoney,\n" +
            "                          ov.isacceptsw, ov.bz, ov.modelname,\n" +
            "                          ov.createdate, ov.createname,\n" +
            "                          zt.projectztjs, zt.evaluationname,\n" +
            "                          zt.evaluationdate, zt.istaskjs, zt.taskname,\n" +
            "                          zt.taskdate, zt.fkztkf, zt.fkname,\n" +
            "                          zt.fkdate, zt.projectresultkf,\n" +
            "                          zt.projectresultname, zt.projectresultdate,\n" +
            "                          zt.khfeedback, zt.khfeedbackname, zt.khfeedbackdate)\n" +
            "from Project_Overview ov,ProjectZt zt\n" +
            "where ov.projectid = zt.projectid")
    List<ProjWithTimeVo> askall();

    @Query(value="update project_overview set test_result=?2 where project_id=?1",nativeQuery=true)
    @Modifying
    @Transactional
    public void updateTestresult(int project_id,String testresult);
}
