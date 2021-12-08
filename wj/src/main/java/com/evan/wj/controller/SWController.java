package com.evan.wj.controller;

import com.alibaba.fastjson.JSONObject;
import com.evan.wj.pojo.Project_Overview;
import com.evan.wj.result.Result;
import com.evan.wj.service.Account_informationService;
import com.evan.wj.service.Project_OverviewService;
import com.evan.wj.vo.ProjWithTimeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @CrossOrigin
    @PostMapping(value = "/api/SWAddNewProj")
    @ResponseBody
    public Result addnew(@RequestBody Project_Overview newproj){
        if(project_overviewService.addnewproj(newproj)) {
            return new Result("0",true,"返回成功");
        }
        else {
            return new Result("400",false,"添加项目错误");
        }
    }

    @CrossOrigin
    @PostMapping(value = "/api/unzt")
    @ResponseBody
    public Result<List<ProjWithTimeVo> > lookunzt(@RequestBody JSONObject json) {
        int interval = -1;
        if(json.getInteger("interval") != null) {
            interval = json.getInteger("interval");
        } else {
            log.info(json.toJSONString());
            return new Result<>("400",false,"未获得正确的间隔时间");
        }
        List<ProjWithTimeVo> pwt = project_overviewService.queryunzt(interval);
        log.info("查询间隔为" + interval + "\n");
        for(ProjWithTimeVo p : pwt){
            log.info(p.toString());
        }
        System.out.println(pwt.toString());
        return new Result<List<ProjWithTimeVo>>(pwt);
    }

    @CrossOrigin
    @PostMapping(value = "/api/havezt")
    @ResponseBody
    public Result<List<ProjWithTimeVo> > lookhavezt(@RequestBody JSONObject json) {
        int interval = -1;
        if(json.getInteger("interval") != null) {
            interval = json.getInteger("interval");
        } else {
            log.info(json.toJSONString());
            return new Result<>("400",false,"未获得正确的间隔时间");
        }
        String resultkf = json.getString("resultkf");
        List<ProjWithTimeVo> pwt = project_overviewService.queryhavezt(interval,resultkf);
        log.info("查询间隔为" + interval + "\n");
        for(ProjWithTimeVo p : pwt){
            log.info(p.toString());
        }
        return new Result<List<ProjWithTimeVo>>(pwt);
    }

//    /**
//     * 展示包含可行性和非可行性的项目信息
//     * @param json
//     * @return
//     */
//    @CrossOrigin
//    @PostMapping(value = "/api/queryAllInfo")
//    @ResponseBody
//    public Result<List<ProjWithTimeVo> > queryAllInfo(@RequestBody JSONObject json) {
//        int interval = -1;
//        if(json.getInteger("interval") != null) {
//            interval = json.getInteger("interval");
//        } else {
//            log.info(json.toJSONString());
//            return new Result<>("400",false,"未获得正确的间隔时间");
//        }
//        String resultkf = json.getString("resultkf");
//        List<ProjWithTimeVo> pwt = project_overviewService.queryhavezt(interval,resultkf);
//        log.info("查询间隔为" + interval + "\n");
//        for(ProjWithTimeVo p : pwt){
//            log.info(p.toString());
//        }
//        return new Result<List<ProjWithTimeVo>>(pwt);
//    }

    @CrossOrigin
    @PostMapping(value = "/api/unfinish")
    @ResponseBody
    public Result<List<ProjWithTimeVo> > lookunfinish(@RequestBody JSONObject json) {
        int interval = -1;
        if(json.getInteger("interval") != null) {
            interval = json.getInteger("interval");
        } else {
            log.info(json.toJSONString());
            return new Result<>("400",false,"未获得正确的间隔时间");
        }
//        int interval = json.getInteger("interval");
        List<ProjWithTimeVo> pwt = project_overviewService.queryunfinish(interval);
        log.info("查询间隔为" + interval + "\n");
        for(ProjWithTimeVo p : pwt){
            log.info(p.toString());
        }
        return new Result<List<ProjWithTimeVo>>(pwt);
    }

    @CrossOrigin
    @PostMapping(value = "/api/allproj")
    @ResponseBody
    public Result<List<ProjWithTimeVo> > lookallproj() {
        log.info("查询所有项目 ");
        List<ProjWithTimeVo> pwt = project_overviewService.queryall();
        log.info("项目信息为：");
        for(ProjWithTimeVo p : pwt){
            log.info(p.toString());
        }
        return new Result<List<ProjWithTimeVo>>(pwt);
    }

    @CrossOrigin
    @PostMapping(value = "/api/havefinish")
    @ResponseBody
    public Result<List<ProjWithTimeVo> > lookhavefinish(@RequestBody JSONObject json) {
        int interval = -1;
        if(json.getInteger("interval") != null) {
            interval = json.getInteger("interval");
        } else {
            log.info(json.toJSONString());
            return new Result<>("400",false,"未获得正确的间隔时间");
        }
//        int interval = json.getInteger("interval");
        List<ProjWithTimeVo> pwt = project_overviewService.queryhavefinish(interval);
        log.info("查询间隔为" + interval + "\n");
        for(ProjWithTimeVo p : pwt){
            log.info(p.toString());
        }
        return new Result<List<ProjWithTimeVo>>(pwt);
    }

    @CrossOrigin
    @PostMapping(value = "/api/unreceive")
    @ResponseBody
    public Result<List<ProjWithTimeVo> > lookunreceive(@RequestBody JSONObject json) {
        int interval = -1;
        if(json.getInteger("interval") != null) {
            interval = json.getInteger("interval");
        } else {
            log.info(json.toJSONString());
            return new Result<>("400",false,"未获得正确的间隔时间");
        }
//        int interval = json.getInteger("interval");
        List<ProjWithTimeVo> pwt = project_overviewService.queryunreceive(interval);
        log.info("查询间隔为" + interval + "\n");
        for(ProjWithTimeVo p : pwt){
            log.info(p.toString());
        }
        return new Result<List<ProjWithTimeVo>>(pwt);
    }

    @CrossOrigin
    @PostMapping(value = "/api/havereceive")
    @ResponseBody
    public Result<List<ProjWithTimeVo> > lookhavereceive(@RequestBody JSONObject json) {
        int interval = -1;
        if(json.getInteger("interval") != null) {
            interval = json.getInteger("interval");
        } else {
            log.info(json.toJSONString());
            return new Result<>("400",false,"未获得正确的间隔时间");
        }
//        int interval = json.getInteger("interval");
        List<ProjWithTimeVo> pwt = project_overviewService.queryhavereceive(interval);
        log.info("查询间隔为" + interval + "\n");
        for(ProjWithTimeVo p : pwt){
            log.info(p.toString());
        }
        return new Result<List<ProjWithTimeVo>>(pwt);
    }
}
