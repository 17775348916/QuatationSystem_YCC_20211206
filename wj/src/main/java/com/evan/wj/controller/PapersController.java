package com.evan.wj.controller;

import com.evan.wj.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ycc.yaochaochao
 * @version 1.0
 * @date 2021/12/5 4:27 下午
 */
@Controller
@Slf4j
public class PapersController {
    @Value("${web.upload-path}")
    private String folder;
    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    @CrossOrigin
    @PostMapping("/api/upload")
    @ResponseBody
    public Result<Map<String, Object>> fileUpload(@RequestBody MultipartFile file, HttpServletRequest req) {
        Map<String, Object> result = new HashMap<>();
        String originalFilename = file.getOriginalFilename();
        if(originalFilename != null){
            String format = "/pdf" + sdf.format(new Date());
            String realPath = folder + format;
            System.out.println(realPath);
            File folder = new File(realPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String newName = UUID.randomUUID().toString() + "&&" + originalFilename;
            try {
                file.transferTo(new File(folder, newName));
                String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + format + newName;
                result.put("status", "success");
                result.put("url", url);
                result.put("originalName",originalFilename);
            } catch (IOException e) {
                result.put("status", "error");
                result.put("msg", e.getMessage());
                e.printStackTrace();
            }
        }

        return new Result<Map<String, Object>>(result);
    }
}
