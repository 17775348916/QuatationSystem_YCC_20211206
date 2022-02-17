package com.evan.wj.service;

import com.alibaba.fastjson.JSONObject;
import com.evan.wj.dao.ProjectZtDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ycc.yaochaochao
 * @version 1.0
 * @date 2022/1/21 3:16 下午
 */
@Service
@Slf4j
public class Project_ZtService {
    @Autowired
    ProjectZtDAO projectZtDAO;
    public String findNumByStatusAndInterval(String status, int interval) {
        Map<String, Integer> map = new HashMap<>();
        int noProvideMission=0,provideMission=0;
        noProvideMission = projectZtDAO.findNoProvideNumByStatusAndInterval(status,interval);
        provideMission = projectZtDAO.findProvideNumByStatusAndInterval(status,interval);
        map.put("noProvideNum",noProvideMission);
        map.put("provideNum",provideMission);
        return JSONObject.toJSONString(map);
    }
}
