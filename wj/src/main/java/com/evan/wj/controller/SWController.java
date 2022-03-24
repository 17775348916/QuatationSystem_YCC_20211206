package com.evan.wj.controller;

import com.alibaba.fastjson.JSONObject;
import com.evan.wj.pojo.Project_Overview;
import com.evan.wj.result.Result;
import com.evan.wj.service.Account_informationService;
import com.evan.wj.service.Project_OverviewService;
import com.evan.wj.service.Project_ZtService;
import com.evan.wj.vo.ProjWithTimeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@Slf4j
public class SWController {
    @Autowired
    Project_OverviewService project_overviewService;
    @Autowired
    Account_informationService account_informationService;

    @Autowired
    Project_ZtService project_ztService;

    @CrossOrigin
    @PostMapping(value = "/api/SWAddNewProj")
    @ResponseBody
    public Result addnew(@RequestBody Project_Overview newproj) {
        if (project_overviewService.addnewproj(newproj)) {
            return new Result("0", true, "返回成功");
        } else {
            return new Result("400", false, "添加项目错误");
        }
    }

    /**
     * 查询尚不可行的项目信息
     * @param json
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/api/showUnavailables")
    @ResponseBody
    public Result<Page<Project_Overview>> showUnavailablesPage(@RequestBody JSONObject json) {
        int page = 1, size = 5,interval = -1;
        Page<Project_Overview> projectOverviews = null;
        if (json.getInteger("page") == null || json.getInteger("size") == null
                || json.getInteger("interval") == null) {
            log.error("[SWController.showUnavailables]:json中的page或size或interval为空");
            return null;
        }
        page=json.getInteger("page")-1;
        size=json.getInteger("size");
        interval = json.getInteger("interval");
        Page<Project_Overview> projects = project_overviewService.showUnavailablesPage(page, size, interval);
        return new Result<>(projects);
    }


    /**
     * 未提供以及提供的原料信息任务数量
     * @param json
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/api/materialMissionNum")
    @ResponseBody
    public Result<String> materialMissionNum(@RequestBody JSONObject json) {
        String status = "已评估";
        int interval=-1;
        if(json.getString("status") == null || json.getInteger("interval") == null){
            log.error("[SWController.materialMissionNum]中的status活着interval前端未传");
            return null;
        }
        status=json.getString("status");
        interval = json.getInteger("interval");
        return new Result<String>(project_ztService.findNumByStatusAndInterval(status,interval));
    }


    @CrossOrigin
    @PostMapping(value = "/api/unzt")
    @ResponseBody
    public Result<List<ProjWithTimeVo>> lookunzt(@RequestBody JSONObject json) {
        int interval = -1;
        if (json.getInteger("interval") != null) {
            interval = json.getInteger("interval");
        } else {
            log.info(json.toJSONString());
            return new Result<>("400", false, "未获得正确的间隔时间");
        }
        List<ProjWithTimeVo> pwt = project_overviewService.queryunzt(interval);
        log.info("查询间隔为" + interval + "\n");
        for (ProjWithTimeVo p : pwt) {
            log.info(p.toString());
        }
        System.out.println(pwt.toString());
        return new Result<List<ProjWithTimeVo>>(pwt);
    }



    @CrossOrigin
    @PostMapping(value = "/api/havezt")
    @ResponseBody
    public Result<List<ProjWithTimeVo>> lookhavezt(@RequestBody JSONObject json) {
        int interval = -1;
        if (json.getInteger("interval") != null) {
            interval = json.getInteger("interval");
        } else {
            log.info(json.toJSONString());
            return new Result<>("400", false, "未获得正确的间隔时间");
        }
        String resultkf = json.getString("resultkf");
        List<ProjWithTimeVo> pwt = project_overviewService.queryhavezt(interval, resultkf);
        log.info("查询间隔为" + interval + "\n");
        for (ProjWithTimeVo p : pwt) {
            log.info(p.toString());
        }
        return new Result<List<ProjWithTimeVo>>(pwt);
    }

    /**
     * 分页查询, 根据成交结果，已评估的当前时间段内的项目.默认返回已评估成交的项目
     *
     * @param json
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/api/evaluatedPage")
    @ResponseBody
    public Result<Page<Project_Overview>> evaluatedPage(@RequestBody JSONObject json) {
        int page = 1, size = 10, interval = -1;
        String status = "已评估", resultkf = "成交";
        Page<Project_Overview> projectOverviews = null;
        if (json.getInteger("page") == null || json.getInteger("size") == null ||
                json.getInteger("interval") == null) {
            log.error("[SWController.evaluatedPage]:json中的page或size或interval为空");
            return null;
        }
        if (json.getString("status") == null || json.getString("resultkf") == null) {
            log.error("[SWController.evaluatedPage]:json中的status或resultkf为空");
            return null;
        }
        page = json.getInteger("page")-1;
        size = json.getInteger("size");
        interval = json.getInteger("interval");
        status = json.getString("status");
        resultkf = json.getString("resultkf");
        if ("所有".equals(resultkf)) {
            projectOverviews = project_overviewService.findByIntervalAndStatus(page, size, interval, status);
        } else {
            projectOverviews = project_overviewService.findByIntervalAndStatusAndResult(page,size,interval,status,resultkf);
        }
        return new Result<>(projectOverviews);
    }


    /**
     * 分页返回 当前状态下（未评估和已评估,默认为未评估） 的时间间隔内的项目
     *
     * @param json
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/api/unevaluatedPage")
    @ResponseBody
    public Result<Page<Project_Overview>> unevaluatedPage(@RequestBody JSONObject json) {
        int page = 1, size = 10, interval = -1;
        String status = "未评估";
        Page<Project_Overview> projectOverviews = null;
        if (json.getInteger("page") == null) {
            log.error("page为空");
        } else {
            if (json.getInteger("size") == null) {
                log.error("size为空");
            } else {
                if (json.getInteger("interval") == null) {
                    log.error("interval为空");
                } else {
                    if (json.getString("status") == null) {
                        log.error("[SWController.unevaluatedPage]中的json未传status");
                    } else {
                        page = json.getInteger("page")-1;
                        size = json.getInteger("size");
                        interval = json.getInteger("interval");
                        status = json.getString("status");
                        projectOverviews = project_overviewService.findByIntervalAndStatus(page, size, interval, status);
                    }

                }
            }
        }
        return new Result<>(projectOverviews);
    }


    @CrossOrigin
    @PostMapping(value = "/api/unfinish")
    @ResponseBody
    public Result<List<ProjWithTimeVo>> lookunfinish(@RequestBody JSONObject json) {
        int interval;
        if (json.getInteger("interval") != null) {
            interval = json.getInteger("interval");
        } else {
            log.info(json.toJSONString());
            return new Result<>("400", false, "未获得正确的间隔时间");
        }
        List<ProjWithTimeVo> pwt = project_overviewService.queryunfinish(interval);
        log.info("查询间隔为" + interval + "\n");
        for (ProjWithTimeVo p : pwt) {
            log.info(p.toString());
        }
        return new Result<List<ProjWithTimeVo>>(pwt);
    }

    @CrossOrigin
    @PostMapping(value = "/api/allproj")
    @ResponseBody
    public Result<Page<Project_Overview>> lookallproj(@RequestBody JSONObject json) {
        int page = 1, size = 5, interval = -1;
        String resultkf = "成交";
        Page<Project_Overview> pwt = null;
        if (json.getInteger("page") == null && json.getInteger("size") == null &&
                json.getString("resultkf") == null && json.getInteger("interval") == null) {
            log.error("[SWController.lookallproj]:json中的page、size、resultkf、interval皆为空");
            return null;
        }
        page=json.getInteger("page")-1;
        size=json.getInteger("size");
        resultkf = json.getString("resultkf");
        if(null == json.getInteger("interval")){
            interval = 30000;
        } else{
            interval = json.getInteger("interval");
        }
        if("所有".equals(resultkf)){
            pwt = project_overviewService.queryallByInterval(page,size,interval);
        }else{
            pwt = project_overviewService.queryallByIntervalAndResultkf(page,size,interval,resultkf);
        }
        return new Result<>(pwt);
    }

    /**
     * 管理员界面下的多条件分页查询
     * @param json
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/api/queryByKeywords")
    @ResponseBody
    public Result<Page<Project_Overview>> queryByKeywords(@RequestBody JSONObject json) {
        int page = 1, size = 5, interval = -1;
        String resultkf = "成交";
        String customerName = "", productName = "", casName = "", khName = "",startTime = "", endTime = "";
        Page<Project_Overview> pwt = null;
        if (json.getInteger("page") == null && json.getInteger("size") == null &&
                json.getString("resultkf") == null && json.getInteger("interval") == null) {
            log.error("[SWController.lookallproj]:json中的page、size、resultkf、interval皆为空");
            return null;
        }
        page=json.getInteger("page")-1;
        size=json.getInteger("size");
        resultkf = json.getString("resultkf");
        customerName = json.getString("customerName");
        productName = json.getString("productName");
        casName = json.getString("casName");
        khName = json.getString("khName");
        endTime = json.getString("endTime");
        startTime = json.getString("startTime");
        if(null == json.getInteger("interval")){
            interval = 30000;
        } else{
            interval = json.getInteger("interval");
        }
        pwt = project_overviewService.queryallByIntervalAndResultkfAndKhrynameAndProjectnameAndCas(page,size,interval,resultkf,customerName,productName,casName,khName,startTime,endTime);
        return new Result<>(pwt);
    }
//    @CrossOrigin
//    public static void main(String[] args) {
//        int interval = -1;
//        JSONObject json = new JSONObject();
//        json.put("interval","");
////        interval = json.getstring("interval");
//        System.out.println(StringUtils.isEmpty(json.getString("interval")));
//    }

    @CrossOrigin
    @PostMapping(value = "/api/finishContactPage")
    @ResponseBody
    public Result<Page<Project_Overview>> finishContactPage(@RequestBody JSONObject json) {
        int page = 1, size = 10, interval = -1;
        String status = "已评估", resultkf = "待定";
        Page<Project_Overview> projectOverviews = null;
        if (json.getInteger("page") == null || json.getInteger("size") == null ||
                json.getInteger("interval") == null) {
            log.error("[SWController.finishContactPage]:json中的page或size或interval为空");
            return null;
        }
        if (json.getString("status") == null || json.getString("resultkf") == null) {
            log.error("[SWController.finishContactPage]:json中的status或resultkf为空");
            return null;
        }
        page = json.getInteger("page")-1;
        size = json.getInteger("size");
        interval = json.getInteger("interval");
        status = json.getString("status");
        resultkf = json.getString("resultkf");
        projectOverviews = project_overviewService.findByIntervalAndStatusAndNotByGivingResult(page, size, interval, status, resultkf);
        return new Result<>(projectOverviews);
    }

    @CrossOrigin
    @PostMapping(value = "/api/havefinish")
    @ResponseBody
    public Result<List<ProjWithTimeVo>> lookhavefinish(@RequestBody JSONObject json) {
        int interval = -1;
        if (json.getInteger("interval") != null) {
            interval = json.getInteger("interval");
        } else {
            log.info(json.toJSONString());
            return new Result<>("400", false, "未获得正确的间隔时间");
        }
        List<ProjWithTimeVo> pwt = project_overviewService.queryhavefinish(interval);
        log.info("查询间隔为" + interval + "\n");
        for (ProjWithTimeVo p : pwt) {
            log.info(p.toString());
        }
        return new Result<List<ProjWithTimeVo>>(pwt);
    }



    @CrossOrigin
    @PostMapping(value = "/api/unreceive")
    @ResponseBody
    public Result<Page<Project_Overview>> lookunreceive(@RequestBody JSONObject json) {
        int interval = -1,page=1,size=5;
        if (json.getInteger("interval") != null && json.getInteger("page")!=null && json.getInteger("size")!=null) {
            page=json.getInteger("page")-1;
            size=json.getInteger("size");
            interval = json.getInteger("interval");
        } else {
            log.info(json.toJSONString());
            return new Result<>("400", false, "[SWController.lookunreceive]前端传入信息有误");
        }
        Page<Project_Overview> pwt = project_overviewService.findByInterval(page, size, interval);
        return new Result<>(pwt);
    }

    @CrossOrigin
    @PostMapping(value = "/api/havereceive")
    @ResponseBody
    public Result<Page<Project_Overview>> lookhavereceive(@RequestBody JSONObject json) {
        int interval = -1,page=1,size=5;
        if (json.getInteger("interval") != null && json.getInteger("page")!=null && json.getInteger("size")!=null) {
            interval = json.getInteger("interval");
            page = json.getInteger("page")-1;
            size = json.getInteger("size");
        } else {
            log.info(json.toJSONString());
            return new Result<>("400", false, "未获得正确的间隔时间");
        }
        Page<Project_Overview> pwt = project_overviewService.findByIntervalAlreadyFilledTestResult(page, size, interval);
        return new Result<>(pwt);
    }

    /**
     * 根据给定的projectId查看项目信息
     * @param json
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/api/queryProjectOverviewByProjectId")
    @ResponseBody
    public Result<Project_Overview> queryProjectOverviewByProjectId(@RequestBody JSONObject json){
        int projectId = -1;
        if (json.getInteger("projectid") == null){
            log.error("[SWController.queryProjectOverviewByProjectId]前端未传projectid");
            return new Result<>("400",false,"未获得正确的projectid");
        }
        projectId=json.getInteger("projectid");
        Project_Overview project = project_overviewService.queryProjectOverviewByProjectId(projectId);
        return new Result<>(project);
    }
}
