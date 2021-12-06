package com.evan.wj.controller;

import com.alibaba.fastjson.JSONObject;
import com.evan.wj.pojo.Material_info;
import com.evan.wj.pojo.Material_info_LR;
import com.evan.wj.pojo.Material_need;
import com.evan.wj.result.Result;
import com.evan.wj.service.MaterialService;
import com.evan.wj.vo.FkCantBuyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
public class MaterialController {
    @Autowired
    MaterialService material_Service;

    @CrossOrigin
    @PostMapping(value = "/api/searchmaterial")
    @ResponseBody
    public Result<List<Material_info>> search(@RequestBody JSONObject json) {
        String info = json.getString("info");
        log.info("查询关键字为：" + info);
        return new Result<List<Material_info>>(material_Service.queryinfo(info));
    }

    @CrossOrigin
    @PostMapping(value = "api/insertmaterial")
    @ResponseBody
    public Result<Integer> insert(@RequestBody Material_info newmaterial) {
        log.info("新添加原料：\n" + newmaterial.toString());
        return new Result<Integer>(material_Service.addnewinfo(newmaterial));
    }

    @CrossOrigin
    @PostMapping(value = "api/deletematerial")
    @ResponseBody
    public Result delete(@RequestBody JSONObject json) {
        int id = json.getInteger("id");
        if(material_Service.deleteinfo(id)) {
            return new Result();
        } else {
            return new Result("400",false,"无要删除ID信息");
        }
    }

    @CrossOrigin
    @PostMapping(value = "api/updatematerial")
    @ResponseBody
    public Result update(@RequestBody Material_info materialInfo) {
        if(material_Service.updateinfo(materialInfo)) {
//            material_Service.saveinfo(materialInfo);
            return new Result();
        } else {
            return new Result("400",false,"无要删除ID信息");
        }
    }

    @CrossOrigin
    @PostMapping(value = "api/xiada")
    @ResponseBody Result xiada(@RequestBody List<Material_need> mlist) {
        if(material_Service.xiada(mlist)) {
            return new Result();
        } else {
            return new Result("400",false,"下达内容有误");
        }
    }

    @CrossOrigin
    @PostMapping(value = "api/querymaterialneed")
    @ResponseBody Result<List<Material_need> > querymaterialneed(@RequestBody JSONObject json) {
        int projectid = -1;
        if(json.getInteger("projectid") != null) {
            projectid = json.getInteger("projectid");
        } else {
            return new Result("400",false,"项目编号有误");
        }
        List<Material_need> materialNeeds = material_Service.askneed(projectid);
        log.info(projectid + "的需要材料情况为\n" + materialNeeds.toString());
        return new Result<List<Material_need>>(material_Service.askneed(projectid));
    }

    @CrossOrigin
    @PostMapping(value = "api/JS/feedback")
    @ResponseBody Result<String> feedback(@RequestBody JSONObject json) {
        int projectid = -1;
        if(json.getInteger("projectid") != null) {
            projectid = json.getInteger("projectid");
//            List<Material_need> materialNeeds = material_Service.askneed(projectid);
//            log.info(projectid + "的需要材料情况为\n" + materialNeeds.toString());
            return material_Service.feedback(projectid);
        } else {
            return new Result("400",false,"项目编号有误");
        }
    }

    @CrossOrigin
    @PostMapping(value = "api/querymateriallr")
    @ResponseBody Result<List<Material_info_LR> > querymateriallr(@RequestBody JSONObject json) {
        int projectid = -1;
        if(json.getInteger("projectid") != null) {
            projectid = json.getInteger("projectid");
        } else {
            log.info(json.toJSONString());
            return new Result<>("400",false,"未获得正确的项目编号");
        }
        log.info(projectid + " ");
        List<Material_info_LR> materialNeeds = material_Service.askneedlr(projectid);
        log.info(projectid + "的需要材料情况为\n" + materialNeeds.toString());
        return new Result<List<Material_info_LR>>(materialNeeds);
    }

    @CrossOrigin
    @PostMapping(value = "api/lookallmaterial")
    @ResponseBody Result<List<Material_info> >lookallmaterial(@RequestBody JSONObject json) {
        log.info("所有原料库信息：");
        List<Material_info> materialInfos= material_Service.askallmaterial();
        for (Material_info materialInfo : materialInfos) {
            log.info(materialInfo.toString());
        }
        return new Result<List<Material_info>>(materialInfos);
    }

    @CrossOrigin
    @PostMapping(value = "api/cantbuy")
    @ResponseBody Result dealcantbuy(@RequestBody FkCantBuyVo newfk) {
        log.info("买不到原料原因为" + newfk.toString());
        int projectid;
        if(newfk.getProjectid() > 0) {
            projectid = newfk.getProjectid();
        } else {
            return new Result("400",false,"未获得正确的项目编号");
        }
        if(material_Service.setcantbuy(newfk.getProjectid(), newfk.getReason(), newfk.getFkname())) {
            return new Result();
        } else {
            return new Result("400",false,"反馈失败");
        }
    }

    @CrossOrigin
    @PostMapping(value = "api/fankui")
    @ResponseBody Result fankui(@RequestBody List<Material_info_LR> mlist) {
//        log.info("原料反馈\n" + material_info_lrs.toString());
        if(material_Service.fankui(mlist)) {
            return new Result();
        } else {
            return new Result("400",false,"反馈内容有误");
        }
    }
}
