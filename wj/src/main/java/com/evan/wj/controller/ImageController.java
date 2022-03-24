package com.evan.wj.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.evan.wj.result.Result;
import com.evan.wj.util.StringUtils;
import com.evan.wj.vo.FileVo;
import com.evan.wj.vo.ImgVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
class ImageController {

    @Value("${web.upload-path}")
    private String folder;
    @CrossOrigin
    @PostMapping("api/imgupload")
    @ResponseBody
    public Result<FileVo> coversUpload(@RequestBody MultipartFile file) throws Exception {
        String originname = file.getOriginalFilename();
        String filetype = originname.substring(originname.lastIndexOf("."));
        File imageFolder = new File(folder);
        File f = new File(imageFolder, StringUtils.getRandomString(9) + filetype);
        log.info("新图片保存在：" + f.getAbsolutePath());
        if (!f.getParentFile().exists()){
            f.getParentFile().mkdirs();
        }
        try {
            file.transferTo(f);
            String imgURL = f.getName();
            log.info(f.getAbsolutePath() + file.toString());
            return new Result<FileVo>(new FileVo(imgURL, originname));
        } catch (IOException e) {
            e.printStackTrace();
            return new Result<FileVo>("400", false, new FileVo(folder, e.toString()), "文件保存失败");
        }
    }

    @CrossOrigin
    @PostMapping("api/lookpic")
    @ResponseBody
    public  Result<ImgVo> imgTrans(@RequestBody ImgVo img) throws IOException{
        String fullpath = folder + "/" + img.getImgpath();
        log.info("读取文件路径：" + fullpath);
        try {
            FileInputStream fis = new FileInputStream(fullpath);
            byte[] rs = new byte[fis.available()];
            fis.read(rs);
            fis.close();
            img.setBase64id(rs);
            return new Result<ImgVo>(img);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result<ImgVo>("400", false, new ImgVo(), "图片打开失败");
        }
    }

    /**
     * 批量转换图片信息
     * @param json
     * @return
     * @throws IOException
     */
    @CrossOrigin
    @PostMapping("api/lookpics")
    @ResponseBody
    public  Result<List<byte[]>> imgTransLis(@RequestBody JSONObject json) throws IOException{
        JSONArray imgs = json.getJSONArray("imgpath");
        List<byte[]> resultList= new ArrayList<>();
        for( Object img : imgs){
            String fullpath = folder + "/" + img;
            log.info("读取文件路径：" + fullpath);
            try {
                FileInputStream fis = new FileInputStream(fullpath);
                byte[] rs = new byte[fis.available()];
                fis.read(rs);
                fis.close();
//                img.setBase64id(rs);
                resultList.add(rs);
            } catch (IOException e) {
                e.printStackTrace();
                resultList.add(new byte[200]);
                log.error("[ImageController.imgTransLis]中"+img+"图片打开失败");
            }
        }
        return new Result<>(resultList);
    }
//    public void MoveFile(String filename, int projectid) {
//
//    }

//    @CrossOrigin
//    @PostMapping("api/uploadfile")
//    @ResponseBody
//    public Result<String> fileUpload(@RequestBody MultipartFile file) throws Exception {
////        MultipartFile file = json.getObject("file", MultipartFile.class);
////        int projectid = json.getInteger("projectid");
//        String folder = "D:/QuatationSystem/prjmng/static/file/proj/"; // + projectid;
//        File imageFolder = new File(folder);
//        File f = new File(imageFolder, StringUtils.getRandomString(9) + file.getOriginalFilename());
//        if (!f.getParentFile().exists())
//            f.getParentFile().mkdirs();
//        try {
//            file.transferTo(f);
//            String imgURL = f.getName();
//            log.info(f.getAbsolutePath() + "\n"+ file.toString());
////            return imgURL;
//            return new Result<String>(imgURL);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new Result<>("400", false, "文件保存失败");
//        }
//    }
}
