package com.evan.wj.service;

import com.evan.wj.dao.Feasible_projectDAO;
import com.evan.wj.dao.Project_OverviewDAO;
import com.evan.wj.pojo.Feasible_project;
import com.evan.wj.pojo.Project_Overview;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class FileService {
    @Autowired
    Project_OverviewDAO project_overviewDAO;
    @Autowired
    Feasible_projectDAO feasibleProjectDAO;

    @Value("${web.upload-path}")
    private String pfolder;

    public void morefile(String url, int projectid, boolean flag) {
        File dir = new File(pfolder + "/" + projectid);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdir();
        }
        String folder = pfolder + "/" + projectid + "/";
        File oripic = new File(pfolder + "/" + url);
        oripic.renameTo(new File(folder + url));
        log.info("移动：" + pfolder + "/" + url + "\n" + folder + "/" + url);

        if(flag) {
            Project_Overview proj = project_overviewDAO.findByProjectid(projectid);
            /**
             * 存放图片的时候是/projectid+url
             */
            proj.setProjectdetails(String.valueOf(projectid) + "/" + url);

            project_overviewDAO.save(proj);
        } else {
            Feasible_project fproj = feasibleProjectDAO.findByProjectid(projectid);
            /**
             * 原来是存入/projectid+url，现在更改为url
             */
            fproj.setPapersjs(url);
            feasibleProjectDAO.save(fproj);
        }
    }
}
