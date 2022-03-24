package com.evan.wj.service;

import com.evan.wj.dao.ProjectZtDAO;
import com.evan.wj.dao.Project_OverviewDAO;
import com.evan.wj.dao.Receive_khDAO;
import com.evan.wj.pojo.ProjectZt;
import com.evan.wj.pojo.Project_Overview;
import com.evan.wj.vo.ProjWithTimeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    /**
     * 根据projectId查看具体项目信息
     *
     * @param projectId
     * @return
     */
    public Project_Overview queryProjectOverviewByProjectId(int projectId) {
        return project_overviewdao.findByProjectid(projectId);
    }

    public Page<Project_Overview> showUnavailablesPage(int page, int size, int interval) {
        project_overviewdao.updatetime();
        Pageable pageable = PageRequest.of(page, size);
        Specification<Project_Overview> specification = new Specification<Project_Overview>() {
            @Override
            public Predicate toPredicate(Root<Project_Overview> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                predicateList.add(criteriaBuilder.equal(root.get("isacceptsw"), "无"));
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("time"), interval * 24));
                Predicate[] predicates = new Predicate[predicateList.size()];
                return query.where(predicateList.toArray(predicates)).getRestriction();
            }
        };
        return project_overviewdao.findAll(specification, pageable);
    }

    public int caculateDays(LocalDateTime dt1, LocalDateTime dt2) {
        long t1 = dt1.toEpochSecond(ZoneOffset.ofHours(0));
        long day1 = t1 / (60 * 60 * 24);
        long t2 = dt2.toEpochSecond(ZoneOffset.ofHours(0));
        long day2 = t2 / (60 * 60 * 24);
        return (int) (day2 - day1);
    }

    /**
     * 分页查询尚未评估的项目
     *
     * @param page     第几页
     * @param size     一页有几条
     * @param interval 最近几天
     * @return 第page页的size条数据
     */
    public Page<Project_Overview> findByIntervalAndStatus(int page, int size, int interval, String status) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Project_Overview> specification = new Specification<Project_Overview>() {
            @Override
            public Predicate toPredicate(Root<Project_Overview> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                Join<Project_Overview, ProjectZt> join = root.join("projectZt", JoinType.LEFT);
                predicateList.add(criteriaBuilder.like(join.get("projectztjs"), status + "%"));
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(join.get("timed"), interval * 24));
                Predicate[] predicates = new Predicate[predicateList.size()];
                return query.where(predicateList.toArray(predicates)).getRestriction();
            }
        };
        return project_overviewdao.findAll(specification, pageable);
    }

    /**
     * @param page
     * @param size
     * @param interval
     * @param status
     * @param resultkf 客服反馈结果（成交，未成交，待定）
     * @return
     */
    public Page<Project_Overview> findByIntervalAndStatusAndResult(int page, int size, int interval, String status, String resultkf) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Project_Overview> specification = new Specification<Project_Overview>() {
            @Override
            public Predicate toPredicate(Root<Project_Overview> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                Join<Project_Overview, ProjectZt> join = root.join("projectZt", JoinType.LEFT);
                predicateList.add(criteriaBuilder.like(join.get("projectztjs"), status + "%"));
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(join.get("timed"), interval * 24));
                predicateList.add(criteriaBuilder.equal(join.get("projectresultkf"), resultkf));
                Predicate[] predicates = new Predicate[predicateList.size()];
                return query.where(predicateList.toArray(predicates)).getRestriction();
            }
        };
        return project_overviewdao.findAll(specification, pageable);
    }

    /**
     * @param page
     * @param size
     * @param interval
     * @param status
     * @param resultkf 客服反馈结果（成交，未成交，待定）
     * @return
     */
    public Page<Project_Overview> findByIntervalAndStatusAndNotByGivingResult(int page, int size, int interval, String status, String resultkf) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Project_Overview> specification = new Specification<Project_Overview>() {
            @Override
            public Predicate toPredicate(Root<Project_Overview> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                Join<Project_Overview, ProjectZt> join = root.join("projectZt", JoinType.LEFT);
                predicateList.add(criteriaBuilder.like(join.get("projectztjs"), status + "%"));
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(join.get("timed"), interval * 24));
                predicateList.add(criteriaBuilder.notEqual(join.get("projectresultkf"), resultkf));
                Predicate[] predicates = new Predicate[predicateList.size()];
                return query.where(predicateList.toArray(predicates)).getRestriction();
            }
        };
        return project_overviewdao.findAll(specification, pageable);
    }


    /**
     * 分页查找尚未填写实验结果testresult的项目
     *
     * @param page
     * @param size
     * @param interval
     * @return
     */
    public Page<Project_Overview> findByInterval(int page, int size, int interval) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Project_Overview> specification = new Specification<Project_Overview>() {
            @Override
            public Predicate toPredicate(Root<Project_Overview> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                Join<Project_Overview, ProjectZt> join = root.join("projectZt", JoinType.LEFT);
                predicateList.add(criteriaBuilder.equal(join.get("projectztjs"), "已评估-可行"));
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(join.get("timed"), interval * 24));
                predicateList.add(criteriaBuilder.equal(join.get("projectresultkf"), "成交"));
                predicateList.add(criteriaBuilder.isNull(root.get("testresult")));
                Predicate[] predicates = new Predicate[predicateList.size()];
                return query.where(predicateList.toArray(predicates)).getRestriction();
            }
        };
        return project_overviewdao.findAll(specification, pageable);
    }

    public boolean xiada(int projectid, String cname) {
        if (projectZtDAO.existsByProjectid(projectid)) {
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

    /**
     * 分页查找已经填写实验结果testresult的项目
     *
     * @param page
     * @param size
     * @param interval
     * @return
     */
    public Page<Project_Overview> findByIntervalAlreadyFilledTestResult(int page, int size, int interval) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Project_Overview> specification = new Specification<Project_Overview>() {
            @Override
            public Predicate toPredicate(Root<Project_Overview> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                Join<Project_Overview, ProjectZt> join = root.join("projectZt", JoinType.LEFT);
                predicateList.add(criteriaBuilder.equal(join.get("projectztjs"), "已评估-可行"));
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(join.get("timed"), interval * 24));
                predicateList.add(criteriaBuilder.equal(join.get("projectresultkf"), "成交"));
                predicateList.add(criteriaBuilder.isNotNull(root.get("testresult")));
                Predicate[] predicates = new Predicate[predicateList.size()];
                return query.where(predicateList.toArray(predicates)).getRestriction();
            }
        };
        return project_overviewdao.findAll(specification, pageable);
    }

    public List<Project_Overview> queryprojunzt(int interval) {
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
        if ("所有".equals(resultkf)) {
            return project_overviewdao.askhaveztall(interval);
        } else {
            return project_overviewdao.askhavezt(interval, resultkf);
        }
    }

    public ProjWithTimeVo queryproj(int projectid) {
        ProjWithTimeVo proj = new ProjWithTimeVo(-1);
        if (project_overviewdao.existsByProjectid(projectid) && projectZtDAO.existsByProjectid(projectid)) {
//            project_overviewdao.updateonetimed(projectid);
            proj = project_overviewdao.askproj(projectid);
            proj.setDura(project_overviewdao.asktime(projectid));
        }
        return proj;
    }

    public boolean addnewproj(Project_Overview newproj) {

        String tmp = newproj.getCreatename();
        if (tmp == null) {
            return false;
        }
        tmp = account_informationService.namebyId(tmp);
        newproj.setCreatename(tmp);
        newproj.setUpdatename(tmp);
        newproj.setCreatedate(LocalDateTime.now());
        newproj.setUpdatedate(LocalDateTime.now());

        if ("有".equals(newproj.getIsdeal()) && "真实".equals(newproj.getKhryisreal()) && ("有钱".equals(newproj.getIsmoney()) || "一般".equals(newproj.getIsmoney()))) {
            newproj.setIsacceptsw("有");
        } else {
            newproj.setIsacceptsw("无");
        }

        try {
            this.save(newproj);
            project_overviewdao.updatetimed();
        } catch (Exception e) {
            return false;
        }

        fileService.morefile(newproj.getProjectdetails(), newproj.getProjectid(), true);

        return true;
    }

    public List<ProjWithTimeVo> queryunfinish(int interval) {
        project_overviewdao.updatetimed();
        List<ProjWithTimeVo> pwt = project_overviewdao.askunfinish(interval);
        for (ProjWithTimeVo proj : pwt) {
            log.info(proj.getCreatedate() + "   " + proj.getEvaluationdate() + "\n");
            proj.setDura((int) java.time.Duration.between(proj.getCreatedate(), proj.getEvaluationdate()).toHours());
        }
        return pwt;
    }

    public List<ProjWithTimeVo> queryhavefinish(int interval) {
        project_overviewdao.updatetimed();
        List<ProjWithTimeVo> pwt = project_overviewdao.askhavefinish(interval);
        for (ProjWithTimeVo proj : pwt) {
            log.info(proj.getCreatedate() + "   " + proj.getEvaluationdate() + "\n");
            proj.setDura((int) java.time.Duration.between(proj.getCreatedate(), proj.getEvaluationdate()).toHours());
        }
        return pwt;
    }

    public boolean updatekhfeedback(int projectid, String feedback, String name) {
        if (projectZtDAO.existsByProjectid(projectid)) {
            ProjectZt pzt = projectZtDAO.findByProjectid(projectid);
            if ("".equals(feedback)) {
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
        if (projectZtDAO.existsByProjectid(projectid)) {
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
        for (ProjWithTimeVo proj : pwt) {
            log.info(proj.getCreatedate() + "   " + proj.getEvaluationdate() + "\n");
            proj.setDura((int) java.time.Duration.between(proj.getCreatedate(), proj.getEvaluationdate()).toHours());
        }
        return pwt;
    }

    public List<ProjWithTimeVo> queryhavereceive(int interval) {
        project_overviewdao.updatetimed();
        List<ProjWithTimeVo> pwt = project_overviewdao.askreceive(interval);
        pwt.removeIf(projWithTimeVo -> !(receive_khDAO.existsByProjectidAndTestresult(projWithTimeVo.getProjectid(), "成功") || receive_khDAO.existsByProjectidAndTestresult(projWithTimeVo.getProjectid(), "失败")));
        for (ProjWithTimeVo proj : pwt) {
            log.info(proj.getCreatedate() + "   " + proj.getEvaluationdate() + "\n");
            proj.setDura((int) java.time.Duration.between(proj.getCreatedate(), proj.getEvaluationdate()).toHours());
        }
        return pwt;
    }

    public Page<Project_Overview> queryallByIntervalAndResultkf(int page, int size, int interval, String resultkf) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Project_Overview> specification = new Specification<Project_Overview>() {
            @Override
            public Predicate toPredicate(Root<Project_Overview> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                Join<Project_Overview, ProjectZt> join = root.join("projectZt", JoinType.LEFT);
                predicateList.add(criteriaBuilder.equal(root.get("isacceptsw"), "有"));
                if (StringUtils.isNotEmpty(resultkf)) {
                    predicateList.add(criteriaBuilder.equal(join.get("projectresultkf"), resultkf));
                }
                if (interval != 30000) {
                    predicateList.add(criteriaBuilder.lessThanOrEqualTo(join.get("timed"), interval * 24));
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                return query.where(predicateList.toArray(predicates)).getRestriction();
            }
        };
        return project_overviewdao.findAll(specification, pageable);
    }

    public Page<Project_Overview> queryallByIntervalAndResultkfAndKhrynameAndProjectnameAndCas(int page, int size, int interval, String resultkf, String khryname, String projectname, String cas, String khName, String startTime, String endTime) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Project_Overview> specification = new Specification<Project_Overview>() {
            @Override
            public Predicate toPredicate(Root<Project_Overview> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                Join<Project_Overview, ProjectZt> join = root.join("projectZt", JoinType.LEFT);
                predicateList.add(criteriaBuilder.equal(root.get("isacceptsw"), "有"));
                if (StringUtils.isNotEmpty(resultkf) && !"所有".equals(resultkf)) {
                    predicateList.add(criteriaBuilder.equal(join.get("projectresultkf"), resultkf));
                }
                if (interval != 30000) {
                    predicateList.add(criteriaBuilder.lessThanOrEqualTo(join.get("timed"), interval * 24));
                }
                if (StringUtils.isNotEmpty(khryname)) {
                    predicateList.add(criteriaBuilder.equal(root.get("khryname"), khryname));
                }
                if (StringUtils.isNotEmpty(projectname)) {
                    predicateList.add(criteriaBuilder.like(root.get("projectname"), "%" + projectname + "%"));
                }
                if (StringUtils.isNotEmpty(cas)) {
                    predicateList.add(criteriaBuilder.equal(root.get("cas"), cas));
                }
                if (StringUtils.isNotEmpty(khName)) {
                    predicateList.add(criteriaBuilder.equal(root.get("khname"), khName));
                }
                if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
                    predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdate").as(String.class), startTime));
                    predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdate").as(String.class), endTime));

                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                return query.where(predicateList.toArray(predicates)).getRestriction();
            }
        };
        return project_overviewdao.findAll(specification, pageable);
    }

    public Page<Project_Overview> queryallByInterval(int page, int size, int interval) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Project_Overview> specification = new Specification<Project_Overview>() {
            @Override
            public Predicate toPredicate(Root<Project_Overview> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                Join<Project_Overview, ProjectZt> join = root.join("projectZt", JoinType.LEFT);
                predicateList.add(criteriaBuilder.equal(root.get("isacceptsw"), "有"));
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(join.get("timed"), interval * 24));
                Predicate[] predicates = new Predicate[predicateList.size()];
                return query.where(predicateList.toArray(predicates)).getRestriction();
            }
        };
        return project_overviewdao.findAll(specification, pageable);
    }
}
