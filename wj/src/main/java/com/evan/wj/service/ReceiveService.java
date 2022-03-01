package com.evan.wj.service;

import com.evan.wj.dao.ProjectZtDAO;
import com.evan.wj.dao.Project_OverviewDAO;
import com.evan.wj.dao.Receive_khDAO;
import com.evan.wj.pojo.ProjectZt;
import com.evan.wj.pojo.Receive_kh;
import com.evan.wj.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReceiveService {
    @Autowired
    Receive_khDAO receive_khDAO;
    @Autowired
    Account_informationService account_informationService;
    @Autowired
    ProjectZtDAO projectZtDAO;
    @Autowired
    Project_OverviewDAO project_overviewDAO;

    public boolean updatePriceAndTime(Receive_kh newr) {
        if (!projectZtDAO.existsByProjectid(newr.getProjectid()) || !receive_khDAO.existsByProjectid(newr.getProjectid())) {
            return false;
        } else {
            String name = account_informationService.namebyId(newr.getUpdatename());
            project_overviewDAO.updatetimed();
            if (!receive_khDAO.existsByProjectidAndIsfinalreceive(newr.getProjectid(), "是")) {
                newr.setCreatedate(LocalDateTime.now());
                newr.setCreatename(name);
                receive_khDAO.save(newr);
            } else {
                newr.setUpdatename(name);
                newr.setUpdatedate(LocalDateTime.now());
                receive_khDAO.updatePriceAndTime(newr.getProjectid(), newr.getIsfinalreceive(),
                        newr.getReceiveprice(), newr.getNeededtime(), newr.getUpdatename(), newr.getUpdatedate());
            }
        }
        return true;
    }

    public boolean addreceivekh(Receive_kh newr) {
        if (!projectZtDAO.existsByProjectid(newr.getProjectid())) {
            return false;
        } else {
            ProjectZt pzt = projectZtDAO.findByProjectid(newr.getProjectid());
            if (pzt.getProjectztjs().equals("已评估-可行") || pzt.getProjectresultkf().equals("成交")) {
                project_overviewDAO.updatetimed();
                String name = account_informationService.namebyId(newr.getCreatename());
                if (!receive_khDAO.existsByProjectidAndIsfinalreceive(newr.getProjectid(), "否")) {
                    newr.setCreatename(name);
                    newr.setCreatedate(LocalDateTime.now());
                    receive_khDAO.save(newr);
                } else {
                    newr.setUpdatename(name);
                    newr.setUpdatedate(LocalDateTime.now());
                    receive_khDAO.updateReceivekhnameAndReceivekhphoneAndReceiveintention(newr.getProjectid(), newr.getReceivekhname(), newr.getReceivekhphone(), newr.getReceiveintention(), newr.getUpdatename(), newr.getUpdatedate());
                }
                return true;
            } else {
                return false;
            }
        }
    }

    @Transactional
    public Result updatetestresult(Receive_kh newr) {
        String updatename = newr.getUpdatename();
        updatename = account_informationService.namebyId(updatename);
        int projectid = newr.getProjectid();
        if (receive_khDAO.existsByProjectid(projectid)) {
            if (receive_khDAO.existsByProjectidAndIsfinalreceive(projectid, "是")) {
                Receive_kh rkh = receive_khDAO.findByProjectidAndIsfinalreceive(projectid, "是");
                project_overviewDAO.updatetimed();
                rkh.setTestresult(newr.getTestresult());
                rkh.setUpdatename(updatename);
                rkh.setUpdatedate(LocalDateTime.now());
                receive_khDAO.updateTestresult(rkh.getProjectid(), rkh.getTestresult(), rkh.getUpdatename(), rkh.getUpdatedate());
                project_overviewDAO.updateTestresult(rkh.getProjectid(), rkh.getTestresult());
                return new Result();
            } else {
                return new Result("400", false, "还未确认最终接单用户");
            }
        } else {
            return new Result("400", false, "项目信息有误");
        }
    }

    public List<Receive_kh> askreceive(int projectid) {
        return receive_khDAO.findAllByProjectidAndIsfinalreceive(projectid, "否");
    }

    public Receive_kh askfinalreceive(int projectid) {
        if(receive_khDAO.existsByProjectidAndIsfinalreceive(projectid,"是")){
            return receive_khDAO.findByProjectidAndIsfinalreceive(projectid, "是");
        }
        log.error("[ReceiveService.askfinalreveive]projectid为"+projectid+"的项目不存在接单客户沟通任务表");
        return new Receive_kh();
    }

    public String asktestresult(int projectid) {
        if (receive_khDAO.existsByProjectidAndIsfinalreceive(projectid, "是")) {
            return receive_khDAO.findByProjectidAndIsfinalreceive(projectid, "是").getTestresult();
        } else {
            return new String("进行中");
        }
    }

    /**
     * 批量查看接单客户意向
     * @param projectIds
     * @return
     */
    public List<Receive_kh> queryCustomerIntension(List<Integer> projectIds) {
        List<Receive_kh> res = new ArrayList<>();
        for (int projectId : projectIds) {
            if (receive_khDAO.existsByProjectidAndIsfinalreceive(projectId,"否")) {
                res.add(receive_khDAO.findByProjectidAndIsfinalreceive(projectId, "否"));
            } else {
                log.error("[ReceiveService.queryCustomerIntension]不存在项目projectid为"+projectId+"的接单客户意向");
                res.add(new Receive_kh());
            }
        }
        return res;
    }

    /**
     * 批量查看确认接单条件
     * @param projectIds
     * @return
     */
    public List<Receive_kh> queryCustomerConfirm(List<Integer> projectIds) {
        List<Receive_kh> res = new ArrayList<>();
        for (int projectId : projectIds) {
            if (receive_khDAO.existsByProjectidAndIsfinalreceive(projectId,"是")) {
                res.add(receive_khDAO.findByProjectidAndIsfinalreceive(projectId, "是"));
            } else {
                log.error("[ReceiveService.queryCustomerIntension]不存在项目projectid为"+projectId+"的确认接单条件");

                res.add(new Receive_kh());
            }
        }
        return res;
    }

}
