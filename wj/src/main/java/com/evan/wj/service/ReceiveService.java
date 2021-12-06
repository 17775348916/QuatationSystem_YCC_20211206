package com.evan.wj.service;

import com.evan.wj.dao.ProjectZtDAO;
import com.evan.wj.dao.Receive_khDAO;
import com.evan.wj.pojo.ProjectZt;
import com.evan.wj.pojo.Receive_kh;
import com.evan.wj.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReceiveService {
    @Autowired
    Receive_khDAO receive_khDAO;
    @Autowired
    Account_informationService account_informationService;
    @Autowired
    ProjectZtDAO projectZtDAO;

    public boolean addreceivekh(Receive_kh newr) {
        if (!projectZtDAO.existsByProjectid(newr.getProjectid())){
            return false;
        } else {
            ProjectZt pzt = projectZtDAO.findByProjectid(newr.getProjectid());
            if (pzt.getProjectztjs().equals("已评估-可行") || pzt.getProjectresultkf().equals("成交")) {
                String name = account_informationService.namebyId(newr.getCreatename());
                newr.setCreatename(name);
                newr.setCreatedate(LocalDateTime.now());
                receive_khDAO.save(newr);
                return true;
            } else {
                return false;
            }
        }
    }

    public Result updatetestresult(Receive_kh newr) {
        String updatename = newr.getUpdatename();
        updatename = account_informationService.namebyId(updatename);
        int projectid = newr.getProjectid();
        if (receive_khDAO.existsByProjectid(projectid)) {
            if (receive_khDAO.existsByProjectidAndIsfinalreceive(projectid, "是")) {
                Receive_kh rkh = receive_khDAO.findByProjectidAndIsfinalreceive(projectid, "是");
                rkh.setTestresult(newr.getTestresult());
                rkh.setUpdatename(updatename);
                rkh.setUpdatedate(LocalDateTime.now());
                receive_khDAO.save(rkh);
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
        return receive_khDAO.findByProjectidAndIsfinalreceive(projectid, "是");
    }

    public String asktestresult(int projectid) {
        if(receive_khDAO.existsByProjectidAndIsfinalreceive(projectid, "是")){
            return receive_khDAO.findByProjectidAndIsfinalreceive(projectid, "是").getTestresult();
        } else {
            return new String("进行中");
        }
    }
}
