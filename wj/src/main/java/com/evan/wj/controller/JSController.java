package com.evan.wj.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.evan.wj.pojo.Feasible_project;
import com.evan.wj.pojo.Final_price;
import com.evan.wj.pojo.No_feasible_project;
import com.evan.wj.pojo.ProjectZt;
import com.evan.wj.result.Result;
import com.evan.wj.service.Feasible_projectService;
import com.evan.wj.service.No_feasible_projectService;
import com.evan.wj.service.Project_OverviewService;
import com.evan.wj.vo.ProjWithTimeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class JSController {
    @Autowired
    Feasible_projectService feasible_projectService;
    @Autowired
    No_feasible_projectService no_feasible_projectService;
    @Autowired
    Project_OverviewService project_overviewService;

    @CrossOrigin
    @PostMapping(value = "/api/JS/AddNewFeasibleProj")
    @ResponseBody
    public Result addnewfeasible(@RequestBody Feasible_project newfproj) {
        log.info("新增可行项目信息为：" + newfproj.toString());
        int code = feasible_projectService.savenew(newfproj);
        if (code == 0) {
            return new Result();
        } else if (code == 1) {
            return new Result("400", false, "项目已存在评估信息");
        } else if (code == 2) {
            return new Result("400", false, "项目编号错误");
        } else {
            return new Result("400", false, "项目未知错误");
        }
    }

    @CrossOrigin
    @PostMapping(value = "/api/JS/AddNewUnfeasibleProj")
    @ResponseBody
    public Result addnewnofeasible(@RequestBody No_feasible_project newfproj) {
        log.info("新增不可行项目信息为：" + newfproj.toString());
        if (no_feasible_projectService.NewProjsave(newfproj)) {
            return new Result();
        } else {
            return new Result("400", false, "项目信息有误");
        }
    }

    @CrossOrigin
    @PostMapping(value = "/api/queryproj")
    @ResponseBody
    public Result<ProjWithTimeVo> query(@RequestBody JSONObject json) {
        int projectid = -1;
        if (json.getInteger("projectid") != null) {
            projectid = json.getInteger("projectid");
        }
        ProjWithTimeVo proj = project_overviewService.queryproj(projectid);
        log.info("查询项目ID为：" + projectid + "\n项目信息为：" + proj.toString());
        if (proj.getProjectid() < 1) {
            return new Result<ProjWithTimeVo>("400", false, "未找到该项目");
        } else {
            return new Result<ProjWithTimeVo>(proj);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/api/queryfeasible")
    @ResponseBody
    public Result<Feasible_project> queryfeasible(@RequestBody JSONObject json) {
        int projectid = -1;
        if (json.getInteger("projectid") != null) {
            projectid = json.getInteger("projectid");
        }
        return feasible_projectService.queryproj(projectid);
    }

    /**
     * 批量查询可行项目信息
     * @param json
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/api/queryfeasibleByProjectIds")
    @ResponseBody
    public Result<List<Feasible_project>> queryfeasibleByProjectIds(@RequestBody JSONObject json) {
        List<Integer> projectIds = new ArrayList<>();
        try{
            JSONArray projectId = json.getJSONArray("projectIds");
            String js=JSONObject.toJSONString(projectId, SerializerFeature.WriteClassName);
            projectIds = JSONObject.parseArray(js,Integer.class);
        }catch (Exception e){
            log.error("[JsController.queryfeasibleByProjectIds]json解析失败");
        }
        if(null == projectIds){
            log.error("[JsController.queryfeasibleByProjectIds]projectIds为空");
        }
        assert projectIds != null;
        List<Feasible_project> feasibleProjects = feasible_projectService.queryprojByProjectId(projectIds);
        return new Result<>(feasibleProjects);
    }

    @CrossOrigin
    @PostMapping(value = "/api/querynofeasible")
    @ResponseBody
    public Result<No_feasible_project> querynofeasible(@RequestBody JSONObject json) {
        int projectid = -1;
        if (json.getInteger("projectid") != null) {
            projectid = json.getInteger("projectid");
        }
        Result<No_feasible_project> proj = no_feasible_projectService.queryproj(projectid);
        log.info("查询项目ID为：" + projectid + "\n最终价格为：" + proj.getData().toString());
        return proj;
    }

    @CrossOrigin
    @PostMapping(value = "/api/queryfinalprice")
    @ResponseBody
    public Result<Final_price> queryfinalprice(@RequestBody JSONObject json) {
        int projectid = -1;
        if (json.getInteger("projectid") != null) {
            projectid = json.getInteger("projectid");
        }
        Result<Final_price> finalprice = new Result<Final_price>(feasible_projectService.askfinalprice(projectid));
        log.info("查询项目ID为：" + projectid + "\n项目信息为：" + finalprice.getData().toString());
        return finalprice;
    }

    @CrossOrigin
    @PostMapping(value = "/api/queryOutsourcingPrice")
    @ResponseBody
    public Result<String> queryOutsourcingPrice(@RequestBody JSONObject json) {
        int projectid = -1;
        if (json.getInteger("projectid") != null) {
            projectid = json.getInteger("projectid");
        }
        return new Result<String>(feasible_projectService.askOutsourcingPrice(projectid));
    }


    @CrossOrigin
    @PostMapping(value = "/api/querypriceinfo")
    @ResponseBody
    public Result querypriceinfo(@RequestBody JSONObject json) {
        int projectid = -1;
        if (json.getInteger("projectid") == null) {
            log.info("[JSController.querypriceinfo]传入的projectid为空");
        }
        projectid = json.getInteger("projectid");
        Result<Final_price> finalprice = new Result<Final_price>(feasible_projectService.askpriceinfo(projectid));
        if(finalprice == null){
            return new Result("200",true,"未查到projectid为"+projectid+"的报价计算器的计算信息");
        }
        log.info("查询项目ID为：" + projectid + "\n项目报价信息为：" + finalprice.getData().toString());
        return finalprice;
    }


    @CrossOrigin
    @PostMapping(value = "/api/querywbprice")
    @ResponseBody
    public Result<String> querywbprice(@RequestBody JSONObject json) {
        int projectid = -1;
        if (json.getInteger("projectid") != null) {
            projectid = json.getInteger("projectid");
        }
        Result<String> finalprice = new Result<String>(feasible_projectService.askwbprice(projectid));
        log.info("查询项目ID为：" + projectid + "\n外包价格为：" + finalprice.getData().toString());
        return finalprice;
    }

    @CrossOrigin
    @PostMapping(value = "/api/querywbprices")
    @ResponseBody
    public Result<List<String>> querywbprices(@RequestBody JSONObject json) {
        List<Integer> projectIds = new ArrayList<>();
        try{
            JSONArray projectId = json.getJSONArray("projectIds");
            String js=JSONObject.toJSONString(projectId, SerializerFeature.WriteClassName);
            projectIds = JSONObject.parseArray(js,Integer.class);
        }catch(Exception e){
            log.error("[JSController.querywbprices]json解析失败");
        }
        if (projectIds == null) {
            log.error("[JSController.querywbprices]projectIds为空");
        }
        assert projectIds != null;
        return new Result<>(feasible_projectService.queryWbPrices(projectIds));
    }


    @CrossOrigin
    @PostMapping(value = "/api/updatekhfeedback")
    @ResponseBody
    public Result updatekhfeedback(@RequestBody ProjectZt npzt) {
        log.info("更新项目信息为：" + npzt);
        if (project_overviewService.updatekhfeedback(npzt.getProjectid(), npzt.getKhfeedback(), npzt.getKhfeedbackname()) ) {
            return new Result();
        } else {
            return new Result("400", false, "反馈更新有误");
        }
    }

    //updatekhfeedback
    @CrossOrigin
    @PostMapping(value = "/api/updateprojectresult")
    @ResponseBody
    public Result updateprojectresult(@RequestBody ProjectZt npzt) {
        log.info("更新项目信息为：" + npzt);
        if (project_overviewService.updateprojectresult(npzt.getProjectid(), npzt.getProjectresultkf(), npzt.getProjectresultname()) ) {
            return new Result();
        } else {
            return new Result("400", false, "项目结果更新有误");
        }
    }


}
