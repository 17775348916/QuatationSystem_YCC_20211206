package com.evan.wj.controller;

import com.evan.wj.dao.Quoting_modelDAO;
import com.evan.wj.pojo.Quoting_model;
import com.evan.wj.result.Result;
import com.evan.wj.service.Quoting_modelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuotingController {
    @Autowired
    Quoting_modelService quoting_modelService;

    @CrossOrigin
    @PostMapping(value = "/api/lookmodel")
    @ResponseBody
    public Result<List<Quoting_model>> lookall() {
        return new Result<List<Quoting_model>>("200", true, quoting_modelService.query(), "查询所有询价模型");
    }

    @CrossOrigin
    @PostMapping(value = "/api/updatemodel")
    @ResponseBody
    public Result update(@RequestBody List<Quoting_model> newmodel) {
        quoting_modelService.save(newmodel);
        return new Result();
    }
    
}
