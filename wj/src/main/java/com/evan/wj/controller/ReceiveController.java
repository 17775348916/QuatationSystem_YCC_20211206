package com.evan.wj.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.evan.wj.pojo.Receive_kh;
import com.evan.wj.result.Result;
import com.evan.wj.service.ReceiveService;
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
public class ReceiveController {
    @Autowired
    ReceiveService receiveService;

    @CrossOrigin
    @PostMapping(value = "/api/updatereceivekh")
    @ResponseBody
    public Result updatereceivekh(@RequestBody Receive_kh newr) {
        log.info("接单项目信息为：" + newr);
        if (receiveService.addreceivekh(newr)) {
            Result r = new Result();
            if(r.isSuccess()) {
                log.info("success");
            }
            return r;
        } else {
            return new Result("400",false,"添加接单信息错误");
        }
    }
    @CrossOrigin
    @PostMapping(value = "/api/updatePriceAndTime")
    @ResponseBody
    public Result<Boolean> updatePriceAndTime(@RequestBody Receive_kh newr) {
        return new Result<>(receiveService.updatePriceAndTime(newr));
    }


    @CrossOrigin
    @PostMapping(value = "/api/updatetestresult")
    @ResponseBody
    public Result updatetestresult(@RequestBody Receive_kh newr) {
        log.info("接单项目更新信息为：" + newr);
        return receiveService.updatetestresult(newr);
    }

    @CrossOrigin
    @PostMapping(value = "/api/queryreceive")
    @ResponseBody
    public Result<List<Receive_kh> > queryreceive(@RequestBody JSONObject json) {
        int projectid = -1;
        if(json.getInteger("projectid") != null) {
            projectid = json.getInteger("projectid");
        } else {
            log.info(json.toJSONString());
            return new Result<>("400",false,"未获得正确的项目编号");
        }
        log.info(projectid + " ");
        List<Receive_kh> receive_khs = receiveService.askreceive(projectid);
        log.info(projectid + "的接单意向情况为\n" + receive_khs.toString());
        return new Result<List<Receive_kh> >(receive_khs);
    }

    @CrossOrigin
    @PostMapping(value = "/api/queryfinalreceive")
    @ResponseBody
    public Result<Receive_kh> queryfinalreceive(@RequestBody JSONObject json) {
        int projectid = -1;
        if(json.getInteger("projectid") != null) {
            projectid = json.getInteger("projectid");
        } else {
            return new Result<>("400",false,"未获得正确的项目编号");
        }
        log.info(projectid + " ");
        Receive_kh receive_khs = receiveService.askfinalreceive(projectid);
        log.info(projectid + "的接单意向情况为\n" + receive_khs.toString());
        return new Result<Receive_kh>(receive_khs);
    }

    @CrossOrigin
    @PostMapping(value = "/api/querytestresult")
    @ResponseBody
    public Result<String> querytestresult(@RequestBody JSONObject json) {
        int projectid = -1;
        if(json.getInteger("projectid") != null) {
            projectid = json.getInteger("projectid");
        } else {
            log.info(json.toJSONString());
            return new Result<>("400",false,"未获得正确的项目编号");
        }
        log.info(projectid + " ");
        String testresult = receiveService.asktestresult(projectid);
        log.info(projectid + "的实验情况为\n" + testresult);
        return new Result<String>(testresult);
    }

    /**
     *批量查询客户接单客户意向（前端/CNeedCommunicateWithTakeOrders）
     * @param json
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/api/queryCustomerIntension")
    @ResponseBody
    public Result<List<Receive_kh>> queryCustomerIntension(@RequestBody JSONObject json){
        List<Integer> projectIds = new ArrayList<>();
        try{
            JSONArray projectId = json.getJSONArray("projectIds");
            String js=JSONObject.toJSONString(projectId, SerializerFeature.WriteClassName);
            projectIds = JSONObject.parseArray(js,Integer.class);
        }catch (Exception e){
            log.error("[JsController.queryfeasibleByProjectIds]json解析失败");
        }
        return new Result<>(receiveService.queryCustomerIntension(projectIds));
    }

    /**
     *批量查询确认接单条件（前端/CNeedCommunicateWithTakeOrders）
     * @param json
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/api/queryCustomerConfirm")
    @ResponseBody
    public Result<List<Receive_kh>> queryCustomerConfirm(@RequestBody JSONObject json){
        List<Integer> projectIds = new ArrayList<>();
        try{
            JSONArray projectId = json.getJSONArray("projectIds");
            String js=JSONObject.toJSONString(projectId, SerializerFeature.WriteClassName);
            projectIds = JSONObject.parseArray(js,Integer.class);
        }catch (Exception e){
            log.error("[JsController.queryfeasibleByProjectIds]json解析失败");
        }
        return new Result<>(receiveService.queryCustomerConfirm(projectIds));
    }

}
