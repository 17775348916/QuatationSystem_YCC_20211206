package com.evan.wj.service;

import com.evan.wj.dao.Material_infoDAO;
import com.evan.wj.dao.Material_info_LRDAO;
import com.evan.wj.dao.Material_needDAO;
import com.evan.wj.dao.ProjectZtDAO;
import com.evan.wj.pojo.Material_info;
import com.evan.wj.pojo.Material_info_LR;
import com.evan.wj.pojo.Material_need;
import com.evan.wj.pojo.ProjectZt;
import com.evan.wj.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class MaterialService {
    @Autowired
    Material_infoDAO material_infoDAO;
    @Autowired
    Material_needDAO material_needDAO;
    @Autowired
    Material_info_LRDAO material_info_lrdao;

    @Autowired
    Project_OverviewService project_overviewService;

    @Autowired
    ProjectZtDAO projectZtDAO;

    @Autowired
    Account_informationService account_informationService;

    public boolean setcantbuy(int projectid, String reason, String fkname) {
        if (projectZtDAO.existsByProjectid(projectid)) {
            ProjectZt pzt = projectZtDAO.findByProjectid(projectid);
//            pzt.setIstaskjs("未下达");
            pzt.setFkztkf("已反馈-买不到");
            pzt.setReason(reason);
            fkname = account_informationService.namebyId(fkname);
            pzt.setFkname(fkname);
            pzt.setFkdate(LocalDateTime.now());
            List<Material_need> materialNeeds = material_needDAO.findAllByProjectidAndDeleteflag(projectid, "1");
            for(Material_need mneed : materialNeeds) {
                Material_info_LR mlr = new Material_info_LR();
                mlr.setProjectid(projectid);
                mlr.setMaterialneedid(mneed.getMaterialneedid());
                mlr.setMaterialid(mneed.getMaterialid());
                mlr.setMaterialname(mneed.getMaterialname());
                mlr.setCas(mneed.getCas());
                mlr.setIsbuy("买不到");
                //todo
                mlr.setDeleteflag("1");
                mlr.setCreatename(fkname);
                mlr.setUpdatename(fkname);
                mlr.setCreatedate(LocalDateTime.now());
                mlr.setUpdatedate(LocalDateTime.now());
                mlr.setNotreason(reason);
                material_info_lrdao.save(mlr);
                mneed.setDeleteflag("0");
                material_needDAO.save(mneed);
            }
            projectZtDAO.save(pzt);
            return true;
        } else {
            return false;
        }
    }

    public List<Material_need> askneed(int projectid) {
        return material_needDAO.findAllByProjectidAndDeleteflag(projectid, "1");
    }

    public boolean xiada(List<Material_need> mlist) {
        if (mlist.size() < 1) {
            return false;
        }
        int projectid = mlist.get(0).getProjectid();
        String snameid = mlist.get(0).getCreatename();
        log.info("技术下达原料信息为：");
        for(Material_need m : mlist) {
            log.info(m.toString());
            if(m.getProjectid()!=projectid || !snameid.equals(m.getCreatename())) {
                return false;
            }
        }
        String cname = account_informationService.namebyId(snameid);
        for(Material_need m : mlist) {
            m.setCreatename(cname);
            m.setUpdatename(cname);
            m.setCreatedate(LocalDateTime.now());
            m.setUpdatedate(LocalDateTime.now());
            m.setDeleteflag("1");
            material_needDAO.save(m);
        }
        if (project_overviewService.xiada(projectid,cname)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean fankui(List<Material_info_LR> mlist) {
        if (mlist.size() < 1) {
            return false;
        }
        int projectid = mlist.get(0).getProjectid();
        String snameid = mlist.get(0).getCreatename();
        log.info("客服反馈原料信息为：");
        for(Material_info_LR m : mlist) {
            log.info(m.toString());
            if(m.getProjectid()!=projectid || !snameid.equals(m.getCreatename())) {
                return false;
            }
        }
        if (projectZtDAO.existsByProjectid(projectid)) {
            String cname = account_informationService.namebyId(snameid);
            LocalDateTime ldt = LocalDateTime.now();
            for(Material_info_LR m : mlist) {
                m.setCreatename(cname);
                m.setUpdatename(cname);
                m.setCreatedate(ldt);
                m.setUpdatedate(ldt);
                material_info_lrdao.save(m);
            }
            ProjectZt pzt = projectZtDAO.findByProjectid(projectid);
            pzt.setFkztkf("已反馈-能买到");
            pzt.setFkdate(ldt);
            pzt.setFkname(cname);
            projectZtDAO.save(pzt);
            return true;
        } else {
            return false;
        }
    }

    public List<Material_info> queryinfo(String info){
        return material_infoDAO.askinfo(info);
    }

    public void saveinfo(Material_info newmaterial){
        material_infoDAO.save(newmaterial);
    }

    public boolean updateinfo(Material_info upmaterial) {
        int id;
        if (upmaterial.getMaterialid() > 0) {
            id = upmaterial.getMaterialid();
        } else {
            return false;
        }
        if ( material_infoDAO.existsByMaterialid(id) ) {
            upmaterial.setUpdatedate(LocalDateTime.now());
            String tmp = upmaterial.getUpdatename();
            tmp = account_informationService.namebyId(tmp);
            upmaterial.setUpdatename(tmp);
            material_infoDAO.save(upmaterial);
            return true;
        } else {
            return false;
        }
    }

    public int addnewinfo(Material_info newm) {
        String tmp = newm.getCreatename();
        tmp = account_informationService.namebyId(tmp);
        newm.setCreatename(tmp);
        newm.setUpdatename(tmp);
        LocalDateTime time = LocalDateTime.now();
        newm.setCreatedate(time);
        newm.setUpdatedate(time);
        newm = material_infoDAO.save(newm);
        log.info(newm.getMaterialid() + " ");
        return newm.getMaterialid();
    }

    public boolean deleteinfo(int id) {
        if(material_infoDAO.existsByMaterialid(id)) {
            material_infoDAO.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Material_info_LR> askneedlr(int projectid) {
        return material_info_lrdao.findAllByProjectidAndDeleteflag(projectid, "1");
    }

    public Result<String> feedback(int projectid) {
        ProjectZt pzt = projectZtDAO.findByProjectid(projectid);
        if (pzt.getFkztkf().equals("未反馈")) {
            return new Result<String>("400",false,"该项目原料未反馈");
        } else if (pzt.getFkztkf().equals("已反馈-买不到")) {
            return new Result<String>("100", true, "该项目有原料买不到。具体原因为：" + pzt.getReason());
        } else {
            return new Result<String>("客服反馈：所有原料均可买到，原料价格已提交后台");
        }
    }

    public Page<Material_info> askallmaterial(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return material_infoDAO.findAll(pageable);
    }
}
