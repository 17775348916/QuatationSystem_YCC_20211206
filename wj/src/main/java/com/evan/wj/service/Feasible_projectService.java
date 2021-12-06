package com.evan.wj.service;

import com.evan.wj.dao.*;
import com.evan.wj.pojo.*;
import com.evan.wj.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class Feasible_projectService {
    @Autowired
    Feasible_projectDAO feasibleProjectDAO;
    @Autowired
    Project_OverviewDAO project_overviewDAO;
    @Autowired
    ProjectZtDAO projectZtDAO;
    @Autowired
    Account_informationService account_informationService;
    @Autowired
    Material_info_LRDAO material_info_lrdao;
    @Autowired
    Quoting_modelDAO quoting_modelDAO;
    @Autowired
    Final_priceDAO final_priceDAO;
    @Autowired
    FileService fileService;

    public void NewProjsave(Feasible_project feasible_project) {
        feasibleProjectDAO.save(feasible_project);
    }

    public Result<Feasible_project> queryproj(int projectid) {
        if (feasibleProjectDAO.existsByProjectid(projectid)) {
            return new Result<Feasible_project>(feasibleProjectDAO.findByProjectid(projectid));
        } else {
            return new Result<>("400", false, "项目id不正确");
        }
    }

    public int savenew(Feasible_project newfproj) {
        int projectid = newfproj.getProjectid();
        if (projectZtDAO.existsByProjectid(projectid)) {
            if (feasibleProjectDAO.existsByProjectid(projectid)) {
                return 1;
            } else {
                LocalDateTime ntime = LocalDateTime.now();
                String evaname = account_informationService.namebyId(newfproj.getEvaluationname());
                newfproj.setEvaluationdate(ntime);
                newfproj.setEvaluationname(evaname);
                feasibleProjectDAO.save(newfproj);

                fileService.morefile(newfproj.getPapersjs(), projectid, false);

                ProjectZt pzt = projectZtDAO.findByProjectid(projectid);
                pzt.setEvaluationname(evaname);
                pzt.setEvaluationdate(ntime);
                projectZtDAO.save(pzt);
                this.culculate(projectid, evaname);
                return 0;
            }
        } else {
            return 2;
        }
    }

    public void culculate(int projectid, String evaname) {
        ProjectZt pzt = projectZtDAO.findByProjectid(projectid);
        Project_Overview pov = project_overviewDAO.findByProjectid(projectid);
        Feasible_project fproj = feasibleProjectDAO.findByProjectid(projectid);
        List<Material_info_LR> mlist = material_info_lrdao.findAllByProjectidAndDeleteflag(projectid, "1");

        String khtype = pov.getKhtype(), ismoney = pov.getIsmoney(), isdifficult = fproj.getIsdifficultjs();

        //有问题
        // TODO: 2021/12/4
        Quoting_model model = quoting_modelDAO.askmodel(khtype, ismoney, isdifficult);
        log.info(projectid + "询价模式依据" + khtype + " " + ismoney + " " + isdifficult + "\n" + model.toString());

        String modelname = model.getModelname();

        Final_price fp = new Final_price();
        fp.setProjectid(projectid);
        fp.setPricemodel(modelname);

        fp.setCreatename(evaname);
        fp.setCreatedate(LocalDateTime.now());

        //原料成本
        double costa = 0;
        for(Material_info_LR m : mlist) {
            costa += m.getPrice();
        }
        fp.setMaterialcost(costa);
        //测试成本
        double costb = 0;
        costb += fproj.getHsl() * model.getHprice();
        costb += fproj.getCsl() * model.getCprice();
        costb += fproj.getMsl() * model.getMprice();
        fp.setCsmaterialcost(costb);
        //溶剂耗材成本
        double costc = costb * model.getRate();
        fp.setRjmaterialcost(costc);
        //劳动成本
        double costd1 = fproj.getZsjcs() / 20.5 * model.getFirmwage();
        double costd2 = fproj.getZsjcs() / 20.5 * model.getCollegewage();

        double firmtot = costa + costb + costc + costd1;
        double collegetot = costa + costb + costc + costd2;
        if(model.getModelname().equals("A")) {
            fp.setWorkcost(costd1);
            fp.setAllcost(firmtot);
        } else {
            fp.setWorkcost(costd2);
            fp.setAllcost(collegetot);
        }

        double wbprice = collegetot / model.getCollegecoefficient();
        fp.setWbprice(wbprice);

        double finalprice;
        if(modelname.equals("A")) {
            finalprice = firmtot / model.getFirmcoefficient();
        } else {
            finalprice = firmtot / model.getProductcoefficient();
        }
        fp.setFinalprice(finalprice);
        final_priceDAO.save(fp);

    }

    public double askfinalprice(int projectid) {
        if (final_priceDAO.existsByProjectid(projectid)){
            return final_priceDAO.findByProjectid(projectid).getFinalprice();
        }
        else return -1;
    }

    public double askwbprice(int projectid) {
        if (final_priceDAO.existsByProjectid(projectid)){
            return final_priceDAO.findByProjectid(projectid).getWbprice();
        }
        else return -1;
    }

    public Final_price askpriceinfo(int projectid) {
        return final_priceDAO.findByProjectid(projectid);
    }
}
